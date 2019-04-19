import {Component, OnInit} from '@angular/core';
// import { Hero } from '../hero';
import {Task} from '../task';
// import { HeroService } from '../hero.service';
import {TaskService} from '../task.service';

@Component({
    selector: 'app-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
    // heroes: Hero[] = [];
    tasks: Task[] = [];

    // constructor(private heroService: HeroService) { }
    constructor(private taskService: TaskService) {
    }

    ngOnInit() {
        // this.getHeroes();
        this.getTasks();
    }

    // getHeroes(): void {
    //   this.heroService.getHeroes()
    //     .subscribe(heroes => this.heroes = heroes.slice(1, 5));
    // }

    getTasks(): void {
        this.taskService.getTasks()
            .subscribe(tasks => this.tasks = tasks.slice(1, 5));
    }
}
