package kanti.denet.shared

val String.asEthereumAddress: String get() = takeLast(20)