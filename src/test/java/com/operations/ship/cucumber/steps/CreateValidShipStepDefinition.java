package com.operations.ship.cucumber.steps;

import com.operations.ship.cucumber.entity.ShipEntity;
import com.operations.ship.dto.ShipCreationDTO;
import com.operations.ship.dto.ShipDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kong.unirest.HttpResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class CreateValidShipStepDefinition {

    private ShipEntity shipEntity = new ShipEntity();

    private HttpResponse response;

    @Given("A list of Ships are available")
    public void aListOfShipsAreAvailable() throws IOException {
        List<ShipDTO> ships = shipEntity.findShipLists(0, 20, "asc", "id");
        assertNotNull(ships, String.format("List of Ships available : ShipDTO [%s]", ships));
    }

    @When("User create a ship of name {string} having code {string} with dimensions {float} metres in length and {float} metres in width")
    public void userWantsToCreatesAShipWithTheFollowingAttributes(String name, String code, double length, double width) {
        ShipCreationDTO shipCreationDTO = new ShipCreationDTO(name, length, width, code);
        response = shipEntity.createShip(shipCreationDTO);
    }

    @Then("the ship is Created successfully")
    public void theShipIsCreatedSuccessfully() {
        assertEquals(201, response.getStatus());
        assertTrue(response.getBody().toString().contains("Successfully"));
    }
}

