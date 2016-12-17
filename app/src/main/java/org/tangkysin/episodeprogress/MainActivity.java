package org.tangkysin.episodeprogress;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener{

    public static final String START_EDIT_POS_KEY = "position";

    private ListView episodeList;
    private static MainActivity instance;

    public static MainActivity getInstance(){
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title_layout);

        TextView titleTv = (TextView) this.findViewById(R.id.title_title);
        titleTv.setClickable(true);
        titleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(instance, EpisodeDetailActivity.class);
                intent.putExtra(START_EDIT_POS_KEY, -1);
                instance.startActivity(intent);
            }
        });

        episodeList = (ListView) this.findViewById(R.id.episodelist);
        episodeList.setAdapter(EpisodeAdapter.sharedEpisodeAdapter());
        episodeList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, EpisodeDetailActivity.class);
        intent.putExtra(START_EDIT_POS_KEY, position);
        this.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EpisodeAdapter.sharedEpisodeAdapter().notifyDataSetChanged();
    }
}
