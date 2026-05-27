import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterOrganizationComponent } from './components/register-organization/register-organization.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' }, // Al abrir la app, te manda al Login automáticamente
  { path: 'login', component: LoginComponent },
  { path: 'register-organization', component: RegisterOrganizationComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: '**', redirectTo: 'login' } // Si ponen cualquier otra ruta, los devuelve al Login por seguridad
];