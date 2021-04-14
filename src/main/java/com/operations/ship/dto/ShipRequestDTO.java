package com.operations.ship.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipRequestDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Length is mandatory")
    private double length;

    @NotNull(message = "Width is mandatory")
    private double width;

    @NotBlank(message = "Code is mandatory")
    private String code;

}
