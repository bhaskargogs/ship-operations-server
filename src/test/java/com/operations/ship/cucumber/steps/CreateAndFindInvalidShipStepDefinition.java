package com.operations.ship.cucumber.steps;

import com.operations.ship.cucumber.entity.ShipEntity;
import com.operations.ship.dto.ShipCreationDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class CreateAndFindInvalidShipStepDefinition {

    private ShipEntity shipEntity = new ShipEntity();
    private HttpResponse response;
    private HttpResponse<JsonNode> jsonResponse;
    ShipCreationDTO shipCreationDTO;
    Long shipID;

    @Given("A user having Ship of name {string} with length {float} m, width {float}m and code {string}")
    public void userHaveShipNameLengthWidthAndCode(String name, double length, double width, String code) {
        shipCreationDTO = new ShipCreationDTO(name, length, width, code);
    }

    @When("the user creates ship")
    public void createShip() {
        response = shipEntity.createShip(shipCreationDTO);
    }

    @Then("an exception is thrown")
    public void exceptionThrown() {
        assertEquals(400, response.getStatus());
    }

    @Given("A user tries to find a ship with ID {long}")
    public void userTriesFindShip(Long id) {
        shipID = id;
    }

    @When("the user searches for the ship")
    public void userSearchesForShip() throws IOException {
        jsonResponse = shipEntity.findShipById(shipID);
    }

    @Then("it fails")
    public void itFails() {
        assertEquals(404, jsonResponse.getStatus());
    }

}
