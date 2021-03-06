import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {DashboardComponent} from './dashboard/dashboard.component';
import {TasksComponent} from './tasks/tasks.component';
import {TaskDetailComponent} from './task-detail/task-detail.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule, MatCheckboxModule} from '@angular/material';
import {ScrumBoardComponent} from './scrum-board/scrum-board.component';

const routes: Routes = [
    {path: '', redirectTo: '/scrum', pathMatch: 'full'},
    {path: 'dashboard', component: DashboardComponent},
    {path: 'scrum', component: ScrumBoardComponent},
    {path: 'detail/:id', component: TaskDetailComponent},
    {path: 'tasks', component: TasksComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes),
        BrowserAnimationsModule,
        MatButtonModule,
        MatCheckboxModule],
    exports: [
        RouterModule,
        BrowserAnimationsModule,
        MatButtonModule,
        MatCheckboxModule
    ]
})
export class AppRoutingModule {
}
