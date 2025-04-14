package com.example.encode;

public class sortdata {

    public static boolean ValidString(String string){
        if(string.length() >= 5 && string.length() <= 12){
            return true;
        }
        else {
            return false;
        }
    }
}
