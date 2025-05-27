package com.example.ProyectoFinal.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class PrestamoRequest {
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private Long usuarioId;
    private Long ejemplarId;
}
