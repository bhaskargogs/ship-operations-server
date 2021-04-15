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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShipServiceTest {

    @Mock
    private ShipService service;

    @Test
    public void testCreate_ValidInput() {
        ShipDTO shipDTO = new ShipDTO(1L, "Bermuda", 2015.23, 565.24, "AAAA-0001-A1", LocalDateTime.now(), LocalDateTime.now());
        when(service.create(shipDTO)).thenReturn(shipDTO);
        assertEquals(shipDTO, service.create(shipDTO));
    }

    @Test
    public void testCreate_ThrowException() {
        ShipDTO shipDTO = new ShipDTO(1L, "Bermuda", 2015.23, 565.24, "A123-0001-A1", LocalDateTime.now(), LocalDateTime.now());
        when(service.create(shipDTO)).thenThrow(InvalidShipException.class);
        assertThrows(InvalidShipException.class, () -> service.create(shipDTO));
    }


}
