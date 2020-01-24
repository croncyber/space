package com.space.service;


import com.space.exception.BadRequestException;
import com.space.exception.ShipNotFoundException;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ShipServiceImpl implements ShipService {


    @Autowired
    private ShipRepository shipRepository;

    // список всех сущестующих кораблей

    @Override
    public Page<Ship> getAllShips(Specification<Ship> shipSpecification, Pageable sort) {
        return shipRepository.findAll(shipSpecification, sort);
    }

    @Override
    public List<Ship> getAllShips(Specification<Ship> shipSpecification) {
        return shipRepository.findAll(shipSpecification);
    }


    // подсчет рейтинга
    private Double calculateRating(Ship ship) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(ship.getProdDate());
        int year = cal.get(Calendar.YEAR);

        // подсчет рейтинга
        BigDecimal rating = new BigDecimal((80 * ship.getSpeed() * (ship.getUsed() ? 0.5 : 1)) / (3019 - year + 1));
        rating = rating.setScale(2, RoundingMode.HALF_UP);

        return rating.doubleValue();
    }

    // создать новый корабль
    @Override
    public Ship createShip(Ship ship) {
        // проверка параметров
        checkShipParams(ship);
        // проверка на использованый / новый
        if (ship.getUsed() == null) {
            ship.setUsed(false);
        }
        // подсчет рейтинга
        Double rating = calculateRating(ship);
        ship.setRating(rating);

        return shipRepository.saveAndFlush(ship);
    }

    // проверка характеристик корабля
    private void checkShipParams(Ship ship) {
        if (ship.getName() != null && (ship.getName().length() < 1 || ship.getName().length() > 50))
            throw new BadRequestException("Incorrect Ship.name");

        if (ship.getPlanet() != null && (ship.getPlanet().length() < 1 || ship.getPlanet().length() > 50))
            throw new BadRequestException("Incorrect Ship.planet");

        if (ship.getCrewSize() != null && (ship.getCrewSize() < 1 || ship.getCrewSize() > 9999))
            throw new BadRequestException("Incorrect Ship.crewSize");

        if (ship.getSpeed() == null) {
            throw new BadRequestException("Incorrect Ship.speed");
        }
        if (ship.getSpeed() != null && (ship.getSpeed() < 0.01D || ship.getSpeed() > 0.99D))
            throw new BadRequestException("Incorrect Ship.speed");

        if (ship.getProdDate() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(ship.getProdDate());
            if (cal.get(Calendar.YEAR) < 2800 || cal.get(Calendar.YEAR) > 3019)
                throw new BadRequestException("Incorrect Ship.date");
        }
    }

    // редактирование характеристик корабля
    @Override
    public Ship updateShip(Long id, Ship ship) {
        // проверка характеристик корабля
        checkShipParams(ship);

        if (!shipRepository.existsById(id)) {
            throw new ShipNotFoundException("Ship not found");
        }


        Ship updateShip = shipRepository.findById(id).get();

        if (ship.getName() != null)
            updateShip.setName(ship.getName());

        if (ship.getPlanet() != null)
            updateShip.setPlanet(ship.getPlanet());

        if (ship.getShipType() != null)
            updateShip.setShipType(ship.getShipType());

        if (ship.getProdDate() != null)
            updateShip.setProdDate(ship.getProdDate());

        if (ship.getSpeed() != null)
            updateShip.setSpeed(ship.getSpeed());

        if (ship.getUsed() != null)
            updateShip.setUsed(ship.getUsed());

        if (ship.getCrewSize() != null)
            updateShip.setCrewSize(ship.getCrewSize());

        // подсчет рейтинга
        Double rating = calculateRating(updateShip);
        updateShip.setRating(rating);


        return shipRepository.save(updateShip);
    }

    // получение корабля по id
    @Override
    public Ship getShip(Long id) {
        if (!shipRepository.existsById(id))
            throw new ShipNotFoundException("Ship not found");

        return shipRepository.findById(id).get();
    }

    @Override
    public void deleteShip(Long id) {
        if (shipRepository.existsById(id))
            shipRepository.deleteById(id);
        else throw new ShipNotFoundException("Ship not found");
    }

    @Override
    public Long findId(String id) {
        if (id == null || id.equals("") || id.equals("0"))
            throw new BadRequestException("ID is invalid");
        try {
            Long longId = Long.parseLong(id);
            return longId;
        } catch (NumberFormatException e) {
            throw new BadRequestException("ID isn't number", e);
        }
    }

    @Override
    public Specification<Ship> filterByName(String name) {
        return (root, query, cb) -> name == null ? null : cb.like(root.get("name"), "%" + name + "%");
    }

    @Override
    public Specification<Ship> filterByPlanet(String planet) {
        return (root, query, cb) -> planet == null ? null : cb.like(root.get("planet"), "%" + planet + "%");
    }

    @Override
    public Specification<Ship> filterByShipType(ShipType shipType) {
        return (root, query, cb) -> shipType == null ? null : cb.equal(root.get("shipType"), shipType);
    }

    @Override
    public Specification<Ship> filterByDate(Long after, Long before) {
        return (root, query, cb) -> {
            if (after == null && before == null)
                return null;
            if (after == null) {
                Date before1 = new Date(before);
                return cb.lessThanOrEqualTo(root.get("prodDate"), before1);
            }
            if (before == null) {
                Date after1 = new Date(after);
                return cb.greaterThanOrEqualTo(root.get("prodDate"), after1);
            }
            Date before1 = new Date(before);
            Date after1 = new Date(after);
            return cb.between(root.get("prodDate"), after1, before1);
        };
    }

    @Override
    public Specification<Ship> filterByIsUsed(Boolean isUsed) {
        return (root, query, cb) -> {
            if (isUsed == null)
                return null;
            if (isUsed)
                return cb.isTrue(root.get("isUsed"));
            else return cb.isFalse(root.get("isUsed"));
        };
    }

    @Override
    public Specification<Ship> filterBySpeed(Double min, Double max) {
        return (root, query, cb) -> {
            if (min == null && max == null)
                return null;
            if (min == null)
                return cb.lessThanOrEqualTo(root.get("speed"), max);
            if (max == null)
                return cb.greaterThanOrEqualTo(root.get("speed"), min);

            return cb.between(root.get("speed"), min, max);
        };
    }

    @Override
    public Specification<Ship> filterByCrewSize(Integer min, Integer max) {
        return (root, query, cb) -> {
            if (min == null && max == null)
                return null;
            if (min == null)
                return cb.lessThanOrEqualTo(root.get("crewSize"), max);
            if (max == null)
                return cb.greaterThanOrEqualTo(root.get("crewSize"), min);

            return cb.between(root.get("crewSize"), min, max);
        };
    }

    @Override
    public Specification<Ship> filterByRating(Double min, Double max) {
        return (root, query, cb) -> {
            if (min == null && max == null)
                return null;
            if (min == null)
                return cb.lessThanOrEqualTo(root.get("rating"), max);
            if (max == null)
                return cb.greaterThanOrEqualTo(root.get("rating"), min);

            return cb.between(root.get("rating"), min, max);
        };
    }
}
