package com.example.dell.ngoproject;

import android.content.Context;
import android.content.SharedPreferences;

class Session{

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Session(Context context) {
        this.context=context;
        sharedPreferences=context.getSharedPreferences("NGO",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

    }


    public void setEmail(String email){
        editor.putString("email",email).commit();
    }

    public void setUserType(String userType){
        editor.putString("userType",userType).commit();
    }

    public String getEmail() {
        return sharedPreferences.getString("email", "");
    }

    public String getUserType(){
        return sharedPreferences.getString("userType", "");
    }

    public void clearData(){
        editor.clear().commit();
    }

    public void setUserId(int id)
    {
        editor.putInt("id",id);
    }
    public int getUserId()
    {
        return sharedPreferences.getInt("id",101);
    }
}
