import { InMemoryDbService } from 'angular-in-memory-web-api';
// import { Hero } from './hero';
import { Task } from './task';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    // const heroes = [
    //   { id: 11, name: 'Mr. Nice' },
    //   { id: 12, name: 'Narco' },
    //   { id: 13, name: 'Bombasto' },
    //   { id: 14, name: 'Celeritas' },
    //   { id: 15, name: 'Magneta' },
    //   { id: 16, name: 'RubberMan' },
    //   { id: 17, name: 'Dynama' },
    //   { id: 18, name: 'Dr IQ' },
    //   { id: 19, name: 'Magma' },
    //   { id: 20, name: 'Tornado' }
    // ];
    // return {heroes};

    const tasks = [
      {id: 11, description: 'Task 11', image: 'myimage.gif', resolved: false},
      {id: 12, description: 'Task 12', image: 'myimage.gif', resolved: false},
      {id: 13, description: 'Task 13', image: 'myimage.gif', resolved: true},
      {id: 14, description: 'Task 14', image: 'myimage.gif', resolved: false},
      {id: 15, description: 'Task 15', image: 'myimage.gif', resolved: false},
      {id: 16, description: 'Task 16', image: 'your-image.gif', resolved: true},
      {id: 17, description: 'Task 17', image: 'myimage.gif', resolved: false},
      {id: 18, description: 'Task 18', image: 'myimage.gif', resolved: false},
      {id: 19, description: 'Task 19', image: 'myimage.gif', resolved: false}
    ];
    return {tasks};
  }

  // Overrides the genId method to ensure that a hero always has an id.
  // If the heroes array is empty,
  // the method below returns the initial number (11).
  // if the heroes array is not empty, the method below returns the highest
  // hero id + 1.
  // genId(heroes: Hero[]): number {
  //   return heroes.length > 0 ? Math.max(...heroes.map(hero => hero.id)) + 1 : 11;
  // }
  genId(tasks: Task[]): number {
    return tasks.length > 0 ? Math.max(...tasks.map(task => task.id)) + 1 : 11;
  }
}
