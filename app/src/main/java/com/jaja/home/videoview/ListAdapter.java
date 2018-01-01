package com.jaja.home.videoview;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.io.IOException;
import java.util.List;

/**
 * Created by tu on 2017/12/31.
 */
public class ListAdapter extends BaseAdapter implements MediaPlayer.OnPreparedListener {

    private List<VideoModel> list;
    private MediaPlayer mediaPlayer;
    private Context mContext;
    private int playIndex = -1;

    public ListAdapter(List<VideoModel> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.act_texture_item, null);
            holder.videoView = (TextureView) convertView.findViewById(R.id.tureView);
            holder.videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    } else {
                        mediaPlayer.start();
                    }
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (holder.videoView.getTag() != null) {
            if ((int) holder.videoView.getTag() != position && (int) holder.videoView.getTag() == playIndex) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
                playIndex = -1;
            }
        }
        final String uri = list.get(position).getUri();
        holder.videoView.setTag(position);
        if (position == playIndex) {
            try {
                SurfaceTexture texture = holder.videoView.getSurfaceTexture();
                if (texture != null) {
                    mediaPlayer.setSurface(new Surface(texture));
                    mediaPlayer.setDataSource(mContext, Uri.parse(uri));
                    mediaPlayer.prepareAsync();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playIndex = position;
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
    }

    class ViewHolder {
        TextureView videoView;
    }
}
