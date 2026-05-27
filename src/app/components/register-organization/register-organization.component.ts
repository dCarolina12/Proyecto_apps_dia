import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register-organization',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './register-organization.component.html'
})
export class RegisterOrganizationComponent {
  nit = '';
  nombre = ''; 
  
  errorMessage = '';
  successMessage = '';

  constructor(private router: Router, private http: HttpClient) {}

  finalizarRegistro() {
    if (!this.nit || !this.nombre) {
      this.errorMessage = 'El NIT y el Nombre son obligatorios según el modelo de base de datos.';
      return;
    }

    const organizacionBody = {
      nit: this.nit,
      name: this.nombre 
    };

    this.http.post<any>('http://localhost:8080/api/organizations', organizacionBody).subscribe({
      next: (response) => {
        this.errorMessage = '';
        this.successMessage = `¡Organización "${this.nombre}" guardada con éxito en la Base de Datos!`;
        
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000);
      },
      error: (err) => {
        this.successMessage = '';
        this.errorMessage = 'Error en el servidor o el NIT ya se encuentra registrado.';
        console.error('Detalles del error:', err);
      }
    });
  }

  cancelar() {
    this.router.navigate(['/login']);
  }
}