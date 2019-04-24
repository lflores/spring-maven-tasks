import {Component, OnInit} from '@angular/core';

import {Task} from '../task';
import {TaskService} from '../task.service';

@Component({
    selector: 'app-task-add',
    templateUrl: './task-add.component.html',
    styleUrls: ['./task-add.component.css']
})
export class TaskAddComponent implements OnInit {
    tasks: Task[];

    constructor(private taskService: TaskService) {
    }

    ngOnInit() {
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
}
