package io.github.chihsiao.eva4kt.ckks

import io.github.chihsiao.eva4j.jni.ckks.EvaCkksParametersJNI
import java.util.*

class EvaCkksParameters private constructor(
    internal val handle: Long
) {
    companion object {
        private val cached by lazy { WeakHashMap<Long, EvaCkksParameters>() }
        fun fromHandle(handle: Long): EvaCkksParameters {
            return cached.getOrElse(handle) {
                EvaCkksParameters(handle)
            }
        }
    }

    init {
        cached[handle] = this
    }

    protected fun finalize() {
        EvaCkksParametersJNI.destroy(handle)
        cached.remove(handle)
    }

    val primeBits: IntArray get() = EvaCkksParametersJNI.getPrimeBits(handle)
    val rotations: Set<Int> get() = EvaCkksParametersJNI.getRotations(handle).toSet()
    val polyModulusDegree: Int get() = EvaCkksParametersJNI.getPolyModulusDegree(handle)
}
