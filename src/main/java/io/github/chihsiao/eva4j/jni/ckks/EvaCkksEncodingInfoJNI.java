package io.github.chihsiao.eva4j.jni.ckks;

public final class EvaCkksEncodingInfoJNI {
    private EvaCkksEncodingInfoJNI() {}

    public static native void destroy(long ckksEncodingInfoAddr);

    public static native int getLevel(long ckksEncodingInfoAddr);
    public static native int getInputType(long ckksEncodingInfoAddr);
    public static native int getScale(long ckksEncodingInfoAddr);
}
