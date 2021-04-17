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
import com.operations.ship.exception.ShipNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShipServiceTest {

    @Mock
    private ShipService service;

    @Test
    public void testCreate_ValidInput() {
        ShipDTO shipDTO = new ShipDTO(1L, "Bermuda", 2015.23, 565.24, "AAAA-0001-A1", ZonedDateTime.now(), ZonedDateTime.now());
        when(service.create(shipDTO)).thenReturn("Successfully created ship with Ship ID" + shipDTO.getId());
        assertEquals("Successfully created ship with Ship ID" + shipDTO.getId(), service.create(shipDTO));
    }

    @Test
    public void testCreate_ThrowException() {
        ShipDTO shipDTO = new ShipDTO(1L, "Bermuda", 2015.23, 565.24, "A123-0001-A1", ZonedDateTime.now(), ZonedDateTime.now());
        when(service.create(shipDTO)).thenThrow(InvalidShipException.class);
        assertThrows(InvalidShipException.class, () -> service.create(shipDTO));
    }

    @Test
    public void testFindById_ValidInput() {
        ShipDTO shipDTO = new ShipDTO(1L, "Bermuda", 2015.23, 565.24, "AAAA-0001-A1", ZonedDateTime.now(), ZonedDateTime.now());
        when((service.findById(1L))).thenReturn(shipDTO);
        assertEquals(shipDTO, service.findById(1L));
    }

    @Test
    public void testFindById_ThrowException() {
        ShipDTO shipDTO = new ShipDTO(1L, "Bermuda", 2015.23, 565.24, "AAAA-0001-A1", ZonedDateTime.now(), ZonedDateTime.now());
        when(service.findById(2L)).thenThrow(ShipNotFoundException.class);
        assertThrows(ShipNotFoundException.class, () -> service.findById(2l));
    }

    @Test
    public void testFindAllSortByName_ReturnsResult() {
        List<ShipDTO> ships = new ArrayList<>();
        ships.add(new ShipDTO(1L, "Illustria", 2154.24, 565.21, "AAAA-0021-A1", ZonedDateTime.parse("2020-10-15T18:30:49.665Z"), ZonedDateTime.parse("2021-01-05T06:45:49.587Z")));
        ships.add(new ShipDTO(2L, "Pascal Magi", 3254.24, 1565.21, "ABBA-0121-A1", ZonedDateTime.parse("2020-12-17T10:41:35.225Z"), ZonedDateTime.parse("2020-12-25T20:15:02.395Z")));
        when(service.findAll(1, 1, "ASC", "name")).thenReturn(ships);
        assertEquals(ships, service.findAll(1, 1, "ASC", "name"));
    }

    @Test
    public void testFindAllSortByName_ReturnNull() {
        when(service.findAll(1, 1, "ASC", "name")).thenReturn(null);
        assertNull(service.findAll(1, 1, "ASC", "name"));
    }

    @Test
    public void testDelete_DeletesSuccessfully() {
        ShipDTO shipDTO = new ShipDTO(1L, "Bermuda", 2015.23, 565.24, "AAAA-0001-A1", ZonedDateTime.now(), ZonedDateTime.now());
        service.delete(1L);
        verify(service, times(1)).delete(1L);
    }

    @Test
    public void testDelete_ThrowException() {
        doThrow(ShipNotFoundException.class).when(service).delete(1L);
        assertThrows(ShipNotFoundException.class, () -> service.delete(1L));
    }

    @Test
    public void testUpdate_UpdateSuccessfully() {
        ShipDTO shipDTO = new ShipDTO(1L, "Bermuda", 2015.23, 565.24, "AAAA-0001-A1", ZonedDateTime.now(), ZonedDateTime.now());
        when(service.update(shipDTO, 1L)).thenReturn("Ship with ID " + shipDTO.getId() + " updated successfully");
        assertEquals("Ship with ID " + shipDTO.getId() + " updated successfully", service.update(shipDTO, 1L));
    }

    @Test
    public void testUpdate_ThrowException() {
        ShipDTO shipDTO = new ShipDTO(1L, "Bermuda", 2015.23, 565.24, "AAAA-0001-A1", ZonedDateTime.now(), ZonedDateTime.now());
        when(service.update(shipDTO, 2L)).thenThrow(ShipNotFoundException.class);
        assertThrows(ShipNotFoundException.class, () -> service.update(shipDTO, 2L));
    }
}
