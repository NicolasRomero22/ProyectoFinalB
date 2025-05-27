package com.example.ProyectoFinal.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EjemplarRequest {
    private String codigo;
    private Long libroId;

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Long getLibroId() {
        return libroId;
    }

    public void setLibroId(Long libroId) {
        this.libroId = libroId;
    }
}