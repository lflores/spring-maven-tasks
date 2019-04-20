import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Task } from './task';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
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

  // Overrides the genId method to ensure that a task always has an id.
  // If the tasks array is empty,
  // the method below returns the initial number (11).
  // if the tasks array is not empty, the method below returns the highest
  // task id + 1.
  genId(tasks: Task[]): number {
    return tasks.length > 0 ? Math.max(...tasks.map(task => task.id)) + 1 : 11;
  }
}
