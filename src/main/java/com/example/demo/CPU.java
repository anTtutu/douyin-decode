package com.example.demo;

import com.sun.jna.Library;
import com.sun.jna.Native;
import org.apache.catalina.Context;

//继承Library，用于加载库文件
public interface CPU extends Library {

    String os = System.getProperty("os.name");  // 获取当前操作系统的类型
    int beginIndex = os != null && os.startsWith("Windows") ? 1 : 0;// windows操作系统为1 否则为0

    CPU INSTANTCE = (CPU) Native.synchronizedLibrary(
            (CPU) Native.loadLibrary(
                    CPU.class.getResource("/libcore.so")
                            .getPath()
                            .substring(beginIndex)
                    , CPU.class
            )
    );


    String a(Context paramContext, byte[] paramArrayOfByte, int paramInt);// Byte code:
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

    String getClock(Context paramContext, byte[] paramArrayOfByte, int paramInt);

    String getMagic(Context paramContext, int paramInt);
}