package io.github.chihsiao.eva4kt.ckks

import io.github.chihsiao.eva4j.jni.ckks.EvaCkksSignatureJNI.*
import io.github.chihsiao.eva4kt.jni.JniPeer

class EvaCkksSignature private constructor(addr: Long)
    : JniPeer(addr, ::destroy, true) {
    companion object {
        internal operator fun invoke(addr: Long) =
                fromAddress(addr, ::EvaCkksSignature)
    }

    val vecSize: Int by lazy { getVecSize(nativeAddr) }
    val inputs: Map<String, EvaCkksEncodingInfo> by lazy {
        getInputs(nativeAddr).mapValues { EvaCkksEncodingInfo(it.value)!! }
    }
}
