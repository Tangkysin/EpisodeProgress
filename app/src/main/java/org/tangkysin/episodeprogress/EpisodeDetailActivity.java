package org.tangkysin.episodeprogress;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;

import static org.tangkysin.episodeprogress.MainActivity.START_EDIT_POS_KEY;

public class EpisodeDetailActivity extends Activity implements View.OnClickListener {

    EditText nameEt;
    EditText seasonEt;
    EditText episodeEt;
    Spinner kindSr;
    Spinner statusSr;
    Spinner progressSr;
    Button saveBtn;
    Button deleteBtn;
    Episode srcEpisode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_detail);
        int position = getIntent().getExtras().getInt(START_EDIT_POS_KEY);

        nameEt = (EditText) this.findViewById(R.id.edit_name);
        seasonEt = (EditText) this.findViewById(R.id.edit_season);
        episodeEt = (EditText) this.findViewById(R.id.edit_episode);

        kindSr = (Spinner) this.findViewById(R.id.select_kind);
        statusSr = (Spinner) this.findViewById(R.id.select_status);
        progressSr = (Spinner) this.findViewById(R.id.select_progress);
        saveBtn = (Button) this.findViewById(R.id.edit_save);
        saveBtn.setOnClickListener(this);

        deleteBtn = (Button) this.findViewById(R.id.edit_delete);
        deleteBtn.setOnClickListener(this);

        String edit_name = "";
        int edit_season = 0;
        int edit_episode = 0;
        Episode.EpisodeKind edit_kind = Episode.EpisodeKind.Unknow;
        Episode.EpisodeStatus edit_status = Episode.EpisodeStatus.Unknow;
        Episode.EpisodeProgress edit_progress = Episode.EpisodeProgress.Unknow;

        srcEpisode = null;
        if (position >= 0) {
            srcEpisode = EpisodeAdapter.sharedEpisodeAdapter().getEpisode(position);
            edit_name = srcEpisode.getName();
            edit_season = srcEpisode.getSeason();
            edit_episode = srcEpisode.getEpisode();
            edit_kind = srcEpisode.getKind();
            edit_status = srcEpisode.getStatus();
            edit_progress = srcEpisode.getProgress();
        }


        Resources res = MainActivity.getInstance().getResources();
        String[] kindArr = res.getStringArray(R.array.episode_kind);
        String[] statusArr = res.getStringArray(R.array.episode_status);
        String[] progressArr = res.getStringArray(R.array.episode_progress);

        int kind_position = 0;
        for (int i = 0; i < kindArr.length; i++) {
            if (Episode.episodeKindSwitcher(edit_kind).equals(kindArr[i])) {
                kind_position = i;
                break;
            }
        }

        int status_position = 0;
        for (int i = 0; i < statusArr.length; i++) {
            if (Episode.episodeStatusSwitcher(edit_status).equals(statusArr[i])) {
                status_position = i;
                break;
            }
        }

        int progress_position = 0;
        for (int i = 0; i < progressArr.length; i++) {
            if (Episode.episodeProgressSwitcher(edit_progress).equals(progressArr[i])) {
                progress_position = i;
                break;
            }
        }

        nameEt.setText(edit_name);
        seasonEt.setText(edit_season + "");
        episodeEt.setText(edit_episode + "");
        kindSr.setSelection(kind_position, true);
        statusSr.setSelection(status_position, true);
        progressSr.setSelection(progress_position, true);

    }

    @Override
    public void onClick(View v) {
        Log.e("episode", v.getId() + "");
        Log.e("episode", R.id.edit_save + "");
        Log.e("episode", R.id.edit_delete + "");
        if (v.getId() == R.id.edit_save) {

            if (nameEt.getText().toString().trim().length() == 0) {
                Toast.makeText(this, "名称不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            String edit_name = nameEt.getText().toString().trim();
            int edit_season = 0;
            int edit_episode = 0;

            if (seasonEt.getText().toString().trim().length() > 0) {
                edit_season = Integer.parseInt(seasonEt.getText().toString());
            }

            if (episodeEt.getText().toString().trim().length() > 0) {
                edit_episode = Integer.parseInt(episodeEt.getText().toString());
            }

            Episode.EpisodeKind edit_kind = Episode.episodeKindSwitcher(kindSr.getSelectedItem().toString());
            Episode.EpisodeStatus edit_status = Episode.episodeStatusSwitcher(statusSr.getSelectedItem().toString());
            Episode.EpisodeProgress edit_progress = Episode.episodeProgressSwitcher(progressSr.getSelectedItem().toString());

            if (srcEpisode == null) {
                srcEpisode = new Episode();
                srcEpisode.setId(new Date().getTime());
            }

            srcEpisode.setName(edit_name);
            srcEpisode.setSeason(edit_season);
            srcEpisode.setEpisode(edit_episode);
            srcEpisode.setKind(edit_kind);
            srcEpisode.setStatus(edit_status);
            srcEpisode.setProgress(edit_progress);

            EpisodeAdapter.sharedEpisodeAdapter().addOrReplaceEpisode(srcEpisode);
            this.finish();
        } else if (v.getId() == R.id.edit_delete) {

            if (srcEpisode == null) {
                this.finish();
            } else {
                new AlertDialog.Builder(this).setTitle("确认删除[" + srcEpisode.getName() + "]吗？")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 点击“确认”后的操作
                                        EpisodeAdapter.sharedEpisodeAdapter().removeEpisode(srcEpisode);
                                        finish();
                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 点击“返回”后的操作,这里不设置没有任何操作
                                    }
                                }).show();
            }

        }
    }
}
