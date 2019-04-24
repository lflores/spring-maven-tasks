import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';

import {Task} from '../task';
import {TaskService} from '../task.service';

@Component({
    selector: 'app-task-detail',
    templateUrl: './task-detail.component.html',
    styleUrls: ['./task-detail.component.css']
})
export class TaskDetailComponent implements OnInit {
    @Input() task: Task;
    statuses: string[] = ['todo', 'in-progress', 'done'];
    compareFn: ((f1: any, f2: any) => boolean) | null = this.compareByValue;
    compareByValue(f1: any, f2: any) {
        return f1 && f2 && f1.value === f2.value;
    }

    constructor(
        private route: ActivatedRoute,
        private taskService: TaskService,
        private location: Location
    ) {
    }

    ngOnInit(): void {
        this.getTask();
    }

    getTask(): void {
        const id = +this.route.snapshot.paramMap.get('id');
        this.taskService.getTask(id)
            .subscribe(task => this.task = task);
    }

    goBack(): void {
        this.location.back();
    }

    save(): void {
        this.taskService.updateTask(this.task)
            .subscribe(() => this.goBack());
    }
}
