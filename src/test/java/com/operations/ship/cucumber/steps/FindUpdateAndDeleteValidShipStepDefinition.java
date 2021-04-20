package com.operations.ship.cucumber.steps;

import com.operations.ship.cucumber.entity.ShipEntity;
import com.operations.ship.dto.ShipDTO;
import com.operations.ship.dto.ShipUpdationDTO;
import com.operations.ship.util.JsonMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FindUpdateAndDeleteValidShipStepDefinition {
    private ShipEntity shipEntity = new ShipEntity();
    private HttpResponse response;
    private HttpResponse<JsonNode> jsonResponse;
    ShipDTO shipDTO;

    int status;

    @Given("the user looks for ship with id {long}")
    public void userLooksForShip(Long id) throws IOException {
        jsonResponse = shipEntity.findShipById(id);
        shipDTO = JsonMapper.mapFromJson(jsonResponse.getBody().getObject().toString(), ShipDTO.class);
    }

    @When("the user gets the ship")
    public void userFindsShip() {
        assertNotNull(shipDTO);
    }

    @And("the user updates the ship with length {float} m and width {float} m")
    public void userUpdatesShip(double length, double width) {
        ShipUpdationDTO shipUpdationDTO = new ShipUpdationDTO();
        shipUpdationDTO.setId(shipDTO.getId());
        shipUpdationDTO.setName(shipDTO.getName());
        shipUpdationDTO.setLength(length);
        shipUpdationDTO.setWidth(width);
        shipUpdationDTO.setCode(shipDTO.getCode());
        response = shipEntity.updateShip(shipUpdationDTO);
    }

    @Then("the ship with ID {int} is updated successfully")
    public void shipUpdatedSuccessfully(long id) {
        assertEquals(200, response.getStatus());
        assertTrue(response.getBody().toString().contains("updated successfully"));
        assertTrue(response.getBody().toString().contains(Long.toString(id)));
    }

    @Given("A user having a ship with id {long}")
    public void userHavingShip(Long id) throws IOException {
        jsonResponse = shipEntity.findShipById(id);
        shipDTO = JsonMapper.mapFromJson(jsonResponse.getBody().getObject().toString(), ShipDTO.class);
    }

    @When("the user gets the valid ship")
    public void userFindsValidShip() {
        assertNotNull(shipDTO);
    }

    @And("the user deletes the ship with id {long}")
    public void userDeletesShip(Long id) {
        status = shipEntity.deleteShip(id);
    }

    @Then("delete is successful")
    public void deleteSuccessful() {
        assertEquals(200, status);
    }
}
