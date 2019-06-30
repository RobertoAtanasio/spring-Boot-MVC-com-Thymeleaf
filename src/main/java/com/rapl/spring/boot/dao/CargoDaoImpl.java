package com.rapl.spring.boot.dao;

import org.springframework.stereotype.Repository;

import com.rapl.spring.boot.domain.Cargo;

@Repository
public class CargoDaoImpl extends AbstractDao<Cargo, Long> implements CargoDao {

}
