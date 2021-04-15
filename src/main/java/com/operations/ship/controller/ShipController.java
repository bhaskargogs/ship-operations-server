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

package com.operations.ship.controller;

import com.operations.ship.dto.ShipCreationDTO;
import com.operations.ship.dto.ShipDTO;
import com.operations.ship.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/ships")
public class ShipController {

    @Autowired
    private ShipService service;

    @PostMapping
    public ResponseEntity<ShipDTO> create(@Valid @RequestBody ShipCreationDTO shipCreationDTO) {
        ShipDTO shipDTO = ShipCreationDTO.makeShipDTO(shipCreationDTO);

        return new ResponseEntity<ShipDTO>(service.create(shipDTO), HttpStatus.CREATED);
    }
}
