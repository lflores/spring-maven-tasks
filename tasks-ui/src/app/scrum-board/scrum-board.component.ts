import {Component, Inject, OnInit} from '@angular/core';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import {TaskService} from '../task.service';
import {Task} from '../task';

import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';

export interface DialogData {
    animal: string;
    name: string;
}

@Component({
    selector: 'app-scrum-board',
    templateUrl: './scrum-board.component.html',
    styleUrls: ['./scrum-board.component.css']
})
export class ScrumBoardComponent implements OnInit {
    task: Task;
    description: string;
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

    constructor(private taskService: TaskService, public dialog: MatDialog) {
    }

    ngOnInit() {
        this.getTasks();
    }

    openAddDialog(): void {
        const dialogRef = this.dialog.open(DialogAddTask, {
            width: '450px',
            data: {description: this.description}
        });
        dialogRef.disableClose = true;

        dialogRef.afterClosed().subscribe(result => {
            if (!result) {
                return;
            }
            this.description = result;
            let task: Task = new Task();
            task.description = this.description;
            task.status = 'todo';
            this.taskService.addTask(task).subscribe(task => {
                this.description = null;
                this.taskService.getTasksByStatus('todo')
                    .subscribe(tasks => this.todo = tasks);
            });
        });
    }

    editTask(task: Task): void {
        this.task = task;
        const dialogRef = this.dialog.open(DialogEditTask, {
            width: '450px',
            data: {
                description: task.description,
                image: task.image,
                status: task.status
            }
        });

        dialogRef.afterClosed().subscribe(result => {
            if (!result) {
                return;
            }
            this.task.description = result.description;
            this.task.image = result.image;
            this.taskService.updateTask(task).subscribe(task => {
                this.taskService.getTasksByStatus('todo')
                    .subscribe(tasks => this.todo = tasks);
            });
        });
    }

    saveTasks(): void {
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

    getTasks(): void {
        this.taskService.getTasksByStatus('todo')
            .subscribe(tasks => this.todo = tasks);
        this.taskService.getTasksByStatus('in-progress')
            .subscribe(tasks => this.inProgress = tasks);
        this.taskService.getTasksByStatus('done')
            .subscribe(tasks => this.done = tasks);
    }
}

@Component({
    selector: 'dialog-add-task',
    templateUrl: 'dialog-add-task.html',
})
export class DialogAddTask {
    constructor(
        public dialogRef: MatDialogRef<DialogAddTask>,
        @Inject(MAT_DIALOG_DATA) public task: Task) {
    }

    onNoClick(): void {
        this.dialogRef.close();
    }
}

@Component({
    selector: 'dialog-edit-task',
    templateUrl: 'dialog-edit-task.html',
})
export class DialogEditTask {
    constructor(
        public dialogRef: MatDialogRef<DialogEditTask>,
        @Inject(MAT_DIALOG_DATA) public task: Task) {
    }

    onNoClick(): void {
        this.dialogRef.close();
    }

    save(): void {
        this.dialogRef.close(this.task);
    }
}
