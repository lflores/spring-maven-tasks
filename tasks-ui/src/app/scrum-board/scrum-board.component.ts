import {Component, OnInit} from '@angular/core';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import {TaskService} from '../task.service';
import {Task} from '../task';

@Component({
    selector: 'app-scrum-board',
    templateUrl: './scrum-board.component.html',
    styleUrls: ['./scrum-board.component.css']
})
export class ScrumBoardComponent implements OnInit {
    todo: Task[] = [];
    inProgress: Task[] = [];
    done: Task[] = [];
    updating: Object[] = [];
    timeoutID;

    drop(event: CdkDragDrop<string[]>) {
        if (event.previousContainer === event.container) {
            moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
        } else {
            transferArrayItem(event.previousContainer.data,
                event.container.data,
                event.previousIndex,
                event.currentIndex);
            let toUpdate = event.container.data[event.currentIndex];
            toUpdate['changed'] = true;
            toUpdate['status'] = event.container.id;
            this.updating.push(toUpdate);
            if (this.timeoutID) {
                clearTimeout(this.timeoutID);
            }
            this.timeoutID = setTimeout(() => {
                this.saveTasks();
            }, 2000);
        }
    }

    constructor(private taskService: TaskService) {
    }

    ngOnInit() {
        this.getTasks();
    }

    saveTasks(): void {
        console.log('Updating task: ' + this.updating[0]['description']);
        let task = new Task();
        task.id = this.updating[0]['id'];
        task.status = this.updating[0]['status'];
        this.taskService.updateTask(task).subscribe(task => {
                this.updating[0]['changed'] = false;
                this.updating.shift();
                if (this.updating.length > 0) {
                    this.saveTasks();
                }
            }
        );
    }
    create;

    addTask():void{
        if(this.create){
            return;
        }
        this.create = {
            description: "<complete>",
            status: "todo",
        }
        this.todo.unshift(this.create);
    }

    getTasks(): void {
        this.taskService.getTasksByStatus('todo')
            .subscribe(tasks => this.todo = tasks);
        this.taskService.getTasksByStatus('in-progress')
            .subscribe(tasks => this.inProgress = tasks);
        this.taskService.getTasksByStatus('done')
            .subscribe(tasks => this.done = tasks);
    }
}
