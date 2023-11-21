#include "io_github_chihsiao_eva4j_jni_ckks_EvaCkksCompilerJNI.h"

#include "jni_bind.h"
using namespace jni;

#include "eva/eva.h"
#include "java/util/Map.h"

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_ckks_EvaCkksCompilerJNI_create
(JNIEnv *, jclass) {
#pragma clang diagnostic push
#pragma ide diagnostic ignored "MemoryLeak"
    return (jlong) new eva::CKKSCompiler();
#pragma clang diagnostic pop
}

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_ckks_EvaCkksCompilerJNI_createWithConfig
(JNIEnv *, jclass, jobject jCkksConfigMap_obj) {
    std::unordered_map<std::string, std::string> ckksConfigMap; {
        LocalObject<java::util::Map> jCkksConfigMap { jCkksConfigMap_obj };
        auto iterator = jCkksConfigMap("entrySet")("iterator");
        while (iterator("hasNext")) {
            LocalObject<java::util::Map$Entry> next = iterator("next");
            LocalString value { (jstring) next("getValue").Release() };
            LocalString key { (jstring) next("getKey").Release() };
            ckksConfigMap.emplace(
                    key.Pin().ToString(),
                    value.Pin().ToString()
            );
        }
    }

#pragma clang diagnostic push
#pragma ide diagnostic ignored "MemoryLeak"
    return (jlong) new eva::CKKSCompiler(eva::CKKSConfig(ckksConfigMap));
#pragma clang diagnostic pop
}

JNIEXPORT void JNICALL
Java_io_github_chihsiao_eva4j_jni_ckks_EvaCkksCompilerJNI_destroy
(JNIEnv *, jclass, jlong ckksCompilerAddr) {
    delete (eva::CKKSCompiler*) ckksCompilerAddr;
}

JNIEXPORT jlongArray JNICALL
Java_io_github_chihsiao_eva4j_jni_ckks_EvaCkksCompilerJNI_compile
(JNIEnv *, jclass, jlong ckksCompilerAddr, jlong programAddr) {
    auto& ckksCompiler = *(eva::CKKSCompiler*) ckksCompilerAddr;
    auto& program = *(eva::Program*) programAddr;
    auto nRet = ckksCompiler.compile(program);

    LocalArray<jlong> ret { 3 }; {
        auto arrayView = ret.Pin();
        arrayView.ptr()[0] = (jlong) std::get<0>(nRet).release();
        arrayView.ptr()[1] = (jlong) new eva::CKKSParameters(std::move(std::get<1>(nRet)));
        arrayView.ptr()[2] = (jlong) new eva::CKKSSignature(std::move(std::get<2>(nRet)));
    }

    return ret.Release();
}
