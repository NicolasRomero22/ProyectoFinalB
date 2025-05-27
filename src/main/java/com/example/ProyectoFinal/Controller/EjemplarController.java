package com.example.ProyectoFinal.Controller;

import com.example.ProyectoFinal.Model.Ejemplar;
import com.example.ProyectoFinal.Model.EjemplarRequest;
import com.example.ProyectoFinal.Model.EjemplarResponse;
import com.example.ProyectoFinal.Model.Libro;
import com.example.ProyectoFinal.Repository.EjemplarRepository;
import com.example.ProyectoFinal.Repository.LibroRepository;
import com.example.ProyectoFinal.Services.EjemplarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ejemplar")
public class EjemplarController {

    @Autowired
    private EjemplarService ejemplarService;
    private final EjemplarRepository ejemplarRepository;
    private final LibroRepository libroRepository;

    public EjemplarController(EjemplarRepository ejemplarRepository, LibroRepository libroRepository) {
        this.ejemplarRepository = ejemplarRepository;
        this.libroRepository = libroRepository;
    }
    @GetMapping("/listar")
    public List<EjemplarResponse> listar() {
        return ejemplarRepository.findAll().stream()
                .map(e -> new EjemplarResponse(e.getId(), e.getCodigo(), e.getLibro().getId()))
                .collect(Collectors.toList());
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Ejemplar> buscar(@PathVariable long id) {
        try {
            return new ResponseEntity<>(ejemplarService.buscar(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/guardar")
    @Transactional
    public ResponseEntity<EjemplarResponse> crearEjemplar(@RequestBody EjemplarRequest request) {
        Libro libro = libroRepository.findById(request.getLibroId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setCodigo(request.getCodigo());
        ejemplar.setLibro(libro);

        if (libro.getEjemplares() == null) {
            libro.setEjemplares(new ArrayList<>());
        }

        libro.getEjemplares().add(ejemplar);
        Ejemplar guardado = ejemplarRepository.save(ejemplar);

        EjemplarResponse response = new EjemplarResponse(
                guardado.getId(),
                guardado.getCodigo(),
                libro.getId()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Ejemplar> actualizar(@PathVariable long id, @RequestBody Ejemplar obj) {
        try {
            return new ResponseEntity<>(ejemplarService.actualizar(id, obj), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id) {
        try {
            ejemplarService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

