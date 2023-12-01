package io.github.chihsiao.eva4kt

import io.github.chihsiao.eva4j.jni.EvaProgramJNI.*
import io.github.chihsiao.eva4j.jni.EvaSharedTermJNI
import io.github.chihsiao.eva4kt.ckks.EvaCkksParameters
import io.github.chihsiao.eva4kt.ckks.EvaCkksSignature
import io.github.chihsiao.eva4kt.enums.EvaOp
import io.github.chihsiao.eva4kt.enums.EvaType
import io.github.chihsiao.eva4kt.jni.JniPeer

class EvaProgramBuilder private constructor(addr: Long)
    : JniPeer(addr, ::destroy, true) {
    companion object {
        internal operator fun invoke(addr: Long) =
                fromAddress(addr, ::EvaProgramBuilder)
    }

    constructor(name: String, vecSize: Long) : this(create(name, vecSize))

    var name: String
        get() = getName(nativeAddr)
        set(value) = setName(nativeAddr, value)

    val vecSize: Long by lazy { getVecSize(nativeAddr) }

    internal var ownedTerms: MutableSet<EvaTerm>? = mutableSetOf()

    override fun finalize() {
        val ownedTerms = this.ownedTerms!!
        this.ownedTerms = null

        ownedTerms.forEach {
            EvaSharedTermJNI.destroy(it.release())
        }

        super.finalize()
    }

    // TODO: inputs, outputs

    fun setInputsScale(scale: Int) = setInputScales(nativeAddr, scale)
    fun setOutputsRange(range: Int) = setOutputRanges(nativeAddr, range)

    internal fun makeTerm(op: EvaOp, terms: Array<out EvaTerm>): EvaTerm =
            EvaTerm(this, makeTerm(nativeAddr, op.value, terms.map { it.nativeAddr }.toLongArray()))!!

    internal fun makeLeftRotation(term: EvaTerm, slots: Int): EvaTerm =
            EvaTerm(this, makeLeftRotation(nativeAddr, term.nativeAddr, slots))!!

    internal fun makeRightRotation(term: EvaTerm, slots: Int): EvaTerm =
            EvaTerm(this, makeRightRotation(nativeAddr, term.nativeAddr, slots))!!

    internal fun makeUniformConstant(value: Double): EvaTerm =
            EvaTerm(this, makeUniformConstant(nativeAddr, value))!!

    internal fun makeDenseConstant(values: DoubleArray): EvaTerm =
            EvaTerm(this, makeDenseConstant(nativeAddr, values))!!

    internal fun makeInput(name: String, type: EvaType): EvaTerm =
            EvaTerm(this, makeInput(nativeAddr, name, type.value))!!

    internal fun makeOutput(name: String, term: EvaTerm): EvaTerm =
            EvaTerm(this, makeOutput(nativeAddr, name, term.nativeAddr))!!

    fun input(name: String, isEncrypted: Boolean = true): EvaExpr {
        return EvaExpr(this, makeInput(name, if (isEncrypted) EvaType.Cipher else EvaType.Plain))
    }

    fun output(name: String, expr: EvaExpr) {
        makeOutput(name, expr.term)
    }
}

class EvaProgram private constructor(addr: Long)
    : JniPeer(addr, ::destroy, true) {
    companion object {
        operator fun invoke(addr: Long) =
                fromAddress(addr, ::EvaProgram)

        operator fun invoke(addr: Long, parameters: EvaCkksParameters, signature: EvaCkksSignature) =
                fromAddress(addr) {
                    EvaProgram(it).apply {
                        this.parameters = parameters
                        this.signature = signature
                    }
                }
    }

    lateinit var parameters: EvaCkksParameters
    lateinit var signature: EvaCkksSignature

    val name: String by lazy { getName(nativeAddr) }
    val vecSize: Long by lazy { getVecSize(nativeAddr) }

    fun toDOT(): String = toDOT(nativeAddr)
}

inline fun buildEvaProgram(
        name: String, vecSize: Long,
        crossinline init: EvaProgramBuilder.() -> Unit
): EvaProgramBuilder {
    return EvaProgramBuilder(name, vecSize).apply(init)
}
