package com.cubas.examen01.gestion.repository;

import com.cubas.examen01.gestion.entity.Categoria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    List<Categoria> findByNombreContaining(String nombre, Pageable pageable);
}
