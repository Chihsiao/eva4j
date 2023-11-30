package io.github.chihsiao.eva4kt.serialization

import io.github.chihsiao.eva4j.jni.serialization.EvaSaveLoadJNI
import io.github.chihsiao.eva4j.jni.serialization.EvaSaveLoadJNI.*
import io.github.chihsiao.eva4kt.EvaProgram
import io.github.chihsiao.eva4kt.ckks.EvaCkksParameters
import io.github.chihsiao.eva4kt.ckks.EvaCkksSignature
import io.github.chihsiao.eva4kt.seal.EvaSealPublic
import io.github.chihsiao.eva4kt.seal.EvaSealSecret
import io.github.chihsiao.eva4kt.seal.EvaSealValuation

fun saveToByteArray(program: EvaProgram): ByteArray = saveToByteArray_Program(program.nativeAddr)
fun saveToByteArray(ckksParameters: EvaCkksParameters): ByteArray = saveToByteArray_CkksParameters(ckksParameters.nativeAddr)
fun saveToByteArray(ckksSignature: EvaCkksSignature): ByteArray = saveToByteArray_CkksSignature(ckksSignature.nativeAddr)
fun saveToByteArray(sealValuation: EvaSealValuation): ByteArray = saveToByteArray_SealValuation(sealValuation.nativeAddr)
fun saveToByteArray(sealPublic: EvaSealPublic): ByteArray = saveToByteArray_SealPublic(sealPublic.nativeAddr)
fun saveToByteArray(sealSecret: EvaSealSecret): ByteArray = saveToByteArray_SealSecret(sealSecret.nativeAddr)

fun loadFromByteArray(bytes: ByteArray): KnownType = KnownType(EvaSaveLoadJNI.loadFromByteArray(bytes))!!
