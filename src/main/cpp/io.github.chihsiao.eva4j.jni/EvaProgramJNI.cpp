#include "io_github_chihsiao_eva4j_jni_EvaProgramJNI.h"

#include "jni_bind.h"
using namespace jni;

#include "eva/eva.h"

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_EvaProgramJNI_create
(JNIEnv *, jclass, jstring jName_obj, jlong vecSize) {
    jni::ThreadGuard threadGuard;

    LocalString jName { jName_obj };
#pragma clang diagnostic push
#pragma ide diagnostic ignored "MemoryLeak"
    return (jlong) new eva::Program(std::string(jName.Pin().ToString()), vecSize);
#pragma clang diagnostic pop
}

JNIEXPORT void JNICALL
Java_io_github_chihsiao_eva4j_jni_EvaProgramJNI_destroy
(JNIEnv *, jclass, jlong programAddr) {
    delete (eva::Program*) programAddr;
}

JNIEXPORT jstring JNICALL
Java_io_github_chihsiao_eva4j_jni_EvaProgramJNI_getName
(JNIEnv *, jclass, jlong programAddr) {
    jni::ThreadGuard threadGuard;

    auto& program = *(eva::Program*) programAddr;
    LocalString ret { program.getName() };
    return ret.Release();
}

JNIEXPORT void JNICALL
Java_io_github_chihsiao_eva4j_jni_EvaProgramJNI_setName
(JNIEnv *, jclass, jlong programAddr, jstring jName_obj) {
    jni::ThreadGuard threadGuard;

    std::string name { LocalString { jName_obj }.Pin().ToString() };
    auto& program = *(eva::Program*) programAddr;

    program.setName(name);
}

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_EvaProgramJNI_getVecSize
(JNIEnv *, jclass, jlong programAddr) {
    auto& program = *(eva::Program*) programAddr;
    return (jlong) program.getVecSize();
}

JNIEXPORT void JNICALL
Java_io_github_chihsiao_eva4j_jni_EvaProgramJNI_setInputScales
(JNIEnv *, jclass, jlong programAddr, jint scale) {
    auto& program = *(eva::Program*) programAddr;
    for (auto& source : program.getSources()) {
        source->set<eva::EncodeAtScaleAttribute>(scale);
    }
}

JNIEXPORT void JNICALL
Java_io_github_chihsiao_eva4j_jni_EvaProgramJNI_setOutputRanges
(JNIEnv *, jclass, jlong programAddr, jint range) {
    auto& program = *(eva::Program*) programAddr;
    for (auto& entry : program.getOutputs()) {
        entry.second->set<eva::RangeAttribute>(range);
    }
}

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_EvaProgramJNI_makeTerm
(JNIEnv *, jclass, jlong programAddr, jint jOp, jlongArray jSharedTermAddrs_obj) {
    jni::ThreadGuard threadGuard;

    auto& program = *(eva::Program*) programAddr;
    std::vector<std::shared_ptr<eva::Term>> operands; {
        LocalArray<jlong> jSharedTermAddrs { jSharedTermAddrs_obj }; {
            auto arrayView = jSharedTermAddrs.Pin();
            for (const auto &item: arrayView) {
                operands.emplace_back(*(std::shared_ptr<eva::Term>*) item);
            }
        }
    }

    auto nRet = program.makeTerm((eva::Op) jOp, operands);
    return (jlong) new std::shared_ptr<eva::Term>(std::move(nRet));
}

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_EvaProgramJNI_makeInput
(JNIEnv *, jclass, jlong programAddr, jstring jName_obj, jint jType) {
    jni::ThreadGuard threadGuard;

    std::string name { LocalString { jName_obj }.Pin().ToString() };
    auto& program = *(eva::Program*) programAddr;

    auto nRet = program.makeInput(name, (eva::Type) jType);
    return (jlong) new std::shared_ptr<eva::Term>(std::move(nRet));
}

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_EvaProgramJNI_makeOutput
(JNIEnv *, jclass, jlong programAddr, jstring jName_obj, jlong sharedTermAddr) {
    jni::ThreadGuard threadGuard;

    auto& program = *(eva::Program*) programAddr;
    std::string name { LocalString { jName_obj }.Pin().ToString() };
    auto& sharedTerm = *(std::shared_ptr<eva::Term>*) sharedTermAddr;

    auto nRet = program.makeOutput(name, sharedTerm);
    return (jlong) new std::shared_ptr<eva::Term>(std::move(nRet));
}

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_EvaProgramJNI_makeLeftRotation
(JNIEnv *, jclass, jlong programAddr, jlong sharedTermAddr, jint slots) {
    auto& sharedTerm = *(std::shared_ptr<eva::Term>*) sharedTermAddr;
    auto& program = *(eva::Program*) programAddr;

    auto nRet = program.makeLeftRotation(sharedTerm, slots);
    return (jlong) new std::shared_ptr<eva::Term>(std::move(nRet));
}

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_EvaProgramJNI_makeRightRotation
(JNIEnv *, jclass, jlong programAddr, jlong sharedTermAddr, jint slots) {
    auto& sharedTerm = *(std::shared_ptr<eva::Term>*) sharedTermAddr;
    auto& program = *(eva::Program*) programAddr;

    auto nRet = program.makeRightRotation(sharedTerm, slots);
    return (jlong) new std::shared_ptr<eva::Term>(std::move(nRet));
}

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_EvaProgramJNI_makeUniformConstant
(JNIEnv *, jclass, jlong programAddr, jdouble constValue) {
    auto& program = *(eva::Program*) programAddr;

    auto nRet = program.makeUniformConstant(constValue);
    return (jlong) new std::shared_ptr<eva::Term>(std::move(nRet));
}

JNIEXPORT jlong JNICALL
Java_io_github_chihsiao_eva4j_jni_EvaProgramJNI_makeDenseConstant
(JNIEnv *, jclass, jlong programAddr, jdoubleArray jValues_obj) {
    jni::ThreadGuard threadGuard;

    auto& program = *(eva::Program*) programAddr;
    LocalArray<jdouble> jValues { jValues_obj };
    std::vector<double> values(jValues.Length()); {
        auto arrayView = jValues.Pin();
        std::copy(arrayView.begin(), arrayView.end(), values.begin());
    }

    auto nRet = program.makeDenseConstant(values);
    return (jlong) new std::shared_ptr<eva::Term>(std::move(nRet));
}

JNIEXPORT jstring JNICALL
Java_io_github_chihsiao_eva4j_jni_EvaProgramJNI_toDOT
(JNIEnv *, jclass, jlong programAddr) {
    jni::ThreadGuard threadGuard;

    auto& program = *(eva::Program*) programAddr;
    LocalString ret { program.toDOT() };
    return ret.Release();
}
