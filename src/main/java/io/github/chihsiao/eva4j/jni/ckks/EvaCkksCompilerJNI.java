package io.github.chihsiao.eva4j.jni.ckks;

import java.util.Map;

public final class EvaCkksCompilerJNI {
    private EvaCkksCompilerJNI() {}

    public static native long create();
    public static native long createWithConfig(Map<String, String> ckksConfigMap);
    public static native void destroy(long ckksCompilerAddr);

    public static native long[] compile(long ckksCompilerAddr, long programAddr);
}
