package io.github.chihsiao.eva4j.jni.seal;

import java.util.Map;

public final class EvaSealSecretJNI {
    private EvaSealSecretJNI() {}

    public static native void destroy(long sealSecretAddr);

    public static native Map<String, double[]> decrypt(long sealSecretAddr, long sealValuationAddr, long ckksSignatureAddr);
}
