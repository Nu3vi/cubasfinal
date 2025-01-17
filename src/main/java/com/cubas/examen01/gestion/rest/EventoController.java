package com.cubas.examen01.gestion.rest;

import com.cubas.examen01.gestion.converter.EventoConverter;
import com.cubas.examen01.gestion.dto.EventoDto;
import com.cubas.examen01.gestion.entity.Evento;
import com.cubas.examen01.gestion.service.EventoService;
import com.cubas.examen01.gestion.service.PdfService;
import com.cubas.examen01.gestion.util.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/v1/eventos")
public class EventoController {
    @Autowired
    private EventoService service;

    @Autowired
    private EventoConverter converter;

    @Autowired
    private PdfService pdfService;

    @GetMapping
    public ResponseEntity<List<Evento>> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize
    ) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<EventoDto> clientes = converter.fromEntities(service.findAll());

        return new WrapperResponse(true, "success", clientes).createResponse(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EventoDto> create (@RequestBody EventoDto evento) {
        Evento entity = converter.fromDTO(evento);
        EventoDto dto = converter.fromEntity(service.save(entity));

        return new WrapperResponse(true, "success", dto).createResponse(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoDto> update (@PathVariable("id") int id, @RequestBody EventoDto cliente) {
        Evento entity = converter.fromDTO(cliente);
        EventoDto dto = converter.fromEntity(service.save(entity));

        return new WrapperResponse(true, "success", dto).createResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete (@PathVariable("id") int id) {
        service.delete(id);
//        return ResponseEntity.ok(null);
        return new WrapperResponse(true, "success", null).createResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDto> findById (@PathVariable("id") int id) {
        EventoDto dto = converter.fromEntity(service.findById(id));

        return new WrapperResponse(true, "success", dto).createResponse(HttpStatus.OK);
    }

    @GetMapping("/report")
    public ResponseEntity<byte[]> generateReport() {
        List<EventoDto> eventos = converter.fromEntities(service.findAll());

        LocalDateTime fecha = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaHora = fecha.format(formato);

        // crear el contexto de Thymeleaf con los datos
        Context context = new Context();
        context.setVariable("registros", eventos);
        context.setVariable("fecha", fechaHora);

        // Llamar al servicio PdfService para generar el PDF
        byte[] pdfBytes = pdfService.createPdf("eventoReporte", context);

        // Configurar los encabezados de la respuesta HTTP para devolver el PDF
        // import org.springframework.http.HttpHeaders;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "reporte_eventos.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}