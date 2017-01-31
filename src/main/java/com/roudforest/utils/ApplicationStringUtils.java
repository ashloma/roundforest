package com.roudforest.utils;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class ApplicationStringUtils {

    public static String getNullIfEmpty(String data) {
        String trimData = data.trim();
        return isBlank(trimData) ? null : trimData;
    }
}
