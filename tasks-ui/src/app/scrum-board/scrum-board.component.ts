import {Component, Inject, OnInit} from '@angular/core';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import {TaskService} from '../task.service';
import {Task} from '../task';

import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';

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
        const dialogRef = this.dialog.open(DialogTask, {
            width: '450px',
            data: {description: this.description},
            panelClass: "task-dialog"
        });
        dialogRef.disableClose = true;

        dialogRef.afterClosed().subscribe(result => {
            if (!result) {
                return;
            }
            let task: Task = new Task();
            task.description = result.description;
            task.image = result.image;
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
        const dialogRef = this.dialog.open(DialogTask, {
            width: '450px',
            data: task,
            panelClass: "task-dialog"
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
    selector: 'dialog-task',
    templateUrl: 'dialog-task.html'
})
export class DialogTask {
    constructor(
        public dialogRef: MatDialogRef<DialogTask>,
        @Inject(MAT_DIALOG_DATA) public task: Task,
        public taskService: TaskService) {
    }

    selectFile(file: File): void {
        this.taskService.uploadImage(file)
            .subscribe(imageFile =>
                this.task.image = imageFile.fileDownloadUri
            );
    }

    openFile(event: any): void {
        event.preventDefault();
        let element:HTMLElement = document.getElementById("uploadPicture") as HTMLElement;
        element.click();
    }

    close(): void {
        this.dialogRef.close(this.task);
    }

    onNoClick(): void {
        this.dialogRef.close();
    }
}
