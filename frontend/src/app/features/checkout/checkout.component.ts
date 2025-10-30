import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ApiService, CarrelloDto } from '../../core/api';
import { StateService } from '../../core/state.service';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  carrello?: CarrelloDto;
  loading = false;
  error = '';
  info = '';
  success = '';
  submitted = false;
  haCarrelloAttivo = true;
  carrelloCaricato = false;

  readonly pagamenti = ['CARTA DI CREDITO', 'PAYPAL', 'CONTRASSEGNO'];

  readonly checkoutForm = this.fb.group({
    nome: ['', Validators.required],
    cognome: ['', Validators.required],
    indirizzo: ['', Validators.required],
    citta: ['', Validators.required],
    provincia: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(2)]],
    cap: ['', [Validators.required, Validators.pattern(/^[0-9]{5}$/)]],
    note: [''],
    pagamento: ['CARTA DI CREDITO', Validators.required]
  });

  constructor(private fb: FormBuilder, private api: ApiService, private state: StateService) {}

  ngOnInit(): void {
    const id = this.state.cartId;
    if (!id) {
      this.haCarrelloAttivo = false;
      this.info = 'Non è presente alcun carrello attivo. Aggiungi componenti prima di procedere al checkout.';
      return;
    }
    this.carica(id);
  }

  private carica(id: number) {
    this.loading = true;
    this.error = '';
    this.info = '';
    this.carrelloCaricato = false;
    this.api.getCarrello(id).subscribe({
      next: carrello => {
        this.carrello = carrello;
        this.loading = false;
        this.carrelloCaricato = true;
        if (!carrello.voci.length) {
          this.info = 'Il carrello è vuoto. Aggiungi elementi prima di completare il checkout.';
        }
      },
      error: () => {
        this.loading = false;
        this.error = 'Impossibile recuperare il carrello. Riprova ad aggiornare la pagina.';
        this.carrello = undefined;
        this.carrelloCaricato = true;
      }
    });
  }

  selezionaPagamento(metodo: string) {
    this.checkoutForm.get('pagamento')?.setValue(metodo);
  }

  pagamentoSelezionato(metodo: string): boolean {
    return this.checkoutForm.get('pagamento')?.value === metodo;
  }

  confermaOrdine() {
    this.submitted = true;
    this.success = '';
    this.error = '';
    if (this.checkoutForm.invalid || !this.carrello || !this.carrello.voci.length) {
      this.error = 'Compila i campi obbligatori e assicurati che il carrello contenga almeno un elemento.';
      return;
    }
    this.success = 'Checkout completato! Riceverai un riepilogo della simulazione via email.';
  }

  totale(): number {
    return this.carrello?.totale ?? 0;
  }

  campoNonValido(nome: string): boolean {
    const campo = this.checkoutForm.get(nome);
    return !!campo && campo.invalid && (campo.touched || this.submitted);
  }
}
