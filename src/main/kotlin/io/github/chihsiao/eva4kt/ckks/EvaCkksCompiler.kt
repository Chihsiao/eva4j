package io.github.chihsiao.eva4kt.ckks

import io.github.chihsiao.eva4j.jni.ckks.EvaCkksCompilerJNI.*
import io.github.chihsiao.eva4kt.EvaProgram
import io.github.chihsiao.eva4kt.EvaProgramBuilder
import io.github.chihsiao.eva4kt.jni.JniPeer

class EvaCkksCompiler private constructor(addr: Long)
    : JniPeer(addr, ::destroy, true) {
    companion object {
        internal operator fun invoke(addr: Long) =
                fromAddress(addr, ::EvaCkksCompiler)
    }

    constructor() : this(create())
    constructor(config: Map<String, String>) : this(createWithConfig(config))

    fun compile(program: EvaProgramBuilder): EvaProgram {
        val addrs: LongArray = compile(nativeAddr, program.nativeAddr)
        val (programAddr, ckksParametersAddr, ckksSignatureAddr) = addrs
        return EvaProgram(programAddr, EvaCkksParameters(ckksParametersAddr)!!, EvaCkksSignature(ckksSignatureAddr)!!)!!
    }
}
