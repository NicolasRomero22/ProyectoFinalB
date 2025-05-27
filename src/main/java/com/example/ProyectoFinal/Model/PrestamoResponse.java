package com.example.ProyectoFinal.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class PrestamoResponse {
    private Long id;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private Long usuarioId;
    private String nombreUsuario;
    private Long ejemplarId;
    private String codigoEjemplar;

    // Constructor que recibe la entidad Prestamo
    public PrestamoResponse(Prestamo p) {
        this.id = p.getId();
        this.fechaPrestamo = p.getFechaPrestamo();
        this.fechaDevolucion = p.getFechaDevolucion();

        if (p.getUsuario() != null) {
            this.usuarioId = p.getUsuario().getId();
            this.nombreUsuario = p.getUsuario().getNombre();
        }

        if (p.getEjemplar() != null) {
            this.ejemplarId = p.getEjemplar().getId();
            this.codigoEjemplar = p.getEjemplar().getCodigo();
        }
    }
}
