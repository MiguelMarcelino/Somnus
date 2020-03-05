import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeMenuComponent } from './home-menu/home-menu.component';
import { AboutMenuComponent } from './about-menu/about-menu.component';
import { ContactMenuComponent } from './contact-menu/contact-menu.component';


const routes: Routes = [
  {path: '', component: HomeMenuComponent},
  {path: 'contact', component: ContactMenuComponent},
  {path: 'about', component: AboutMenuComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
