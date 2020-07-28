import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { ContactsComponent } from './components/contacts/contacts.component';
import { CvTemplateComponent } from './components/cvs/cv-template/cv-template.component';
import { ServerStatsComponent } from './components/server-stats/server-stats.component';
import { TeamMemberPageComponent } from './components/team-member-page/team-member-page.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { CreateArticlesSectionComponent } from './components/create-articles-section/create-articles-section.component';


const routes: Routes = [
  {
    path: '', 
    redirectTo: 'homePage',
    pathMatch: 'full'
  },
  {
    path: "home",
    component: HomeComponent
  },
  {
    path: "homePage",
    component: HomePageComponent
  },
  {
    path: "contacts",
    component: ContactsComponent
  },
  {
    path: "cvTesting",
    component: CvTemplateComponent
  },
  {
    path: 'server_stats',
    component: ServerStatsComponent
  },
  {
    path: 'team',
    component: TeamMemberPageComponent
  },
  {
    path: 'login',
    component: LoginPageComponent
  },
  {
    path: 'createArticle',
    component: CreateArticlesSectionComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
