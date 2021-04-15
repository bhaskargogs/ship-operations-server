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
