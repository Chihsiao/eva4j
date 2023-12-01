package io.github.chihsiao.eva4j.jni.seal;

public final class EvaSealValuationJNI {
    private EvaSealValuationJNI() {}

    public static native long create(long sealPublicAddr);
    public static native void destroy(long sealValuationAddr);

    public static native String[] getKeys(long sealValuationAddr);

    public static native long getValue(long sealValuationAddr, String key);
    public static native void setValue(long sealValuationAddr, String key, long schemeValueAddr);
}
