package io.github.chihsiao.eva4j.jni.ckks;

public final class EvaCkksParametersJNI {
    private EvaCkksParametersJNI() {}

    public static native void destroy(long ckksParametersAddr);

    public static native int[] getPrimeBits(long ckksParametersAddr);
    public static native int[] getRotations(long ckksParametersAddr);
    public static native int getPolyModulusDegree(long ckksParametersAddr);
}
