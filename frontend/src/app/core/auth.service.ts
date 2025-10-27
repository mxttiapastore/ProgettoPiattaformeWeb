import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';

type AuthResponse = { token: string; expiresIn: number };

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(private http: HttpClient) {}

  login(username: string, password: string) {
    return this.http
      .post<AuthResponse>('/api/auth/login', { username, password })
      .pipe(tap(res => this.persist(res)));
  }

  register(username: string, password: string) {
    return this.http
      .post<AuthResponse>('/api/auth/register', { username, password })
      .pipe(tap(res => this.persist(res)));
  }

  logout() {
    localStorage.clear();
  }

  get token() {
    return localStorage.getItem('token');
  }

  isLoggedIn() {
    const token = this.token;
    const expiresAtRaw = localStorage.getItem('expiresAt');
    const expiresAt = Number(expiresAtRaw || 0);

    if ((token && expiresAtRaw && Date.now() >= expiresAt) || (!token && expiresAtRaw)) {
      localStorage.clear();
      return false;
    }

    return !!token;
  }

  private persist(res: AuthResponse) {
    localStorage.setItem('token', res.token);
    const expiresAt = Date.now() + res.expiresIn * 1000;
    localStorage.setItem('expiresAt', String(expiresAt));
  }
}
