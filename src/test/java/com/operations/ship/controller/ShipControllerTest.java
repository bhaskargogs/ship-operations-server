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

import com.fasterxml.jackson.core.type.TypeReference;
import com.operations.ship.dto.ShipCreationDTO;
import com.operations.ship.dto.ShipDTO;
import com.operations.ship.dto.ShipUpdationDTO;
import com.operations.ship.exception.InvalidShipException;
import com.operations.ship.exception.ShipNotFoundException;
import com.operations.ship.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
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

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        ShipDTO shipDTO = new ShipDTO(1L, "Bermuda", 2015.23, 565.24, "AAAA-0001-A1", ZonedDateTime.now(), ZonedDateTime.now());
        when(shipController.create(any(ShipCreationDTO.class))).thenReturn(new ResponseEntity<>("Successfully created Ship with Ship ID 1", HttpStatus.CREATED));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/ships")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(shipDTO))).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(201, status);
        verify(shipController).create((any(ShipCreationDTO.class)));
        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
    }

    @Test
    public void testCreate_returns400() throws Exception {
        ShipDTO shipDTO = new ShipDTO(1L, "Bermuda", 2015.23, 565.24, "ABC-011-A1", ZonedDateTime.now(), ZonedDateTime.now());
        when(shipController.create(any(ShipCreationDTO.class))).thenThrow(InvalidShipException.class);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/ships")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(shipDTO))).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(400, status);
        verify(shipController).create((any(ShipCreationDTO.class)));
    }

    @Test
    public void testFindById_returns200() throws Exception {
        ShipDTO shipDTO = new ShipDTO(1L, "Bermuda", 2015.23, 565.24, "AAAA-0001-A1", ZonedDateTime.now(), ZonedDateTime.now());
        when(shipController.findById(1L)).thenReturn(new ResponseEntity<>(shipDTO, HttpStatus.OK));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/ships/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(200, status);
        verify(shipController).findById(1L);
        String content = result.getResponse().getContentAsString();
        assertNotNull(JsonMapper.mapFromJson(content, ShipDTO.class));
    }

    @Test
    public void testFindById_returns404() throws Exception {
        ShipDTO shipDTO = new ShipDTO(1L, "Bermuda", 2015.23, 565.24, "AAAA-0001-A1", ZonedDateTime.now(), ZonedDateTime.now());
        when(shipController.findById(2L)).thenThrow(ShipNotFoundException.class);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/ships/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(404, status);
        verify(shipController).findById(2L);
    }

    @Test
    public void testFindAllSortByName_Returns200Result() throws Exception {
        List<ShipDTO> ships = new ArrayList<>();
        ships.add(new ShipDTO(1L, "Illustria", 2154.24, 565.21, "AAAA-0021-A1", ZonedDateTime.parse("2020-10-15T18:30:49.665Z"), ZonedDateTime.parse("2021-01-05T06:45:49.587Z")));
        ships.add(new ShipDTO(2L, "Pascal Magi", 3254.24, 1565.21, "ABBA-0121-A1", ZonedDateTime.parse("2020-12-17T10:41:35.225Z"), ZonedDateTime.parse("2020-12-25T20:15:02.395Z")));
        when(shipController.findAllSorted(1, 1, "ASC", "name")).thenReturn(new ResponseEntity<>(ships, HttpStatus.OK));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/ships")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("pageNo", "1")
                .param("pageSize", "1")
                .param("sort", "name")).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(200, status);
        verify(shipController).findAllSorted(1, 1, "ASC", "name");
        assertNotNull(JsonMapper.mapListFromJson(result.getResponse().getContentAsString(), new TypeReference<List<ShipDTO>>() {
        }));
//        assertEquals(actual, ships);
    }

    @Test
    public void testFindAllSortByName_Returns200Null() throws Exception {
        when(shipController.findAllSorted(1, 1, "ASC", "name")).thenReturn(new ResponseEntity<>(null, HttpStatus.OK));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/ships")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("pageNo", "1")
                .param("pageSize", "1")
                .param("sort", "name")).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(200, status);
        verify(shipController).findAllSorted(1, 1, "ASC", "name");
        assertTrue(StringUtils.isBlank(result.getResponse().getContentAsString()));
    }

    @Test
    public void testDelete_Returns200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/ships/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDelete_Returns404() throws Exception {
        doThrow(new ShipNotFoundException("id", String.valueOf(1L))).when(shipController).delete(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/ships/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdate_Returns200() throws Exception {
        ShipDTO shipDTO = new ShipDTO(1L, "Zaloni", 2015.23, 565.24, "AAAA-0001-A1", ZonedDateTime.now(), ZonedDateTime.now());
        when(shipController.update(any(ShipUpdationDTO.class), eq(1L))).thenReturn(new ResponseEntity<>("Ship with ID " + shipDTO.getId() + " updated successfully", HttpStatus.OK));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/ships/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(shipDTO))).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(200, status);
        verify(shipController).update(any(ShipUpdationDTO.class), eq(1l));
        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
    }

    @Test
    public void testUpdate_Returns404() throws Exception {
        ShipDTO shipDTO = new ShipDTO(1L, "Zaloni", 2015.23, 565.24, "AAAA-0001-A1", ZonedDateTime.now(), ZonedDateTime.now());
        when(shipController.update(any(ShipUpdationDTO.class), eq(2L))).thenThrow(ShipNotFoundException.class);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/ships/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(JsonMapper.objectToJson(shipDTO))).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(404, status);
        verify(shipController).update(any(ShipUpdationDTO.class), eq(2l));
    }
}
