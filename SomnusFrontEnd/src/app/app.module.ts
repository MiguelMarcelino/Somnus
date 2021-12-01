import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER, ErrorHandler } from '@angular/core';
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
import { ServerStatsComponent } from './components/server-info/server-stats.component';
import { TeamMemberPageComponent } from './components/team-member-page/team-member-page.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { ArticlesSectionComponent } from './components/articles-section/articles-section.component';
import { CreatePostSectionComponent } from './components/create-post-section/create-post-section.component';
import { AppRoutesService } from './services/routes/app-routes.service';
import { ArticlePageComponent } from './components/article-page/article-page.component';
import { NotFoundPageComponent } from './components/not-found-page/not-found-page.component';
import { RegisterComponent } from './components/register/register.component';
import { QuillEditorComponent } from './components/quill-editor/quill-editor.component';
import { CustomHttpInterceptor } from '../handlers/custom-http-interceptor';
import { FooterComponent } from './components/footer/footer.component';
import { DebugMessagesComponent } from './components/debug-messages/debug-messages.component';
import { GlobalErrorHandler } from 'src/handlers/global-error-handler';
import { GamesPageComponent } from './components/games-page/games-page.component';
import { SystemInfoComponent } from './components/system-info/system-info.component';
import { GaugeChartComponent } from './components/gauge-chart/gauge-chart.component';
import { NgxGaugeModule } from 'ngx-gauge';
import { TempServerInfoComponent } from './components/temp-server-info/temp-server-info.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { UpdateUserInfoComponent } from './components/update-user-info/update-user-info.component';
import { HighlightModule, HIGHLIGHT_OPTIONS } from 'ngx-highlightjs';
import { CommentsSectionComponent } from './components/comments-section/comments-section.component';
import { PostBoxComponentComponent } from './components/post-box/post-box.component';
import { NewsSectionComponent } from './components/news-section/news-section.component';
import { NewsPageComponent } from './components/news-page/news-page.component';

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
    CreatePostSectionComponent,
    ArticlePageComponent,
    NotFoundPageComponent,
    RegisterComponent,
    QuillEditorComponent,
    FooterComponent,
    DebugMessagesComponent,
    GamesPageComponent,
    SystemInfoComponent,
    GaugeChartComponent,
    TempServerInfoComponent,
    UserProfileComponent,
    UpdateUserInfoComponent,
    CommentsSectionComponent,
    NewsSectionComponent,
    NewsPageComponent,
    PostBoxComponentComponent
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
    QuillModule.forRoot(),
    NgxGaugeModule
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
      useClass: CustomHttpInterceptor,
      multi: true
    },
    {
      provide: ErrorHandler,
      useClass: GlobalErrorHandler,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

