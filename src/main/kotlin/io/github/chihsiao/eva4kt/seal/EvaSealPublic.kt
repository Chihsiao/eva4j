package io.github.chihsiao.eva4kt.seal

import io.github.chihsiao.eva4j.jni.seal.EvaSealPublicJNI.*
import io.github.chihsiao.eva4kt.EvaProgram
import io.github.chihsiao.eva4kt.ckks.EvaCkksSignature
import io.github.chihsiao.eva4kt.jni.JniPeer

class EvaSealPublic private constructor(handle: Long)
    : JniPeer(handle, ::destroy, true) {
    companion object {
        internal operator fun invoke(addr: Long) =
                fromAddress(::EvaSealPublic, addr)
    }

    fun execute(program: EvaProgram, inputs: EvaSealValuation): EvaSealValuation =
            EvaSealValuation(execute(nativeAddr, program.nativeAddr, inputs.nativeAddr))

    fun encrypt(inputs: Map<String, DoubleArray>, signature: EvaCkksSignature): EvaSealValuation =
            EvaSealValuation(encrypt(nativeAddr, inputs, signature.nativeAddr))
}
