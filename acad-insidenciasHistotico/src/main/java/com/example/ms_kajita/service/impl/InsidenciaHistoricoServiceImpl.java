package com.example.ms_kajita.service.impl;

import com.example.ms_kajita.dto.DocenteDto;
import com.example.ms_kajita.dto.PlanAcademicoDto;
import com.example.ms_kajita.dto.UsuarioDto;
import com.example.ms_kajita.entity.InsidenciaHistorico;
import com.example.ms_kajita.feing.DocenteFeing;
import com.example.ms_kajita.feing.PlanAcademicoFeing;
import com.example.ms_kajita.feing.UsuarioFeign;
import com.example.ms_kajita.repository.InsidenciaHistoricoRepository;
import com.example.ms_kajita.service.InsidenciaHistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InsidenciaHistoricoServiceImpl implements InsidenciaHistoricoService {

    @Autowired
    private InsidenciaHistoricoRepository insidenciaHistoricoRepository;
    @Autowired
    private UsuarioFeign usuarioFeign; // Correcto: inyectaste catalogoFeign
    @Autowired
    private DocenteFeing docenteFeing;
    @Autowired
    private PlanAcademicoFeing planAcademicoFeing;

    @Override
    public List<InsidenciaHistorico> listar() {
        return insidenciaHistoricoRepository.findAll().stream()
                .map(asistencia -> {
                    asistencia.setUsuarioNombre(obtenerUsuarioNombre(asistencia.getUsuarioIdUsuario().intValue()));
                    asistencia.setDocenteNombre(obtenerDocenteNombre(asistencia.getDocenteIdDocente()));
                    asistencia.setPlanAcademicoNombre(obtenerPlanAcademicoNombre(asistencia.getPlanAcademicoIdPlanAcademico()));
                    return asistencia;
                })
                .collect(Collectors.toList());
    }

    @Override
    public InsidenciaHistorico guardar(InsidenciaHistorico insidenciaHistorico) {
        InsidenciaHistorico insidenciaHistoricoGuardada = insidenciaHistoricoRepository.save(insidenciaHistorico);
        insidenciaHistoricoGuardada.setUsuarioNombre(obtenerUsuarioNombre(insidenciaHistoricoGuardada.getUsuarioIdUsuario().intValue()));
        insidenciaHistoricoGuardada.setDocenteNombre(obtenerDocenteNombre(insidenciaHistoricoGuardada.getDocenteIdDocente()));
        insidenciaHistoricoGuardada.setPlanAcademicoNombre(obtenerPlanAcademicoNombre(insidenciaHistoricoGuardada.getPlanAcademicoIdPlanAcademico()));
        return insidenciaHistoricoGuardada;
    }

    @Override
    public InsidenciaHistorico actualizar(InsidenciaHistorico insidenciaHistorico) {
        InsidenciaHistorico insidenciaHistoricoActualizada = insidenciaHistoricoRepository.save(insidenciaHistorico);
        insidenciaHistoricoActualizada.setUsuarioNombre(obtenerUsuarioNombre(insidenciaHistoricoActualizada.getUsuarioIdUsuario().intValue()));
        insidenciaHistoricoActualizada.setDocenteNombre(obtenerDocenteNombre(insidenciaHistoricoActualizada.getDocenteIdDocente()));
        insidenciaHistoricoActualizada.setPlanAcademicoNombre(obtenerPlanAcademicoNombre(insidenciaHistoricoActualizada.getPlanAcademicoIdPlanAcademico()));
        return insidenciaHistoricoActualizada;
    }

    @Override
    public Optional<InsidenciaHistorico> listarPorId(Integer id) {
        Optional<InsidenciaHistorico> asistenciaOptional = insidenciaHistoricoRepository.findById(id);
        if (asistenciaOptional.isPresent()) {
            InsidenciaHistorico insidenciaHistorico = asistenciaOptional.get();
            insidenciaHistorico.setUsuarioNombre(obtenerUsuarioNombre(insidenciaHistorico.getUsuarioIdUsuario().intValue()));
            insidenciaHistorico.setDocenteNombre(obtenerDocenteNombre(insidenciaHistorico.getDocenteIdDocente()));
            insidenciaHistorico.setPlanAcademicoNombre(obtenerPlanAcademicoNombre(insidenciaHistorico.getPlanAcademicoIdPlanAcademico()));
            return Optional.of(insidenciaHistorico);
        }
        return Optional.empty();
    }

    @Override
    public void eliminarPorId(Integer id) {
        insidenciaHistoricoRepository.deleteById(id);
    }


    private String obtenerUsuarioNombre(Integer idUsuario) {
        try {
            UsuarioDto usuarioDto = usuarioFeign.buscarUsuario(idUsuario).getBody();
            if (usuarioDto != null) {
                return usuarioDto.getUser();
            }
        } catch (Exception e) {
            return "Usuario no encontrado";
        }
        return null;
    }

    private String obtenerDocenteNombre(Integer idDocente) {
        try {
            DocenteDto docenteDto = docenteFeing.buscarDocente(idDocente).getBody();
            if (docenteDto != null) {
                return docenteDto.getNombre();
            }
        } catch (Exception e) {
            return "Docente no encontrado";
        }
        return null;
    }

    private String obtenerPlanAcademicoNombre(Integer idPlan) {
        try {
            PlanAcademicoDto planAcademicoDto = planAcademicoFeing.buscarPlanAcademico(idPlan).getBody();
            if (planAcademicoDto != null) {
                return planAcademicoDto.getNombrePlan();
            }
        } catch (Exception e) {
            return "Plan académico no encontrado";
        }
        return null;
    }
}