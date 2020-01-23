package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ShipService {
    List<Ship> getAllShips(Specification<Ship> shipSpecification);
    Page<Ship> getAllShips(Specification<Ship> shipSpecification, Pageable sort);
    Ship createShip(Ship ship);
    Ship getShip(Long id);
    Ship updateShip(Long id, Ship ship);
    void deleteShip(Long id);
    Long findId (String id);

    Specification<Ship> filterByName (String name);
    Specification<Ship> filterByPlanet (String planet);
    Specification<Ship> filterByShipType (ShipType shipType);
    Specification<Ship> filterByDate (Long after, Long before);
    Specification<Ship> filterByIsUsed (Boolean isUsed);
    Specification<Ship> filterBySpeed (Double minSpeed, Double maxSpeed);
    Specification<Ship> filterByCrewSize (Integer minSize, Integer maxSize);
    Specification<Ship> filterByRating (Double minRating, Double maxRating);

}
