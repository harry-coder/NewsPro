package com.example.harpreet.okhlee.volly;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Harpreet on 21/02/2017.
 */

public class vollySingleton {

    static vollySingleton instance = null;
    RequestQueue requestQueue = null;
    public static ImageLoader imageLoader;

    private vollySingleton() {
        requestQueue = Volley.newRequestQueue(MyApplication.getContext());
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            LruCache<String, Bitmap> cache = new LruCache((int) (Runtime.getRuntime().maxMemory()) / 1024 / 8);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

                cache.put(url, bitmap);
            }
        });

    }

    public static vollySingleton getInstance() {
        if (instance == null) {
            return instance = new vollySingleton();
        }

        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
