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
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ShipCodeParams {
    public static final String INVALID_SHIP_CODE_MSG = "Ship Code [%s] is invalid";
    private static final String SHIP_CODE_REGEX = "^[A-Z]{4}-\\d{4}-[A-Z]{1}\\d{1}";

    public static boolean validateCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            Pattern pattern = Pattern.compile(SHIP_CODE_REGEX);
            Matcher matcher = pattern.matcher(code);

            return matcher.matches();
        }

        return false;
    }

}
