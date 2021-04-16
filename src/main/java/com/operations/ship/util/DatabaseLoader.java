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

import com.operations.ship.model.Ship;
import com.operations.ship.repository.ShipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;

@Configuration
@Slf4j
public class DatabaseLoader {

    @Bean
    CommandLineRunner initShips(ShipRepository repository) {
        return (args -> {
            repository.deleteAll();

            repository.save(new Ship("Illustria", 391.24, 65.21, "ABYI-0021-A1", ZonedDateTime.parse("2020-10-15T18:30:49.665Z"), ZonedDateTime.parse("2021-01-05T06:45:49.587Z")));
            repository.save(new Ship("Seagallop", 354.24, 55.64, "UQYT-2124-B9", ZonedDateTime.parse("2020-12-17T10:14:54.785Z"), ZonedDateTime.parse("2020-12-25T20:15:02.395Z")));
            repository.save(new Ship("Konteri", 391.16, 51.67, "JUOQ-5789-S3", ZonedDateTime.parse("2020-06-20T21:52:02.578Z"), ZonedDateTime.parse("2020-06-20T21:52:02.578Z")));
            repository.save(new Ship("Diadema", 400.04, 69.58, "PTBH-4720-G5", ZonedDateTime.parse("2020-09-15T10:41:35.225Z"), ZonedDateTime.parse("2020-09-15T10:41:35.225Z")));
            repository.save(new Ship("Blue", 375.65, 49.15, "LHWY-5690-E7", ZonedDateTime.parse("2020-04-21T16:25:39.587Z"), ZonedDateTime.parse("2020-04-24T15:00:54.751Z")));
            repository.save(new Ship("Illustria", 387.84, 31.75, "KUQI-1204-L9", ZonedDateTime.parse("2020-07-20T06:45:20.354Z"), ZonedDateTime.parse("2020-09-11T09:30:21.487Z")));
            repository.save(new Ship("Decolore", 382.95, 69.45, "BXGW-9547-O9", ZonedDateTime.parse("2020-11-04T14:20:45.475Z"), ZonedDateTime.parse("2020-11-20T15:30:54.785Z")));
            repository.save(new Ship("Queen Berry", 328.63, 75.69, "UQMZ-3687-J1", ZonedDateTime.parse("2021-01-20T18:45:57.584Z"), ZonedDateTime.parse("2021-01-21T09:35:45.587Z")));

            log.info("-------------------------");
            log.info("List of ships");
            log.info("-------------------------");
            for (Ship ship: repository.findAll()) {
                log.info(ship.toString());
            }
            log.info("------------------------");
        });
    }
}
