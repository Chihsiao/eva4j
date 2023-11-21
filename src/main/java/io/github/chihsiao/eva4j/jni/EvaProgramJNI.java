package io.github.chihsiao.eva4j.jni;

public final class EvaProgramJNI {
    private EvaProgramJNI() {}

    public static native long create(String name, long vecSize);
    public static native void destroy(long programAddr);

    public static native String getName(long programAddr);
    public static native void setName(long programAddr, String name);

    public static native long getVecSize(long programAddr);

    public static native void setInputScales(long programAddr, int scale);
    public static native void setOutputRanges(long programAddr, int range);

    public static native long makeTerm(long programAddr, int op, long[] sharedTermAddrs);

    public static native long makeInput(long programAddr, String name, int type);
    public static native long makeOutput(long programAddr, String name, long sharedTermAddr);

    public static native long makeLeftRotation(long programAddr, long sharedTermAddr, int slots);
    public static native long makeRightRotation(long programAddr, long sharedTermAddr, int slots);

    public static native long makeUniformConstant(long programAddr, double constValue);
    public static native long makeDenseConstant(long programAddr, double[] values);

    public static native String toDOT(long programAddr);
}
