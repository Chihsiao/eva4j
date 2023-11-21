#include "io_github_chihsiao_eva4j_jni_EvaSharedTermJNI.h"

#include "jni_bind.h"
using namespace jni;

#include "eva/eva.h"

JNIEXPORT void JNICALL
Java_io_github_chihsiao_eva4j_jni_EvaSharedTermJNI_destroy
(JNIEnv *, jclass, jlong sharedTermAddr) {
    delete (std::shared_ptr<eva::Term>*) sharedTermAddr;
}

JNIEXPORT jint JNICALL
Java_io_github_chihsiao_eva4j_jni_EvaSharedTermJNI_getOp
(JNIEnv *, jclass, jlong sharedTermAddr) {
    auto& sharedTerm = *(std::shared_ptr<eva::Term>*) sharedTermAddr;
    return (jint) sharedTerm->op;
}
