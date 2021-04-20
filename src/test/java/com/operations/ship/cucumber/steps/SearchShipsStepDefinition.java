package com.operations.ship.cucumber.steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.operations.ship.cucumber.entity.ShipEntity;
import com.operations.ship.dto.ShipDTO;
import com.operations.ship.util.JsonMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class SearchShipsStepDefinition {
    private ShipEntity shipEntity = new ShipEntity();
    private HttpResponse<JsonNode> jsonResponse;
    private List<ShipDTO> ships;
    String searchParam;

    @Given("A user has an search string of {string}")
    public void userHasSearchString(String search) {
        searchParam = search;
    }

    @When("the user searches for ships")
    public void userSeachesForShips() throws IOException {
        jsonResponse = shipEntity.searchShips(searchParam);
        ships = JsonMapper.mapListFromJson(jsonResponse.getBody().getArray().toString(), new TypeReference<List<ShipDTO>>() {});
    }

    @Then("the user gets list of ships")
    public void userGetsListShips() {
        assertNotNull(ships);
    }
}
