#pragma once

#include "jni_bind.h"
#include "java/lang/Object.h"

namespace java::util {
    static constexpr jni::Class Iterator {
        "java/util/Iterator",

        jni::Method { "hasNext", jni::Return { jboolean {} }, jni::Params {} },
        jni::Method { "next", jni::Return { java::lang::Object }, jni::Params {} }
    };
}
