package com.example.ProyectoFinal.Controller;

import com.example.ProyectoFinal.Model.*;
import com.example.ProyectoFinal.Repository.EjemplarRepository;
import com.example.ProyectoFinal.Repository.PrestamoRepository;
import com.example.ProyectoFinal.Repository.UsuarioRepository;
import com.example.ProyectoFinal.Services.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/prestamo")
public class PrestamoController {

    @Autowired
    private PrestamoRepository prestamoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;  // <-- Inyección aquí

    @Autowired
    private EjemplarRepository ejemplarRepository;

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping("/listar")
    public List<PrestamoResponse> listar() {
        List<Prestamo> prestamos = prestamoRepository.findAllWithUsuarioAndEjemplar();
        return prestamos.stream()
                .map(PrestamoResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Prestamo> buscar(@PathVariable long id) {
        try {
            return new ResponseEntity<>(prestamoService.buscar(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/guardar")
    public ResponseEntity<PrestamoResponse> guardar(@RequestBody PrestamoRequest prestamoRequest) {
        try {
            Prestamo prestamo = new Prestamo();

            // Buscar usuario y ejemplar por ID
            Usuario usuario = usuarioRepository.findById(prestamoRequest.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            Ejemplar ejemplar = ejemplarRepository.findById(prestamoRequest.getEjemplarId())
                    .orElseThrow(() -> new RuntimeException("Ejemplar no encontrado"));

            prestamo.setUsuario(usuario);
            prestamo.setEjemplar(ejemplar);

            prestamo.setFechaPrestamo((prestamoRequest.getFechaPrestamo()));
            prestamo.setFechaDevolucion((prestamoRequest.getFechaDevolucion()));

            Prestamo guardado = prestamoService.guardar(prestamo);
            return new ResponseEntity<>(new PrestamoResponse(guardado), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Prestamo> actualizar(@PathVariable long id, @RequestBody Prestamo obj) {
        try {
            return new ResponseEntity<>(prestamoService.actualizar(id, obj), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id) {
        try {
            prestamoService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

