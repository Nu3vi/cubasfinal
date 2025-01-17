package com.cubas.examen01.gestion.service.impl;

import com.cubas.examen01.gestion.entity.Evento;
import com.cubas.examen01.gestion.exception.GeneralException;
import com.cubas.examen01.gestion.exception.NoDataFoundException;
import com.cubas.examen01.gestion.exception.ValidateException;
import com.cubas.examen01.gestion.repository.EventoRepository;
import com.cubas.examen01.gestion.service.EventoService;
import com.cubas.examen01.gestion.validator.EventoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository repository;


    @Override
    @Transactional(readOnly = true)
    public List<Evento> findAll(Pageable page) {
        try {
            List<Evento> registros = repository.findAll(page).toList();
            return registros;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidorr");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Evento> findAll() {
        try {
            List<Evento> registros = repository.findAll();
            return registros;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidorr");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Evento> findByNombre(String nombre, Pageable page) {
        try {
            List<Evento> registros = repository.findByNombreContaining(nombre, page);
            return registros;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Evento findById(int id) {
        try {
            Evento registro = repository.findById(id)
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
    public Evento save(Evento evento) {
        try {
            EventoValidator.save(evento);

            if(evento.getId() == 0) {
                Evento nuevo = repository.save(evento);
                return nuevo;
            }

            Evento registro = repository.findById(evento.getId())
                    .orElseThrow(() -> new NoDataFoundException("No existe un registro como ese id"));
            registro.setNombre(evento.getNombre());
            registro.setDescripcion(evento.getDescripcion());
            registro.setFechaInicio(evento.getFechaInicio());
            registro.setFechaFin(evento.getFechaFin());
            registro.setCosto(evento.getCosto());
            repository.save(registro);

            return registro;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }

    @Override
    public void delete(int id) {
        try {
            Evento registro = repository.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("No existe un registro como ese id"));
            repository.delete(registro);
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }
}