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
