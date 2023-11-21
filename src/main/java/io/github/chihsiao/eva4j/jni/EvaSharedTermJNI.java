package io.github.chihsiao.eva4j.jni;

public final class EvaSharedTermJNI {
    private EvaSharedTermJNI() {}

    public static native void destroy(long sharedTermAddr);

    public static native int getOp(long sharedTermAddr);
}
