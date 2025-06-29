package com.example.ms_kajita.service;

import com.example.ms_kajita.entity.InsidenciaHistorico;

import java.util.List;
import java.util.Optional;

public interface InsidenciaHistoricoService {


    List<InsidenciaHistorico> listar();

    InsidenciaHistorico guardar(InsidenciaHistorico insidenciaHistorico);

    InsidenciaHistorico actualizar(InsidenciaHistorico insidenciaHistorico);

    Optional<InsidenciaHistorico> listarPorId(Integer id);

    public void eliminarPorId(Integer id);


}