package com.rapl.spring.boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rapl.spring.boot.dao.CargoDao;
import com.rapl.spring.boot.domain.Cargo;
import com.rapl.spring.boot.repository.CargoRepository;

@Service 
@Transactional(readOnly = false)
public class CargoServiceImpl implements CargoService {

	@Autowired
	private CargoDao dao;
	
	@Autowired
	private CargoRepository cargoRepository;

	@Override
	public void salvar(Cargo cargo) {
		dao.save(cargo);		
	}

	@Override
	public void editar(Cargo cargo) {
		dao.update(cargo);		
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);		
	}

	@Override 
	@Transactional(readOnly = true)
	public Cargo buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override 
	@Transactional(readOnly = true)
	public List<Cargo> buscarTodos() {
		return dao.findAll();
	}

	@Override
	public Optional<Cargo> buscarPorIdOp(Long id) {
		Optional<Cargo> cargo = cargoRepository.findById(id);
		return cargo;
	}
}
