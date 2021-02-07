import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER } from '@angular/core';
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
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from '../shared/material.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ServerStatsComponent } from './components/server-stats/server-stats.component';
import { TeamMemberPageComponent } from './components/team-member-page/team-member-page.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { ArticlesSectionComponent } from './components/articles-section/articles-section.component';
import { CreateArticlesSectionComponent } from './components/create-articles-section/create-articles-section.component';
import { AppRoutesService } from './services/routes/app-routes.service';
import { ArticlePageComponent } from './components/article-page/article-page.component';
import { NotFoundPageComponent } from './components/not-found-page/not-found-page.component';
import { RegisterComponent } from './components/register/register.component';
import { QuillEditorComponent } from './components/quill-editor/quill-editor.component';
import { JwtInterceptor } from './services/interceptor/jwt.interceptor';
import { FooterComponent } from './components/footer/footer.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ContactsComponent,
    HomePageComponent,
    ServerStatsComponent,
    TeamMemberPageComponent,
    LoginPageComponent,
    ArticlesSectionComponent,
    CreateArticlesSectionComponent,
    ArticlePageComponent,
    NotFoundPageComponent,
    RegisterComponent,
    QuillEditorComponent,
    FooterComponent,
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
  providers: [
    {
      provide: APP_INITIALIZER,
      multi: true,
      deps: [AppRoutesService],
      useFactory: (appRoutesService: AppRoutesService) => {
        return () => {
          // Make sure to return a promise!
          return appRoutesService.loadAppConfig();
        };
      }
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
