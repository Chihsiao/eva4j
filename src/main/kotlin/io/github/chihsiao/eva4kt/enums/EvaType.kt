package io.github.chihsiao.eva4kt.enums

enum class EvaType(val value: Int) {
    Undef(0), Cipher(1), Raw(2), Plain(3);

    companion object {
        private val types by lazy {
            buildMap {
                EvaType.entries.forEach {
                    put(it.value, it)
                }
            }
        }

        fun valueOf(value: Int): EvaType {
            return types[value] ?: throw IllegalArgumentException("$value")
        }
    }
}
