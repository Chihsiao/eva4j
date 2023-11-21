#include "jni_bind.h"
#include <memory>

static std::unique_ptr<jni::JvmRef<jni::kDefaultJvm>> jvm;

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void*) {
    jvm = std::make_unique<jni::JvmRef<jni::kDefaultJvm>>(vm);
    return JNI_VERSION_1_6;
}
