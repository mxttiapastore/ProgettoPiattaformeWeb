// frontend/src/app/features/componenti-list/componenti-list.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService, ComponenteDto } from '../../core/api';

@Component({
  selector: 'app-componenti-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <h2>Componenti</h2>
    <div *ngIf="loading">Caricamento…</div>
    <div *ngIf="error">{{ error }}</div>
    <ul *ngIf="!loading && !error">
      <li *ngFor="let c of data">
        <strong>{{ c.tipologia }}</strong> — id {{ c.id }}
      </li>
    </ul>
  `
})
export class ComponentiListComponent implements OnInit {
  data: ComponenteDto[] = [];
  loading = true;
  error?: string;

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.api.listComponenti().subscribe({
      next: (r) => { this.data = r; this.loading = false; },
      error: () => { this.error = 'Errore nel caricamento'; this.loading = false; }
    });

  }
}
