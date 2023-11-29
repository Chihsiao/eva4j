package io.github.chihsiao.eva4kt.seal

import io.github.chihsiao.eva4j.jni.seal.EvaSealModuleJNI.generateKeys
import io.github.chihsiao.eva4kt.ckks.EvaCkksParameters

fun generateKeys(ckksParameters: EvaCkksParameters): Pair<EvaSealPublic, EvaSealSecret> {
    val (sealPublicAddr, sealSecretAddr) = generateKeys(ckksParameters.nativeAddr)
    return EvaSealPublic(sealPublicAddr) to EvaSealSecret(sealSecretAddr)
}
