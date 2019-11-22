package com.example.tugasbesar_03.networking;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MySingleton extends Application {
    private static MySingleton mySingleton;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private static Context context;

    public MySingleton() {

    }

    private MySingleton(Context context){
        this.context = context;
        this.requestQueue = getRequestQueue();

        this.imageLoader = new ImageLoader(this.requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> lruCache =
                    new LruCache<String, Bitmap>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return lruCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                lruCache.put(url, bitmap);
            }
        });
    }

    public static synchronized MySingleton getInstance(Context context){
        if(mySingleton == null){
            mySingleton = new MySingleton(context);
        }
        return mySingleton;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            this.requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request){
        getRequestQueue().add(request);
    }

    public ImageLoader getImageLoader(){
        return imageLoader;
    }
}
