package com.brayan.guardianfinanciero.controller;

import com.brayan.guardianfinanciero.dto.customer.CustomerCreateRequest;
import com.brayan.guardianfinanciero.dto.customer.CustomerResponse;
import com.brayan.guardianfinanciero.dto.customer.CustomerStatusUpdateRequest;
import com.brayan.guardianfinanciero.dto.customer.CustomerUpdateRequest;
import com.brayan.guardianfinanciero.model.enums.CustomerStatus;
import com.brayan.guardianfinanciero.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "customer-controller", description = "Endpoints para la gestión de clientes en la plataforma antifraude")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST')")
    @Operation(summary = "Crear cliente", description = "Registra un nuevo cliente. Requiere rol ADMIN o ANALYST.")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerCreateRequest request) {
        return new ResponseEntity<>(customerService.createCustomer(request), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
    @Operation(summary = "Listar clientes", description = "Devuelve la lista de todos los clientes registrados.")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
    @Operation(summary = "Buscar por ID", description = "Busca un cliente usando su ID interno.")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping("/document/{documentNumber}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
    @Operation(summary = "Buscar por documento", description = "Busca un cliente por su número de documento único.")
    public ResponseEntity<CustomerResponse> getCustomerByDocumentNumber(@PathVariable String documentNumber) {
        return ResponseEntity.ok(customerService.getCustomerByDocumentNumber(documentNumber));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
    @Operation(summary = "Listar por estado", description = "Filtra la lista de clientes por su estado actual (ACTIVE, BLOCKED, UNDER_REVIEW).")
    public ResponseEntity<List<CustomerResponse>> getCustomersByStatus(@PathVariable CustomerStatus status) {
        return ResponseEntity.ok(customerService.getCustomersByStatus(status));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
    @Operation(summary = "Buscar por nombre", description = "Permite buscar clientes mediante coincidencias parciales en su nombre.")
    public ResponseEntity<List<CustomerResponse>> searchCustomersByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(customerService.searchCustomersByName(name));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST')")
    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos básicos de un cliente (nombre, email, teléfono). Requiere ADMIN o ANALYST.")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long id, 
            @Valid @RequestBody CustomerUpdateRequest request) {
        return ResponseEntity.ok(customerService.updateCustomer(id, request));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST')")
    @Operation(summary = "Cambiar estado de cliente", description = "Modifica únicamente el estado del cliente (ej. bloquearlo o ponerlo en revisión). Requiere ADMIN o ANALYST.")
    public ResponseEntity<CustomerResponse> updateCustomerStatus(
            @PathVariable Long id, 
            @Valid @RequestBody CustomerStatusUpdateRequest request) {
        return ResponseEntity.ok(customerService.updateCustomerStatus(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar cliente", description = "Elimina físicamente un cliente de la base de datos. Solo permitido para el rol ADMIN.")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
