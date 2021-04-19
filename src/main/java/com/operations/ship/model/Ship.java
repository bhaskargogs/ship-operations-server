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

package com.operations.ship.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "ship")
public class Ship implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private double length;

    private double width;

    private String code;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "updated_date")
    private ZonedDateTime updatedDate;

    private Ship() {

    }

    public Ship(String name, double length, double width, String code, ZonedDateTime createdDate, ZonedDateTime updatedDate) {
        this.name = name;
        this.length = length;
        this.width = width;
        this.code = code;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

}
