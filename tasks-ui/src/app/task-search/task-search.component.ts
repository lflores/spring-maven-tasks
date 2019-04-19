import {Component, OnInit} from '@angular/core';

import {Observable, Subject} from 'rxjs';

import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';

import {Task} from '../task';
import {TaskService} from '../task.service';

@Component({
    selector: 'app-task-search',
    templateUrl: './task-search.component.html',
    styleUrls: ['./task-search.component.css']
})
export class TaskSearchComponent implements OnInit {
    tasks$: Observable<Task[]>;
    private searchTerms = new Subject<string>();

    constructor(private taskService: TaskService) {
    }

    // Push a search term into the observable stream.
    search(term: string): void {
        this.searchTerms.next(term);
    }

    ngOnInit(): void {
        this.tasks$ = this.searchTerms.pipe(
            // wait 300ms after each keystroke before considering the term
            debounceTime(300),

            // ignore new term if same as previous term
            distinctUntilChanged(),

            // switch to new search observable each time the term changes
            switchMap((term: string) => this.taskService.searchTasks(term)),
        );
    }
}
