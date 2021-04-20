package com.operations.ship.cucumber.client;

import com.operations.ship.dto.ShipCreationDTO;
import com.operations.ship.dto.ShipResponseDTO;
import com.operations.ship.dto.ShipUpdationDTO;
import com.operations.ship.exception.InvalidShipException;
import com.operations.ship.exception.ShipNotFoundException;
import com.operations.ship.util.JsonMapper;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;

@Slf4j
public abstract class ShipClient {
    private static final String SHIP_URI = "http://localhost:8080/ships";

    public static HttpResponse createShip(ShipCreationDTO shipCreationDTO) {
        HttpResponse<String> response;

        try {
            response = Unirest.post(SHIP_URI)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(shipCreationDTO)
                    .asString();
            log.info(String.format("ShipClient.createShip(): [%s]", response.getBody()));
        } catch (InvalidShipException ex) {
            throw new RuntimeException(ex);
        }
        return response;
    }

    public static HttpResponse<JsonNode> searchShips(String searchParam) {
        HttpResponse<JsonNode> response = Unirest.get(SHIP_URI + "/search")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .queryString("searchParam", searchParam)
                .asJson();

        return response;
    }

    public static ShipResponseDTO listShips(int pageNumber, int pageSize, String direction, String sortField) throws IOException {
        ShipResponseDTO responseDTO = new ShipResponseDTO();
        HttpResponse<JsonNode> response = Unirest.get(SHIP_URI)
                .header("accept", "application/json")
                .queryString("pageNo", pageNumber)
                .queryString("pageSize", pageSize)
                .queryString("direction", direction)
                .queryString("sort", sortField)
                .asJson();

        if (response.isSuccess()) {
            responseDTO = JsonMapper.mapFromJson(response.getBody().getObject().toString(), ShipResponseDTO.class);
            log.info(String.format("ShipClient.listShips(): ShipDTO = [%s]", responseDTO.getShips().toString()));
        }

        return responseDTO;
    }

    public static HttpResponse updateShip(ShipUpdationDTO shipUpdationDTO) {
        HttpResponse<String> response;
        try {
            response = Unirest.put(SHIP_URI + "/" + shipUpdationDTO.getId())
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(shipUpdationDTO)
                    .asString();
            log.info(String.format("ShipClient.updateShip(): [%s]", response.getBody()));
        } catch (ShipNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        return response;
    }

    public static HttpResponse<JsonNode> findById(Long id) throws IOException {
        HttpResponse<JsonNode> response;
        try {
            response = Unirest.get(SHIP_URI + "/" + id)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .asJson();
        } catch (ShipNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        return response;
    }

    public static HttpResponse delete(Long id) {
        HttpResponse response = Unirest.delete(SHIP_URI + "/" + id)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).asEmpty();
        return response;
    }
}
