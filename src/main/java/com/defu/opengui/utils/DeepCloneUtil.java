package com.defu.opengui.utils;

import java.io.*;

/**
 * @author: dfliu
 * @date: 2024/02/23
 **/
public class DeepCloneUtil {

    public static <T extends Serializable> T deepClone(T obj) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        oos.close();
        byte[] bytes = bos.toByteArray();
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        T clonedObj = (T) ois.readObject();
        ois.close();
        return clonedObj;
    }
}
