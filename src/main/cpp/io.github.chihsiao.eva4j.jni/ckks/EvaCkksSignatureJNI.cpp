#include "io_github_chihsiao_eva4j_jni_ckks_EvaCkksSignatureJNI.h"

#include "jni_bind.h"
using namespace jni;

#include "eva/eva.h"
#include "java/lang/Long.h"
#include "java/util/HashMap.h"

JNIEXPORT void JNICALL
Java_io_github_chihsiao_eva4j_jni_ckks_EvaCkksSignatureJNI_destroy
(JNIEnv *, jclass, jlong ckksSignatureAddr) {
    delete (eva::CKKSSignature*) ckksSignatureAddr;
}

JNIEXPORT jint JNICALL
Java_io_github_chihsiao_eva4j_jni_ckks_EvaCkksSignatureJNI_getVecSize
(JNIEnv *, jclass, jlong ckksSignatureAddr) {
    return ((eva::CKKSSignature*) ckksSignatureAddr)->vecSize;
}

JNIEXPORT jobject JNICALL
Java_io_github_chihsiao_eva4j_jni_ckks_EvaCkksSignatureJNI_getInputs
(JNIEnv *, jclass, jlong ckksSignatureAddr) {
    auto& ckksSignature = *(eva::CKKSSignature*) ckksSignatureAddr;
    LocalObject<java::util::HashMap> ret {}; {
        for (auto& item: ckksSignature.inputs) {
            auto* value = new eva::CKKSEncodingInfo(item.second);
            ret("put", LocalObject<java::lang::Object> { (jobject) LocalString { item.first }.Release() },
                LocalObject<java::lang::Object> { (jobject) LocalObject<java::lang::Long> { (jlong) value }.Release() });
        }
    }

    return ret.Release();
}
