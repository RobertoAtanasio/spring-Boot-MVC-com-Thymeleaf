package com.rapl.spring.boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
		Optional<Cargo> cargoOK = this.buscarPorNome(cargo.getNome());
		if (cargoOK.isPresent()) {
			Assert.isNull(cargo.getNome(),"Cargo já existente");
		}
		dao.save(cargo);
	}

	@Override
	public void editar(Cargo cargo) {
		dao.update(cargo);		
	}

	@Override
	public void excluir(Long id) {
		if (this.cargoTemFuncionarios(id)) {
			Assert.isNull(id,"Cargo não excluido. Tem funcionário(s) vinculado(s).");
		}
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
	public boolean cargoTemFuncionarios(Long id) {
		if (buscarPorId(id).getFuncionarios().isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public Optional<Cargo> buscarPorNome(String nome) {
		return cargoRepository.findByNome(nome);
	}

}
