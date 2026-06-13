import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { LoginRequest, AuthResponse, UserResponse } from '../models/auth.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}/auth`;
  
  private currentUserSubject = new BehaviorSubject<UserResponse | null>(this.getUserFromStorage());
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {}

  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`http://localhost:8080/api/auth/login`, credentials).pipe(
      tap(response => this.saveAuthData(response))
    );
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('role');
    localStorage.removeItem('fullName');
    localStorage.removeItem('email');
    this.currentUserSubject.next(null);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  getCurrentUser(): UserResponse | null {
    return this.currentUserSubject.value;
  }

  hasRole(role: string): boolean {
    const userRole = localStorage.getItem('role');
    return userRole === `ROLE_${role}` || userRole === role;
  }

  hasAnyRole(roles: string[]): boolean {
    const userRole = localStorage.getItem('role');
    if (!userRole) return false;
    
    const roleBase = userRole.startsWith('ROLE_') ? userRole.substring(5) : userRole;
    return roles.includes(roleBase) || roles.includes(userRole);
  }

  private saveAuthData(response: AuthResponse): void {
    localStorage.setItem('token', response.token);
    localStorage.setItem('user', JSON.stringify(response.user));
    localStorage.setItem('role', response.user.role);
    localStorage.setItem('fullName', response.user.fullName);
    localStorage.setItem('email', response.user.email);
    this.currentUserSubject.next(response.user);
  }

  private getUserFromStorage(): UserResponse | null {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      try {
        return JSON.parse(userStr);
      } catch (e) {
        return null;
      }
    }
    return null;
  }
}
