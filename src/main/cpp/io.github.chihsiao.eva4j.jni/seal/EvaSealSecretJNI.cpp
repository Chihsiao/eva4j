#include "io_github_chihsiao_eva4j_jni_seal_EvaSealSecretJNI.h"

#include "jni_bind.h"
using namespace jni;

#include "eva/eva.h"
#include "java/util/HashMap.h"

JNIEXPORT void JNICALL
Java_io_github_chihsiao_eva4j_jni_seal_EvaSealSecretJNI_destroy
(JNIEnv *, jclass, jlong sealSecretAddr) {
    delete (eva::SEALSecret*) sealSecretAddr;
}

JNIEXPORT jobject JNICALL
Java_io_github_chihsiao_eva4j_jni_seal_EvaSealSecretJNI_decrypt
(JNIEnv *, jclass, jlong sealSecretAddr, jlong sealValuationAddr, jlong ckksSignatureAddr) {
    auto& sealSecret = *(eva::SEALSecret*) sealSecretAddr;
    auto& sealValuation = *(eva::SEALValuation*) sealValuationAddr;
    auto& ckksSignature = *(eva::CKKSSignature*) ckksSignatureAddr;
    auto nRet = sealSecret.decrypt(sealValuation, ckksSignature);

    LocalObject<java::util::HashMap> ret {}; {
        for (auto& entry : nRet) {
            LocalString key { entry.first };
            LocalArray<jdouble> value { entry.second.size() }; {
                std::copy(entry.second.begin(), entry.second.end(), value.Pin().ptr());
            }

            ret("put", LocalObject<java::lang::Object> { (jobject) key.Release() },
                LocalObject<java::lang::Object> { (jobject) value.Release() });
        }
    }

    return ret.Release();
}
