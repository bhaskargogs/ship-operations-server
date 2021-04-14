package com.operations.ship.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class ShipCodeParamsTest {

    @Test
    public void testValidShipCode() {
        String shipCode = "aans-0001-A2".toUpperCase();
        assertTrue(ShipCodeParams.validateCode(shipCode));
    }

    @Test
    public void testInvalidShipCode() {
        String shipCode = "a2dA-111A-AS".toUpperCase();
        assertFalse(ShipCodeParams.validateCode(shipCode));
    }
}
