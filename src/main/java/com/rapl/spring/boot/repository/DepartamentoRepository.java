package com.rapl.spring.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rapl.spring.boot.domain.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long>{

}
