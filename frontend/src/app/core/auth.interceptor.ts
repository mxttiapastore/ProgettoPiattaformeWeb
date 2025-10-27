import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private auth: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.auth.token;
    const expiresAtRaw = localStorage.getItem('expiresAt');
    const expiresAt = Number(expiresAtRaw || 0);
    const isExpired = expiresAt > 0 && Date.now() >= expiresAt;
    const isApiRequest = req.url.startsWith('/api') || req.url.includes('/api/');

    if (token && !isExpired && isApiRequest) {
      req = req.clone({ setHeaders: { Authorization: `Bearer ${token}` } });
    }

    if ((token && isExpired) || (!token && expiresAtRaw)) {
      localStorage.clear();
    }

    return next.handle(req);
  }
}
