package com.rapl.spring.boot.service;

import java.util.List;
import java.util.Optional;

import com.rapl.spring.boot.domain.Cargo;

public interface CargoService {

	void salvar(Cargo cargo);
	
	void editar(Cargo cargo);
	
	void excluir(Long id);
	
	Cargo buscarPorId(Long id);
	
	List<Cargo> buscarTodos();
	
	Optional<Cargo> buscarPorIdOp(Long id);
}
