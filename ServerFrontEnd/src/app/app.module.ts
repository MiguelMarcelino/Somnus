import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MglTimelineModule } from 'angular-mgl-timeline';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { ContactsComponent } from './components/contacts/contacts.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { CvTemplateComponent } from './components/cvs/cv-template/cv-template.component';
import { GamesComponent } from './components/games/games.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from '../shared/material.module';
import { AddGameEventComponent } from './components/add-game-event/add-game-event.component';
import { HttpClientModule } from '@angular/common/http';
import { ServerStatsComponent } from './components/server-stats/server-stats.component';
import { TeamMemberPageComponent } from './components/team-member-page/team-member-page.component';
import { AboutPageComponent } from './components/about-page/about-page.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ContactsComponent,
    HomePageComponent,
    CvTemplateComponent,
    GamesComponent,
    AddGameEventComponent,
    ServerStatsComponent,
    TeamMemberPageComponent,
    AboutPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    MglTimelineModule,
    MaterialModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
