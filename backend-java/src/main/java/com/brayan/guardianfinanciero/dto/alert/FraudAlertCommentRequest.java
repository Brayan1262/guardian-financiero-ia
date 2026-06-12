package com.brayan.guardianfinanciero.dto.alert;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FraudAlertCommentRequest {

    @NotBlank(message = "El comentario del analista es obligatorio")
    private String analystComment;
}
