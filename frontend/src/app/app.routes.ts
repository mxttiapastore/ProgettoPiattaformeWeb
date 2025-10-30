import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login.component';
import { RegisterComponent } from './features/auth/register.component';
import { HomeComponent } from './features/home/home.component';
import { BuilderComponent } from './features/builder/builder.component';
import { ConfigurationsComponent } from './features/configurations/configurations.component';
import { CartComponent } from './features/cart/cart.component';
import { CheckoutComponent } from './features/checkout/checkout.component';
import { AddComponentComponent } from './features/admin/add-component.component';
import { AuthGuard } from './core/auth.guard';
import { RoleGuard } from './core/role.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'builder', component: BuilderComponent, canActivate: [AuthGuard] },
  { path: 'configurazioni', component: ConfigurationsComponent, canActivate: [AuthGuard] },
  { path: 'carrello', component: CartComponent, canActivate: [AuthGuard] },
  { path: 'checkout', component: CheckoutComponent, canActivate: [AuthGuard] },
  { path: 'admin/componenti', component: AddComponentComponent, canActivate: [AuthGuard, RoleGuard], data: { role: 'ROLE_ADMIN' } },
  { path: '**', redirectTo: '' }
];
