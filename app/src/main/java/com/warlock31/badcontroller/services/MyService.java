package com.warlock31.badcontroller.services;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.warlock31.badcontroller.MyApplication;
import com.warlock31.badcontroller.network.UrlEndpoints;
import com.warlock31.badcontroller.network.VolleySingleton;
import com.warlock31.badcontroller.pojo.WordPressPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.warlock31.badcontroller.network.Keys.EndPointXBox.KEY_EXCERPT;
import static com.warlock31.badcontroller.network.Keys.EndPointXBox.KEY_ID;
import static com.warlock31.badcontroller.network.Keys.EndPointXBox.KEY_TITLE;

/**
 * Created by Warlock on 5/4/2016.
 */

public class MyService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i("Warlock","Job Started");
        new MyTask(this).execute();
//        mJobHandler.sendMessage(Message.obtain(mJobHandler, 1, params));
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
//        mJobHandler.removeMessages(1);
        Log.i("Warlock","Job Started");
        jobFinished(params, false);
        return false;
    }


    private static class MyTask extends AsyncTask<JobParameters, Void, JobParameters> {
        private VolleySingleton volleySingleton;
        private ImageLoader imageLoader;
        private RequestQueue requestQueue;


        MyService myService;

        MyTask(MyService myService) {
            this.myService = myService;
            volleySingleton = VolleySingleton.getInstance();
            requestQueue = volleySingleton.getRequestQueue();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JobParameters doInBackground(JobParameters... jobParameterses) {
            JSONObject response = sendJsonRequest();
            try {
                ArrayList<WordPressPost> wordPressPostArrayList = parseJsonObject(response);
                Log.i("Warlock","doInBackground");
                MyApplication.getWritableDatabase().insertWordPressPosts(wordPressPostArrayList,false);
                Log.i("Warlock","getWritableDatabase");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jobParameterses[0];
        }

        @Override
        protected void onPostExecute(JobParameters jobParameters) {
            super.onPostExecute(jobParameters);
            myService.jobFinished(jobParameters, false);
        }

        private JSONObject sendJsonRequest() {

            JSONObject response = null;

            RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();
//        Log.i("Warlock","Entered sendJsonRequest");
            URL url;
            String newUrl = null;
            try {
                url = new URL(UrlEndpoints.URL_XBOX);
                newUrl = url.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, newUrl, requestFuture, requestFuture);


            jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });

            requestQueue.add(jsonObjectRequest);
            try {
                response = requestFuture.get(30000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }

            return response;
        }


        private ArrayList<WordPressPost> parseJsonObject(JSONObject response) throws JSONException {
            //Log.i("Warlock","Entered parseJsonArray");

            ArrayList<WordPressPost> wordPressPostArrayList = new ArrayList<>();

            if (response == null || response.length() == 0) {
                return null;
            }

            String id = "0";
            String title = "NA";
            String excerpt = "NA";

            JSONArray jsonArray = response.getJSONArray("posts");

            for (int i = 0; i < jsonArray.length(); i++) {


                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id = jsonObject.getString(KEY_ID);
//            String title = jsonObject.getString(KEY_TITLE);

                JSONObject jsonImage = jsonObject.getJSONObject("thumbnail_images");
                JSONObject jsonImage1 = jsonImage.getJSONObject("medium_large");


                title = jsonObject.getString(KEY_TITLE);
//            String content = jsonObject.getString(KEY_CONTENT);
                excerpt = jsonObject.getString(KEY_EXCERPT);
                //Log.i("Warlock",title+excerpt);
                //String featuredMediaID = jsonImage.getString(KEY_FEATURED_MEDIA);
                WordPressPost newPost = new WordPressPost();
                newPost.setFeaturedMedia(jsonImage1.getString("url"));
                newPost.setId(id);
                newPost.setTitle(title);
//            newPost.setContent(content);
                newPost.setExcerpt(excerpt);

//

//                listPosts.add(newPost);

                wordPressPostArrayList.add(newPost);

            }
            //Log.i("Warlock",listPosts.toString());


            return wordPressPostArrayList;

        }
    }
}
