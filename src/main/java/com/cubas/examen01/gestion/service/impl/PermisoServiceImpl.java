package com.cubas.examen01.gestion.service.impl;

import com.cubas.examen01.gestion.entity.Permiso;
import com.cubas.examen01.gestion.exception.GeneralException;
import com.cubas.examen01.gestion.exception.NoDataFoundException;
import com.cubas.examen01.gestion.exception.ValidateException;
import com.cubas.examen01.gestion.repository.PermisoRepository;
import com.cubas.examen01.gestion.service.PermisoService;
import com.cubas.examen01.gestion.validator.PermisoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermisoServiceImpl implements PermisoService {
    @Autowired
    private PermisoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Permiso> findAll(Pageable page) {
        try {
            List<Permiso> registros = repository.findAll(page).toList();
            return registros;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Permiso> findByNombre(String nombre, Pageable page) {
        try {
            List<Permiso> registros = repository.findByNombreContaining(nombre, page);
            return registros;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Permiso findById(int id) {
        try {
            Permiso registro = repository.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("No existe un registro como ese id"));
            return registro;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }

    @Override
    @Transactional
    public Permiso save(Permiso permiso) {
        try {
            PermisoValidator.save(permiso);

            if(permiso.getId() == 0) {
                Permiso nuevo = repository.save(permiso);
                return nuevo;
            }

            Permiso registro = repository.findById(permiso.getId())
                    .orElseThrow(() -> new NoDataFoundException("No existe un registro como ese id"));
            registro.setNombre(permiso.getNombre());
            repository.save(registro);

            return registro;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }

    @Override
    @Transactional
    public void delete(int id) {
        try {
            Permiso registro = repository.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("No existe un registro como ese id"));
            repository.delete(registro);
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }
}
