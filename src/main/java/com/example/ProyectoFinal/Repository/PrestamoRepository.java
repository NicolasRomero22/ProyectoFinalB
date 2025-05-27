package com.example.ProyectoFinal.Repository;
import com.example.ProyectoFinal.Model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    @Query("SELECT p FROM Prestamo p JOIN FETCH p.usuario JOIN FETCH p.ejemplar")
    List<Prestamo> findAllWithUsuarioAndEjemplar();
}