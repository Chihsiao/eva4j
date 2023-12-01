// from https://github.com/HerrCai0907/cpp-private-exposer/blob/main/src/exposer.hpp

#pragma once

namespace exposer {

    template <class MemberType>
    struct Exposer {
        static MemberType memberPtr;
    };

    template <class MemberType>
    MemberType Exposer<MemberType>::memberPtr;

    template <class MemberType, MemberType MemberPtr>
    struct ExposerImpl {
        static struct ExposerFactory {
            ExposerFactory() {
                Exposer<MemberType>::memberPtr = MemberPtr;
            }
        } factory;
    };

    template <class MemberType, MemberType Ptr>
    typename ExposerImpl<MemberType, Ptr>::ExposerFactory ExposerImpl<MemberType, Ptr>::factory;

} // namespace exposer

#define ACCESS(NS, FuncName, ClassName, AttrName, AttrType)                                     \
    template struct exposer::ExposerImpl<decltype(&ClassName::AttrName), &ClassName::AttrName>; \
    namespace NS {                                                                              \
        AttrType& FuncName(ClassName& T) {                                                      \
            return T.*exposer::Exposer<AttrType ClassName::*>::memberPtr;                       \
        }                                                                                       \
    }

ACCESS(eva4j::magic_access, get_context_of_eva_SEALPublic, eva::SEALPublic, context, seal::SEALContext)
