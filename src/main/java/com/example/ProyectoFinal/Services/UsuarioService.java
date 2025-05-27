package com.example.ProyectoFinal.Services;

import com.example.ProyectoFinal.Model.Usuario;
import com.example.ProyectoFinal.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    public UsuarioRepository usuarioRepository;

    public Usuario buscar(long id_usuario) {
        if (usuarioRepository.existsById(id_usuario)) {
            return usuarioRepository.getById(id_usuario);
        } else {
            throw new RuntimeException("Id no asignada " + id_usuario);
        }
    }

    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public void eliminar(long id_usuario) {
        if (usuarioRepository.existsById(id_usuario)) {
            usuarioRepository.deleteById(id_usuario);
        } else {
            throw new RuntimeException("Id no asignada " + id_usuario);
        }
    }

    public Usuario actualizar(long id_usuario, Usuario usuario) {
        if (usuarioRepository.existsById(id_usuario)) {
            usuario.setId(id_usuario);
            return usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Id no asignada");
        }
    }
}

