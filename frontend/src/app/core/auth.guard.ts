import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean {
    const token = localStorage.getItem('token');
    const expiresAtRaw = localStorage.getItem('expiresAt');
    const expiresAt = Number(expiresAtRaw || 0);

    if ((token && expiresAtRaw && Date.now() > expiresAt) || (!token && expiresAtRaw)) {
      localStorage.clear();
      this.router.navigate(['/login']);
      return false;
    }

    if (!token) {
      this.router.navigate(['/login']);
      return false;
    }

    return true;
  }
}
