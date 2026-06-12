export interface LoginRequest {
  email?: string;
  password?: string;
}

export interface AuthResponse {
  token: string;
  user: UserResponse;
}

export interface UserResponse {
  id: number;
  fullName: string;
  email: string;
  documentNumber: string;
  role: string;
  status: string;
}
