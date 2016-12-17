package org.tangkysin.episodeprogress;

import android.content.res.Resources;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by Tang on 2016/10/7.
 */

public class Episode implements Serializable{
    public enum EpisodeKind {
        Episode, Comics, Animation, Unknow
    }

    public enum EpisodeStatus {
        Fnish, Unfinished, Unknow
    }

    public enum EpisodeProgress {
        Watching, Resting, Done, Unknow
    }

    private String name;
    private EpisodeKind kind;
    private int season;
    private int episode;
    private EpisodeStatus status;
    private EpisodeProgress progress;
    private boolean isTop;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EpisodeKind getKind() {
        return kind;
    }

    public void setKind(EpisodeKind kind) {
        this.kind = kind;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public EpisodeStatus getStatus() {
        return status;
    }

    public void setStatus(EpisodeStatus status) {
        this.status = status;
    }

    public int getEpisode() {

        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public EpisodeProgress getProgress() {
        return progress;
    }

    public void setProgress(EpisodeProgress progress) {
        this.progress = progress;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    public String toJsonStr(){
        return new Gson().toJson(this);
    }

    public static EpisodeKind episodeKindSwitcher(String kind) {
        Resources res = MainActivity.getInstance().getResources();
        String[] kindArr = res.getStringArray(R.array.episode_kind);
        EpisodeKind retVal = EpisodeKind.Unknow;
        if (kind.trim().equals(kindArr[1])) {
            retVal = EpisodeKind.Episode;
        } else if (kind.trim().equals(kindArr[2])) {
            retVal = EpisodeKind.Comics;
        } else if (kind.trim().equals(kindArr[3])) {
            retVal = EpisodeKind.Animation;
        } else if (kind.trim().equals(kindArr[0])) {
            retVal = EpisodeKind.Unknow;
        }

        return retVal;
    }

    public static String episodeKindSwitcher(EpisodeKind kind) {
        Resources res = MainActivity.getInstance().getResources();
        String[] kindArr = res.getStringArray(R.array.episode_kind);
        String retVal = kindArr[0];
        if(kind == EpisodeKind.Episode) {
            retVal = kindArr[1];
        }if(kind == EpisodeKind.Comics) {
            retVal = kindArr[2];
        }if(kind == EpisodeKind.Animation) {
            retVal = kindArr[3];
        }if(kind == EpisodeKind.Unknow) {
            retVal = kindArr[0];
        }
        return retVal;
    }

    public static EpisodeStatus episodeStatusSwitcher(String status) {
        Resources res = MainActivity.getInstance().getResources();
        String[] statusArr = res.getStringArray(R.array.episode_status);
        EpisodeStatus retVal = EpisodeStatus.Unknow;
        if (status.trim().equals(statusArr[1])) {
            retVal = EpisodeStatus.Fnish;
        } else if (status.trim().equals(statusArr[2])) {
            retVal = EpisodeStatus.Unfinished;
        } else if (status.trim().equals(statusArr[0])) {
            retVal = EpisodeStatus.Unknow;
        }

        return retVal;
    }

    public static String episodeStatusSwitcher(EpisodeStatus status) {
        Resources res = MainActivity.getInstance().getResources();
        String[] statusArr = res.getStringArray(R.array.episode_status);
        String retVal = statusArr[0];
        if (status == EpisodeStatus.Fnish) {
            retVal = statusArr[1];
        }else if (status == EpisodeStatus.Unfinished) {
            retVal = statusArr[2];
        }else if (status == EpisodeStatus.Unknow) {
            retVal = statusArr[0];
        }
        return retVal;
    }

    public static EpisodeProgress episodeProgressSwitcher(String progress) {
        Resources res = MainActivity.getInstance().getResources();
        String[] progressArr = res.getStringArray(R.array.episode_progress);
        EpisodeProgress retVal = EpisodeProgress.Unknow;
        if (progress.trim().equals(progressArr[1])) {
            retVal = EpisodeProgress.Watching;
        } else if (progress.trim().equals(progressArr[2])) {
            retVal = EpisodeProgress.Resting;
        } else if (progress.trim().equals(progressArr[3])) {
            retVal = EpisodeProgress.Done;
        } else if (progress.trim().equals(progressArr[0])) {
            retVal = EpisodeProgress.Unknow;
        }
        return retVal;
    }

    public static String episodeProgressSwitcher(EpisodeProgress progress) {
        Resources res = MainActivity.getInstance().getResources();
        String[] progressArr = res.getStringArray(R.array.episode_progress);
        String retVal = progressArr[0];
        if (progress==EpisodeProgress.Watching) {
            retVal = progressArr[1];
        }else if (progress==EpisodeProgress.Resting) {
            retVal = progressArr[2];
        }else if (progress==EpisodeProgress.Done) {
            retVal = progressArr[3];
        }else if (progress==EpisodeProgress.Unknow) {
            retVal = progressArr[0];
        }
        return retVal;
    }

}
