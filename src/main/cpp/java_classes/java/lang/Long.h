#pragma once

#include "jni_bind.h"

namespace java::lang {
    static constexpr jni::Class Long {
        "java/lang/Long",

        jni::Constructor { jlong {} },

        jni::Method { "longValue", jni::Return { jlong {} }, jni::Params {} }
    };
}
