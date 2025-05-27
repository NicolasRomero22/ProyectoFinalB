package com.example.ProyectoFinal.Services;

import com.example.ProyectoFinal.Model.Prestamo;
import com.example.ProyectoFinal.Repository.EjemplarRepository;
import com.example.ProyectoFinal.Repository.PrestamoRepository;
import com.example.ProyectoFinal.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestamoService {
    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EjemplarRepository ejemplarRepository;

    public Prestamo buscar(long id_prestamo) {
        if (prestamoRepository.existsById(id_prestamo)) {
            return prestamoRepository.getById(id_prestamo);
        } else {
            throw new RuntimeException("Id no asignada " + id_prestamo);
        }
    }

    public Prestamo guardar(Prestamo obj) {
        // Recuperar el usuario real
        if (obj.getUsuario() != null && obj.getUsuario().getId() != null) {
            obj.setUsuario(usuarioRepository.findById(obj.getUsuario().getId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
        }

        // Recuperar el ejemplar real
        if (obj.getEjemplar() != null && obj.getEjemplar().getId() != null) {
            obj.setEjemplar(ejemplarRepository.findById(obj.getEjemplar().getId())
                    .orElseThrow(() -> new RuntimeException("Ejemplar no encontrado")));
        }

        return prestamoRepository.save(obj);
    }

    public List<Prestamo> listar() {
        return prestamoRepository.findAll();
    }

    public void eliminar(long id_prestamo) {
        if (prestamoRepository.existsById(id_prestamo)) {
            prestamoRepository.deleteById(id_prestamo);
        } else {
            throw new RuntimeException("Id no asignada " + id_prestamo);
        }
    }

    public Prestamo actualizar(long id_prestamo, Prestamo prestamo) {
        if (prestamoRepository.existsById(id_prestamo)) {
            prestamo.setId(id_prestamo);
            return prestamoRepository.save(prestamo);
        } else {
            throw new RuntimeException("Id no asignada");
        }
    }
}