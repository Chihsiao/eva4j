package io.github.chihsiao.eva4j.jni.seal;

public final class EvaSchemeValueJNI {
    private EvaSchemeValueJNI() {}

    public static native void destroy(long schemeValueAddr);
}
