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

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule, MatIconModule,MatCheckboxModule} from '@angular/material';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatInputModule} from '@angular/material';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatCardModule} from '@angular/material/card';
import {MatRadioModule} from '@angular/material/radio';


@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MatButtonModule,
        MatInputModule,
        MatIconModule,
        MatCheckboxModule,
        MatToolbarModule,
        MatFormFieldModule,
        HttpClientModule,
        MatRadioModule,
        MatCardModule

        // The HttpClientInMemoryWebApiModule module intercepts HTTP requests
        // and returns simulated server responses.
        // Remove it when a real server is ready to receive requests.
        // HttpClientInMemoryWebApiModule.forRoot(
            // InMemoryDataService, {dataEncapsulation: false}
        // )
    ],
    exports: [
        MatButtonModule, MatCheckboxModule, MatIconModule,MatToolbarModule,MatFormFieldModule,MatInputModule,
        MatCardModule,MatRadioModule
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
