package com.space.controller;


import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/rest")
public class ShipController {
    private final Logger logger = LoggerFactory.getLogger(ShipController.class);

    private ShipService shipService;


    @Autowired
    public void setShipService(ShipService shipService) {
        this.shipService = shipService;
    }


    @RequestMapping(value = "/ships", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Ship> getAllShips(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "planet", required = false) String planet,
                                  @RequestParam(value = "shipType", required = false) ShipType shipType,
                                  @RequestParam(value = "after", required = false) Long after,
                                  @RequestParam(value = "before", required = false) Long before,
                                  @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                  @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                                  @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                                  @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                                  @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                                  @RequestParam(value = "minRating", required = false) Double minRating,
                                  @RequestParam(value = "maxRating", required = false) Double maxRating,
                                  @RequestParam(value = "order", required = false, defaultValue = "ID") ShipOrder order,
                                  @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));

        return shipService.getAllShips(shipService.filterByName(name)
                .and(shipService.filterByPlanet(planet))
                .and(shipService.filterByShipType(shipType))
                .and(shipService.filterByDate(after, before))
                .and(shipService.filterByIsUsed(isUsed))
                .and(shipService.filterBySpeed(minSpeed, maxSpeed))
                .and(shipService.filterByCrewSize(minCrewSize, maxCrewSize))
                .and(shipService.filterByRating(minRating, maxRating)), pageable).getContent();
    }

    @RequestMapping(value = "/ships/count", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Integer getCount(@RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "planet", required = false) String planet,
                            @RequestParam(value = "shipType", required = false) ShipType shipType,
                            @RequestParam(value = "after", required = false) Long after,
                            @RequestParam(value = "before", required = false) Long before,
                            @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                            @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                            @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                            @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                            @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                            @RequestParam(value = "minRating", required = false) Double minRating,
                            @RequestParam(value = "maxRating", required = false) Double maxRating) {

        return shipService.getAllShips(
                Specification.where(shipService.filterByName(name)
                        .and(shipService.filterByPlanet(planet)))
                        .and(shipService.filterByShipType(shipType))
                        .and(shipService.filterByDate(after, before))
                        .and(shipService.filterByIsUsed(isUsed))
                        .and(shipService.filterBySpeed(minSpeed, maxSpeed))
                        .and(shipService.filterByCrewSize(minCrewSize, maxCrewSize))
                        .and(shipService.filterByRating(minRating, maxRating))).size();
    }


    @PostMapping(value = "/ships")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Ship createShip(@RequestBody Ship ship) {
        logger.info("Creating ship: " + ship);
        logger.info("Ship created successfully with info: " + ship);
        return  shipService.createShip(ship);
    }

    @GetMapping(value = "ships/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Ship getShip(@PathVariable(value = "id") String id) {
        Long longId = shipService.findId(id);
        return shipService.getShip(longId);
    }

    @DeleteMapping(value = "ships/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteShip(@PathVariable(value = "id") String id) {
        Long idLong = shipService.findId(id);
        logger.info("Deleting ship with id: " + idLong);
        shipService.deleteShip(idLong);
        logger.info("Ship deleted successfully");
    }

    @PostMapping(value = "ships/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Ship updateShip(@PathVariable(value = "id") String id, @RequestBody Ship ship) {
        logger.info("Updating ship: " + ship);
        Long idLong = shipService.findId(id);
        logger.info("Ship with id:" + idLong + " updated successfully with info: " + ship);
        return shipService.updateShip(idLong, ship);
    }


}
