//
// Created by luozhiran on 2020/5/6.
//

#include "com_yk_cdemo_JniUtils.h"


//引起crash
void add() {
    volatile int *a = (int *) (NULL);
    *a = 1;
}


extern "C"
JNIEXPORT int JNICALL
Java_com_yk_cdemo_JniUtils_add(JNIEnv *env, jobject thiz) {
    // TODO: implement add()
    add();
    return 1 + 3;
}




