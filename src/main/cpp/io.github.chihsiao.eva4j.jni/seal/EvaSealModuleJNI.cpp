#include "io_github_chihsiao_eva4j_jni_seal_EvaSealModuleJNI.h"

#include "jni_bind.h"
using namespace jni;

#include "eva/eva.h"

JNIEXPORT jlongArray JNICALL
Java_io_github_chihsiao_eva4j_jni_seal_EvaSealModuleJNI_generateKeys
(JNIEnv *, jclass, jlong ckksParametersAddr) {
    jni::ThreadGuard threadGuard;

    auto& ckksParameters = *(eva::CKKSParameters*) ckksParametersAddr;
    auto nRet = generateKeys(ckksParameters);

    LocalArray<jlong> ret { 2 }; {
        auto arrayView = ret.Pin();
        arrayView.ptr()[0] = (jlong) std::get<0>(nRet).release();
        arrayView.ptr()[1] = (jlong) std::get<1>(nRet).release();
    }

    return ret.Release();
}
