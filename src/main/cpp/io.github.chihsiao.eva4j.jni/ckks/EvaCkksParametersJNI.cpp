#include "io_github_chihsiao_eva4j_jni_ckks_EvaCkksParametersJNI.h"

#include "jni_bind.h"
using namespace jni;

#include "eva/eva.h"

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_ckks_EvaCkksParametersJNI_create
(JNIEnv *, jclass, jintArray jPrimeBits_obj, jintArray jRotations_obj, jint polyModulusDegree) {
    jni::ThreadGuard threadGuard;

    auto ret = new eva::CKKSParameters();
    ret->polyModulusDegree = polyModulusDegree;

    LocalArray<jint> jPrimeBits { jPrimeBits_obj };
    std::vector<std::uint32_t> primeBits(jPrimeBits.Length()); {
        auto arrayView = jPrimeBits.Pin();
        std::copy(arrayView.begin(),
                  arrayView.end(),
                  primeBits.begin());
    }

    ret->primeBits = std::move(primeBits);

    std::set<int> rotations; {
        auto arrayView = LocalArray<jint> { jRotations_obj }.Pin();
        rotations.insert(arrayView.begin(), arrayView.end());
    }

    ret->rotations = std::move(rotations);

    return (jlong) ret;
}

JNIEXPORT void JNICALL
Java_io_github_chihsiao_eva4j_jni_ckks_EvaCkksParametersJNI_destroy
(JNIEnv *, jclass, jlong ckksParametersAddr) {
    delete (eva::CKKSParameters*) ckksParametersAddr;
}

JNIEXPORT jintArray JNICALL
Java_io_github_chihsiao_eva4j_jni_ckks_EvaCkksParametersJNI_getPrimeBits
(JNIEnv *, jclass, jlong ckksParametersAddr) {
    jni::ThreadGuard threadGuard;

    auto& ckksParameters = *(eva::CKKSParameters*) ckksParametersAddr;
    LocalArray<jint> ret { ckksParameters.primeBits.size() }; {
        auto arrayView = ret.Pin();
        std::copy(ckksParameters.primeBits.cbegin(),
                  ckksParameters.primeBits.cend(),
                  arrayView.begin());
    }

    return ret.Release();
}

JNIEXPORT jintArray JNICALL
Java_io_github_chihsiao_eva4j_jni_ckks_EvaCkksParametersJNI_getRotations
(JNIEnv *, jclass, jlong ckksParametersAddr) {
    jni::ThreadGuard threadGuard;

    auto& ckksParameters = *(eva::CKKSParameters*) ckksParametersAddr;
    LocalArray<jint> ret { ckksParameters.rotations.size() }; {
        auto arrayView = ret.Pin();
        std::copy(ckksParameters.rotations.cbegin(),
                  ckksParameters.rotations.cend(),
                  arrayView.begin());
    }

    return ret.Release();
}

JNIEXPORT jint JNICALL
Java_io_github_chihsiao_eva4j_jni_ckks_EvaCkksParametersJNI_getPolyModulusDegree
(JNIEnv *, jclass, jlong ckksParametersAddr) {
    return (jint) ((eva::CKKSParameters*) ckksParametersAddr)->polyModulusDegree;
}
