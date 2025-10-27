// frontend/src/app/core/api.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ComponenteDto {
  id: number;
  tipologia: string;
}

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly base = '/api';

  constructor(private http: HttpClient) {}

  listComponenti(): Observable<ComponenteDto[]> {
    return this.http.get<ComponenteDto[]>(`${this.base}/componenti/tutti`);
  }

  // esempi per dopo:
  getComponente(id: number): Observable<ComponenteDto> {
    return this.http.get<ComponenteDto>(`${this.base}/componenti/${id}`);
  }
  createComponente(body: Partial<ComponenteDto>): Observable<ComponenteDto> {
    return this.http.post<ComponenteDto>(`${this.base}/componenti`, body);
  }
}
