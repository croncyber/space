package com.space.repository;


import com.space.model.Ship;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ShipRepository extends CrudRepository<Ship, Long> {

    //Get ships list
    @Override
    List<Ship> findAll();

    //Get ships count
    @Override
    long count();

    //Create ship
    @Override
    <S extends Ship> S save(S entity);

    //Get ship
    @Override
    Optional<Ship> findById(Long aLong);

    //Update ship


    //Delete ship
    @Override
    void deleteById(Long aLong);
}
