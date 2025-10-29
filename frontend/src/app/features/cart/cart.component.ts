import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ApiService, CarrelloDto } from '../../core/api';
import { StateService } from '../../core/state.service';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  carrello?: CarrelloDto;
  loading = false;
  error = '';
  message = '';

  constructor(private api: ApiService, private state: StateService) {}

  ngOnInit(): void {
    const id = this.state.cartId;
    if (id) {
      this.carica(id);
    } else {
      this.message = 'Non hai ancora creato un carrello. Aggiungi un componente dal configuratore per iniziare.';
    }
  }

  creaCarrello() {
    this.loading = true;
    this.error = '';
    this.message = '';
    this.api.creaCarrello().subscribe({
      next: carrello => {
        this.state.setCartId(carrello.id);
        this.loading = false;
        this.carrello = carrello;
        this.message = 'Carrello creato! Aggiungi componenti dal configuratore o dalle build salvate.';
      },
      error: () => {
        this.loading = false;
        this.error = 'Non è stato possibile creare un nuovo carrello.';
      }
    });
  }

  aggiorna() {
    const id = this.state.cartId;
    if (!id) {
      this.message = 'Nessun carrello disponibile. Creane uno per iniziare.';
      return;
    }
    this.carica(id);
  }

  private carica(id: number) {
    this.loading = true;
    this.error = '';
    this.api.getCarrello(id).subscribe({
      next: carrello => {
        this.carrello = carrello;
        this.state.setCartId(carrello.id);
        this.loading = false;
      },
      error: () => {
        this.loading = false;
        this.error = 'Impossibile recuperare il carrello. Creane uno nuovo.';
      }
    });
  }

  totale(): number {
    return this.carrello?.totale ?? 0;
  }
}
