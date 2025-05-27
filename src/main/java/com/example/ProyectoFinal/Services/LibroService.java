package com.example.ProyectoFinal.Services;

import com.example.ProyectoFinal.Model.Libro;
import com.example.ProyectoFinal.Repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {
    @Autowired
    public LibroRepository libroRepository;

    public Libro buscar(long id_libro) {
        if (libroRepository.existsById(id_libro)) {
            return libroRepository.getById(id_libro);
        } else {
            throw new RuntimeException("Id no asignada " + id_libro);
        }
    }

    public Libro guardar(Libro libro) {
        return libroRepository.save(libro);
    }

    public List<Libro> listar() {
        return libroRepository.findAll();
    }

    public void eliminar(long id_libro) {
        if (libroRepository.existsById(id_libro)) {
            libroRepository.deleteById(id_libro);
        } else {
            throw new RuntimeException("Id no asignada " + id_libro);
        }
    }

    public Libro actualizar(long id_libro, Libro libro) {
        if (libroRepository.existsById(id_libro)) {
            libro.setId(id_libro);
            return libroRepository.save(libro);
        } else {
            throw new RuntimeException("Id no asignada");
        }
    }
}