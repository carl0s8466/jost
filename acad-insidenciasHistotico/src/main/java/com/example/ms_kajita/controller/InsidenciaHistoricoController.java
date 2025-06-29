package com.example.ms_kajita.controller;

import com.example.ms_kajita.entity.InsidenciaHistorico;
import com.example.ms_kajita.service.InsidenciaHistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/asistencias")
public class InsidenciaHistoricoController {

    @Autowired
    private InsidenciaHistoricoService insidenciaHistoricoService;

    @GetMapping
    public ResponseEntity<List<InsidenciaHistorico>> listarTodas() {
        List<InsidenciaHistorico> insidenciaHistoricos = insidenciaHistoricoService.listar();
        return ResponseEntity.ok(insidenciaHistoricos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsidenciaHistorico> obtenerPorId(@PathVariable Integer id) {
        Optional<InsidenciaHistorico> asistenciaOptional = insidenciaHistoricoService.listarPorId(id);
        return asistenciaOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InsidenciaHistorico> guardar(@RequestBody InsidenciaHistorico insidenciaHistorico) {
        InsidenciaHistorico nuevaInsidenciaHistorico = insidenciaHistoricoService.guardar(insidenciaHistorico);
        return ResponseEntity.ok(nuevaInsidenciaHistorico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsidenciaHistorico> actualizar(@PathVariable Integer id, @RequestBody InsidenciaHistorico insidenciaHistorico) {
        if (!insidenciaHistoricoService.listarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        insidenciaHistorico.setIdInsidenciasHistorico(id); // Asegura que se actualice el registro correcto
        InsidenciaHistorico insidenciaHistoricoActualizada = insidenciaHistoricoService.actualizar(insidenciaHistorico);
        return ResponseEntity.ok(insidenciaHistoricoActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!insidenciaHistoricoService.listarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        insidenciaHistoricoService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
