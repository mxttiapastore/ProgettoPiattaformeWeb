// frontend/src/app/features/auth/login.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  imports: [ReactiveFormsModule, CommonModule],
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  form!: FormGroup;          // dichiarazione senza usare fb qui
  error = '';

  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {
    // inizializzo DOPO l'iniezione di fb -> niente TS2729
    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  login() {
    if (this.form.invalid) return;
    const { username, password } = this.form.value;
    this.auth.login(username!, password!).subscribe({
      next: () => this.router.navigate(['/home']),
      error: () => this.error = 'Credenziali errate'
    });
  }
}
