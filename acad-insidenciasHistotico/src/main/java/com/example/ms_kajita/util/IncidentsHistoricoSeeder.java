package com.example.ms_kajita.util; // Make sure this package is correct

import com.example.ms_kajita.entity.InsidenciaHistorico; // Make sure this is the correct package for your entity
import com.example.ms_kajita.repository.InsidenciaHistoricoRepository; // Make sure this is the correct package for your repository
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class IncidentsHistoricoSeeder implements CommandLineRunner {

    private final InsidenciaHistoricoRepository insidenciaHistoricoRepository;

    public IncidentsHistoricoSeeder(InsidenciaHistoricoRepository insidenciaHistoricoRepository) {
        this.insidenciaHistoricoRepository = insidenciaHistoricoRepository;
    }

    @Override
    public void run(String... args) {
        // Only insert data if the table is empty
        if (insidenciaHistoricoRepository.count() == 0) {
            // Creating instances of InsidenciasHistorico
            InsidenciaHistorico i1 = new InsidenciaHistorico();
            // idInsidenciasHistorico is @GeneratedValue, so we don't set it here
            i1.setFechaRegistro(LocalDateTime.now().minusHours(2));
            i1.setTitulo("Problema con Acceso a Plataforma");
            i1.setDescripccion("El estudiante X no puede acceder al campus virtual desde ayer.");
            i1.setUsuarioIdUsuario(1001L); // Example user ID (Long)
            i1.setDocenteIdDocente(201); // Example teacher ID (Integer)
            i1.setPlanAcademicoIdPlanAcademico(301); // Example academic plan ID (Integer)
            // @Transient fields (usuarioNombre, docenteNombre, planAcademicoNombre) are not set here.
            // They are populated by your service layer when data is retrieved.

            InsidenciaHistorico i2 = new InsidenciaHistorico();
            i2.setFechaRegistro(LocalDateTime.now().minusDays(1));
            i2.setTitulo("Retraso en Entrega de Tarea");
            i2.setDescripccion("Docente Y reporta que varios alumnos no entregaron la tarea a tiempo.");
            i2.setUsuarioIdUsuario(1002L);
            i2.setDocenteIdDocente(202);
            i2.setPlanAcademicoIdPlanAcademico(302);

            InsidenciaHistorico i3 = new InsidenciaHistorico();
            i3.setFechaRegistro(LocalDateTime.now().minusDays(3));
            i3.setTitulo("Duda sobre Calificación");
            i3.setDescripccion("Estudiante Z tiene una consulta sobre la calificación de su último examen.");
            i3.setUsuarioIdUsuario(1003L);
            i3.setDocenteIdDocente(203);
            i3.setPlanAcademicoIdPlanAcademico(301); // Same plan ID as i1 for variety

            // Save the entities to the database
            insidenciaHistoricoRepository.save(i1);
            insidenciaHistoricoRepository.save(i2);
            insidenciaHistoricoRepository.save(i3);

            System.out.println("Datos de incidencias históricas de ejemplo insertados correctamente.");
        } else {
            System.out.println("Las incidencias históricas ya existen, no se insertaron datos.");
        }
    }
}
