package io.github.chihsiao.eva4kt.seal

import io.github.chihsiao.eva4j.jni.seal.EvaSchemeValueJNI.*
import io.github.chihsiao.eva4kt.jni.JniPeer

class EvaSchemeValue private constructor(addr: Long)
    : JniPeer(addr, ::destroy, true) {
    companion object {
        operator fun invoke(addr: Long) =
                fromAddress(addr, ::EvaSchemeValue)
    }
}
