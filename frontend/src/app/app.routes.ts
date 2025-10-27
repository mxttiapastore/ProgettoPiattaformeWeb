import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login.component';
import { ComponentiListComponent } from './features/componenti-list/componenti-list.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'home', component: ComponentiListComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
];
