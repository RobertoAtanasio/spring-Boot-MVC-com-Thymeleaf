package com.rapl.spring.boot.service;

import java.util.List;

import com.rapl.spring.boot.domain.Departamento;

public interface DepartamentoService {

	void salvar(Departamento departamento);

    void editar(Departamento departamento);

    void excluir(Long id);

    Departamento buscarPorId(Long id);
    
    List<Departamento> buscarTodos();
}
