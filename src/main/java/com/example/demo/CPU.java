package com.example.demo;

import com.sun.jna.Library;
import com.sun.jna.Native;

//继承Library，用于加载库文件
public interface CPU extends Library {

    CPU INSTANCE = (CPU) Native.loadLibrary("core", CPU.class);

    String getClock(Object object, byte[] paramArrayOfByte, int paramInt);

    String getMagic(Object object, int paramInt);
}