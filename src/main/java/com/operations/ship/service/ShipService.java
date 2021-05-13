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
import com.operations.ship.dto.ShipResponseDTO;
import com.operations.ship.exception.InvalidShipException;
import com.operations.ship.exception.ShipNotFoundException;
import com.operations.ship.model.Ship;
import com.operations.ship.model.Ship_;
import com.operations.ship.repository.ShipRepository;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipService {

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private ModelMapper mapper;

    @Transactional
    public ShipDTO findById(Long id) {
        return mapper.map(ShipService.findById(shipRepository, id), ShipDTO.class);
    }

    @Transactional
    public ShipResponseDTO findAll(int pageNo, int pageSize, String direction, String fieldName) {
        if (!direction.equals("asc") && !direction.equals("desc")) {
            throw new InvalidShipException("Invalid Direction value: ", direction);
        }
        Pageable paging = PageRequest.of(pageNo, pageSize, (direction.equals("asc")) ? Sort.by(fieldName).ascending() : Sort.by(fieldName).descending());
        Page<Ship> ships = shipRepository.findAll(paging);
        ShipResponseDTO shipResponseDTO = new ShipResponseDTO();
        if (ships.hasContent()) {
            shipResponseDTO.setShips(ships.getContent().stream()
                    .map(ship -> mapper.map(ship, ShipDTO.class))
                    .collect(Collectors.toList()));
            shipResponseDTO.setTotalShips(ships.getTotalElements());
        } else {
            shipResponseDTO.setShips(new ArrayList<>());
            shipResponseDTO.setTotalShips(null);
        }
        return shipResponseDTO;
    }

    @Transactional
    public String create(ShipDTO shipDTO) throws InvalidShipException {
        Ship insertedShip = null;
        try {
            insertedShip = shipRepository.save(mapper.map(shipDTO, Ship.class));
        } catch (InvalidShipException ex) {
            throw new InvalidShipException("InvalidShipException while creating Ship: " + shipDTO);
        }
        return "Successfully Created Ship with ID " + insertedShip.getId();
    }

    @Transactional
    public void delete(Long id) throws ShipNotFoundException {
        try {
            shipRepository.delete(ShipService.findById(shipRepository, id));
        } catch (ShipNotFoundException ex) {
            throw new ShipNotFoundException("ShipNotFoundException while deleting Ship of id: {}", id.toString());
        }
    }

    @Transactional
    public String update(ShipDTO updatedShipDTO, Long id) {
        Ship shipToUpdate = ShipService.findById(shipRepository, id);
        if (StringUtils.isNotBlank(updatedShipDTO.getName())) {
            shipToUpdate.setName(updatedShipDTO.getName());
        }
        shipToUpdate.setLength(updatedShipDTO.getLength());
        shipToUpdate.setWidth(updatedShipDTO.getWidth());
        if (StringUtils.isNotBlank(updatedShipDTO.getCode())) {
            shipToUpdate.setCode(updatedShipDTO.getCode());
        }
        shipToUpdate.setUpdatedDate(ZonedDateTime.now());
        shipRepository.save(shipToUpdate);
        return "Ship with ID " + shipToUpdate.getId() + " updated successfully";
    }

    @Transactional
    public List<ShipDTO> search(String searchParam) {
        Specification<Ship> specs = (searchParam.matches("^[0-9]+.[0-9]*$")) ?
                Specification.where(lengthLike(Double.parseDouble(searchParam)).or(idEqual((long) Double.parseDouble(searchParam)))
                        .or(widthLike(Double.parseDouble(searchParam))).or(codeLike(searchParam.toUpperCase()))) :
                Specification.where(nameLike(searchParam).or(codeLike(searchParam.toUpperCase())));
        List<Ship> ships = shipRepository.findAll(specs);
        return ships.stream().map(ship -> mapper.map(ship, ShipDTO.class)).collect(Collectors.toList());
    }

    private Specification<Ship> idEqual(Long id) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get(Ship_.ID), id);
    }

    private Specification<Ship> nameLike(String name) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get(Ship_.NAME), "%" + name + "%");
    }

    private Specification<Ship> lengthLike(double length) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get(Ship_.LENGTH), length);
    }

    private Specification<Ship> widthLike(double width) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get(Ship_.WIDTH), width);
    }

    private Specification<Ship> codeLike(String code) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get(Ship_.CODE), "%" + code + "%");
    }

    private static Ship findById(ShipRepository repository, Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ShipNotFoundException("id", String.valueOf(id)));
    }
}
