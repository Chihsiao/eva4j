#include "io_github_chihsiao_eva4j_jni_serialization_EvaSaveLoadJNI.h"

#include "jni_bind.h"
using namespace jni;

#include "eva/eva.h"

JNIEXPORT jbyteArray JNICALL
Java_io_github_chihsiao_eva4j_jni_serialization_EvaSaveLoadJNI_saveToByteArray_1Program
(JNIEnv *env, jclass, jlong programAddr) {
    jni::ThreadGuard threadGuard;

    auto& program = *(eva::Program*) programAddr;
    std::string serialized = eva::saveToString(program);
    LocalArray<jbyte> ret { serialized.size() }; {
        std::copy(serialized.begin(), serialized.end(), ret.Pin().ptr());
    }

    return ret.Release();
}

JNIEXPORT jbyteArray JNICALL
Java_io_github_chihsiao_eva4j_jni_serialization_EvaSaveLoadJNI_saveToByteArray_1CkksParameters
(JNIEnv *, jclass, jlong ckksParametersAddr) {
    jni::ThreadGuard threadGuard;

    auto& ckksParameters = *(eva::CKKSParameters*) ckksParametersAddr;
    std::string serialized = eva::saveToString(ckksParameters);
    LocalArray<jbyte> ret { serialized.size() }; {
        std::copy(serialized.begin(), serialized.end(), ret.Pin().ptr());
    }

    return ret.Release();
}

JNIEXPORT jbyteArray JNICALL
Java_io_github_chihsiao_eva4j_jni_serialization_EvaSaveLoadJNI_saveToByteArray_1CkksSignature
(JNIEnv *, jclass, jlong ckksSignatureAddr) {
    jni::ThreadGuard threadGuard;

    auto& ckksSignature = *(eva::CKKSSignature*) ckksSignatureAddr;
    std::string serialized = eva::saveToString(ckksSignature);
    LocalArray<jbyte> ret { serialized.size() }; {
        std::copy(serialized.begin(), serialized.end(), ret.Pin().ptr());
    }

    return ret.Release();
}

JNIEXPORT jbyteArray JNICALL
Java_io_github_chihsiao_eva4j_jni_serialization_EvaSaveLoadJNI_saveToByteArray_1SealValuation
(JNIEnv *, jclass, jlong sealValuationAddr) {
    jni::ThreadGuard threadGuard;

    auto& sealValuation = *(eva::SEALValuation*) sealValuationAddr;
    std::string serialized = eva::saveToString(sealValuation);
    LocalArray<jbyte> ret { serialized.size() }; {
        std::copy(serialized.begin(), serialized.end(), ret.Pin().ptr());
    }

    return ret.Release();
}

JNIEXPORT jbyteArray JNICALL
Java_io_github_chihsiao_eva4j_jni_serialization_EvaSaveLoadJNI_saveToByteArray_1SealPublic
(JNIEnv *, jclass, jlong sealPublicAddr) {
    jni::ThreadGuard threadGuard;

    auto& sealPublic = *(eva::SEALPublic*) sealPublicAddr;
    std::string serialized = eva::saveToString(sealPublic);
    LocalArray<jbyte> ret { serialized.size() }; {
        std::copy(serialized.begin(), serialized.end(), ret.Pin().ptr());
    }

    return ret.Release();
}

JNIEXPORT jbyteArray JNICALL
Java_io_github_chihsiao_eva4j_jni_serialization_EvaSaveLoadJNI_saveToByteArray_1SealSecret
(JNIEnv *, jclass, jlong sealSecretAddr) {
    jni::ThreadGuard threadGuard;

    auto& sealSecret = *(eva::SEALSecret*) sealSecretAddr;
    std::string serialized = eva::saveToString(sealSecret);
    LocalArray<jbyte> ret { serialized.size() }; {
        std::copy(serialized.begin(), serialized.end(), ret.Pin().ptr());
    }

    return ret.Release();
}

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_serialization_EvaSaveLoadJNI_loadFromByteArray
(JNIEnv *, jclass, jbyteArray jByteArray_obj) {
    jni::ThreadGuard threadGuard;

    LocalArray<jbyte> jByteArray { jByteArray_obj };
    auto arrayView = jByteArray.Pin();

    std::string str(arrayView.begin(), arrayView.end());
    return (jlong) new eva::KnownType(std::move(eva::loadFromString(str)));
}
