package com.example.abraham.grinchat;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */

    SectionsPagerAdapter mSectionsPagerAdapter;

    public static final String TAG = MainActivity.class.getSimpleName();

    public static final int TAKE_PHOTO_REQUEST = 0;
    public static final int TAKE_VIDEO_REQUEST = 1;
    public static final int GET_PHOTO_REQUEST = 2;
    public static final int GET_VIDEO_REQUEST = 3;

    public static final int MEDIA_TYPE_IMAGE = 4;
    public static final int MEDIA_TYPE_VIDEO = 5;

    public static final int FILE_SIZE_LIMIT= 1024*1024*10;

    protected Uri mMediaUri;
    protected List<ParseObject> mListOfMessages;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;


    protected DialogInterface.OnClickListener mCameraDialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            /*switch(which){
                case 0: //Take Picture*/
            if (which == 0) {
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                if (mMediaUri == null) {
                    Toast.makeText(MainActivity.this, R.string.error_message_missing_external_storage, Toast.LENGTH_LONG)
                            .show();
                } else {
                    takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                    startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST);
                }
            }
            //   case 1: //Take Video
            else if (which == 1) {
                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);

                if (mMediaUri == null) {
                    Toast.makeText(MainActivity.this, R.string.error_message_missing_external_storage, Toast.LENGTH_LONG)
                            .show();
                } else {
                    takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                    takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
                    takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                    startActivityForResult(takeVideoIntent, TAKE_VIDEO_REQUEST);
                }
            }
            //    case 2: //Get Picture
            else if (which == 2) {
                Intent getPhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getPhotoIntent.setType("image/*");
                startActivityForResult(getPhotoIntent, GET_PHOTO_REQUEST);
            }
            //    case 3: //Get Video
            else if (which == 3) {
                Intent getVideoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getVideoIntent.setType("video/*");
                Toast.makeText(MainActivity.this, R.string.warning_video_size_limit, Toast.LENGTH_LONG).show();
                startActivityForResult(getVideoIntent, GET_VIDEO_REQUEST);
            }
        }
    };
        //}
    //};

    private Uri getOutputMediaFileUri(int mediaType) {

        //Check to see if external storage exists
        if(isExternalStorageAvailable()){
            //Get App Name
            String appName = MainActivity.this.getString(R.string.app_name);
            //1. Get directory of external storage.
            File mediaStorageDirectory = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    appName);

            //2. Create directory
            if(! mediaStorageDirectory.exists()){
                if(! mediaStorageDirectory.mkdirs()){
                    Log.e(TAG, "Problem with external media storage");
                }
            }

            //Create file name and media file
            File mediaFile;
            Date now = new Date();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(now);

            String path = mediaStorageDirectory.getPath() + File.separator;
            if (mediaType == MEDIA_TYPE_IMAGE){
                mediaFile = new File(path + "IMG_" + timestamp + ".jpg");
            }
            else if (mediaType == MEDIA_TYPE_VIDEO){
                mediaFile = new File(path + "VID_" + timestamp + ".mp4");
            }
            else{
                return null;
            }
            return Uri.fromFile(mediaFile);
        }
        else{
            return null;
        }
    }

    private boolean isExternalStorageAvailable(){
        String state = Environment.getExternalStorageState();

        //If there is external media storage, return true.
        if(state.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);

        ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser == null) {
            navigateToLogin();
        }

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        //mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mSectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            if( requestCode == GET_PHOTO_REQUEST || requestCode == GET_VIDEO_REQUEST){
                if(data == null){
                    Toast.makeText(this, R.string.title_generic_error_message, Toast.LENGTH_SHORT).show();
                }
                else{
                    mMediaUri = data.getData();
                }
                if(requestCode == GET_VIDEO_REQUEST){
                    //Make sure file is less than 10MB
                    int fileSize = 0;
                    InputStream inputStream = null;

                    try{
                        inputStream = getContentResolver().openInputStream(mMediaUri);
                        fileSize = inputStream.available();
                    }
                    catch(FileNotFoundException e){
                        Toast.makeText(this, R.string.title_generic_error_message, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    catch(IOException e){
                        Toast.makeText(this, R.string.title_generic_error_message, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    finally{
                        try {
                            inputStream.close();
                        }    catch(IOException e){/*Intentionally left blank*/}
                    }

                    if(fileSize >= FILE_SIZE_LIMIT){
                        Toast.makeText(this, R.string.error_video_size_too_large, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }

            else {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(mMediaUri);
            sendBroadcast(mediaScanIntent);
            }

            Intent recipientsIntent = new Intent(this, RecipientsActivity.class);
            recipientsIntent.setData(mMediaUri);

            String fileType;
            if (requestCode == GET_PHOTO_REQUEST || requestCode == TAKE_PHOTO_REQUEST){
                fileType = ParseConstants.TYPE_IMAGE;
            }
            else {//if (requestCode == GET_VIDEO_REQUEST || requestCode == TAKE_VIDEO_REQUEST)
                fileType = ParseConstants.TYPE_VIDEO;
            }
            recipientsIntent.putExtra(ParseConstants.KEY_FILE_TYPE, fileType);

            startActivity(recipientsIntent);
        }

        else if (resultCode != RESULT_CANCELED){
            Toast.makeText(this, R.string.title_generic_error_message, Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToLogin() {
        ParseUser.logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // switch(id) {
        //    case R.id.action_logout:
        if(id == R.id.action_logout){
            navigateToLogin();
        }
        else if(id == R.id.action_edit_friends){    //case R.id.action_edit_friends:
                Intent intent = new Intent(this, EditFriendsActivity.class);
                startActivity(intent);}
       else {//if(id == R.id.action_edit_friends) { //case R.id.action_camera:
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setItems(R.array.camera_choices, mCameraDialogListener);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

}
