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

package com.operations.ship.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.operations.ship.exception.InvalidShipException;
import com.operations.ship.util.ShipCodeParams;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class ShipCreationDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Length is mandatory")
    private double length;

    @NotNull(message = "Width is mandatory")
    private double width;

    @NotBlank(message = "Code is mandatory")
    private String code;


    public ShipCreationDTO(String name, double length, double width, String code) {
        this.name = name;
        this.length = length;
        this.width = width;
        this.code = code;
    }

    public static ShipDTO makeShipDTO(ShipCreationDTO request) {
        ShipDTO shipDTO = new ShipDTO();
        ShipCreationDTO.validate(request);
        shipDTO.setName(request.getName());
        shipDTO.setLength(request.getLength());
        shipDTO.setWidth(request.getWidth());
        if (StringUtils.isNotBlank(request.getCode())) {
            shipDTO.setCode(request.getCode());
        }
        return shipDTO;
    }

    private static void validate(ShipCreationDTO request) {
        String code = request.getCode();

        if (!ShipCodeParams.validateCode(code)) {
            throw new InvalidShipException(ShipCodeParams.INVALID_SHIP_CODE_MSG, code);
        }
    }

}
