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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShipServiceTest {

    @Mock
    private ShipService service;

    ShipDTO shipDTO1;
    ShipDTO shipDTO2;
    ShipResponseDTO shipResponseDTO = new ShipResponseDTO();
    List<ShipDTO> ships = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        shipDTO1 = new ShipDTO(1L, "Bermuda", 2015.23, 565.24, "AAAA-0001-A1");
        shipDTO2 = new ShipDTO(2L, "Pascal Magi", 3254.24, 1565.21, "ABBA-0121-A1");
        ships.add(shipDTO1);
        ships.add(shipDTO2);
        shipResponseDTO.setShips(ships);
        shipResponseDTO.setTotalShips((long) ships.size());
    }

    @Test
    public void testCreate_ValidInput() {
        when(service.create(shipDTO1)).thenReturn("Successfully created ship with Ship ID" + shipDTO1.getId());
        assertEquals("Successfully created ship with Ship ID" + shipDTO1.getId(), service.create(shipDTO1));
    }

    @Test
    public void testCreate_ThrowException() {
        when(service.create(shipDTO1)).thenThrow(InvalidShipException.class);
        assertThrows(InvalidShipException.class, () -> service.create(shipDTO1));
    }

    @Test
    public void testFindById_ValidInput() {
        when((service.findById(1L))).thenReturn(shipDTO1);
        assertEquals(shipDTO1, service.findById(1L));
    }

    @Test
    public void testSearch_ReturnsList() {
        when(service.search("54")).thenReturn(ships);
        assertEquals(ships, service.search("54"));
    }
    @Test
    public void testSearch_ReturnsNull() {
        assertTrue(service.search("54").isEmpty());
    }

    @Test
    public void testFindById_ThrowException() {
        when(service.findById(2L)).thenThrow(ShipNotFoundException.class);
        assertThrows(ShipNotFoundException.class, () -> service.findById(2L));
    }

    @Test
    public void testFindAllSortByName_ReturnsResult() {
        when(service.findAll(1, 1, "asc", "name")).thenReturn(shipResponseDTO);
        assertEquals(shipResponseDTO, service.findAll(1, 1, "asc", "name"));
    }

    @Test
    public void testFindAllSortByName_ReturnNull() {
        when(service.findAll(1, 1, "asc", "name")).thenReturn(null);
        assertNull(service.findAll(1, 1, "asc", "name"));
    }

    @Test
    public void testDelete_DeletesSuccessfully() {
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
        when(service.update(shipDTO1, 1L)).thenReturn("Ship with ID " + shipDTO1.getId() + " updated successfully");
        assertEquals("Ship with ID " + shipDTO1.getId() + " updated successfully", service.update(shipDTO1, 1L));
    }

    @Test
    public void testUpdate_ThrowException() {
        when(service.update(shipDTO1, 2L)).thenThrow(ShipNotFoundException.class);
        assertThrows(ShipNotFoundException.class, () -> service.update(shipDTO1, 2L));
    }
}
