#include "io_github_chihsiao_eva4j_jni_ckks_EvaCkksEncodingInfoJNI.h"

#include "jni_bind.h"
using namespace jni;

#include "eva/eva.h"

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_ckks_EvaCkksEncodingInfoJNI_create
(JNIEnv *, jclass, jint inputType, jint scale, jint level) {
    return (jlong) new eva::CKKSEncodingInfo((eva::Type) inputType, scale, level);
}

JNIEXPORT void JNICALL
Java_io_github_chihsiao_eva4j_jni_ckks_EvaCkksEncodingInfoJNI_destroy
(JNIEnv *, jclass, jlong ckksEncodingInfoAddr) {
    delete (eva::CKKSEncodingInfo*) ckksEncodingInfoAddr;
}

JNIEXPORT jint JNICALL
Java_io_github_chihsiao_eva4j_jni_ckks_EvaCkksEncodingInfoJNI_getLevel
(JNIEnv *, jclass, jlong ckksEncodingInfoAddr) {
    return ((eva::CKKSEncodingInfo*) ckksEncodingInfoAddr)->level;
}

JNIEXPORT jint JNICALL
Java_io_github_chihsiao_eva4j_jni_ckks_EvaCkksEncodingInfoJNI_getInputType
(JNIEnv *, jclass, jlong ckksEncodingInfoAddr) {
    return (jint) ((eva::CKKSEncodingInfo*) ckksEncodingInfoAddr)->inputType;
}

JNIEXPORT jint JNICALL
Java_io_github_chihsiao_eva4j_jni_ckks_EvaCkksEncodingInfoJNI_getScale
(JNIEnv *, jclass, jlong ckksEncodingInfoAddr) {
    return (jint) ((eva::CKKSEncodingInfo*) ckksEncodingInfoAddr)->scale;
}
