import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DocumentService {
  private apiUrl = 'http://localhost:8080/api/documents'; 

  constructor(private http: HttpClient) {}

  obtenerDocumentos(): Observable<any[]> {
  return this.http.get<any[]>('http://localhost:8080/api/documents/organization/1');
}

 
 
actualizarDocumento(id: number, datos: { status: string }) {
  return this.http.put(`http://localhost:8080/api/documents/${id}/status`, datos);
}
  
  guardarDocumento(documento: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, documento);
  }
  subirDocumento(formData: FormData) {
  return this.http.post('http://localhost:8080/api/documents/upload', formData);
}
}