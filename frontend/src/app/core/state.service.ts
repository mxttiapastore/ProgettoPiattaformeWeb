import { Injectable } from '@angular/core';
import { Observable, map, of, tap } from 'rxjs';
import { ApiService, CarrelloDto } from './api';

@Injectable({ providedIn: 'root' })
export class StateService {
  private readonly CART_KEY = 'cartId';

  constructor(private api: ApiService) {}

  get cartId(): number | null {
    const raw = localStorage.getItem(this.CART_KEY);
    const value = raw ? Number(raw) : NaN;
    return Number.isFinite(value) ? value : null;
  }

  ensureCarrello(): Observable<number> {
    const existing = this.cartId;
    if (existing) {
      return of(existing);
    }
    return this.api.creaCarrello().pipe(
      tap((carrello: CarrelloDto) => this.setCartId(carrello.id)),
      map(carrello => carrello.id)
    );
  }

  setCartId(id: number) {
    localStorage.setItem(this.CART_KEY, String(id));
  }

  clear() {
    localStorage.removeItem(this.CART_KEY);
  }
}
