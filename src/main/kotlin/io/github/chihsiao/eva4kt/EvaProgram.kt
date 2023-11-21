package io.github.chihsiao.eva4kt

import io.github.chihsiao.eva4j.jni.EvaProgramJNI
import io.github.chihsiao.eva4kt.ckks.EvaCkksParameters
import io.github.chihsiao.eva4kt.ckks.EvaCkksSignature
import io.github.chihsiao.eva4kt.enums.EvaType
import io.github.chihsiao.eva4kt.enums.EvaOp

sealed class EvaProgram private constructor(
    internal val handle: Long
) {
    protected fun finalize() {
        EvaProgramJNI.destroy(handle)
    }

    open val name: String
        get() = EvaProgramJNI.getName(handle)

    val vecSize: Long
        get() = EvaProgramJNI.getVecSize(handle)

    class Builder internal constructor(handle: Long) : EvaProgram(handle) {
        constructor(name: String, vecSize: Long) : this(EvaProgramJNI.create(name, vecSize))

        override var name: String
            get() = EvaProgramJNI.getName(handle)
            set(value) { EvaProgramJNI.setName(handle, value) }

        // TODO: inputs, outputs

        fun setInputsScale(scale: Int) { EvaProgramJNI.setInputScales(handle, scale) }
        fun setOutputsRange(range: Int) { EvaProgramJNI.setOutputRanges(handle, range) }

        internal fun makeTerm(op: EvaOp, terms: Array<out EvaTerm>): EvaTerm =
            EvaTerm.fromHandle(this, EvaProgramJNI.makeTerm(handle, op.value,
                terms.map { it.handle }.toLongArray()))

        internal fun makeInput(name: String, type: EvaType): EvaTerm =
            EvaTerm.fromHandle(this, EvaProgramJNI.makeInput(handle, name, type.value))

        internal fun makeOutput(name: String, term: EvaTerm): EvaTerm =
            EvaTerm.fromHandle(this, EvaProgramJNI.makeOutput(handle, name, term.handle))

        internal fun makeLeftRotation(term: EvaTerm, slots: Int): EvaTerm =
            EvaTerm.fromHandle(this, EvaProgramJNI.makeLeftRotation(handle, term.handle, slots))

        internal fun makeRightRotation(term: EvaTerm, slots: Int): EvaTerm =
            EvaTerm.fromHandle(this, EvaProgramJNI.makeRightRotation(handle, term.handle, slots))

        internal fun makeUniformConstant(value: Double): EvaTerm =
            EvaTerm.fromHandle(this, EvaProgramJNI.makeUniformConstant(handle, value))

        internal fun makeDenseConstant(values: DoubleArray): EvaTerm =
            EvaTerm.fromHandle(this, EvaProgramJNI.makeDenseConstant(handle, values))

        fun input(name: String, isEncrypted: Boolean = true): EvaExpr {
            return EvaExpr(makeInput(name, if (isEncrypted) EvaType.Cipher else EvaType.Plain), this)
        }

        fun output(name: String, expr: EvaExpr) {
            makeOutput(name, expr.term)
        }
    }

    class CompiledProgram internal constructor(
        handle: Long, val parameters: EvaCkksParameters,
        val signature: EvaCkksSignature
    ) : EvaProgram(handle) {
        fun toDOT(): String = EvaProgramJNI.toDOT(handle)
    }
}

inline fun buildEvaProgram(
    name: String, vecSize: Long,
    crossinline init: EvaProgram.Builder.() -> Unit
): EvaProgram.Builder {
    return EvaProgram.Builder(name, vecSize).apply(init)
}
