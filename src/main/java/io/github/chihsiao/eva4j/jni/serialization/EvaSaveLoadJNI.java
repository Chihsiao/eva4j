package io.github.chihsiao.eva4j.jni.serialization;

public final class EvaSaveLoadJNI {
    private EvaSaveLoadJNI() {}

    public static native byte[] saveToByteArray_Program(long programAddr);
    public static native byte[] saveToByteArray_CkksParameters(long ckksParametersAddr);
    public static native byte[] saveToByteArray_CkksSignature(long ckksSignatureAddr);
    public static native byte[] saveToByteArray_SealValuation(long sealValuationAddr);
    public static native byte[] saveToByteArray_SealPublic(long sealPublicAddr);
    public static native byte[] saveToByteArray_SealSecret(long sealSecretAddr);

    public static native long loadFromByteArray(byte[] bytes);
}
