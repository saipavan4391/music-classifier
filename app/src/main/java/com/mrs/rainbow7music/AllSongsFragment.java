package com.mrs.rainbow7music;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllSongsFragment extends Fragment {

    private static final String TAG ="Allsongsfragment" ;
    private RecyclerView mRecyclerView;
    private  SongAdapter songAdapter;

    public AllSongsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRecyclerView= (RecyclerView) inflater.inflate(R.layout.fragment_all_songs,container,false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.i(TAG, "onCreateView: " + "mRecyclerView execuyes");
        songAdapter=new SongAdapter(getActivity(),getAllSongsList(getActivity()));;
        mRecyclerView.setAdapter(songAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity(),getAllSongsList(getActivity()).get(position).getMsongTitle() + "is selected",Toast.LENGTH_SHORT).show();
            //    Log.i(TAG, "onClick: " + getAllSongsList(getActivity()).get(position).getMsongTitle() + "is selected") ;
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity(),getAllSongsList(getActivity()).get(position).getMsongTitle() + "is long pressed",Toast.LENGTH_SHORT).show();

             //   Log.i(TAG, "onLongClick: "+ getAllSongsList(getActivity()).get(position).getMsongTitle() + "");

            }

            @Override
            public void onFling(View view, int position) {

            }
        }));
        return  mRecyclerView;


    }
    //list of songs
    public static ArrayList<Song> getAllSongsList(Context context) {
        ArrayList<Song> songs=new ArrayList<Song>();
        Uri musicUri= android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri malbumart=Uri.parse("content://media/external/audio/albumart");
        ContentResolver contentResolver=context.getContentResolver();
        Cursor music_cursor= contentResolver.query(musicUri, null, MediaStore.Audio.Media.IS_MUSIC, null, null);
        if(music_cursor==null){
            //QUERY FAILED
            Log.e("Getsonglist", "QUERY FAILED!!");
        }
        if(!music_cursor.moveToFirst()){
            Toast.makeText(context, "No music to retreive", Toast.LENGTH_SHORT).show();
        }
        int columnID=music_cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
        int columnTitle=music_cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
        int columnData=music_cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        int columnArtist=music_cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);


        if (music_cursor.moveToFirst()) {
            do {
                long songID = music_cursor.getLong(columnID);
                String songTitle = music_cursor.getString(columnTitle);
                String songData = music_cursor.getString(columnData);
                String songArtist=music_cursor.getString(columnArtist);
                Bitmap songImage= decodeBitmapFromSong(context,songData,150,150);
                Log.i(TAG, "getAllSongsList: "+ songTitle +songData + songArtist);
                songs.add(new Song(songID,songTitle,songData,songArtist,songImage));

            } while (music_cursor.moveToNext());
        }
        return songs;
    }
    public static Bitmap decodeBitmapFromSong(Context context,String path, int reqWidth, int reqHeight) {
        // TODO Auto-generated method stub
        Bitmap imageBitmap = null;
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // retreive album art from song
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_menu_gallery);
        imageBitmap=drawableToBitmap(drawable);
        imageBitmap=addWhiteBorder(imageBitmap,5);
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();

        try {
            mmr.setDataSource(path);
            byte[] rawData = mmr.getEmbeddedPicture();
            if (rawData != null) {
                BitmapFactory.decodeByteArray(rawData, 0, rawData.length,
                        options);
            }
            options.inJustDecodeBounds = false;
            if (rawData != null) {
                imageBitmap = BitmapFactory.decodeByteArray(rawData, 0,
                        rawData.length, options);
                imageBitmap = Bitmap.createScaledBitmap(imageBitmap, reqWidth,
                        reqHeight, true);
                imageBitmap= addWhiteBorder(imageBitmap,5);
            }


        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            Log.d("tag", "Song doesnot contain an image");
        }
		/*
		 * // Calculate inSampleSize options.inSampleSize =
		 * calculateInSampleSize(options, reqWidth, reqHeight);
		 */

        // Decode bitmap with inSampleSize set

        return imageBitmap;

    }



    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    private static Bitmap addWhiteBorder(Bitmap bmp, int borderSize) {
        Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + borderSize * 2, bmp.getHeight() + borderSize * 2, bmp.getConfig());
        Canvas canvas = new Canvas(bmpWithBorder);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bmp, borderSize, borderSize, null);
        return bmpWithBorder;
    }

   static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

       private GestureDetector gestureDetector;
       private ClickListener clickListener;

       public RecyclerTouchListener(Context context,final RecyclerView rv, final ClickListener clickListener) {
           this.clickListener=clickListener;
           gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
               @Override
               public boolean onSingleTapUp(MotionEvent e) {
                   return true;
               }

               @Override
               public void onLongPress(MotionEvent e) {
                   super.onLongPress(e);
                 View child=  rv.findChildViewUnder(e.getX(),e.getY());
                   if(child!=null && clickListener!=null){
                       clickListener.onLongClick(child, rv.getChildAdapterPosition(child));
                   }
               }

               @Override
               public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                   return super.onFling(e1, e2, velocityX, velocityY);
               }
           });
       }

       @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

         View child=  rv.findChildViewUnder(e.getX(),e.getY());
           if(child!=null && clickListener!=null && gestureDetector.onTouchEvent(e)){

               clickListener.onClick(child,rv.getChildAdapterPosition(child));
           }
          // Log.i(TAG, "onInterceptTouchEvent: " + gestureDetector.onTouchEvent(e) + e + "");
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
    public static  interface ClickListener{

        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
        public void onFling(View view,int position);
    }

}


