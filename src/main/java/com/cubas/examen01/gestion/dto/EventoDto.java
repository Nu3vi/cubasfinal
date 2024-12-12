package com.cubas.examen01.gestion.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventoDto {
    private int id;
    private String nombre;
    private String Descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private Double costo;
}

