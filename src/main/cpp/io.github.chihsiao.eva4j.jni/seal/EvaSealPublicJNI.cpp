#include "io_github_chihsiao_eva4j_jni_seal_EvaSealPublicJNI.h"

#include "jni_bind.h"
using namespace jni;

#include "eva/eva.h"
#include "java/util/Map.h"

JNIEXPORT void JNICALL
Java_io_github_chihsiao_eva4j_jni_seal_EvaSealPublicJNI_destroy
(JNIEnv *, jclass, jlong sealPublicAddr) {
    delete (eva::SEALPublic*) sealPublicAddr;
}

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_seal_EvaSealPublicJNI_execute
(JNIEnv *, jclass, jlong sealPublicAddr, jlong programAddr, jlong sealValuationAddr) {
    auto& sealPublic = *(eva::SEALPublic*) sealPublicAddr;
    auto& program = *(eva::Program*) programAddr;
    auto& sealValuation = *(eva::SEALValuation*) sealValuationAddr;
    auto nRet = sealPublic.execute(program, sealValuation);

#pragma clang diagnostic push
#pragma ide diagnostic ignored "MemoryLeak"
    return (jlong) new eva::SEALValuation(std::move(nRet));
#pragma clang diagnostic pop
}

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_seal_EvaSealPublicJNI_encrypt
(JNIEnv *, jclass, jlong sealPublicAddr, jobject jInputs_obj, jlong ckksSignatureAddr) {
    jni::ThreadGuard threadGuard;

    auto& sealPublic = *(eva::SEALPublic*) sealPublicAddr;

    std::unordered_map<std::string, std::vector<double>> inputs; {
        LocalObject<java::util::Map> jInputs { jInputs_obj };
        auto iterator = jInputs("entrySet")("iterator");
        while (iterator("hasNext")) {
            LocalObject<java::util::Map$Entry> next = iterator("next");
            LocalArray<jdouble> jInput { (jdoubleArray) next("getValue").Release() };
            std::vector<double> input(jInput.Length()); {
                auto arrayView = jInput.Pin();
                std::copy(arrayView.begin(),
                          arrayView.end(),
                          input.begin());
            }

            LocalString key { (jstring) next("getKey").Release() };
            inputs.emplace(key.Pin().ToString(), std::move(input));
        }
    }

    auto& ckksSignature = *(eva::CKKSSignature*) ckksSignatureAddr;
    auto nRet = sealPublic.encrypt(inputs, ckksSignature);

#pragma clang diagnostic push
#pragma ide diagnostic ignored "MemoryLeak"
    return (jlong) new eva::SEALValuation(std::move(nRet));
#pragma clang diagnostic pop
}
