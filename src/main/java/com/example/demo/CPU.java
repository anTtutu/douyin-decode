//package com.example.demo;
//
//
////继承Library，用于加载库文件
//public class CPU{
//
//    static {
//        LibLoader.loadLib("libcore.so");
//    }
//
////    @Autowired
////    CPU INSTANTCE = (CPU) Native.loadLibrary("core", CPU.class);
//
//
//    public static native String a(Object paramContext, byte[] paramArrayOfByte, int paramInt);// Byte code:
//    //   0: ldc com/yxcorp/gifshow/util/CPU
//    //   2: monitorenter
//    //   3: aload_0
//    //   4: aload_1
//    //   5: iload_2
//    //   6: invokestatic getClock : (Landroid/content/Context;[BI)Ljava/lang/String;
//    //   9: astore_0
//    //   10: ldc com/yxcorp/gifshow/util/CPU
//    //   12: monitorexit
//    //   13: aload_0
//    //   14: areturn
//    //   15: astore_0
//    //   16: ldc com/yxcorp/gifshow/util/CPU
//    //   18: monitorexit
//    //   19: aload_0
//    //   20: athrow
//    // Exception table:
//    //   from	to	target	type
//    //   3	10	15	finally }
//
//    public static native String getClock(Object paramContext, byte[] paramArrayOfByte, int paramInt);
//
//    public static native String getMagic(Object paramContext, int paramInt);
//}