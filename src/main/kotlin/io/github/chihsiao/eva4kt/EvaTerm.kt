package io.github.chihsiao.eva4kt

import io.github.chihsiao.eva4j.jni.EvaSharedTermJNI.destroy
import io.github.chihsiao.eva4j.jni.EvaSharedTermJNI.getOp
import io.github.chihsiao.eva4kt.enums.EvaOp
import io.github.chihsiao.eva4kt.jni.JniPeer

class EvaTerm private constructor(addr: Long)
    : JniPeer(addr, ::destroy, true) {
    companion object {
        operator fun invoke(builder: EvaProgramBuilder, addr: Long) =
                fromAddress(addr, ::EvaTerm)?.apply {
                    this.builder = builder
                }
    }

    lateinit var builder: EvaProgramBuilder private set

    val op: EvaOp by lazy { EvaOp.valueOf(getOp(nativeAddr)) }
}
