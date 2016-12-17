package org.tangkysin.episodeprogress;

import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Tang on 2016/10/7.
 */

public class Tools {

    public static void saveEpisodeArrayList(ArrayList<Episode> episodeArrayList) {
        SharedPreferences preferences = MainActivity.getInstance().getSharedPreferences("base64",
                MODE_PRIVATE);
        // 创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 创建对象输出流，并封装字节流
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(episodeArrayList);
            // 将字节流编码成base64的字符窜
            String oAuth_Base64 = new String(Base64.encode(baos
                    .toByteArray(), Base64.DEFAULT));
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("EpisodeArrayList", oAuth_Base64);

            editor.commit();
        } catch (IOException e) {
            // TODO Auto-generated
            e.printStackTrace();
        }
    }

    public static ArrayList<Episode> readEpisodeArrayList() {
        ArrayList<Episode> retVal = null;
        SharedPreferences preferences = MainActivity.getInstance().getSharedPreferences("base64",
                MODE_PRIVATE);
        String productBase64 = preferences.getString("EpisodeArrayList", "");

        //读取字节
        byte[] base64 = Base64.decode(productBase64.getBytes(), Base64.DEFAULT);

        //封装到字节流
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try {
            //再次封装
            ObjectInputStream bis = new ObjectInputStream(bais);
            try {
                //读取对象
                retVal = (ArrayList<Episode>) bis.readObject();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                retVal = new ArrayList<Episode>();
            }
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            retVal = new ArrayList<Episode>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            retVal = new ArrayList<Episode>();
        }
        return retVal;
    }
}
