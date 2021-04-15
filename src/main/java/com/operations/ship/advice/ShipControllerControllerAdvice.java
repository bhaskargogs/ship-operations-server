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

package com.operations.ship.advice;

import com.operations.ship.exception.InvalidShipException;
import com.operations.ship.exception.ShipNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ShipControllerControllerAdvice {

    @ExceptionHandler(ShipNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleShipNotFoundException(ShipNotFoundException ex) {
        return makeResponseEntity(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidShipException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleInvalidShipNotFoundException(InvalidShipException ex) {
        return makeResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

    private static ResponseEntity<?> makeResponseEntity(RuntimeException ex, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(ex.getMessage());
    }
}
