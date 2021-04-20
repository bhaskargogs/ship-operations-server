package com.operations.ship.cucumber.entity;

import com.operations.ship.cucumber.client.ShipClient;
import com.operations.ship.dto.ShipCreationDTO;
import com.operations.ship.dto.ShipDTO;
import com.operations.ship.dto.ShipResponseDTO;
import com.operations.ship.dto.ShipUpdationDTO;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Slf4j
public class ShipEntity {
    private Long id;
    private String name;
    private double length;
    private double width;
    private String code;
    private ZonedDateTime createdDate;
    private ZonedDateTime updatedDate;

    public HttpResponse<JsonNode> findShipById(long id) throws IOException {
        return ShipClient.findById(id);
    }

    public HttpResponse<JsonNode> searchShips(String searchParam) throws IOException {
        return ShipClient.searchShips(searchParam);
    }

    public List<ShipDTO> findShipLists(int pageNo, int pageSize, String direction, String sortField) throws IOException {
        ShipResponseDTO responseDTO = ShipClient.listShips(pageNo, pageSize, direction, sortField);
        List<ShipDTO> shipDTOS = responseDTO.getShips();

        if (!responseDTO.getShips().isEmpty()) {
            shipDTOS = responseDTO.getShips();
            log.info("findShipLists(): found Ships lists: " + shipDTOS.toString());
        } else {
            log.info("findShipLists(): Ships list is empty.");
        }
        return shipDTOS;
    }

    public HttpResponse createShip(ShipCreationDTO creationDTO) {
        String response = ShipClient.createShip(creationDTO).getBody().toString();
        if (!response.isEmpty()) {
            log.info("createShip(): " + response.toString());
        } else {
            log.info("createShip(): Ship not created");
        }
        return ShipClient.createShip(creationDTO);
    }

    public HttpResponse updateShip(ShipUpdationDTO updationDTO) {
        String response = ShipClient.updateShip(updationDTO).getBody().toString();
        if (!response.isEmpty()) {
            log.info("createShip(): " + response.toString());
        } else {
            log.info("createShip(): Ship not created");
        }
        return ShipClient.updateShip(updationDTO);
    }

    public int deleteShip(Long id) {
        HttpResponse response = ShipClient.delete(id);
        return response.getStatus();
    }

}