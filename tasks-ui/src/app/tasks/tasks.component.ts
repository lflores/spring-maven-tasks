import {Component, OnInit} from '@angular/core';

import {Task} from '../task';
import {TaskService} from '../task.service';

@Component({
    selector: 'app-tasks',
    templateUrl: './tasks.component.html',
    styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {
    tasks: Task[];

    constructor(private taskService: TaskService) {
    }

    ngOnInit() {
        this.getTasks();
    }

    getTasks(): void {
        this.taskService.getTasks()
            .subscribe(tasks => this.tasks = tasks);
    }

    add(description: string): void {
        description = description.trim();
        if (!description) {
            return;
        }
        this.taskService.addTask({description} as Task)
            .subscribe(task => {
                this.tasks.push(task);
            });
    }

    delete(task: Task): void {
        this.tasks = this.tasks.filter(h => h !== task);
        this.taskService.deleteTask(task).subscribe();
    }
}
