package com.mrs.rainbow7music;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by bobby on 17/12/15.
 */
public class SongAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private static final String TAG = "songadapter";
    LayoutInflater layoutInflater;

    private List<Song> songData =Collections.emptyList();
    public SongAdapter(Context context,List<Song> songData) {
        Log.i(TAG, "SongAdapter: " + "constructir is caleed");

        layoutInflater=LayoutInflater.from(context);

        this.songData=songData;
    }

    @Override
    public  CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: first line of oncreateviewholder");
        View view=layoutInflater.inflate(R.layout.custom_song_row, parent, false);
        Log.i(TAG, "onCreateViewHolder: " + "oncreateviewholderisrunning");
        CustomViewHolder customViewHolder=new CustomViewHolder(view);
        return customViewHolder;

    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        Song current=songData.get(position);
        holder.songImage.setImageBitmap(current.getMsongimageView());
        holder.songArtist.setText(current.getmSongArtist());
        holder.songTitle.setText(current.getMsongTitle());


    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: "+ songData.size());
        return songData.size();
    }


}
class CustomViewHolder extends RecyclerView.ViewHolder {

    ImageView songImage;
    TextView songTitle;
    TextView songArtist;
    ImageView more_vertical;
private final String TAG="customviewholder";
    public CustomViewHolder(View itemView) {

        super(itemView);
        Log.i(TAG, "CustomViewHolder: ");
        songImage= (ImageView) itemView.findViewById(R.id.artist_image);
        songTitle= (TextView) itemView.findViewById(R.id.song_title);
        songArtist= (TextView) itemView.findViewById(R.id.song_artist);
        more_vertical= (ImageView) itemView.findViewById(R.id.more_vert);
        songImage.setSelected(true);
        songTitle.setSelected(true);
        songArtist.setSelected(true);


    }


}
