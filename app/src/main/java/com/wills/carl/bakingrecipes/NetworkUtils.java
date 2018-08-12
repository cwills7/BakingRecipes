package com.wills.carl.bakingrecipes;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private final static String RECIPE_URL= "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


    public static URL buildRecipeUrl(){
        Uri buildUri = Uri.parse(RECIPE_URL).buildUpon().build();

        URL url = null;
        try{
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }

    public static String getHttpResponse(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = con.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } catch (Exception e){
            return null;

        } finally{
            con.disconnect();
        }
    }

}
