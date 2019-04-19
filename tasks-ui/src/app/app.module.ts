import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {HttpClientInMemoryWebApiModule} from 'angular-in-memory-web-api';
import {InMemoryDataService} from './in-memory-data.service';

import {AppRoutingModule} from './app-routing.module';

import {AppComponent} from './app.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {MessagesComponent} from './messages/messages.component';

import {TaskDetailComponent} from './task-detail/task-detail.component';
import {TasksComponent} from './tasks/tasks.component';
import {TaskSearchComponent} from './task-search/task-search.component';

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        AppRoutingModule,
        HttpClientModule,

        // The HttpClientInMemoryWebApiModule module intercepts HTTP requests
        // and returns simulated server responses.
        // Remove it when a real server is ready to receive requests.
        HttpClientInMemoryWebApiModule.forRoot(
            InMemoryDataService, {dataEncapsulation: false}
        )
    ],
    declarations: [
        AppComponent,
        DashboardComponent,
        MessagesComponent,
        TasksComponent,
        TaskDetailComponent,
        TaskSearchComponent
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
