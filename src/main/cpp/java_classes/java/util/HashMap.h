#pragma once

#include "jni_bind.h"
#include "java/lang/Object.h"

namespace java::util {
    static constexpr jni::Class HashMap {
        "java/util/HashMap",

        jni::Constructor {},

        jni::Method { "put", jni::Return { java::lang::Object }, jni::Params { java::lang::Object, java::lang::Object } }
    };
}
