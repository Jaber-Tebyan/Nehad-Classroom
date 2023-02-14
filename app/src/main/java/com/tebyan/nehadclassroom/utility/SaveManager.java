package com.tebyan.nehadclassroom.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


public class SaveManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Gson gson;

    public SaveManager(Context context,String fileName){
        sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        gson=new Gson();

    }
    public SaveManager(Context context){
        sharedPreferences=context.getSharedPreferences("DefaultSharedPref",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }


    public void Save(String key,Object obj){
        String text=Serialize(obj);
        editor.putString(key,text);
        editor.apply();
    }
    public <T> T Load(String key,Class<T> type){
        String text=sharedPreferences.getString(key,"");
        return DeSerialize(text,type);
    }
    public String Serialize(Object object){
        return gson.toJson(object);
    }
    public <T> T DeSerialize(String text,Class<T> type){
        return gson.fromJson(text, type);
    }
}
