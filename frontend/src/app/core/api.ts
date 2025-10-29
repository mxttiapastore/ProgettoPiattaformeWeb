import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}

export interface ComponenteDto {
  id: number;
  tipologia: string;
  nome: string;
  marca: string;
  prezzo: number;
  socketCpu?: string | null;
  chipsetScheda?: string | null;
  fattoreForma?: string | null;
  tipoMemoria?: string | null;
  wattaggio?: number | null;
}

export interface NuovoComponenteRequest {
  tipologia: string;
  nome: string;
  marca: string;
  prezzo: number;
  socketCpu?: string | null;
  chipsetScheda?: string | null;
  fattoreForma?: string | null;
  tipoMemoria?: string | null;
  wattaggio?: number | null;
}

export interface VoceConfigurazioneDto {
  id: number;
  componenteId: number;
  nomeComponente: string;
  tipologia: string;
  quantita: number;
  prezzoUnitario: number;
}

export interface ConfigurazioneDto {
  id: number;
  nome: string;
  voci: VoceConfigurazioneDto[];
  totale: number;
}

export interface CarrelloDto {
  id: number;
  voci: VoceCarrelloDto[];
  totale: number;
}

export interface VoceCarrelloDto {
  id: number;
  componenteId: number;
  nomeComponente: string;
  quantita: number;
  prezzoUnitario: number;
  subtotale: number;
}

export interface ProfiloUtenteDto {
  username: string;
  roles: string[];
}

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly base = '/api';

  constructor(private http: HttpClient) {}

  getTipologie(): Observable<string[]> {
    return this.http.get<string[]>(`${this.base}/componenti/tipologie`);
  }

  listComponenti(params: {
    tipologia: string;
    marca?: string | null;
    page?: number;
    size?: number;
  }): Observable<Page<ComponenteDto>> {
    let httpParams = new HttpParams().set('tipologia', params.tipologia);
    if (params.marca) {
      httpParams = httpParams.set('marca', params.marca);
    }
    if (typeof params.page === 'number') {
      httpParams = httpParams.set('page', params.page);
    }
    if (typeof params.size === 'number') {
      httpParams = httpParams.set('size', params.size);
    }
    return this.http.get<Page<ComponenteDto>>(`${this.base}/componenti`, { params: httpParams });
  }

  createComponente(body: NuovoComponenteRequest): Observable<ComponenteDto> {
    return this.http.post<ComponenteDto>(`${this.base}/componenti`, body);
  }

  createConfigurazione(nome: string): Observable<ConfigurazioneDto> {
    return this.http.post<ConfigurazioneDto>(`${this.base}/configurazioni`, { nome });
  }

  aggiungiVoceConfigurazione(configId: number, componenteId: number, quantita = 1): Observable<ConfigurazioneDto> {
    return this.http.put<ConfigurazioneDto>(`${this.base}/configurazioni/${configId}/voci`, {
      componenteId,
      quantita
    });
  }

  getConfigurazione(id: number): Observable<ConfigurazioneDto> {
    return this.http.get<ConfigurazioneDto>(`${this.base}/configurazioni/${id}`);
  }

  listConfigurazioni(): Observable<ConfigurazioneDto[]> {
    return this.http.get<ConfigurazioneDto[]>(`${this.base}/configurazioni`);
  }

  creaCarrello(): Observable<CarrelloDto> {
    return this.http.post<CarrelloDto>(`${this.base}/carrelli`, {});
  }

  getCarrello(id: number): Observable<CarrelloDto> {
    return this.http.get<CarrelloDto>(`${this.base}/carrelli/${id}`);
  }

  aggiungiComponenteCarrello(carrelloId: number, componenteId: number, quantita = 1): Observable<CarrelloDto> {
    return this.http.put<CarrelloDto>(`${this.base}/carrelli/${carrelloId}/componenti`, {
      componenteId,
      quantita
    });
  }

  aggiungiConfigurazioneAlCarrello(carrelloId: number, configurazioneId: number): Observable<CarrelloDto> {
    return this.http.put<CarrelloDto>(`${this.base}/carrelli/${carrelloId}/configurazioni/${configurazioneId}`, {});
  }

  profilo(): Observable<ProfiloUtenteDto> {
    return this.http.get<ProfiloUtenteDto>(`${this.base}/users/me`);
  }
}

