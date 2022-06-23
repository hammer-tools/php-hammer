package net.rentalhost.plugins.php.hammer.services;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringService {
    public static Stream<String> listTypes(final String types) {
        return Arrays.stream(StringUtils.split(types, "|"));
    }

    public static Stream<String> listNonNullableTypes(final String types) {
        return listTypes(types).filter(s -> !s.equals("null") && !s.equals("\\null"));
    }

    public static String joinTypesStream(final Stream<String> types) {
        return types.collect(Collectors.joining("|"));
    }
}
