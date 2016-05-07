package com.warlock31.badcontroller.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Warlock on 5/2/2016.
 */
public class WordPressPost implements Parcelable {
    private String id;
    private String title;
    private String publishedDate;
    private String content;
    private String shortDescription;
    private String featuredMedia;
    private String excerpt;

    public WordPressPost() {
    }

    public WordPressPost(Parcel input){
        id=input.readString();
        title = input.readString();
        publishedDate = input.readString();
        shortDescription = input.readString();
        featuredMedia = input.readString();
    }

    public WordPressPost(String id, String title, String publishedDate, String content, String shortDescription, String mainImageURL, String excrept) {
        this.id = id;
        this.title = title;
        this.publishedDate = publishedDate;
        this.content = content;
        this.shortDescription = shortDescription;
        this.featuredMedia = featuredMedia;
        this.excerpt = excrept;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getContent() {
        return content;
    }

    public String getShortDescription() {
        return shortDescription;
    }



    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
    public String getFeaturedMedia() {
        return featuredMedia;
    }

    public void setFeaturedMedia(String featuredMedia) {
        this.featuredMedia = featuredMedia;
    }


    @Override
    public String toString() {
        return "WordPressPost{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publishedDate=" + publishedDate +
                ", content='" + content + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", featuredMedia='" + featuredMedia + '\'' +
                '}';
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(publishedDate);
        parcel.writeString(shortDescription);
        parcel.writeString(featuredMedia);
    }

    public static final Parcelable.Creator<WordPressPost> CREATOR
            = new Parcelable.Creator<WordPressPost>() {
        public WordPressPost createFromParcel(Parcel in) {
            return new WordPressPost(in);
        }

        public WordPressPost[] newArray(int size) {
            return new WordPressPost[size];
        }
    };

}




