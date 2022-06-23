package net.rentalhost.plugins.php.hammer.services;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.stream.Stream;

public class StringService {
    public static Stream<String> listNonNullableTypes(final String types) {
        return Arrays.stream(StringUtils.split(types, "|"))
                     .filter(s -> !s.equals("null") && !s.equals("\\null"));
    }
}
