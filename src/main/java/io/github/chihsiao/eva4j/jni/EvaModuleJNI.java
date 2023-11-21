package io.github.chihsiao.eva4j.jni;

import java.util.Map;

public final class EvaModuleJNI {
    private EvaModuleJNI() {}

    // TODO: save, load, set_num_threads

    public static native Map<String, double[]> evaluate(long programAddr, Map<String, double[]> inputs);
}
