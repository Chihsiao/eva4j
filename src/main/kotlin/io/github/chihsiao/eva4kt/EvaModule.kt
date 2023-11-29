package io.github.chihsiao.eva4kt

import io.github.chihsiao.eva4j.jni.EvaModuleJNI.evaluate

fun evaluate(
        program: EvaProgram,
        inputs: Map<String, DoubleArray>
): Map<String, DoubleArray> = evaluate(program.nativeAddr, inputs)
