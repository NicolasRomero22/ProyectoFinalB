package com.example.ProyectoFinal.Services;

import com.example.ProyectoFinal.Model.Categoria;
import com.example.ProyectoFinal.Repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    @Autowired
    public CategoriaRepository categoriaRepository;

    public Categoria buscar(long id_categoria) {
        if (categoriaRepository.existsById(id_categoria)) {
            return categoriaRepository.getById(id_categoria);
        } else {
            throw new RuntimeException("Id no asignada " + id_categoria);
        }
    }

    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    public void eliminar(long id_categoria) {
        if (categoriaRepository.existsById(id_categoria)) {
            categoriaRepository.deleteById(id_categoria);
        } else {
            throw new RuntimeException("Id no asignada " + id_categoria);
        }
    }

    public Categoria actualizar(long id_categoria, Categoria categoria) {
        if (categoriaRepository.existsById(id_categoria)) {
            categoria.setId(id_categoria);
            return categoriaRepository.save(categoria);
        } else {
            throw new RuntimeException("Id no asignada");
        }
    }
}
