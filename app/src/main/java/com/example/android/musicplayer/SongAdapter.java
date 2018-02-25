package com.example.android.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Eugen on 22-Feb-18.
 */

public class SongAdapter extends ArrayAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<Song> songList = null;
    private ArrayList<Song> songArrayList;


    public SongAdapter(Context context, int resource, List<Song> songList) {
        super(context, resource, songList);
        mContext = context;
        this.songList = songList;
        inflater = LayoutInflater.from(mContext);
        this.songArrayList = new ArrayList<>();
        this.songArrayList.addAll(songList);
    }

    public class ViewHolder {
        TextView title;
        TextView artist;
        TextView duration;
        ImageView favorites;
        TextView rank;
    }

    @Override
    public int getCount() {
        return songList.size();
    }

    @Override
    public Song getItem(int position) {
        return songList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view_item, null);
            // Locate the TextViews in list_view_item.xml
            holder.title = (TextView) view.findViewById(R.id.list_item_title);
            holder.artist = (TextView) view.findViewById(R.id.list_item_artist);
            holder.duration = view.findViewById(R.id.list_item_duration);
            holder.favorites = view.findViewById(R.id.list_item_favorites);
            holder.rank = view.findViewById(R.id.list_item_rank);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Song currentSong = songList.get(position);
        holder.title.setText(currentSong.getTitle());
        holder.artist.setText(currentSong.getArtist());
        holder.duration.setText(currentSong.getDuration());
        holder.rank.setText(String.valueOf(currentSong.getRanking()));
        if(currentSong.isFavorite())
            holder.favorites.setImageResource(R.drawable.star_filled);
        else
            holder.favorites.setImageResource(R.drawable.star);

        //Change favorite status on star click
        holder.favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Invert favorite status;
                currentSong.setFavorite(!currentSong.isFavorite());
                notifyDataSetChanged();
            }
        });

        return view;
    }

    // Filter text on search
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        songList.clear();
        if (charText.length() == 0) {
            songList.addAll(songArrayList);
        } else {
            for (Song wp : songArrayList) {
                if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText) || wp.getArtist().toLowerCase(Locale.getDefault()).contains(charText)) {
                    songList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
