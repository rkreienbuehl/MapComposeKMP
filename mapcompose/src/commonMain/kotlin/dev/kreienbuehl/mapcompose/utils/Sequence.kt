package dev.kreienbuehl.mapcompose.utils

fun <I, O> Sequence<I>.toArray(
    factory: (size: Int, init: (Int) -> I) -> O
): O {
    val l = toList()
    val iter = iterator()
    return factory(l.size) { iter.next() }
}