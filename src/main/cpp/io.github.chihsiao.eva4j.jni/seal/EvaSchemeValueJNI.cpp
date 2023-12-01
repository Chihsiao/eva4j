#include "io_github_chihsiao_eva4j_jni_seal_EvaSchemeValueJNI.h"

#include "jni_bind.h"
using namespace jni;

#include "eva/eva.h"

JNIEXPORT void JNICALL
Java_io_github_chihsiao_eva4j_jni_seal_EvaSchemeValueJNI_destroy
(JNIEnv *, jclass, jlong schemeValueAddr) {
    delete (eva::SchemeValue*) schemeValueAddr;
}
