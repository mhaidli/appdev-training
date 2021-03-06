package com.example.abraham.grinchat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abraham on 1/12/2015.
 */
public class InboxFragment extends ListFragment {


    public static final String TAG = InboxFragment.class.getSimpleName();

    protected Uri mMediaUri;
    protected List<ParseObject> mListOfMessages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setProgressBarIndeterminateVisibility(true);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_MESSAGES);
        query.whereEqualTo(ParseConstants.KEY_RECIPIENT_IDS, ParseUser.getCurrentUser().getObjectId());
        query.orderByDescending(ParseConstants.KEY_CREATED_AT);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> messages, ParseException e) {
                getActivity().setProgressBarIndeterminateVisibility(false);

                if (e == null){
                    //Success!
                    mListOfMessages = messages;
                    String[] parseUsernames = new String[mListOfMessages.size()];

                    int i = 0;
                    for (ParseObject message : mListOfMessages) {
                        parseUsernames[i] = message.getString(ParseConstants.KEY_SENDER_NAME);
                        i++;
                    }

                    if(getListView().getAdapter() == null) {
                        MessageAdapter adapter = new MessageAdapter(
                                getListView().getContext(),
                                mListOfMessages);
                        setListAdapter(adapter);
                    }
                    else{
                        ((MessageAdapter)getListView().getAdapter()).refill(mListOfMessages);
                    }
                }
                else {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ParseObject message = mListOfMessages.get(position);
        String messageType = message.getString(ParseConstants.KEY_FILE_TYPE);
        ParseFile file = message.getParseFile(ParseConstants.KEY_FILE);
        Uri fileUri = Uri.parse(file.getUrl());
        Context thisContext = getActivity();
        //Toast.makeText(getActivity(), "Debugging 2", Toast.LENGTH_LONG).show();
        //View image
        if(messageType.equals(ParseConstants.TYPE_IMAGE)){
            Intent intent1 = new Intent(thisContext, ViewImageActivity.class);
            intent1.setData(fileUri);
            startActivity(intent1);
        }
        //else if(messageType.equals(ParseConstants.TYPE_VIDEO)
        else{ // Start Video
            Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
            intent.setDataAndType(fileUri, "video/*");
            startActivity(intent);
        }

        List<String> ids = message.getList(ParseConstants.KEY_RECIPIENT_IDS);

        if(ids.size() == 1){
            message.deleteInBackground();
        }
        else{
            ids.remove(ParseUser.getCurrentUser().getObjectId());

            ArrayList<String> idsToRemove = new ArrayList<String>();
            idsToRemove.add(ParseUser.getCurrentUser().getObjectId());

            message.removeAll(ParseConstants.KEY_RECIPIENT_IDS, idsToRemove);
            message.saveInBackground();
        }
    }
}