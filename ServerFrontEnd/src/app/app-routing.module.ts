import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { ContactsComponent } from './components/contacts/contacts.component';
import { CvTemplateComponent } from './components/cvs/cv-template/cv-template.component';
import { GamesComponent } from './components/games/games.component';
import { ServerStatsComponent } from './components/server-stats/server-stats.component';
import { TeamMemberPageComponent } from './components/team-member-page/team-member-page.component';


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
    path: "games",
    component: GamesComponent
  },
  {
    path: 'server_stats',
    component: ServerStatsComponent
  },
  {
    path: 'team',
    component: TeamMemberPageComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
