package io.github.chihsiao.eva4kt.seal

import io.github.chihsiao.eva4j.jni.seal.EvaSealSecretJNI.decrypt
import io.github.chihsiao.eva4j.jni.seal.EvaSealSecretJNI.destroy
import io.github.chihsiao.eva4kt.ckks.EvaCkksSignature
import io.github.chihsiao.eva4kt.jni.JniPeer

class EvaSealSecret private constructor(addr: Long)
    : JniPeer(addr, ::destroy, true) {
    companion object {
        internal operator fun invoke(addr: Long) =
                fromAddress(addr, ::EvaSealSecret)
    }

    fun decrypt(encOutputs: EvaSealValuation, signature: EvaCkksSignature): Map<String, DoubleArray> =
            decrypt(nativeAddr, encOutputs.nativeAddr, signature.nativeAddr)
}
