package com.rapl.spring.boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.rapl.spring.boot.dao.DepartamentoDao;
import com.rapl.spring.boot.domain.Departamento;
import com.rapl.spring.boot.repository.DepartamentoRepository;

@Service
public class DepartamentoServiceImpl implements DepartamentoService{

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private DepartamentoDao dao;
	
	@Autowired
	private DepartamentoRepository departamentoRepository;

	@Transactional(readOnly = false)
	@Override
	public void salvar(Departamento departamento) {
		Optional<Departamento> dep = departamentoRepository.findByNome(departamento.getNome());
		if (dep.isPresent()) {
			String mens = messageSource.getMessage("Departamento.existente", null, LocaleContextHolder.getLocale());
			Assert.isNull(departamento.getNome(),mens);
		}
		dao.save(departamento);		
	}

	@Transactional(readOnly = false)
	@Override
	public void editar(Departamento departamento) {
		dao.update(departamento);		
	}

	@Transactional(readOnly = false)
	@Override
	public void excluir(Long id) {
		dao.delete(id);		
	}

	@Transactional(readOnly = true)
	@Override
	public Departamento buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Departamento> buscarTodos() {
		return dao.findAll();
	}

	@Override
	public boolean departamentoTemCargos(Long id) {
		if (buscarPorId(id).getCargos().isEmpty()) {
			return false;
		}
		return true;
	}
}
