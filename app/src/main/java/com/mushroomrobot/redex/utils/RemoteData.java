package com.mushroomrobot.redex.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Nick.
 */

//This class shall serve as a utility class that handles network connections

public class RemoteData {


    public static HttpURLConnection getConnection(String url){
        System.out.println("URL: " + url);
        HttpURLConnection hcon = null;

        try {
            hcon = (HttpURLConnection) new URL(url).openConnection();
            hcon.setReadTimeout(30000); //Timeout at 30 seconds
            hcon.setRequestProperty("User-Agent", "android:com.mushroomrobot.redex:v0.2 (by /u/ferspec)");

        }catch (MalformedURLException e){
            Log.e("getConnection()", "Invalid URL: " + e.toString());
        } catch (IOException e){
            Log.e("getConnection()", "Could not connect: " + e.toString());
        }

        return hcon;
    }

    public static String readContents(String url){

        //Check if the cache returns data for this url
        byte[] t = MyCache.read(url);
        String cached = null;
        if (t!=null){
            cached = new String(t);
            t = null;
        }
        if (cached!=null){
            Log.d("MSG", "Using cache for " + url);
            return cached;
        }

        //The following will be executed only if the cache did not contain data for this URL
        HttpURLConnection hcon = getConnection(url);
        if(hcon==null) return null;
        try {
            StringBuffer sb = new StringBuffer(8192);
            String tmp = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(hcon.getInputStream()));
            while((tmp=br.readLine())!=null){
                sb.append(tmp).append("\n");
            }
            br.close();

            //We now add this data to the cache
            MyCache.write(url, sb.toString());
            return sb.toString();
        } catch (IOException e){
            Log.d("READ FAILED", e.toString());
            return null;
        }
    }
}
