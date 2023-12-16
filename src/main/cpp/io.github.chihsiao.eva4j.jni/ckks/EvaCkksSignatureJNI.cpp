#include "io_github_chihsiao_eva4j_jni_ckks_EvaCkksSignatureJNI.h"

#include "jni_bind.h"
using namespace jni;

#include "eva/eva.h"
#include "java/lang/Long.h"
#include "java/util/HashMap.h"
#include "java/util/Map.h"

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_ckks_EvaCkksSignatureJNI_create
(JNIEnv *, jclass, jint vecSize, jobject jInputs_obj) {
    jni::ThreadGuard threadGuard;

    std::unordered_map<std::string, eva::CKKSEncodingInfo> inputs; {
        LocalObject<java::util::Map> jInputs { jInputs_obj };
        auto iterator = jInputs("entrySet")("iterator");
        while (iterator("hasNext")) {
            LocalObject<java::util::Map$Entry> next = iterator("next");
            LocalString key { (jstring) next("getKey").Release() };
            LocalObject<java::lang::Long> jCkksEncodingInfoAddr = next("getValue");
            auto& input = *(eva::CKKSEncodingInfo*) jCkksEncodingInfoAddr("longValue");
            inputs.emplace(key.Pin().ToString(), eva::CKKSEncodingInfo(input));
        }
    }

    return (jlong) new eva::CKKSSignature(vecSize, std::move(inputs));
}

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
    jni::ThreadGuard threadGuard;

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
