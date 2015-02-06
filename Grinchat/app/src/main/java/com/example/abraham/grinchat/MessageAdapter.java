package com.example.abraham.grinchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Abraham on 1/18/2015.
 */
public class MessageAdapter extends ArrayAdapter<ParseObject> {

    protected Context mContext;
    protected List<ParseObject> mMessages;

    public MessageAdapter(Context context, List<ParseObject> messages){
        super(context, R.layout.message_layout, messages);

        mContext = context;
        mMessages = messages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_layout, null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.messageIcon);
            holder.nameLabel = (TextView) convertView.findViewById(R.id.senderLabel);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        ParseObject message = mMessages.get(position);
        String imageType = message.getString(ParseConstants.KEY_FILE_TYPE);
        //String imageType;
        if(imageType.equals(ParseConstants.TYPE_VIDEO)){
            holder.iconImageView.setImageResource(R.drawable.ic_action_play);
        }
        else if(imageType.equals(ParseConstants.TYPE_IMAGE)){
            //error!
            holder.iconImageView.setImageResource(R.drawable.ic_action_picture);
        }

        //holder.iconImageView.setImageResource(returnIcon(imageType));
        holder.nameLabel.setText(message.getString(ParseConstants.KEY_SENDER_NAME));

        return convertView;
    }

    private static class ViewHolder{
       ImageView iconImageView;
        TextView nameLabel;
    }



    private int returnIcon(String imageType){

        if(imageType.equals(ParseConstants.TYPE_IMAGE)){
            return R.drawable.ic_action_picture;
        }
        else {
            return R.drawable.ic_action_play;
        }
    }


    public void refill(List<ParseObject> messages){
        mMessages.clear();
        mMessages.addAll(messages);
        notifyDataSetChanged();
    }
}

