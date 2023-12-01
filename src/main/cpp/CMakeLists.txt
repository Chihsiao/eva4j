cmake_minimum_required(VERSION 3.5)
project(eva4j_native)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -stdlib=libc++")

find_package(Java 1.8 REQUIRED COMPONENTS Development)
find_package(JNI REQUIRED)
include(UseJava)

add_subdirectory(third_party/EVA)

add_library(eva4j_native SHARED)

target_sources(
        eva4j_native PRIVATE
        io.github.chihsiao.eva4j.jni/EvaInitJNI.cpp
        io.github.chihsiao.eva4j.jni/EvaModuleJNI.cpp
        io.github.chihsiao.eva4j.jni/EvaProgramJNI.cpp
        io.github.chihsiao.eva4j.jni/EvaSharedTermJNI.cpp

        io.github.chihsiao.eva4j.jni/seal/EvaSealModuleJNI.cpp
        io.github.chihsiao.eva4j.jni/seal/EvaSealPublicJNI.cpp
        io.github.chihsiao.eva4j.jni/seal/EvaSealSecretJNI.cpp
        io.github.chihsiao.eva4j.jni/seal/EvaSealValuationJNI.cpp

        io.github.chihsiao.eva4j.jni/ckks/EvaCkksCompilerJNI.cpp
        io.github.chihsiao.eva4j.jni/ckks/EvaCkksEncodingInfoJNI.cpp
        io.github.chihsiao.eva4j.jni/ckks/EvaCkksParametersJNI.cpp
        io.github.chihsiao.eva4j.jni/ckks/EvaCkksSignatureJNI.cpp)

target_sources(
        eva4j_native PRIVATE
        io.github.chihsiao.eva4j.jni/serialization/EvaKnownTypeJNI.cpp
        io.github.chihsiao.eva4j.jni/serialization/EvaSaveLoadJNI.cpp)

target_include_directories(eva4j_native PUBLIC
        ${JNI_INCLUDE_DIRS} ../../../build/generated/sources/headers/java/main)

target_include_directories(eva4j_native PRIVATE java_classes)

target_link_libraries(eva4j_native PUBLIC ${JNI_LIBRARIES} eva)
