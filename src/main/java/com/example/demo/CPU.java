package com.example.demo;

import com.sun.jna.Library;
import com.sun.jna.Native;

//继承Library，用于加载库文件
public interface CPU extends Library {

    CPU INSTANCE = (CPU) Native.loadLibrary("core", CPU.class);

    String a(Object object, byte[] paramArrayOfByte, int paramInt);// Byte code:
    //   0: ldc com/yxcorp/gifshow/util/CPU
    //   2: monitorenter
    //   3: aload_0
    //   4: aload_1
    //   5: iload_2
    //   6: invokestatic getClock : (Landroid/content/Context;[BI)Ljava/lang/String;
    //   9: astore_0
    //   10: ldc com/yxcorp/gifshow/util/CPU
    //   12: monitorexit
    //   13: aload_0
    //   14: areturn
    //   15: astore_0
    //   16: ldc com/yxcorp/gifshow/util/CPU
    //   18: monitorexit
    //   19: aload_0
    //   20: athrow
    // Exception table:
    //   from	to	target	type
    //   3	10	15	finally }

    String getClock(Object object, byte[] paramArrayOfByte, int paramInt);

    String getMagic(Object object, int paramInt);
}