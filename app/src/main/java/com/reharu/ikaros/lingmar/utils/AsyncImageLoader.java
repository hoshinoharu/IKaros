package com.reharu.ikaros.lingmar.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.reharu.harubase.tools.HLog;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Lingmar on 2017/3/18.
 */

public class AsyncImageLoader {

    private ImageView imageView;
    private String url;

    public AsyncImageLoader(ImageView imageView, String url) {
        this.imageView = imageView;
        this.url = url;
    }

    /**
     * 异步多线程加载图片
     */
    public void showImageByAsyncTask() {
        new LoadImageAsyncTask().execute(url);
    }

    class LoadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            return getBitmapFromURL(strings[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            // 放置图片
            imageView.setImageBitmap(bitmap);
        }
    }

    /**
     * 异步加载图片
     * @param urlString
     * @return
     */
    private Bitmap getBitmapFromURL(String urlString) {
        Bitmap bitmap = null;
        InputStream InputStream = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(InputStream);

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            HLog.ex("123", e);
        } finally {
            if (InputStream != null) {
                try {
                    InputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bitmap;
    }
}
