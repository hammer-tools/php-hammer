package net.rentalhost.plugins.services;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;

public class TypeService {
    private static final List<String> nullType = prependGlobalNamespace(List.of("null"));

    @NotNull
    public static Stream<String> splitTypes(final String types) {
        return Arrays.stream(StringUtils.split(types, "|"));
    }

    public static Stream<String> exceptNull(final String types) {
        return splitTypes(types).filter(s -> !nullType.contains(s));
    }

    public static String joinTypesStream(@NotNull final Stream<String> types) {
        return types.collect(Collectors.joining("|"));
    }

    private static @NotNull List<String> prependGlobalNamespace(@NotNull final List<String> types) {
        types.addAll(types.stream()
                          .map(s -> "\\" + s)
                          .collect(Collectors.toList()));

        return types;
    }
}
