package edu.grinnell.appdev.pradhanp.grinstagram;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prabir on 5/4/15.
 */
public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.MyViewHolder> {
    protected Context mContext;
    protected List<ParseObject> mLinks;


    public LinkAdapter(Context context, List<ParseObject> links) {
        mContext = context;
        mLinks = links;
    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ParseObject link = mLinks.get(position);

        holder.nameLabel.setText(link.getString(ParseConstants.NAME));
        if (link.getBoolean(ParseConstants.LIKE))
            holder.like.setImageDrawable(mContext.getDrawable(R.drawable.heart_red));
        else holder.like.setImageDrawable(mContext.getDrawable(R.drawable.heart_white));
        Picasso.with(mContext).load(link.getString(ParseConstants.LINK)).into(holder.mainImage);

        // set onclick listener for like button
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject updated = link;
                updated.put(ParseConstants.LIKE, !updated.getBoolean(ParseConstants.LIKE));
                updated.saveInBackground();

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new MyViewHolder(convertView);
    }
/*
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.card_view, null);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject link = mLinks.get(position);

        holder.nameLabel.setText(link.getString(ParseConstants.NAME));
        if (link.getBoolean(ParseConstants.LIKE))
            holder.like.setImageDrawable(mContext.getDrawable(R.drawable.heart_red));
        else holder.like.setImageDrawable(mContext.getDrawable(R.drawable.heart_white));
        Picasso.with(mContext).load(link.getString(ParseConstants.LINK)).into(holder.mainImage);

        return convertView;
    }
    */

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView userIcon;
        TextView nameLabel;
        ImageView mainImage;
        ImageView like;

        public MyViewHolder(View v) {
            super(v);
            userIcon = (ImageView) v.findViewById(R.id.userIcon);
            mainImage = (ImageView) v.findViewById(R.id.mainImage);
            like = (ImageView) v.findViewById(R.id.heart);
            nameLabel = (TextView) v.findViewById(R.id.name);
        }
    }

    public void refill(List<ParseObject> links) {
        mLinks.clear();
        mLinks.addAll(links);
        notifyDataSetChanged();
    }
}
