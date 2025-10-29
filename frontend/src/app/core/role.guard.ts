import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class RoleGuard implements CanActivate {
  constructor(private auth: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const requiredRole = route.data['role'] as string | undefined;

    if (!requiredRole) {
      return true;
    }

    if (this.auth.hasRole(requiredRole)) {
      return true;
    }

    this.router.navigate(['/']);
    return false;
  }
}
