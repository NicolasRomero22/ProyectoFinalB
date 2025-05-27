package com.example.ProyectoFinal.Repository;
import com.example.ProyectoFinal.Model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {}
