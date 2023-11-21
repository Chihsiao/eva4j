package io.github.chihsiao.eva4kt.seal

import io.github.chihsiao.eva4j.jni.seal.EvaSealPublicJNI
import io.github.chihsiao.eva4kt.EvaProgram
import io.github.chihsiao.eva4kt.ckks.EvaCkksSignature
import java.util.*

class EvaSealPublic private constructor(
    internal val handle: Long
) {
    companion object {
        private val cached by lazy { WeakHashMap<Long, EvaSealPublic>() }
        fun fromHandle(handle: Long): EvaSealPublic {
            return cached.getOrElse(handle) {
                EvaSealPublic(handle)
            }
        }
    }

    init {
        cached[handle] = this
    }

    protected fun finalize() {
        EvaSealPublicJNI.destroy(handle)
        cached.remove(handle)
    }

    fun execute(program: EvaProgram.CompiledProgram, inputs: EvaSealValuation): EvaSealValuation =
        EvaSealValuation.fromHandle(EvaSealPublicJNI.execute(handle, program.handle, inputs.handle))

    fun encrypt(inputs: Map<String, DoubleArray>, signature: EvaCkksSignature): EvaSealValuation =
        EvaSealValuation.fromHandle(EvaSealPublicJNI.encrypt(handle, inputs, signature.handle))
}
