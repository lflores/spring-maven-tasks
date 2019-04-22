import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {DashboardComponent} from './dashboard/dashboard.component';
import {TasksComponent} from './tasks/tasks.component';
import {TaskDetailComponent} from './task-detail/task-detail.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule, MatCheckboxModule} from '@angular/material';

const routes: Routes = [
    {path: '', redirectTo: '/dashboard', pathMatch: 'full'},
    {path: 'dashboard', component: DashboardComponent},
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
