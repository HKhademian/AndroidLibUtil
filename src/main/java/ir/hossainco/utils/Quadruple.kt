@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package ir.hossainco.utils

typealias Quadruple<A, B, C, D> = Pair<Pair<A, B>, Pair<C, D>>

operator fun <A> Quadruple<A, *, *, *>.component1() = first.first
operator fun <B> Quadruple<*, B, *, *>.component2() = first.second
operator fun <C> Quadruple<*, *, C, *>.component3() = second.first
operator fun <D> Quadruple<*, *, *, D>.component4() = second.second
