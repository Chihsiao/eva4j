package io.github.chihsiao.eva4kt.ckks

import io.github.chihsiao.eva4j.jni.ckks.EvaCkksSignatureJNI
import java.util.*

class EvaCkksSignature private constructor(
    internal val handle: Long
) {
    companion object {
        private val cached by lazy { WeakHashMap<Long, EvaCkksSignature>() }
        fun fromHandle(handle: Long): EvaCkksSignature {
            return cached.getOrElse(handle) {
                EvaCkksSignature(handle)
            }
        }
    }

    init {
        cached[handle] = this
    }

    protected fun finalize() {
        EvaCkksSignatureJNI.destroy(handle)
        cached.remove(handle)
    }

    val vecSize: Int get() = EvaCkksSignatureJNI.getVecSize(handle)
    val inputs: Map<String, EvaCkksEncodingInfo> get() = EvaCkksSignatureJNI
        .getInputs(handle).mapValues { EvaCkksEncodingInfo.fromHandle(it.value) }
}
