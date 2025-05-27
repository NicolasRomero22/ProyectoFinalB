package com.example.ProyectoFinal.Services;

import com.example.ProyectoFinal.Model.Autor;
import com.example.ProyectoFinal.Repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {
    @Autowired
    public AutorRepository autorRepository;

    public Autor buscar(long id_autor) {
        if (autorRepository.existsById(id_autor)) {
            return autorRepository.getById(id_autor);
        } else {
            throw new RuntimeException("Id no asignada " + id_autor);
        }
    }

    public Autor guardar(Autor autor) {
        return autorRepository.save(autor);
    }

    public List<Autor> listar() {
        return autorRepository.findAll();
    }

    public void eliminar(long id_autor) {
        if (autorRepository.existsById(id_autor)) {
            autorRepository.deleteById(id_autor);
        } else {
            throw new RuntimeException("Id no asignada " + id_autor);
        }
    }

    public Autor actualizar(long id_autor, Autor autor) {
        if (autorRepository.existsById(id_autor)) {
            autor.setId(id_autor);
            return autorRepository.save(autor);
        } else {
            throw new RuntimeException("Id no asignada");
        }
    }
}
