package io.github.chihsiao.eva4kt.seal

import io.github.chihsiao.eva4j.jni.seal.EvaSealValuationJNI
import java.util.*

class EvaSealValuation private constructor(
    internal val handle: Long
) {
    companion object {
        private val cached by lazy { WeakHashMap<Long, EvaSealValuation>() }
        fun fromHandle(handle: Long): EvaSealValuation {
            return cached.getOrElse(handle) {
                EvaSealValuation(handle)
            }
        }
    }

    init {
        cached[handle] = this
    }

    protected fun finalize() {
        EvaSealValuationJNI.destroy(handle)
        cached.remove(handle)
    }
}
