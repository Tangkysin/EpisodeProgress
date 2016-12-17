package org.tangkysin.episodeprogress;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tang on 2016/10/7.
 */

public class EpisodeAdapter extends BaseAdapter {

    private static final String episodeFileName = "";

    private ArrayList<Episode> episodeArrayList;
    private LayoutInflater localinflater;

    protected ArrayList<Episode> refreshEpisodeArrayList() {
        episodeArrayList = Tools.readEpisodeArrayList();
        episodeArrayList = sortEpisodeByProgress(episodeArrayList, true);
        return episodeArrayList;
    }

    protected void saveEpisodeArrayList() {
        Tools.saveEpisodeArrayList(episodeArrayList);
    }

    protected void addOrReplaceEpisode(Episode episode) {
        int tar_poistion = -1;
        for(int i = 0; i<episodeArrayList.size();i++){
            if(episodeArrayList.get(i).getId() == episode.getId()){
                tar_poistion = i;
                break;
            }
        }

        if(tar_poistion >= 0){
            episodeArrayList.set(tar_poistion, episode);
        }else{
            episodeArrayList.add(episode);
        }

        saveEpisodeArrayList();
    }

    protected void removeEpisode(Episode episode){
        int tar_poistion = -1;
        for(int i = 0; i<episodeArrayList.size();i++){
            if(episodeArrayList.get(i).getId() == episode.getId()){
                tar_poistion = i;
                break;
            }
        }

        if(tar_poistion >= 0){
            episodeArrayList.remove(tar_poistion);
        }
        saveEpisodeArrayList();
    }

    protected ArrayList<Episode> sortEpisodeByProgress(ArrayList<Episode> episodeArrayList, boolean watchingPrior){
        ArrayList<Episode> watching = new ArrayList<Episode>();
        ArrayList<Episode> resting = new ArrayList<Episode>();
        ArrayList<Episode> done = new ArrayList<Episode>();
        ArrayList<Episode> unknow = new ArrayList<Episode>();

        ArrayList<Episode> retVal = new ArrayList<Episode>();

        for (Episode episode:episodeArrayList){
            if(episode.getProgress() == Episode.EpisodeProgress.Watching){
                watching.add(episode);
            }else if (episode.getProgress() == Episode.EpisodeProgress.Resting){
                resting.add(episode);
            }else if (episode.getProgress() == Episode.EpisodeProgress.Done){
                done.add(episode);
            }else{
                unknow.add(episode);
            }
        }

        if (watchingPrior){
            retVal.addAll(watching);
            retVal.addAll(resting);
        }else{
            retVal.addAll(resting);
            retVal.addAll(watching);
        }
        retVal.addAll(unknow);
        retVal.addAll(done);
        return retVal;
    }

    protected Episode getEpisode(int position){
        return episodeArrayList.get(position);
    }

    private static EpisodeAdapter mEpisodeAdapter = null;

    public static EpisodeAdapter sharedEpisodeAdapter() {
        if (mEpisodeAdapter == null) {
            mEpisodeAdapter = new EpisodeAdapter();
        }
        return mEpisodeAdapter;
    }

    public EpisodeAdapter() {
        this.episodeArrayList = new ArrayList<>();
        this.refreshEpisodeArrayList();
        this.localinflater = (LayoutInflater) MainActivity.getInstance().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.episodeArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.episodeArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.episodeArrayList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = localinflater.inflate(R.layout.episode_item, null);
        }

        if(position % 2 == 0){
            convertView.setBackgroundColor(Color.GRAY);
        }else{
            convertView.setBackgroundColor(Color.DKGRAY);
        }

        TextView nameTv = (TextView) convertView.findViewById(R.id.episode_name);
        TextView seasonEpisodeTv = (TextView) convertView.findViewById(R.id.episode_season_episode);
        TextView kindTv = (TextView) convertView.findViewById(R.id.episode_kind);
        TextView statusTv = (TextView) convertView.findViewById(R.id.episode_status);
        TextView progressTv = (TextView) convertView.findViewById(R.id.episode_progress);
//        ToggleButton istopTb = (ToggleButton) convertView.findViewById(R.id.episode_istop);

        nameTv.setText(this.episodeArrayList.get(position).getName());
        seasonEpisodeTv.setText("看完了\n第" + this.episodeArrayList.get(position).getSeason() + "季第" + this.episodeArrayList.get(position).getEpisode() + "集");

        String kindStr = Episode.episodeKindSwitcher(this.episodeArrayList.get(position).getKind());
        kindTv.setText(kindStr);

        String statusStr = Episode.episodeStatusSwitcher(this.episodeArrayList.get(position).getStatus());
        statusTv.setText(statusStr);

        String progressStr = Episode.episodeProgressSwitcher(this.episodeArrayList.get(position).getProgress());
        progressTv.setText(progressStr);

//        istopTb.setChecked(this.episodeArrayList.get(position).isTop());
        return convertView;
    }

}
