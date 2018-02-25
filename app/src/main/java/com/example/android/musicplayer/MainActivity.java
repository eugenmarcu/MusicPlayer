package com.example.android.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    // Declare Variables
    ListView list;
    SongAdapter adapter;
    SearchView editSearch;
    ArrayList<Song> songList = new ArrayList<Song>();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    adapter.filter("");
                    editSearch.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_top10:
                    adapter.filter("");
                    sortData();
                    editSearch.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_favorites:
                    adapter.filter("");
                    editSearch.setVisibility(View.GONE);
                    selectFavorites();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Generate sample data
        addAllSongs();

        // Locate the ListView in activity_main.xml
        list = (ListView) findViewById(R.id.listview);

        // Pass results to ListViewAdapter Class
        adapter = new SongAdapter(this, 0, songList);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        //Open Now playing activity on selected item
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song currentSong = songList.get(position);
                Intent intent = new Intent(MainActivity.this, NowPlayingActivity.class);
                intent.putExtra("title", currentSong.getTitle());
                intent.putExtra("artist", currentSong.getArtist());
                intent.putExtra("duration", currentSong.getDuration());
                startActivity(intent);
            }
        });

        // Locate the EditText in activity_main.xml
        editSearch = (SearchView) findViewById(R.id.search);
        editSearch.setOnQueryTextListener(this);
        editSearch.clearFocus();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }

    //sorting data on Top10
    private void sortData(){
        List<Song> newList = new ArrayList<>();
        Collections.sort(songList, new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return o1.getRanking()-o2.getRanking();
            }
        });
        for(int i=0; i<10; i++)
            newList.add(songList.get(i));
        songList.clear();
        songList.addAll(newList);
    }

    //select Favorites songs
    private void selectFavorites(){
        List<Song> newList = new ArrayList<>();
        for(int i=0; i<songList.size(); i++){
            Song currentSong = songList.get(i);
            if(currentSong.isFavorite()){
                newList.add(currentSong);
            }
        }
        songList.clear();
        songList.addAll(newList);
    }

    //add dummy data
    private void addAllSongs(){
        // Generate sample data
        songList.clear();
        songList.add(new Song(getString(R.string.song1_title), getString(R.string.song1_artist),  getString(R.string.song1_duration), 3, true));
        songList.add(new Song(getString(R.string.song2_title), getString(R.string.song2_artist),  getString(R.string.song2_duration), 2, true));
        songList.add(new Song(getString(R.string.song3_title), getString(R.string.song3_artist),  getString(R.string.song3_duration), 10, false));
        songList.add(new Song(getString(R.string.song4_title), getString(R.string.song4_artist),  getString(R.string.song4_duration), 7, false));
        songList.add(new Song(getString(R.string.song5_title), getString(R.string.song5_artist),  getString(R.string.song5_duration), 4, false));
        songList.add(new Song(getString(R.string.song6_title), getString(R.string.song6_artist),  getString(R.string.song6_duration), 6, false));
        songList.add(new Song(getString(R.string.song7_title), getString(R.string.song7_artist),  getString(R.string.song7_duration), 5, true));
        songList.add(new Song(getString(R.string.song8_title), getString(R.string.song8_artist),  getString(R.string.song8_duration), 1, true));
        songList.add(new Song(getString(R.string.song9_title), getString(R.string.song9_artist),  getString(R.string.song9_duration), 9, false));
        songList.add(new Song(getString(R.string.song10_title), getString(R.string.song10_artist),  getString(R.string.song10_duration), 8, false));
    }
}
