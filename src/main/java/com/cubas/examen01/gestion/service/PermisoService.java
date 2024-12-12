package com.cubas.examen01.gestion.service;

import com.cubas.examen01.gestion.entity.Permiso;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PermisoService {
    public List<Permiso> findAll(Pageable page);
    public List<Permiso> findByNombre(String nombre, Pageable page);
    public Permiso findById(int id);
    public Permiso save(Permiso permiso);
    public void delete(int id);
}
