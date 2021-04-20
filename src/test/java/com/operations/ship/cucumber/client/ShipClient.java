package com.operations.ship.cucumber.client;

import com.operations.ship.dto.ShipCreationDTO;
import com.operations.ship.dto.ShipDTO;
import com.operations.ship.dto.ShipResponseDTO;
import com.operations.ship.dto.ShipUpdationDTO;
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
        HttpResponse<String> response = Unirest.post(SHIP_URI)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(shipCreationDTO)
                .asString();

        if (response.isSuccess()) {
            log.info(String.format("ShipClient.createShip(): [%s]", response.getBody()));
        }

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
        HttpResponse<String> response = Unirest.put(SHIP_URI + "/" + shipUpdationDTO.getId())
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(shipUpdationDTO)
                .asString();

        if (response.isSuccess()) {
            log.info(String.format("ShipClient.updateShip(): [%s]", response.getBody()));
        }

        return response;
    }

    public static ShipDTO findById(Long id) throws IOException {
        ShipDTO shipDTO = new ShipDTO();
        HttpResponse<JsonNode> response = Unirest.get(SHIP_URI + "/" + id)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .asJson();

        if (response.isSuccess()) {
            shipDTO = JsonMapper.mapFromJson(response.getBody().getObject().toString(), ShipDTO.class);
            log.info(String.format("ShipClient.updateShip(): [%s]", shipDTO));
        }

        return shipDTO;
    }

    public static HttpResponse delete(Long id) {
        HttpResponse response = Unirest.delete(SHIP_URI + "/" + id)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).asEmpty();
        return response;
    }
}
