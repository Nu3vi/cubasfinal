package com.cubas.examen01.gestion.validator;

import com.cubas.examen01.gestion.entity.Evento;
import com.cubas.examen01.gestion.exception.ValidateException;

public class EventoValidator {
    public static void save(Evento registro){
        if(registro.getNombre() == null || registro.getNombre().trim().isEmpty()) {
            throw new ValidateException("El nombre es requerido");
        }
        if(registro.getNombre().length() > 70) {
            throw new ValidateException("El nombre no debe exceder los 70 caracteres");
        }
        if(registro.getFechaInicio() == null) {
            throw new ValidateException("La fecha de inicio es requerida");
        }
        if(registro.getFechaFin() == null) {
            throw new ValidateException("La fecha fin es requerida");
        }
        if(registro.getCosto() < 0) {
            throw new ValidateException("Ingrese un valor válido");
        }
    }
}
