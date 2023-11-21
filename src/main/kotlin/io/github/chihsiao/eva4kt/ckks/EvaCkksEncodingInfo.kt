package io.github.chihsiao.eva4kt.ckks

import io.github.chihsiao.eva4j.jni.ckks.EvaCkksEncodingInfoJNI
import io.github.chihsiao.eva4kt.enums.EvaType
import java.util.*

class EvaCkksEncodingInfo private constructor(
    internal val handle: Long
) {
    companion object {
        private val cached by lazy { WeakHashMap<Long, EvaCkksEncodingInfo>() }
        fun fromHandle(handle: Long): EvaCkksEncodingInfo {
            return cached.getOrElse(handle) {
                EvaCkksEncodingInfo(handle)
            }
        }
    }

    init {
        cached[handle] = this
    }

    protected fun finalize() {
        EvaCkksEncodingInfoJNI.destroy(handle)
        cached.remove(handle)
    }

    val level: Int get() = EvaCkksEncodingInfoJNI.getLevel(handle)
    val inputType: EvaType get() = EvaType.valueOf(EvaCkksEncodingInfoJNI.getInputType(handle))
    val scale: Int get() = EvaCkksEncodingInfoJNI.getScale(handle)
}
