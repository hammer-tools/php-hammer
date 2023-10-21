package net.rentalhost.plugins.php.hammer.extensions.kotlin

fun <T> List<T>.joinToStringEx(
  separator: CharSequence = ", ",
  separatorLast: CharSequence = " and ",
  prefix: CharSequence = "",
  postfix: CharSequence = "",
  limit: Int = -1,
  truncated: CharSequence = "...",
  transform: ((T) -> CharSequence)? = null
): String {
  if (size <= 1 || (limit != -1 && size > limit))
    return joinToString(separator, prefix, postfix, limit, truncated, transform)

  val last = with(last()) { transform?.invoke(this) ?: this }

  return take(size - 1).joinToString(separator, prefix, postfix, limit, truncated, transform) + separatorLast + last
}
