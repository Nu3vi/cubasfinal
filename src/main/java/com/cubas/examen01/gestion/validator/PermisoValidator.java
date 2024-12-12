package com.cubas.examen01.gestion.validator;

import com.cubas.examen01.gestion.entity.Permiso;
import com.cubas.examen01.gestion.exception.ValidateException;

public class PermisoValidator {
    public static void save(Permiso registro) {
        if(registro.getNombre() == null || registro.getNombre().trim().isEmpty()) {
            throw new ValidateException("El nombre es requerido");
        }
        if(registro.getNombre().length() > 100) {
            throw new ValidateException("El nombre no debe exceder los 100 caracteres");
        }
    }
}
