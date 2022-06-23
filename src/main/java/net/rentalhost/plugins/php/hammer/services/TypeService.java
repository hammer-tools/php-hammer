package net.rentalhost.plugins.php.hammer.services;

import com.jetbrains.php.lang.psi.resolve.types.PhpType;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TypeService {
    public static final Set<String> nativeTypes = Set.of(
        PhpType._MIXED,
        PhpType._VOID, PhpType._NEVER, PhpType._NULL,
        PhpType._ARRAY, PhpType._ITERABLE,
        PhpType._OBJECT,
        PhpType._INT, PhpType._INTEGER,
        PhpType._BOOL, PhpType._BOOLEAN, PhpType._TRUE, PhpType._FALSE,
        PhpType._STRING,
        PhpType._FLOAT, PhpType._DOUBLE,
        PhpType._CALLBACK, PhpType._CALLABLE,
        PhpType._RESOURCE
    );

    public static Stream<String> listTypes(final String types) {
        return Arrays.stream(StringUtils.split(types, "|"));
    }

    public static Stream<String> listNonNullableTypes(final String types) {
        return listTypes(types).filter(s -> !s.equals(PhpType._NULL.substring(1)) && !s.equals(PhpType._NULL));
    }

    public static String joinTypesStream(final Stream<String> types) {
        return types.collect(Collectors.joining("|"));
    }
}
