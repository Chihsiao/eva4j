package io.github.chihsiao.eva4j.jni.seal;

public final class EvaSealModuleJNI {
    private EvaSealModuleJNI() {}

    public static native long[] generateKeys(long ckksParametersAddr);
}
