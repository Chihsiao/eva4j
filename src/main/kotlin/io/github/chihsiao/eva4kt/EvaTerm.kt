package io.github.chihsiao.eva4kt

import io.github.chihsiao.eva4j.jni.EvaSharedTermJNI
import io.github.chihsiao.eva4kt.enums.EvaOp
import java.util.*

class EvaTerm private constructor(
    val program: EvaProgram.Builder,
    internal val handle: Long
) {
    companion object {
        private val cached by lazy { WeakHashMap<Long, EvaTerm>() }
        fun fromHandle(program: EvaProgram.Builder, handle: Long): EvaTerm {
            return cached.getOrElse(handle) {
                EvaTerm(program, handle)
            }.also {
                if (it.program != program) {
                    throw IllegalStateException()
                }
            }
        }
    }

    init {
        cached[handle] = this
    }

    protected fun finalize() {
        EvaSharedTermJNI.destroy(handle)
        cached.remove(handle)
    }

    val op: EvaOp get() = EvaOp.valueOf(EvaSharedTermJNI.getOp(handle))
}
