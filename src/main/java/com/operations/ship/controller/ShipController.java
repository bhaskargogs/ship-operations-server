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
import com.operations.ship.dto.ShipResponseDTO;
import com.operations.ship.dto.ShipUpdationDTO;
import com.operations.ship.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/ships")
public class ShipController {

    @Autowired
    private ShipService service;

    @GetMapping("/{id}")
    public ResponseEntity<ShipDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ShipResponseDTO> findAllSorted(@RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
                                                         @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
                                                         @RequestParam(name = "direction", defaultValue = "asc") String direction,
                                                         @RequestParam(name = "sort", defaultValue = "id") String sort) {
        ShipResponseDTO shipLists = service.findAll(pageNo, pageSize, direction, sort);
        return new ResponseEntity<>(shipLists, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ShipDTO>> search(@RequestParam(name = "searchParam") String searchParam) {
        return new ResponseEntity<>(service.search(searchParam), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> update(@Valid @RequestBody ShipUpdationDTO shipUpdationRequest, @PathVariable Long id) {
        ShipDTO shipDTO = ShipUpdationDTO.makeShipDTO(shipUpdationRequest);
        return new ResponseEntity<>(service.update(shipDTO, id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody ShipCreationDTO shipCreationRequest) {
        ShipDTO shipDTO = ShipCreationDTO.makeShipDTO(shipCreationRequest);
        return new ResponseEntity<>(service.create(shipDTO), HttpStatus.CREATED);
    }
}
