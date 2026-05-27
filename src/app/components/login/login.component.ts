import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http'; 

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.component.html'
})
export class LoginComponent {
  email = '';
  password = '';
  errorMessage = '';

  constructor(private router: Router, private http: HttpClient) {}

  entrar() {
    if (!this.email || !this.password) {
      this.errorMessage = 'Por favor, completa todos los campos.';
      return;
    }

    const loginBody = {
      email: this.email,
      password: this.password
    };

    this.http.post<any>('http://localhost:8080/api/auth/login', loginBody).subscribe({
      next: (response) => {
        this.errorMessage = '';
        
        localStorage.setItem('token', response.token);
        localStorage.setItem('role', response.role);
        localStorage.setItem('organizationId', response.organizationId);
        
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        this.errorMessage = 'Credenciales inválidas en el sistema de docuFacil.';
        console.error(err);
      }
    });
  }

  irARegistro() {
    this.router.navigate(['/register-organization']);
  }
}