package com.space.service;


import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;

import java.util.List;

@Service("shipService")
@Transactional
public class ShipServiceImpl implements ShipService {

    Logger logger = LoggerFactory.getLogger(ShipServiceImpl.class);

    @Autowired
    private ShipRepository shipRepository;


    @Override
    public List<Ship> getAllShips() {
        return Lists.newArrayList(shipRepository.findAll());
    }

    @Override
    public Ship createShip(Ship ship) {
        return null;
    }

    @Override
    public Ship getShip(Long id) {
        return null;
    }

    @Override
    public Ship updateShip(Long id, Ship ship) {

        return shipRepository.;
    }

    @Override
    public void deleteShip(Long id) {
        shipRepository.deleteById(id);
    }
}
