package com.brayan.guardianfinanciero.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreateRequest {
    
    @NotBlank(message = "El número de documento es obligatorio")
    @Size(min = 8, message = "El documento debe tener al menos 8 caracteres")
    private String documentNumber;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(min = 3, message = "El nombre debe tener al menos 3 caracteres")
    private String fullName;

    @Email(message = "Formato de email inválido")
    private String email;

    private String phone;
}
