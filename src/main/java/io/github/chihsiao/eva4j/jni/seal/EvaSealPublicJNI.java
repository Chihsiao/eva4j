package io.github.chihsiao.eva4j.jni.seal;

import java.util.Map;

public final class EvaSealPublicJNI {
    private EvaSealPublicJNI() {}

    public static native void destroy(long sealPublicAddr);

    public static native long execute(long sealPublicAddr, long programAddr, long sealValuationAddr);

    public static native long encrypt(long sealPublicAddr, Map<String, double[]> inputs, long ckksSignatureAddr);
}
