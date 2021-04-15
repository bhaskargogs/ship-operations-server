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

import java.time.LocalDateTime;

@Configuration
@Slf4j
public class DatabaseLoader {

    @Bean
    CommandLineRunner initShips(ShipRepository repository) {
        return (args -> {
            repository.deleteAll();

            repository.save(new Ship("Illustria", 2154.24, 565.21, "AAAA-0021-A1", LocalDateTime.now(), LocalDateTime.now()));
            repository.save(new Ship("Pascal Magi", 3254.24, 1565.21, "ABBA-0121-A1", LocalDateTime.now(), LocalDateTime.now()));

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
