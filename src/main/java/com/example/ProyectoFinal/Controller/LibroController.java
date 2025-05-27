package com.example.ProyectoFinal.Controller;

import com.example.ProyectoFinal.Model.*;
import com.example.ProyectoFinal.Repository.AutorRepository;
import com.example.ProyectoFinal.Repository.CategoriaRepository;
import com.example.ProyectoFinal.Repository.EjemplarRepository;
import com.example.ProyectoFinal.Repository.LibroRepository;
import com.example.ProyectoFinal.Services.LibroService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/api/libro")
public class LibroController {

    @Autowired
    private LibroService libroService;
    private final LibroRepository libroRepository;
    private final CategoriaRepository categoriaRepository;
    private final AutorRepository autorRepository;
    private final EjemplarRepository ejemplarRepository;
    @GetMapping("/listar")
    public ResponseEntity<List<Libro>> listar() {
        return new ResponseEntity<>(libroService.listar(), HttpStatus.OK);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Libro> buscar(@PathVariable long id) {
        try {
            return new ResponseEntity<>(libroService.buscar(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/guardar")
    @Transactional
    public ResponseEntity<Libro> crearLibro(@RequestBody LibroRequest request) {
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        Autor autor = autorRepository.findById(request.getAutorId())
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));

        Libro libro = new Libro();
        libro.setTitulo(request.getTitulo());
        libro.setCategoria(categoria);
        libro.setAutor(autor);
        Libro libroGuardado = libroRepository.save(libro);

        return ResponseEntity.ok(libroGuardado);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Libro> actualizar(@PathVariable long id, @RequestBody Libro obj) {
        try {
            return new ResponseEntity<>(libroService.actualizar(id, obj), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id) {
        try {
            libroService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

