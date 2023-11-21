package io.github.chihsiao.eva4kt.ckks

import io.github.chihsiao.eva4j.jni.ckks.EvaCkksCompilerJNI
import io.github.chihsiao.eva4kt.EvaProgram
import java.util.*

class EvaCkksCompiler private constructor(
    internal val handle: Long
) {
    companion object {
        private val cached by lazy { WeakHashMap<Long, EvaCkksCompiler>() }
        fun fromHandle(handle: Long): EvaCkksCompiler {
            return cached.getOrElse(handle) {
                EvaCkksCompiler(handle)
            }
        }
    }

    init {
        cached[handle] = this
    }

    constructor() : this(EvaCkksCompilerJNI.create())
    constructor(config: Map<String, String>) : this(EvaCkksCompilerJNI.createWithConfig(config))

    protected fun finalize() {
        EvaCkksCompilerJNI.destroy(handle)
        cached.remove(handle)
    }

    fun compile(program: EvaProgram): EvaProgram.CompiledProgram {
        val handles: LongArray = EvaCkksCompilerJNI.compile(handle, program.handle)
        val (programHandle, ckksParametersHandle, ckksSignatureHandle) = handles
        return EvaProgram.CompiledProgram(programHandle,
            EvaCkksParameters.fromHandle(ckksParametersHandle),
            EvaCkksSignature.fromHandle(ckksSignatureHandle)
        )
    }
}
