package io.github.chihsiao.eva4j.jni;

import java.util.Map;

public final class EvaModuleJNI {
    private EvaModuleJNI() {}

    public static native Map<String, double[]> evaluate(long programAddr, Map<String, double[]> inputs);
}
