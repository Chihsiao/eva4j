package io.github.chihsiao.eva4kt.ckks

import io.github.chihsiao.eva4j.jni.ckks.EvaCkksParametersJNI.*
import io.github.chihsiao.eva4kt.jni.JniPeer

class EvaCkksParameters private constructor(addr: Long)
    : JniPeer(addr, ::destroy, true) {
    companion object {
        internal operator fun invoke(addr: Long) =
                fromAddress(::EvaCkksParameters, addr)
    }

    val primeBits: IntArray by lazy { getPrimeBits(nativeAddr) }
    val rotations: Set<Int> by lazy { getRotations(nativeAddr).toSet() }
    val polyModulusDegree: Int by lazy { getPolyModulusDegree(nativeAddr) }
}
