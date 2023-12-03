#include "io_github_chihsiao_eva4j_jni_seal_EvaSealValuationJNI.h"

#include "jni_bind.h"
using namespace jni;

#include "eva/eva.h"
#include "exposer.h"

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_seal_EvaSealValuationJNI_create
(JNIEnv *, jclass, jlong sealPublicAddr) {
    auto& sealPublic = *(eva::SEALPublic*) sealPublicAddr;
    seal::SEALContext& sealPublic_context = eva4j::magic_access::get_context_of_eva_SEALPublic(sealPublic);
    return (jlong) new eva::SEALValuation(sealPublic_context);
}

JNIEXPORT void JNICALL
Java_io_github_chihsiao_eva4j_jni_seal_EvaSealValuationJNI_destroy
(JNIEnv *, jclass, jlong sealValuationAddr) {
    delete (eva::SEALValuation*) sealValuationAddr;
}

JNIEXPORT jobjectArray JNICALL
Java_io_github_chihsiao_eva4j_jni_seal_EvaSealValuationJNI_getKeys
(JNIEnv *, jclass, jlong sealValuationAddr) {
    jni::ThreadGuard threadGuard;

    auto& sealValuation = *(eva::SEALValuation*) sealValuationAddr;
    std::vector<std::string> keys; {
        for (auto const& item : sealValuation) {
            keys.emplace_back(item.first);
        }
    }

    LocalArray<jstring> ret { keys.size() }; {
        for (size_t i = 0; i < keys.size(); ++i) {
            ret.Set(i, keys[i]);
        }
    }

    return ret.Release();
}

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_seal_EvaSealValuationJNI_getValue
(JNIEnv *, jclass, jlong sealValuationAddr, jstring jKey_obj) {
    jni::ThreadGuard threadGuard;

    auto& sealValuation = *(eva::SEALValuation*) sealValuationAddr;
    std::string key(LocalString { jKey_obj }.Pin().ToString());
    auto& nRet = sealValuation[key];

    return (jlong) new eva::SchemeValue(nRet);
}

JNIEXPORT void JNICALL
Java_io_github_chihsiao_eva4j_jni_seal_EvaSealValuationJNI_setValue
(JNIEnv *, jclass, jlong sealValuationAddr, jstring jKey_obj, jlong schemeValueAddr) {
    jni::ThreadGuard threadGuard;

    auto& sealValuation = *(eva::SEALValuation*) sealValuationAddr;
    std::string key(LocalString { jKey_obj }.Pin().ToString());
    auto& schemeValue = *(eva::SchemeValue*) schemeValueAddr;
    sealValuation[key] = schemeValue;
}
