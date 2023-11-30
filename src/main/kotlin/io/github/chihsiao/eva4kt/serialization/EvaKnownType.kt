package io.github.chihsiao.eva4kt.serialization

import io.github.chihsiao.eva4j.jni.serialization.EvaKnownTypeJNI.*
import io.github.chihsiao.eva4kt.EvaProgram
import io.github.chihsiao.eva4kt.ckks.EvaCkksParameters
import io.github.chihsiao.eva4kt.ckks.EvaCkksSignature
import io.github.chihsiao.eva4kt.jni.JniPeer
import io.github.chihsiao.eva4kt.seal.EvaSealPublic
import io.github.chihsiao.eva4kt.seal.EvaSealSecret
import io.github.chihsiao.eva4kt.seal.EvaSealValuation

class KnownType private constructor(addr: Long)
    : JniPeer(addr, ::destroy, false) {
    companion object {
        internal operator fun invoke(addr: Long) =
                fromAddress(::KnownType, addr)
    }

    fun getIfProgram(): EvaProgram? = EvaProgram(getIfProgram(nativeAddr))
    fun getIfCkksParameters(): EvaCkksParameters? = EvaCkksParameters(getIfCkksParameters(nativeAddr))
    fun getIfCkksSignature(): EvaCkksSignature? = EvaCkksSignature(getIfCkksSignature(nativeAddr))
    fun getIfSealValuation(): EvaSealValuation? = EvaSealValuation(getIfSealValuation(nativeAddr))
    fun getIfSealPublic(): EvaSealPublic? = EvaSealPublic(getIfSealPublic(nativeAddr))
    fun getIfSealSecret(): EvaSealSecret? = EvaSealSecret(getIfSealSecret(nativeAddr))
}
