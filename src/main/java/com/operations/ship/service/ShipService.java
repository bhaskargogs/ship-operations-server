/*
 *    Copyright 2021 Bhaskar
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.operations.ship.service;

import com.operations.ship.dto.ShipDTO;
import com.operations.ship.exception.InvalidShipException;
import com.operations.ship.model.Ship;
import com.operations.ship.repository.ShipRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ShipService {

    @Autowired
    ShipRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Transactional
    public ShipDTO create(ShipDTO shipDTO) throws InvalidShipException {
        Ship insertedShip = null;
        try {
            insertedShip = repository.save(mapper.map(shipDTO, Ship.class));
        } catch (InvalidShipException ex) {
            log.error("InvalidShipException while creating Ship: {}", shipDTO, ex);
            throw new InvalidShipException(ex.getMessage());
        }
        return mapper.map(insertedShip, ShipDTO.class);
    }
}
