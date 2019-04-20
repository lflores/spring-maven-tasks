import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardComponent } from './dashboard.component';

import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { TASKS } from '../mock-tasks';
import { TaskService } from '../task.service';
import {TaskSearchComponent} from '../task-search/task-search.component';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;
  let taskService;
  let getTasksSpy;

  beforeEach(async(() => {
    taskService = jasmine.createSpyObj('TaskService', ['getTasks']);
    getTasksSpy = taskService.getTasks.and.returnValue( of(TASKS) );
    TestBed.configureTestingModule({
      declarations: [
        DashboardComponent,
        TaskSearchComponent
      ],
      imports: [
        RouterTestingModule.withRoutes([])
      ],
      providers: [
        { provide: TaskService, useValue: taskService }
      ]
    })
    .compileComponents();

  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  it('should display "Top Tasks" as headline', () => {
    expect(fixture.nativeElement.querySelector('h3').textContent).toEqual('Top Tasks');
  });

  it('should call taskService', async(() => {
    expect(getTasksSpy.calls.any()).toBe(true);
    }));

  it('should display 4 links', async(() => {
    expect(fixture.nativeElement.querySelectorAll('a').length).toEqual(4);
  }));

});
