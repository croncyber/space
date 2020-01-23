package com.space.repository;


import com.space.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

// https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl/
public interface ShipRepository extends JpaRepository<Ship, Long>, JpaSpecificationExecutor<Ship> {
}
