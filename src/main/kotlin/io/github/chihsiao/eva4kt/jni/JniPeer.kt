package io.github.chihsiao.eva4kt.jni

import java.lang.ref.WeakReference
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.cast

open class JniPeer(
        nativeAddr: Long,
        private val nativeDeleter: (Long) -> Unit,
        private val cacheIt: Boolean = false,
) {
    companion object {
        private val cachedJniPeers by lazy { WeakHashMap<Pair<KClass<*>, Long>, WeakReference<JniPeer>>() }

        inline fun <reified T : Any> fromAddress(
                noinline constructor: (Long) -> T, addr: Long
        ): T? = fromAddress(T::class, constructor, addr)

        fun <T : Any> fromAddress(
                type: KClass<out T>,
                constructor: (Long) -> T,
                addr: Long
        ): T? {
            if (addr == 0L) return null
            val weakRef = cachedJniPeers[type to addr]
            if (weakRef != null) {
                val ref = weakRef.get()
                if (ref != null) return type.cast(ref) else {
                    cachedJniPeers.remove(type to addr, weakRef)
                }
            }

            return constructor(addr)
        }
    }

    internal var nativeAddr: Long = nativeAddr
        private set

    internal fun release(): Long {
        if (nativeAddr != 0L && cacheIt)
            cachedJniPeers.remove(this::class to nativeAddr)
        val ret = nativeAddr
        nativeAddr = 0L
        return ret
    }

    init {
        if (nativeAddr != 0L && cacheIt) {
            cachedJniPeers[this::class to nativeAddr] = WeakReference(this)
        }
    }

    protected open fun finalize() {
        if (nativeAddr != 0L) {
            nativeDeleter(nativeAddr)
            if (cacheIt) cachedJniPeers.remove(this::class to nativeAddr)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) {
            return false
        }

        other as JniPeer
        return nativeAddr == other.nativeAddr
    }

    override fun hashCode(): Int {
        return nativeAddr.hashCode()
    }
}
