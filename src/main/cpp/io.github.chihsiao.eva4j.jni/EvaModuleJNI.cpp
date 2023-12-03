#include "io_github_chihsiao_eva4j_jni_EvaModuleJNI.h"

#include "jni_bind.h"
using namespace jni;

#include "eva/eva.h"
#include "java/util/Map.h"
#include "java/util/HashMap.h"

JNIEXPORT jobject JNICALL
Java_io_github_chihsiao_eva4j_jni_EvaModuleJNI_evaluate
(JNIEnv *env, jclass, jlong programHandle, jobject jInputs_obj) {
    jni::ThreadGuard threadGuard;

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

    auto nRet = eva::evaluate(*(eva::Program*) programHandle, inputs);

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
