package io.github.chihsiao.eva4j.jni.ckks;

import java.util.Map;

public final class EvaCkksSignatureJNI {
    private EvaCkksSignatureJNI() {}

    public static native void destroy(long ckksSignatureAddr);

    public static native int getVecSize(long ckksSignatureAddr);
    public static native Map<String, Long> getInputs(long ckksSignatureAddr);
}
