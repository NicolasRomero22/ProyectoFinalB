package com.example.ProyectoFinal.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EjemplarResponse {
    private Long id;
    private String codigo;
    private Long libroId;

    public EjemplarResponse(Long id, String codigo, Long libroId) {
        this.id = id;
        this.codigo = codigo;
        this.libroId = libroId;
    }

    public EjemplarResponse(Ejemplar ejemplar) {
        this.id = ejemplar.getId();
        this.codigo = ejemplar.getCodigo();
        this.libroId = ejemplar.getLibro().getId();
    }

    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Long getLibroId() {
        return libroId;
    }
}


