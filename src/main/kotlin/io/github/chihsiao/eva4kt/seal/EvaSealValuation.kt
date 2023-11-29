package io.github.chihsiao.eva4kt.seal

import io.github.chihsiao.eva4j.jni.seal.EvaSealValuationJNI.destroy
import io.github.chihsiao.eva4kt.jni.JniPeer

class EvaSealValuation private constructor(addr: Long)
    : JniPeer(addr, ::destroy, true) {
    companion object {
        internal operator fun invoke(addr: Long) =
                fromAddress(::EvaSealValuation, addr)
    }
}
