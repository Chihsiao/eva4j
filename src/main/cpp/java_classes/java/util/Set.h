#pragma once

#include "jni_bind.h"
#include "java/util/Iterator.h"

namespace java::util {
    static constexpr jni::Class Set {
        "java/util/Set",

        jni::Method { "iterator", jni::Return { java::util::Iterator }, jni::Params {} }
    };
}
