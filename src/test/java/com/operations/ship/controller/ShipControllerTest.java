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
import com.operations.ship.exception.InvalidShipException;
import com.operations.ship.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ShipController.class)
@Slf4j
public class ShipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShipController shipController;

    @Test
    public void testCreate_returns201() throws Exception {
        ShipDTO shipDTO = new ShipDTO(1L, "Bermuda", 2015.23, 565.24, "AAAA-0001-A1", LocalDateTime.now(), LocalDateTime.now());
        when(shipController.create(any(ShipCreationDTO.class))).thenReturn(new ResponseEntity<>(shipDTO, HttpStatus.CREATED));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/ships")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(shipDTO))).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(201, status);
        verify(shipController).create((any(ShipCreationDTO.class)));
        String content = result.getResponse().getContentAsString();
        assertNotNull(JsonMapper.mapFromJson(content, ShipDTO.class));
    }

    @Test
    public void testCreate_returns400() throws Exception {
        ShipDTO shipDTO = new ShipDTO(1L, "Bermuda", 2015.23, 565.24, "ABC-011-A1", LocalDateTime.now(), LocalDateTime.now());
        doThrow(InvalidShipException.class).when(shipController).create(any(ShipCreationDTO.class));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/ships")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(400, status);
    }

}
