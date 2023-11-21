package io.github.chihsiao.eva4j.jni.seal;

public final class EvaSealValuationJNI {
    private EvaSealValuationJNI() {}

    public static native void destroy(long sealValuationAddr);
}
