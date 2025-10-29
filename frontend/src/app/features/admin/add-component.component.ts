import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ApiService, NuovoComponenteRequest } from '../../core/api';

@Component({
  selector: 'app-admin-add-component',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './add-component.component.html',
  styleUrls: ['./add-component.component.css']
})
export class AddComponentComponent implements OnInit {
  form: FormGroup;
  tipologie: string[] = [];
  success = '';
  error = '';
  isSubmitting = false;

  constructor(private fb: FormBuilder, private api: ApiService) {
    this.form = this.fb.group({
      tipologia: ['', Validators.required],
      nome: ['', [Validators.required, Validators.maxLength(255)]],
      marca: ['', [Validators.required, Validators.maxLength(100)]],
      prezzo: ['', [Validators.required, Validators.min(0.01)]],
      socketCpu: [''],
      chipsetScheda: [''],
      fattoreForma: [''],
      tipoMemoria: [''],
      wattaggio: ['']
    });
  }

  ngOnInit(): void {
    this.api.getTipologie().subscribe({
      next: tipologie => this.tipologie = tipologie,
      error: () => this.error = 'Impossibile recuperare le tipologie. Riprova più tardi.'
    });
  }

  submit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.isSubmitting = true;
    this.success = '';
    this.error = '';

    const value = this.form.value;
    const payload: NuovoComponenteRequest = {
      tipologia: value.tipologia,
      nome: value.nome,
      marca: value.marca,
      prezzo: Number(value.prezzo),
      socketCpu: value.socketCpu || null,
      chipsetScheda: value.chipsetScheda || null,
      fattoreForma: value.fattoreForma || null,
      tipoMemoria: value.tipoMemoria || null,
      wattaggio: value.wattaggio ? Number(value.wattaggio) : null
    };

    this.api.createComponente(payload).subscribe({
      next: comp => {
        this.isSubmitting = false;
        this.success = `Componente "${comp.nome}" aggiunto correttamente.`;
        this.form.reset({ tipologia: value.tipologia });
      },
      error: err => {
        this.isSubmitting = false;
        this.error = err.status === 409
          ? 'Esiste già un componente con lo stesso nome, tipologia e marca.'
          : 'Impossibile salvare il componente.';
      }
    });
  }

  isInvalid(control: string) {
    const ctrl = this.form.get(control);
    return !!ctrl && ctrl.invalid && (ctrl.touched || ctrl.dirty);
  }
}
