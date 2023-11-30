#include "io_github_chihsiao_eva4j_jni_serialization_EvaKnownTypeJNI.h"

#include "jni_bind.h"
using namespace jni;

#include "eva/eva.h"

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_serialization_EvaKnownTypeJNI_getIfProgram
(JNIEnv *, jclass, jlong knownTypeAddr) {
    auto& knownType = *(eva::KnownType*) knownTypeAddr;
    auto* unique_ptr = std::get_if<std::unique_ptr<eva::Program>>(&knownType);
    if (unique_ptr == nullptr) return (jlong) 0L;
    return (jlong) unique_ptr->release();
}

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_serialization_EvaKnownTypeJNI_getIfCkksParameters
(JNIEnv *, jclass, jlong knownTypeAddr) {
    auto& knownType = *(eva::KnownType*) knownTypeAddr;
    auto* unique_ptr = std::get_if<std::unique_ptr<eva::CKKSParameters>>(&knownType);
    if (unique_ptr == nullptr) return (jlong) 0L;
    return (jlong) unique_ptr->release();
}

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_serialization_EvaKnownTypeJNI_getIfCkksSignature
(JNIEnv *, jclass, jlong knownTypeAddr) {
    auto& knownType = *(eva::KnownType*) knownTypeAddr;
    auto* unique_ptr = std::get_if<std::unique_ptr<eva::CKKSSignature>>(&knownType);
    if (unique_ptr == nullptr) return (jlong) 0L;
    return (jlong) unique_ptr->release();
}

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_serialization_EvaKnownTypeJNI_getIfSealValuation
(JNIEnv *, jclass, jlong knownTypeAddr) {
    auto& knownType = *(eva::KnownType*) knownTypeAddr;
    auto* unique_ptr = std::get_if<std::unique_ptr<eva::SEALValuation>>(&knownType);
    if (unique_ptr == nullptr) return (jlong) 0L;
    return (jlong) unique_ptr->release();
}

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_serialization_EvaKnownTypeJNI_getIfSealPublic
(JNIEnv *, jclass, jlong knownTypeAddr) {
    auto& knownType = *(eva::KnownType*) knownTypeAddr;
    auto* unique_ptr = std::get_if<std::unique_ptr<eva::SEALPublic>>(&knownType);
    if (unique_ptr == nullptr) return (jlong) 0L;
    return (jlong) unique_ptr->release();
}

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_serialization_EvaKnownTypeJNI_getIfSealSecret
(JNIEnv *, jclass, jlong knownTypeAddr) {
    auto& knownType = *(eva::KnownType*) knownTypeAddr;
    auto* unique_ptr = std::get_if<std::unique_ptr<eva::SEALSecret>>(&knownType);
    if (unique_ptr == nullptr) return (jlong) 0L;
    return (jlong) unique_ptr->release();
}

JNIEXPORT void JNICALL
Java_io_github_chihsiao_eva4j_jni_serialization_EvaKnownTypeJNI_destroy
(JNIEnv *, jclass, jlong knownTypeAddr) {
    delete (eva::KnownType*) knownTypeAddr;
}
