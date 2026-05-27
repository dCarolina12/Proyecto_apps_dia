import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DocumentService } from '../../service/document.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  providers: [DocumentService],
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit {
  seccionActiva: string = 'documentos';
  usuarioLogueado: string = 'diana@mail.com';
  rolUsuario: string = 'ADMIN';
  mostrarModalDoc: boolean = false;

  mostrarModalEstado: boolean = false;
  docSeleccionado: any = null;

  nuevoDocTitle: string = '';
  nuevoDocType: string = 'application/pdf';
  archivoSeleccionado: File | null = null; 

  usuariosLista = [
    { id: 1, nombre: 'Diana Carolina', correo: 'diana@mail.com', rol: 'ADMIN', estado: 'Activo' }
  ];

  documentosLista: any[] = [];

  constructor(private router: Router, private documentService: DocumentService) {}

  ngOnInit() {
    this.cargarDocumentos();
  }

  cargarDocumentos() {
    this.documentService.obtenerDocumentos().subscribe({
      next: (data) => { this.documentosLista = data; },
      error: (err) => console.error('Error al cargar documentos:', err)
    });
  }

  abrirModalCambiarEstado(doc: any) {
    this.docSeleccionado = doc;
    this.mostrarModalEstado = true;
  }

  abrirModalDocumento() { this.mostrarModalDoc = true; }
  cerrarModalDocumento() { this.mostrarModalDoc = false; }
  cambiarPestana(pestaña: string) { this.seccionActiva = pestaña; }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.archivoSeleccionado = file;
      this.nuevoDocTitle = file.name;
      this.nuevoDocType = file.type;
    }
  }

  guardarNuevoDocumento() {
    if (!this.archivoSeleccionado) {
      alert('Por favor, selecciona un archivo.');
      return;
    }

    const formData = new FormData();
    formData.append('title', this.nuevoDocTitle);
    formData.append('file', this.archivoSeleccionado);
    formData.append('userId', '1'); 

    this.documentService.subirDocumento(formData).subscribe({
      next: () => {
        this.cargarDocumentos();
        this.cerrarModalDocumento();
        alert('¡Documento subido exitosamente!');
      },
      error: (err) => {
        console.error('Error al subir:', err);
        alert('Error: Revisa que el servicio de backend esté recibiendo el FormData.');
      }
    });
  }

  cerrarSesion() { this.router.navigate(['/login']); }
 

 
  actualizarEstado(nuevoEstado: string) {
  if (this.docSeleccionado) {
    const datosActualizados: { status: string } = { status: nuevoEstado };

    // Verificamos que el servicio esté bien llamado
    this.documentService.actualizarDocumento(this.docSeleccionado.id, datosActualizados)
      .subscribe({
        next: (response) => {
          alert('Estado actualizado correctamente.');
          this.cargarDocumentos();
          this.mostrarModalEstado = false;
        },
        error: (err) => {
          console.error('Error:', err);
          alert('Error al guardar en la base de datos.');
        }
      });
  }
}
}