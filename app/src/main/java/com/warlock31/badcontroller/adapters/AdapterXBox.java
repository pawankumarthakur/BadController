package com.warlock31.badcontroller.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.warlock31.badcontroller.R;
import com.warlock31.badcontroller.network.VolleySingleton;
import com.warlock31.badcontroller.pojo.WordPressPost;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Warlock on 5/4/2016.
 */
public class AdapterXBox extends RecyclerView.Adapter<AdapterXBox.ViewHolderXbox> {

    private ArrayList<WordPressPost> listPost = new ArrayList<>();

    private LayoutInflater layoutInflater;

    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;

    public AdapterXBox(Context context) {
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    public void setListPost(ArrayList<WordPressPost> listPost){
        this.listPost = listPost;
        notifyItemRangeChanged(0,listPost.size());

    }

    @Override
    public ViewHolderXbox onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.single_wordpress_post, parent, false);
        ViewHolderXbox holderXbox = new ViewHolderXbox(view);
        return holderXbox;
    }

    @Override
    public void onBindViewHolder(final ViewHolderXbox holder, int position) {
        WordPressPost currentPost = listPost.get(position);
        Spanned spanned = Html.fromHtml(currentPost.getExcerpt());
        holder.postTitle.setText(currentPost.getTitle());
        holder.postExcrept.setText(spanned);
        String postImageUrl = currentPost.getFeaturedMedia();
        if (postImageUrl != null){
            imageLoader.get(postImageUrl,new ImageLoader.ImageListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                //holder.postImage.setImageDrawable(R.drawable.cover_pic);
                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
//                    Log.i("WarImage",response.toString());
                holder.postImage.setImageBitmap(response.getBitmap());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return listPost.size();
    }

    static class ViewHolderXbox extends RecyclerView.ViewHolder {

        private ImageView postImage;
        private TextView postTitle;
        private TextView postExcrept;

        public ViewHolderXbox(View itemView) {
            super(itemView);

            postImage = (ImageView) itemView.findViewById(R.id.postImage);
            postTitle = (TextView) itemView.findViewById(R.id.postTitle);
            postExcrept = (TextView) itemView.findViewById(R.id.postExcrept);
        }
    }


}
