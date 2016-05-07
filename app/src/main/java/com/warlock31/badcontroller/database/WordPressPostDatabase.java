package com.warlock31.badcontroller.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.warlock31.badcontroller.pojo.WordPressPost;

import java.util.ArrayList;

/**
 * Created by Warlock on 5/6/2016.
 */

public class WordPressPostDatabase {

    private WordPressPostHelper wordPressPostHelper;
    private SQLiteDatabase database;


    public WordPressPostDatabase(Context context) {
        wordPressPostHelper = new WordPressPostHelper(context);
        database = wordPressPostHelper.getWritableDatabase();
    }


    public void insertWordPressPosts(ArrayList<WordPressPost> listPosts, boolean clearPrevious) {
        if (clearPrevious) {
//            deleteAll();
        }

        String sql = "INSERT INTO " + WordPressPostHelper.TABLE_WORDPRESS_POST + " VALUES (?,?,?,?,?,?);";

        SQLiteStatement statement = database.compileStatement(sql);

        database.beginTransaction();

        for (int i = 0; i < listPosts.size(); i++) {
            WordPressPost currentPost = listPosts.get(i);

            statement.clearBindings();
            //for a given column index, simply bind the data to be put inside that index
            statement.bindString(2, currentPost.getId());
            statement.bindString(3, currentPost.getTitle());
            statement.bindString(4, currentPost.getPublishedDate());
            statement.bindString(5, currentPost.getContent());
            statement.bindString(6, currentPost.getFeaturedMedia());
            statement.bindString(7,currentPost.getExcerpt());

            Log.i("Warlock","Inserting Entries");

            statement.execute();


        }

        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public ArrayList<WordPressPost> readPosts() {

        ArrayList<WordPressPost> listPosts = new ArrayList<>();


        String[] columns = {WordPressPostHelper.COLUMN_UID,
                WordPressPostHelper.COLUMN_POST_ID,
                WordPressPostHelper.COLUMN_TITLE,
                WordPressPostHelper.COLUMN_PUBLISHED_DATE,
                WordPressPostHelper.COLUMN_CONTENT,
                WordPressPostHelper.COLUMN_FEATURED_MEDIA,
                WordPressPostHelper.COLUMN_EXCERPT
        };

        Cursor cursor = database.query(WordPressPostHelper.TABLE_WORDPRESS_POST,columns,null,null,null,null,null);

        if (cursor != null & cursor.moveToFirst()){
            do {
                WordPressPost wordPressPost = new WordPressPost();

                wordPressPost.setTitle(cursor.getString(cursor.getColumnIndex(WordPressPostHelper.COLUMN_TITLE)));
                wordPressPost.setPublishedDate(cursor.getString(cursor.getColumnIndex(WordPressPostHelper.COLUMN_PUBLISHED_DATE)));
                wordPressPost.setContent(cursor.getString(cursor.getColumnIndex(WordPressPostHelper.COLUMN_CONTENT)));
                wordPressPost.setFeaturedMedia(cursor.getString(cursor.getColumnIndex(WordPressPostHelper.COLUMN_FEATURED_MEDIA)));
                wordPressPost.setExcerpt(cursor.getString(cursor.getColumnIndex(WordPressPostHelper.COLUMN_EXCERPT)));

                listPosts.add(wordPressPost);

            }
            while (cursor.moveToNext());
        }

        return listPosts;
    }

    private void deleteAll() {

        database.delete(WordPressPostHelper.TABLE_WORDPRESS_POST,null,null);
    }



    /*
    * WordPressPostHelper class code below
    */

    public class WordPressPostHelper extends SQLiteOpenHelper {

        private Context mcontext;
        private static final String DB_NAME = "wp_post_db";
        private static final int DB_VERSION = 1;
        public static final String TABLE_WORDPRESS_POST = "wordpress_posts";
        public static final String COLUMN_UID = "_id";
        public static final String COLUMN_POST_ID = "post_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PUBLISHED_DATE = "published_date";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_FEATURED_MEDIA = "featured_media";
        public static final String COLUMN_EXCERPT = "excerpt";

        public static final String CREATE_TABLE_WORDPRESS_POST = "CREATE TABLE" + TABLE_WORDPRESS_POST + " (" +
                COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_POST_ID + "TEXT" +
                COLUMN_TITLE + " TEXT," +
                COLUMN_PUBLISHED_DATE + " TEXT" +
                COLUMN_CONTENT + " TEXT" +
                COLUMN_FEATURED_MEDIA + " TEXT" +
                COLUMN_EXCERPT + " TEXT" +
                ");";


        public WordPressPostHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            mcontext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(CREATE_TABLE_WORDPRESS_POST);
                Log.i("Warlock", "CREATE TABLE WORSPRESS POST EXECUTE");
            } catch (SQLiteException exception) {
                exception.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                Log.i("Warlock", "UPGRADE TABLE WORSPRESS POST EXECUTE");
                sqLiteDatabase.execSQL("DROP TABLE " + TABLE_WORDPRESS_POST + " IF EXISTS;");
                onCreate(sqLiteDatabase);
            } catch (SQLiteException exception) {
                exception.printStackTrace();
            }

        }
    }

}
