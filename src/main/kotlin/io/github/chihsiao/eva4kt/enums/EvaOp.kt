package io.github.chihsiao.eva4kt.enums

enum class EvaOp(val value: Int) {
    Undef(0), Input(1), Output(2), Constant(3),
    Negate(10), Add(11), Sub(12), Mul(13), RotateLeftConst(14), RotateRightConst(15),
    Relinearize(20), ModSwitch(21), Rescale(22), Encode(23);

    companion object {
        private val ops by lazy {
            buildMap {
                EvaOp.entries.forEach {
                    put(it.value, it)
                }
            }
        }

        fun valueOf(value: Int): EvaOp {
            return ops[value] ?: throw IllegalArgumentException("$value")
        }
    }
}
