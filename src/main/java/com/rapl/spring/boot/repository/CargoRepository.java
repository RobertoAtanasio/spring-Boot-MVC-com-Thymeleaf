package com.rapl.spring.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rapl.spring.boot.domain.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long> {

}
