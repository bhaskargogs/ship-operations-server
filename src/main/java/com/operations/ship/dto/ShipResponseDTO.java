package com.operations.ship.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipResponseDTO {
    private Long totalShips;
    private List<ShipDTO> ships;
}
