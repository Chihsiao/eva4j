package io.github.chihsiao.eva4j.jni.serialization;

public final class EvaKnownTypeJNI {
    private EvaKnownTypeJNI() {}

    public static native long getIfProgram(long knownTypeAddr);
    public static native long getIfCkksParameters(long knownTypeAddr);
    public static native long getIfCkksSignature(long knownTypeAddr);
    public static native long getIfSealValuation(long knownTypeAddr);
    public static native long getIfSealPublic(long knownTypeAddr);
    public static native long getIfSealSecret(long knownTypeAddr);

    public static native void destroy(long knownTypeAddr);
}
