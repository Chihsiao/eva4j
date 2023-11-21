package io.github.chihsiao.eva4kt.seal

import io.github.chihsiao.eva4j.jni.seal.EvaSealSecretJNI
import io.github.chihsiao.eva4kt.ckks.EvaCkksSignature
import java.util.*

class EvaSealSecret private constructor(
    val public: EvaSealPublic, internal val handle: Long
) {
    companion object {
        private val cached by lazy { WeakHashMap<Long, EvaSealSecret>() }
        fun fromHandle(public: EvaSealPublic, handle: Long): EvaSealSecret {
            return cached.getOrElse(handle) {
                EvaSealSecret(public, handle)
            }.also {
                if (it.public != public) {
                    throw IllegalStateException()
                }
            }
        }
    }

    init {
        cached[handle] = this
    }

    protected fun finalize() {
        EvaSealSecretJNI.destroy(handle)
        cached.remove(handle)
    }

    fun decrypt(encOutputs: EvaSealValuation, signature: EvaCkksSignature): Map<String, DoubleArray> =
        EvaSealSecretJNI.decrypt(handle, encOutputs.handle, signature.handle)
}
