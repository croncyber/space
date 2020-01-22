package com.space.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("springJpaShipService")
@Repository
@Transactional
public class ShipServiceImpl implements ShipService {
    Logger logger = LoggerFactory.getLogger(ShipServiceImpl.class);

}
