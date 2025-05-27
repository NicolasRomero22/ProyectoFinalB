package com.example.ProyectoFinal.Services;

import com.example.ProyectoFinal.Model.Ejemplar;
import com.example.ProyectoFinal.Repository.EjemplarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EjemplarService {
    @Autowired
    public EjemplarRepository ejemplarRepository;

    public Ejemplar buscar(long id_ejemplar) {
        if (ejemplarRepository.existsById(id_ejemplar)) {
            return ejemplarRepository.getById(id_ejemplar);
        } else {
            throw new RuntimeException("Id no asignada " + id_ejemplar);
        }
    }

    public Ejemplar guardar(Ejemplar ejemplar) {
        return ejemplarRepository.save(ejemplar);
    }

    public List<Ejemplar> listar() {
        return ejemplarRepository.findAll();
    }

    public void eliminar(long id_ejemplar) {
        if (ejemplarRepository.existsById(id_ejemplar)) {
            ejemplarRepository.deleteById(id_ejemplar);
        } else {
            throw new RuntimeException("Id no asignada " + id_ejemplar);
        }
    }

    public Ejemplar actualizar(long id_ejemplar, Ejemplar ejemplar) {
        if (ejemplarRepository.existsById(id_ejemplar)) {
            ejemplar.setId(id_ejemplar);
            return ejemplarRepository.save(ejemplar);
        } else {
            throw new RuntimeException("Id no asignada");
        }
    }
}
