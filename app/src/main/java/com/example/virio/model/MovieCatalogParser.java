package com.example.virio.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sharlukovich on 02.05.2016.
 */
public class MovieCatalogParser {
    private static final String TAG = MovieCatalogParser.class.getName();
    private final Context context;

    public MovieCatalogParser(Context context) {
        this.context = context;
    }

    public List<Movie> getMovieCatalog() {
        String jsonString = getJsonString();
        Gson gson = new Gson();
        Movie[] movies = gson.fromJson(jsonString, new Movie[]{}.getClass());

        Log.d(TAG, "movies.length = " + movies.length);
        return Arrays.asList(movies);
    }

    private String getJsonString() {
        AssetManager assetManager = context.getAssets();
        String jsonString = null;
        try {
            InputStream is = assetManager.open("movies.json");
            Log.d(TAG, "input stream = " + is);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
            Log.d(TAG, "jsonString = " + jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}