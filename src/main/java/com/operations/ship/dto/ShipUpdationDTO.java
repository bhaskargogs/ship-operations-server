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

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class ShipUpdationDTO {

    @Id
    @NotNull
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Length is mandatory")
    private double length;

    @NotNull(message = "Width is mandatory")
    private double width;

    @NotBlank(message = "Code is mandatory")
    private String code;

    public ShipUpdationDTO(Long id, String name, double length, double width, String code) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.width = width;
        this.code = code;
    }

    public static ShipDTO makeShipDTO(ShipUpdationDTO requestDTO) {
        ShipDTO shipDTO = new ShipDTO();
        ShipUpdationDTO.validate(requestDTO);
        shipDTO.setId(requestDTO.getId());
        shipDTO.setName(requestDTO.getName());
        shipDTO.setLength(requestDTO.getLength());
        shipDTO.setWidth(requestDTO.getWidth());
        if (StringUtils.isNotBlank(requestDTO.getCode())) {
            shipDTO.setCode(requestDTO.getCode());
        }
        return shipDTO;

    }

    private static void validate(ShipUpdationDTO requestDTO) {
        if (!ShipCodeParams.validateCode(requestDTO.getCode())) {
            throw new InvalidShipException(ShipCodeParams.INVALID_SHIP_CODE_MSG, requestDTO.getCode());
        }
    }

}
