import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ApiService, ComponenteDto, ConfigurazioneDto, Page } from '../../core/api';
import { StateService } from '../../core/state.service';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-builder',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './builder.component.html',
  styleUrls: ['./builder.component.css']
})
export class BuilderComponent implements OnInit, OnDestroy {
  tipologie: string[] = [];
  selectedTipologia?: string;
  componenti?: Page<ComponenteDto>;
  loadingComponenti = false;
  componentiError = '';
  ricercaNome = '';
  private searchDebounce?: ReturnType<typeof setTimeout>;

  configurazione?: ConfigurazioneDto;
  configurazioneNome = '';
  isSavingConfigurazione = false;
  configurazioneMessaggio = '';
  configurazioneErrore = '';

  cartMessaggio = '';
  cartErrore = '';

  constructor(private api: ApiService, private state: StateService) {}

  ngOnInit(): void {
    this.api.getTipologie().subscribe({
      next: tipologie => {
        this.tipologie = tipologie;
        if (tipologie.length > 0) {
          this.selezionaTipologia(tipologie[0]);
        }
      },
      error: () => {
        this.componentiError = 'Non è stato possibile caricare le tipologie disponibili.';
      }
    });
  }

  selezionaTipologia(tipologia: string) {
    this.selectedTipologia = tipologia;
    this.componenti = undefined;
    this.caricaComponenti(0);
  }

  caricaComponenti(page = 0) {
    if (!this.selectedTipologia) {
      return;
    }
    this.loadingComponenti = true;
    this.componentiError = '';
    this.api
      .listComponenti({
        tipologia: this.selectedTipologia,
        nome: this.ricercaNome ? this.ricercaNome : undefined,
        page,
        size: 12
      })
      .subscribe({
        next: res => {
          this.componenti = res;
          this.loadingComponenti = false;
        },
        error: () => {
          this.componentiError = 'Errore durante il recupero dei componenti.';
          this.loadingComponenti = false;
        }
      });
  }

  paginaSuccessiva() {
    if (!this.componenti || this.componenti.number >= this.componenti.totalPages - 1) {
      return;
    }
    this.caricaComponenti(this.componenti.number + 1);
  }

  paginaPrecedente() {
    if (!this.componenti || this.componenti.number === 0) {
      return;
    }
    this.caricaComponenti(this.componenti.number - 1);
  }

  creaConfigurazione() {
    const nome = this.configurazioneNome.trim();
    if (!nome) {
      this.configurazioneErrore = 'Inserisci un nome per la configurazione.';
      return;
    }
    this.isSavingConfigurazione = true;
    this.configurazioneErrore = '';
    this.configurazioneMessaggio = '';
    this.api.createConfigurazione(nome).subscribe({
      next: conf => {
        this.configurazione = conf;
        this.configurazioneMessaggio = 'Configurazione creata! Ora aggiungi i componenti.';
        this.isSavingConfigurazione = false;
      },
      error: () => {
        this.configurazioneErrore = 'Impossibile creare la configurazione. Riprova.';
        this.isSavingConfigurazione = false;
      }
    });
  }

  salvaConfigurazione() {
    if (!this.configurazione) {
      this.configurazioneErrore = 'Crea una configurazione prima di salvarla.';
      return;
    }
    this.configurazioneErrore = '';
    this.configurazioneMessaggio = '';
    this.api.getConfigurazione(this.configurazione.id).subscribe({
      next: conf => {
        this.configurazione = conf;
        this.configurazioneMessaggio = 'Configurazione correttamente salvata!';
      },
      error: () => {
        this.configurazioneErrore = 'Errore durante il salvataggio della configurazione.';
      }
    });
  }

  aggiungiAllaConfigurazione(componente: ComponenteDto) {
    if (!this.configurazione) {
      this.configurazioneErrore = 'Crea una configurazione prima di aggiungere componenti.';
      return;
    }
    this.configurazioneErrore = '';
    this.configurazioneMessaggio = '';
    this.api.aggiungiVoceConfigurazione(this.configurazione.id, componente.id).subscribe({
      next: conf => {
        this.configurazione = conf;
        this.configurazioneMessaggio = `${componente.nome} aggiunto alla configurazione.`;
      },
      error: () => {
        this.configurazioneErrore = 'Errore durante l\'aggiunta alla configurazione.';
      }
    });
  }

  aggiungiConfigurazioneAlCarrello() {
    if (!this.configurazione) {
      this.cartErrore = 'Crea una configurazione per poterla spedire nel carrello.';
      return;
    }
    this.cartErrore = '';
    this.cartMessaggio = '';
    this.state.ensureCarrello()
      .pipe(switchMap(id => this.api.aggiungiConfigurazioneAlCarrello(id, this.configurazione!.id)))
      .subscribe({
        next: carrello => {
          this.state.setCartId(carrello.id);
          this.cartMessaggio = 'Configurazione aggiunta al carrello!';
        },
        error: () => {
          this.cartErrore = 'Non è stato possibile aggiungere la configurazione al carrello.';
        }
      });
  }

  aggiungiComponenteAlCarrello(componente: ComponenteDto) {
    this.cartErrore = '';
    this.cartMessaggio = '';
    this.state.ensureCarrello()
      .pipe(switchMap(id => this.api.aggiungiComponenteCarrello(id, componente.id)))
      .subscribe({
        next: carrello => {
          this.state.setCartId(carrello.id);
          this.cartMessaggio = `${componente.nome} aggiunto al carrello.`;
        },
        error: () => {
          this.cartErrore = 'Errore durante l\'aggiunta al carrello.';
        }
      });
  }

  valoreTotaleConfigurazione(): number {
    return this.configurazione?.totale ?? 0;
  }

  onSearchChange(term: string) {
    this.ricercaNome = term;
    if (this.searchDebounce) {
      clearTimeout(this.searchDebounce);
    }
    this.searchDebounce = setTimeout(() => this.caricaComponenti(0), 250);
  }

  ngOnDestroy(): void {
    if (this.searchDebounce) {
      clearTimeout(this.searchDebounce);
    }
  }
}
