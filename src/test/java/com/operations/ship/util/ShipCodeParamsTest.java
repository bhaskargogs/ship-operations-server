package com.operations.ship.util;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShipCodeParamsTest {

    @Test
    public void testValidShipCode() {
        String shipCode = "aans-0001-A2";
        assertTrue(ShipCodeParams.validateCode(shipCode));
    }

    @Test
    public void testInvalidShipCode() {
        String shipCode = "a2dA-111A-AS";
        assertFalse(ShipCodeParams.validateCode(shipCode));
    }
}
