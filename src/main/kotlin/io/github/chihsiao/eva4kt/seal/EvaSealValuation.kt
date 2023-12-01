package io.github.chihsiao.eva4kt.seal

import io.github.chihsiao.eva4j.jni.seal.EvaSealValuationJNI.*
import io.github.chihsiao.eva4kt.jni.JniPeer
import java.lang.ref.WeakReference

class EvaSealValuation private constructor(addr: Long)
    : JniPeer(addr, ::destroy, true) {
    companion object {
        internal operator fun invoke(addr: Long) =
                fromAddress(addr, ::EvaSealValuation)
    }

    constructor(sealPublic: EvaSealPublic) : this(create(sealPublic.nativeAddr))

    private val cachedSchemeValues by lazy {
        mutableMapOf<String, WeakReference<EvaSchemeValue>>()
                .apply { for (key in getKeys(nativeAddr)) put(key, WeakReference(null)) }
    }

    val keys by lazy { cachedSchemeValues.keys }

    operator fun get(key: String): EvaSchemeValue? {
        val weakRef = cachedSchemeValues[key] ?: return null
        val ref = weakRef.get()
        if (ref != null) {
            return ref
        } else {
            val newRef = EvaSchemeValue(getValue(nativeAddr, key))
            cachedSchemeValues[key] = WeakReference(newRef)
            return newRef
        }
    }

    operator fun set(key: String, value: EvaSchemeValue) {
        setValue(nativeAddr, key, value.nativeAddr)
    }
}
