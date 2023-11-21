#pragma once

#include "jni_bind.h"
#include "java/util/Set.h"

namespace java::util {
    static constexpr jni::Class Map {
        "java/util/Map",

        jni::Method { "entrySet", jni::Return { java::util::Set }, jni::Params {}}
    };

    static constexpr Class Map$Entry {
        "java/util/Map$Entry",

        jni::Method { "getKey", jni::Return { java::lang::Object }, jni::Params {} },
        jni::Method { "getValue", jni::Return { java::lang::Object }, jni::Params {} }
    };
}
