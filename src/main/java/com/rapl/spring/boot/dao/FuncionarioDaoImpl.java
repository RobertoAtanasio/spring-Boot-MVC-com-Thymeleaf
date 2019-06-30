package com.rapl.spring.boot.dao;

import org.springframework.stereotype.Repository;

import com.rapl.spring.boot.domain.Funcionario;

@Repository
public class FuncionarioDaoImpl extends AbstractDao<Funcionario, Long> implements FuncionarioDao {

}
