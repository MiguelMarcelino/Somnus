import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MglTimelineModule } from 'angular-mgl-timeline'; // still not in use
import { environment } from '../environments/environment'; 
import { AngularFireModule } from '@angular/fire';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { QuillModule } from 'ngx-quill'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { ContactsComponent } from './components/contacts/contacts.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { CvTemplateComponent } from './components/cvs/cv-template/cv-template.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from '../shared/material.module';
import { AddGameEventComponent } from './components/add-game-event/add-game-event.component';
import { HttpClientModule } from '@angular/common/http';
import { ServerStatsComponent } from './components/server-stats/server-stats.component';
import { TeamMemberPageComponent } from './components/team-member-page/team-member-page.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { GamingSectionComponent } from './components/gaming-section/gaming-section.component';
import { ArticlesSectionComponent } from './components/articles-section/articles-section.component';
import { CreateArticlesSectionComponent } from './components/create-articles-section/create-articles-section.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ContactsComponent,
    HomePageComponent,
    CvTemplateComponent,
    AddGameEventComponent,
    ServerStatsComponent,
    TeamMemberPageComponent,
    LoginPageComponent,
    GamingSectionComponent,
    ArticlesSectionComponent,
    CreateArticlesSectionComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    MglTimelineModule,
    HttpClientModule,
    AngularFireModule.initializeApp(environment.firebaseConfig),
    AngularFireAuthModule,
    QuillModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
