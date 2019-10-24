package com.example.demo;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * @author xinyan
 * @title: CPU
 * @projectName demo
 * @description: TODO
 * @date 2019/9/30 11:17
 */
//继承Library，用于加载库文件
public interface CPU extends Library {
    String os = System.getProperty("os.name");  // 获取当前操作系统的类型
    int beginIndex = os != null && os.startsWith("Windows") ? 1 : 0;// windows操作系统为1 否则为0

    CPU ar = (CPU) Native.synchronizedLibrary(
            (CPU) Native.loadLibrary(
                    CPU.class.getResource("/libcore.so")
                            .getPath()
                            .substring(beginIndex)
                    , CPU.class
            )
    );

    String getClock(Object context, byte[] bArr, int i);

    String getMagic(Object context, int i);

//    static {
//        ar.a("core");
//    }
//
//    synchronized String a(Context context, byte[] bArr, int i) {
//        String clock;
//        synchronized (CPU.class) {
//            clock = getClock(context, bArr, i);
//        }
//        return clock;
//    }


}
