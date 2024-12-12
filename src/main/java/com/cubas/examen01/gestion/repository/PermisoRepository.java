package com.cubas.examen01.gestion.repository;

import com.cubas.examen01.gestion.entity.Permiso;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Integer> {
    List<Permiso> findByNombreContaining(String nombre, Pageable pageable);
}
