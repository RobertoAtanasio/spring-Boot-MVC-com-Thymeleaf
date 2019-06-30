package com.rapl.spring.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rapl.spring.boot.domain.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

}
