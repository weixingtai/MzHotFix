#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_meizu_mzhotfix_SOFileManager_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "这是一段通过So返回的修复前的字符串";
//    std::string hello = "这是一段通过So返回的修复后的字符串";
    return env->NewStringUTF(hello.c_str());
}