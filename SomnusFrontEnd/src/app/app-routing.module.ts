import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { ContactsComponent } from './components/contacts/contacts.component';
import { ServerStatsComponent } from './components/server-info/server-stats.component';
import { TeamMemberPageComponent } from './components/team-member-page/team-member-page.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { CreatePostSectionComponent } from './components/create-post-section/create-post-section.component';
import { ArticlesSectionComponent } from './components/articles-section/articles-section.component';
import { ArticlePageComponent } from './components/article-page/article-page.component';
import { AuthGuard } from './services/authentication/auth-guard.service';
import { RegisterComponent } from './components/register/register.component';
import { GamesPageComponent } from './components/games-page/games-page.component';
import { Role } from './models/role.model';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { NewsSectionComponent } from './components/news-section/news-section.component';
import { NewsPageComponent } from './components/news-page/news-page.component';

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
    path: 'server-info',
    component: ServerStatsComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.Admin, Role.Manager]}
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
    path: 'createPost',
    component: CreatePostSectionComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.Editor, Role.Admin]}
  },
  {
    path: 'articles',
    component: ArticlesSectionComponent
  },
  {
    path: 'article/:id/:normalizedTopic/:normalizedName',
    component: ArticlePageComponent
  },
  {
    path: 'news-posts',
    component: NewsSectionComponent
  },
  {
    path: 'news-post/:id/:normalizedName',
    component: NewsPageComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: "user-profile",
    component: UserProfileComponent,
    canActivate: [AuthGuard]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
