import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { ContactsComponent } from './components/contacts/contacts.component';
import { ServerStatsComponent } from './components/server-stats/server-stats.component';
import { TeamMemberPageComponent } from './components/team-member-page/team-member-page.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { CreateArticlesSectionComponent } from './components/create-articles-section/create-articles-section.component';
import { ArticlesSectionComponent } from './components/articles-section/articles-section.component';
import { ArticlePageComponent } from './components/article-page/article-page.component';
import { AuthGuard } from './services/authentication/auth-guard.service';
import { RegisterComponent } from './components/register/register.component';
import { FooterComponent } from './components/footer/footer.component';

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
    component: CreateArticlesSectionComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'articles',
    component: ArticlesSectionComponent
  },
  {
    path: 'article/:id',
    component: ArticlePageComponent
  },
  {
    path: 'register',
    component: RegisterComponent,
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
