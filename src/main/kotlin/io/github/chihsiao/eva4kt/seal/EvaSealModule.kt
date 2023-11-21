package io.github.chihsiao.eva4kt.seal

import io.github.chihsiao.eva4j.jni.seal.EvaSealModuleJNI
import io.github.chihsiao.eva4kt.ckks.EvaCkksParameters

fun generateKeys(ckksParameters: EvaCkksParameters): EvaSealSecret {
    val (sealPublicHandle, sealSecretHandle) = EvaSealModuleJNI.generateKeys(ckksParameters.handle)
    return EvaSealSecret.fromHandle(EvaSealPublic.fromHandle(sealPublicHandle), sealSecretHandle)
}
