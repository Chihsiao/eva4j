package io.github.chihsiao.eva4kt

import io.github.chihsiao.eva4j.jni.EvaModuleJNI

// TODO: save, load, set_num_threads

fun evaluate(
    program: EvaProgram,
    inputs: Map<String, DoubleArray>
): Map<String, DoubleArray> = EvaModuleJNI.evaluate(program.handle, inputs)
