// frontend/src/app/app.routes.ts
import { Routes } from '@angular/router';
import { ComponentiListComponent } from './features/componenti-list/componenti-list.component';

export const routes: Routes = [
  { path: '', redirectTo: 'componenti', pathMatch: 'full' },
  { path: 'componenti', component: ComponentiListComponent },
  { path: '**', redirectTo: 'componenti' }
];
