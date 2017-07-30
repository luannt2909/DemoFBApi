package com.luannt.lap10515.demosimpleapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.luannt.lap10515.demosimpleapp.R;
import com.luannt.lap10515.demosimpleapp.data.entity.Friend;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lap10515 on 18/07/2017.
 */

public class FriendListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int LIST_VIEWTYPE = 1;
    private static final int PROGRESS_VIEWTYPE = 0;

    private Context mContext;
    private List<Friend> mFriendList;
    LayoutInflater mInflater;

    public FriendListAdapter(Context context, List<Friend> friendList) {
        this.mContext = context;
        this.mFriendList = friendList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == LIST_VIEWTYPE){
            View view = mInflater.inflate(R.layout.item_friend_list, parent, false);
            return new FriendViewHolder(view);
        }else{
            View view = mInflater.inflate(R.layout.item_progress,parent, false);
            return new ProgressViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == LIST_VIEWTYPE){
            FriendViewHolder friendHolder =(FriendViewHolder)holder;
            Friend data = mFriendList.get(position);

            friendHolder.txtFriendName.setText(data.getName());
            Animation anim= AnimationUtils.loadAnimation(mContext,android.R.anim.fade_in);
            friendHolder.imgAvatar.setAnimation(anim);
            Uri uri = Uri.parse(data.getPicture().getAvatar().getUrl());
            friendHolder.imgAvatar.setImageURI(uri);
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            boolean inMemoryCache = imagePipeline.isInBitmapMemoryCache(uri);
            if(inMemoryCache){
                Log.d("URI IN MEMORY CACHE","YESSS");
            }

            /*Glide.with(mContext)
                    .load(data.getPicture().getAvatar().getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(friendHolder.imgAvatar);*/
        }
    }


    @Override
    public int getItemCount() {
        return mFriendList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mFriendList.get(position) != null){
            return LIST_VIEWTYPE;
        }else {
            return PROGRESS_VIEWTYPE;
        }
    }

    public void setmFriendList(List<Friend> list){
        mFriendList = list;
        notifyDataSetChanged();
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder {
        //@BindView(R.id.avatar)
        //ImageView imgAvatar;

        @BindView(R.id.circle)
        SimpleDraweeView imgAvatar;

        @BindView(R.id.txt_friend_name)
        TextView txtFriendName;

        public FriendViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.pb_itemloading)
        ProgressBar pbLoading;
        public ProgressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
