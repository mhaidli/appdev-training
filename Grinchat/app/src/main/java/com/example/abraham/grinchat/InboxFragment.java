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

                    /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            getListView().getContext(),
                            android.R.layout.simple_list_item_1,
                            parseUsernames);*/
                    MessageAdapter adapter = new MessageAdapter(
                        getListView().getContext(),
                        mListOfMessages);
                    setListAdapter(adapter);
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

        //Toast.makeText(getActivity(), "Debugging 1", Toast.LENGTH_LONG).show();
/*        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Debugging");
        builder.setTitle("Debugging");
        builder.setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();*/


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
            /*AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            builder1.setMessage("Debugging Video");
            builder1.setTitle("Debugging Video");
            builder1.setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog1 = builder1.create();
            dialog1.show();*/
        }
    }
}