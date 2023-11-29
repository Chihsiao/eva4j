package io.github.chihsiao.eva4kt.jni

import org.jetbrains.annotations.Contract
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.cast

open class JniPeer(
        nativeAddr: Long,
        private val nativeDeleter: (Long) -> Unit,
        private val cacheIt: Boolean = false,
) {
    companion object {
        private val cachedJniPeers by lazy { WeakHashMap<Long, JniPeer>() }

        inline fun <reified T : Any> fromAddress(
                noinline constructor: (Long) -> T, addr: Long
        ): T? = fromAddress(T::class, constructor, addr)

        fun <T : Any> fromAddress(
                type: KClass<out T>,
                constructor: (Long) -> T,
                addr: Long
        ): T? {
            if (addr == 0L) return null
            return cachedJniPeers[addr]?.let { type.cast(it) } ?: constructor(addr)
        }
    }

    internal var nativeAddr: Long = nativeAddr
        private set

    internal fun release(): Long {
        if (nativeAddr != 0L && cacheIt)
            cachedJniPeers.remove(nativeAddr)
        val ret = nativeAddr
        nativeAddr = 0L
        return ret
    }

    init {
        if (nativeAddr != 0L && cacheIt) {
            cachedJniPeers[nativeAddr] = this
        }
    }

    protected open fun finalize() {
        if (nativeAddr != 0L) {
            nativeDeleter(nativeAddr)
            if (cacheIt) cachedJniPeers.remove(nativeAddr)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is JniPeer) return false
        if (nativeAddr != other.nativeAddr) return false
        return true
    }

    override fun hashCode(): Int {
        return nativeAddr.hashCode()
    }
}
