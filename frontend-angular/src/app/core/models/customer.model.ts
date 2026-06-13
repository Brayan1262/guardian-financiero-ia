export interface CustomerResponse {
  id: number;
  documentType: string;
  documentNumber: string;
  fullName: string;
  email: string;
  phoneNumber: string;
  status: string;
  registrationDate: string;
}

export interface CustomerRequest {
  documentType: string;
  documentNumber: string;
  fullName: string;
  email: string;
  phoneNumber: string;
  status?: string;
}
