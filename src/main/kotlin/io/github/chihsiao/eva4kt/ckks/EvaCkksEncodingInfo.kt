package io.github.chihsiao.eva4kt.ckks

import io.github.chihsiao.eva4j.jni.ckks.EvaCkksEncodingInfoJNI.*
import io.github.chihsiao.eva4kt.enums.EvaType
import io.github.chihsiao.eva4kt.jni.JniPeer

class EvaCkksEncodingInfo private constructor(addr: Long)
    : JniPeer(addr, ::destroy, true) {
    companion object {
        internal operator fun invoke(addr: Long) =
                fromAddress(addr, ::EvaCkksEncodingInfo)
    }

    val level: Int by lazy { getLevel(nativeAddr) }
    val inputType: EvaType by lazy { EvaType.valueOf(getInputType(nativeAddr)) }
    val scale: Int by lazy { getScale(nativeAddr) }
}
