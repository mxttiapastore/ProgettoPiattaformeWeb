import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ApiService, ConfigurazioneDto } from '../../core/api';
import { StateService } from '../../core/state.service';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-configurations',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './configurations.component.html',
  styleUrls: ['./configurations.component.css']
})
export class ConfigurationsComponent implements OnInit {
  configurazioni: ConfigurazioneDto[] = [];
  loading = true;
  error = '';
  success = '';

  constructor(private api: ApiService, private state: StateService) {}

  ngOnInit(): void {
    this.carica();
  }

  carica() {
    this.loading = true;
    this.error = '';
    this.api.listConfigurazioni().subscribe({
      next: conf => {
        this.configurazioni = conf;
        this.loading = false;
      },
      error: () => {
        this.error = 'Impossibile recuperare le configurazioni salvate.';
        this.loading = false;
      }
    });
  }

  aggiungiAlCarrello(config: ConfigurazioneDto) {
    this.success = '';
    this.error = '';
    this.state.ensureCarrello()
      .pipe(switchMap(id => this.api.aggiungiConfigurazioneAlCarrello(id, config.id)))
      .subscribe({
        next: carrello => {
          this.state.setCartId(carrello.id);
          this.success = `Configurazione "${config.nome}" aggiunta al carrello.`;
        },
        error: () => {
          this.error = 'Non è stato possibile aggiungere la configurazione al carrello.';
        }
      });
  }
}
