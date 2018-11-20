package com.vip.vipapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.Settings.Secure;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/*
 *
 * change package name change
 * id
 * proj number
 * share client (findShareClient)
 * version number(for same client id)
 * #change msg type id
 * manifest package name
 * Icon
 */

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener, OnClickListener {
    private static String url = "", url1 = "", url2 = "";
    //   private static final String BASEURL = "http://one.hnp.dev/";
//    private static final String BASEURL = "http://2ndcousinmedia.com/";
    //private static final String BASEURL = "http://d9081a55.ngrok.io/";
    // String variables.
    Runnable notification;
//    static String baseUrl = " http://2ndcousinmedia.com/";
   // static String baseUrl = "http://d9081a55.ngrok.io/";
    private static final String YoutubeDeveloperKey = "AIzaSyAN4mjZm7wGNkFEF5iL8kgw5XWVUJQvGXs";
    String sendURL = GlobalArrayList.BASE_URL + "my_mailer/email_sent.json";
   // String sendURL = "http://d9081a55.ngrok.io/my_mailer/email_sent.json";
    String srch_goog, tag, client_id = "";
    String[] srch_curl;
    String search_url, search_string;
    String subtxt, maintxt, srch;
    String uri, phone, email, short_name;
    String data_html;
    String regid;
    // String PROJECT_NUMBER = "113508768748";

    String audio_url;
    String PROJECT_NUMBER = "";
    String[] g, temp;
    String u = "";
    String currentDate = "";
    static String ACTION = "";

    static int getNewMessage = 0;

    JSONArray value = null;
    JSONObject json;

    static Timer timer, timerUpdate;
    static TimerTask hourlyTask, hourlyTaskUpdate;

    TouchyWebView web_view;
    AQuery aq;
    ProgressBar progressBar;
    RelativeLayout mP;
    // ScrollView object.
    ScrollView sc1, sc2, sc3, sc4, scrollView;
    ImageButton buttonPlay, buttonStop;
    // TextView object.
    TextView main, sub, web, next, prev, share, txtLogout;
    private ImageButton buttonPlayPause;
    private SeekBar seekBarProgress;
    public TextView editTextSongURL;
    TextView duration;
    int timeElapsed, current;
    private MediaPlayer mediaPlayer;
    private int mediaFileLengthInMilliseconds; // this value contains the song duration in milliseconds. Look at getDuration() method in MediaPlayer class

    private final Handler handler = new Handler();
    // EditText object.
    EditText ques_name, ques_phone, ques_email, ques_query, search_text;
    EditText msg_name, msg_phone, msg_email, msg_query;
    EditText reg_firstName, reg_lastName, reg_email, reg_password;
    EditText signinEmail, signinPassword;
    EditText edtChatMessage;
    TextView shortName;
    ImageButton special_message;
    // ImageView for change layouts on screen.
    ImageView ques, msgg, call, search, cobrand, upper, mid_below, imgChat;

    // RelativeLayout object.
    RelativeLayout question, msg, yutube, forweb, cstm, cstm_below,
            cstm_rel_bottom, cbrand, forsearch, forbuttons, xx, registration,
            signin;
    LinearLayout forSpecialMessage;
    RelativeLayout relChatLayout;

    // Buttons to fire action.
    Button cancelMessage, cancelQues, cancelled, send_msg, send_ques, register,
            btnRegSignin, btnSignup, btnSignin, btnSendChatMessage;

    // ListView for show chat messages.
    ListView chatListView;

    ChatAdapter adapter;
    //MediaPlayer mPlayer;
    YouTubePlayerView youTubeView;
    private YouTubePlayer YPlayer;
    ParseClientDetails p1;
    ParseClientMessage p2;
    CoBrandingDetails brandingDetails;

    int first_srch = 1;
    int px, h, w;
    int index = 0, pivot = 6;
    static int limit = 0;
    static boolean isChat = false;
    List<String> full_list, msg_id;
    List<String> vid_list, text_list, video_type;

//    GoogleCloudMessaging gcm;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    DisplayMetrics displayMetrics;
    ProgressDialog progressDialog;

	/*
     * static AdView adView; private static final String AD_UNIT_ID =
	 * "ca-app-pub-7094721625794575/9921094240"; RelativeLayout adLayout;
	 */

    // private static final int RECOVERY_DIALOG_REQUEST = 1;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        //setting the dynamic URLs

        GlobalArrayList.setClientID(getString(R.string.client_id));
        PROJECT_NUMBER = getString(R.string.project_number);

        url = GlobalArrayList.BASE_URL + "desktop_widgets/client_detail_iphone.json?client_id="
                + getString(R.string.client_id) + "";
        url1 = GlobalArrayList.BASE_URL + "messages/listmessage.json?client_id="
                + getString(R.string.client_id) + "";

        url2 = GlobalArrayList.BASE_URL + "desktop_widgets/cobranding_details.json?client_id="
                + getString(R.string.client_id) + "";
//
//        url = "http://d9081a55.ngrok.io/desktop_widgets/client_detail_iphone.json?client_id="
//                + getString(R.string.client_id) + "";
//        url1 = "http://d9081a55.ngrok.io/messages/listmessage.json?client_id="
//                + getString(R.string.client_id) + "";
//
//        url2 = "http://d9081a55.ngrok.io/desktop_widgets/cobranding_details.json?client_id="
//                + getString(R.string.client_id) + "";

//
//        url = "http://192.168.1.31/desktop_widgets/client_detail_iphone.json?client_id="
//                + getString(R.string.client_id) + "";
//        url1 = "http://192.168.1.31/messages/listmessage.json?client_id="
//                + getString(R.string.client_id) + "";
//
//        url2 = "http://192.168.1.31/desktop_widgets/cobranding_details.json?client_id="
//                + getString(R.string.client_id) + "";

        client_id = "" + getString(R.string.client_id) + "";

        buttonPlayPause = (ImageButton) findViewById(R.id.ButtonTestPlayPause);

        seekBarProgress = (SeekBar) findViewById(R.id.SeekBarTestPlay);
        mP = (RelativeLayout) findViewById(R.id.mediaPlayer);
//        scrollView=(ScrollView)findViewById(R.id.scrollView);


        //specify the location of media file
        Uri uri = Uri.parse("https://09-lvl3-pdl.vimeocdn.com/01/4543/4/122719956/348069749.mp4?expires=1450772668&token=07a03e25f428f07226ff5");

        //Setting MediaController and URI, then starting the videoView

        displayMetrics = this.getResources().getDisplayMetrics();
        aq = new AQuery(this);
        full_list = new ArrayList<String>();
        text_list = new ArrayList<String>();
        vid_list = new ArrayList<String>();
        msg_id = new ArrayList<String>();
        video_type = new ArrayList<String>();

        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();

        p2 = new ParseClientMessage(url1, MainActivity.this, limit);
        ACTION = "";

        brandingDetails = new CoBrandingDetails(MainActivity.this, url2,
                displayMetrics.widthPixels);

        p1 = new ParseClientDetails(url, MainActivity.this);

        search_text = (EditText) findViewById(R.id.srchh);
        forweb = (RelativeLayout) findViewById(R.id.forweb);
        cstm = (RelativeLayout) findViewById(R.id.cstmRel);
        shortName = (TextView) findViewById(R.id.shortname);
        if (isInternetPresent) {
//            getRegId();
            brandingDetails.execute();
            p1.execute();
            p2.execute();
        } else {
            Toast t = Toast.makeText(this,
                    "Please check your internet connectivity",
                    Toast.LENGTH_SHORT);
            t.show();
        }

        chatListView = (ListView) findViewById(R.id.lst_chat);

        sc1 = (ScrollView) findViewById(R.id.sc1);
        sc2 = (ScrollView) findViewById(R.id.sc2);
        sc3 = (ScrollView) findViewById(R.id.sc3);
        sc4 = (ScrollView) findViewById(R.id.sc4);


        duration = (TextView) findViewById(R.id.dur);
        cstm_rel_bottom = (RelativeLayout) findViewById(R.id.cstmRelBtm);
        special_message = (ImageButton) findViewById(R.id.special_message);
        ques_name = (EditText) findViewById(R.id.q_edit_name);
        ques_phone = (EditText) findViewById(R.id.q_edit_phone);
        ques_email = (EditText) findViewById(R.id.q_edit_email);
        ques_query = (EditText) findViewById(R.id.q_edit_ques);
        msg_name = (EditText) findViewById(R.id.edit_name);
        msg_phone = (EditText) findViewById(R.id.edit_phone);
        msg_email = (EditText) findViewById(R.id.edit_email);
        forSpecialMessage = (LinearLayout) findViewById(R.id.forspcl_msg);
        msg_query = (EditText) findViewById(R.id.edit_msg);
        reg_firstName = (EditText) findViewById(R.id.edit_firstname);
        reg_lastName = (EditText) findViewById(R.id.edit_lastname);
        reg_email = (EditText) findViewById(R.id.edit_regemail);
        reg_password = (EditText) findViewById(R.id.edit_password);
        signinEmail = (EditText) findViewById(R.id.edit_email_signin);
        signinPassword = (EditText) findViewById(R.id.edit_password_signin);
        edtChatMessage = (EditText) findViewById(R.id.edt_message);

        main = (TextView) findViewById(R.id.main);
        share = (TextView) findViewById(R.id.share);
        // web = (TextView) findViewById(R.id.web);
        next = (TextView) findViewById(R.id.next);
        prev = (TextView) findViewById(R.id.previous);
        sub = (TextView) findViewById(R.id.sub);
        txtLogout = (TextView) findViewById(R.id.txt_logout);

        msg = (RelativeLayout) findViewById(R.id.message);
        question = (RelativeLayout) findViewById(R.id.question);
        xx = (RelativeLayout) findViewById(R.id.xx);
        yutube = (RelativeLayout) findViewById(R.id.youtube);
        forsearch = (RelativeLayout) findViewById(R.id.forsearch);
        forbuttons = (RelativeLayout) findViewById(R.id.forbuttons);
        cbrand = (RelativeLayout) findViewById(R.id.forcobrand);
        registration = (RelativeLayout) findViewById(R.id.registration);
        signin = (RelativeLayout) findViewById(R.id.signin);
        relChatLayout = (RelativeLayout) findViewById(R.id.relChatLayout);

        ques = (ImageView) findViewById(R.id.ques);
        upper = (ImageView) findViewById(R.id.upper);
        mid_below = (ImageView) findViewById(R.id.middel_belo);
        msgg = (ImageView) findViewById(R.id.img_msgg);
        call = (ImageView) findViewById(R.id.img_call);
        search = (ImageView) findViewById(R.id.imageView4);
        imgChat = (ImageView) findViewById(R.id.img_chat);

        cancelMessage = (Button) findViewById(R.id.cancel_message);
        cancelQues = (Button) findViewById(R.id.cancel_ques);
        cancelled = (Button) findViewById(R.id.cancellinggg);
        send_msg = (Button) findViewById(R.id.send_message);
        send_ques = (Button) findViewById(R.id.send_ques);
        register = (Button) findViewById(R.id.register);
        btnRegSignin = (Button) findViewById(R.id.btn_regsignin);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnSignin = (Button) findViewById(R.id.btn_signin);
        btnSendChatMessage = (Button) findViewById(R.id.btn_send_message);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(YoutubeDeveloperKey, this);
        web_view = (TouchyWebView) findViewById(R.id.web_view);
        cobrand = (ImageView) findViewById(R.id.cobrand);
        web_view.setWebViewClient(new MyBrowser());
        getActionBar().hide();

        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        web_view.getSettings().setLoadsImagesAutomatically(true);
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.getSettings().setPluginState(WebSettings.PluginState.ON);
        web_view.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // web_view.loadUrl(html);

        try {
            getNewMessage = getIntent().getExtras().getInt("message");
            if (getNewMessage == 1) {
                showNewMessageDialog();
            }
        } catch (Exception e) {

        }

        Drawable d = getResources().getDrawable(R.drawable.center);
        h = d.getIntrinsicHeight();
        w = d.getIntrinsicWidth();

        // Log.i("", "height" + h);
        if (displayMetrics.densityDpi >= 160) {
            px = (h * displayMetrics.densityDpi) / 160;
            w = (w * displayMetrics.densityDpi) / 160;
        } else {
            px = h;
        }
//        data_html = "<html><body><iframe src=\"http://media.w3.org/2010/05/sintel/trailer.mp4\" width=\"300" +
//                "\" height=\"300\" frameborder=\"0\" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe></body></html>";
////        data_html ="<html><object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" height=\"352\" responsive=\"0\" title=\"Chiropractic_wake-up\" width=\"640\"><param name=\"allowscriptaccess\" value=\"always\"><param name=\"movie\" value=\"http://ezs3.s3.amazonaws.com/player/510/player.swf\"><param name=\"wmode\" value=\"opaque\"><param name=\"allowfullscreen\" value=\"true\"><param name=\"timed_link\" value=\"0\"><param name=\"flashvars\" value=\"lightcolor=dd0000&amp;icons=true&amp;dock=false&amp;image=http://ezs3.1f9d9d099dee401b7be94865e62e13b4.s3.amazonaws.com/WakeUp Call_large_Thumb.jpg&amp;mute=false&amp;backcolor=ffffff&amp;aboutlink=http://www.ezs3.com/about&amp;controlbar=over&amp;autostart=false&amp;stretching=exactfit&amp;frontcolor=000000&amp;screencolor=000000&amp;streamer=rtmp://s95insi9z5pkm.cloudfront.net/cfx/st&amp;repeat=none&amp;file=marketingmirrorminute.m4v&amp;provider=rtmp&amp;abouttext=eZs3&amp;skin=http://ezs3.s3.amazonaws.com/player/skins/snel.zip&amp;plugins=captions-1&amp;captions.back=true\"><embed height=\"352\" responsive=\"0\" title=\"Chiropractic_wake-up\" width=\"640\" allowscriptaccess=\"always\" src=\"http://ezs3.s3.amazonaws.com/player/510/player.swf\" wmode=\"opaque\" allowfullscreen=\"true\" timed_link=\"0\" flashvars=\"lightcolor=dd0000&amp;icons=true&amp;dock=false&amp;image=http://ezs3.1f9d9d099dee401b7be94865e62e13b4.s3.amazonaws.com/WakeUp Call_large_Thumb.jpg&amp;mute=false&amp;backcolor=ffffff&amp;aboutlink=http://www.ezs3.com/about&amp;controlbar=over&amp;autostart=false&amp;stretching=exactfit&amp;frontcolor=000000&amp;screencolor=000000&amp;streamer=rtmp://s95insi9z5pkm.cloudfront.net/cfx/st&amp;repeat=none&amp;file=marketingmirrorminute.m4v&amp;provider=rtmp&amp;abouttext=eZs3&amp;skin=http://ezs3.s3.amazonaws.com/player/skins/snel.zip&amp;plugins=captions-1&amp;captions.back=true\" /></object></html>";


//        LayoutParams params2 = (LayoutParams) cstm_rel_bottom.getLayoutParams();
//        params2.height = px;
//        LayoutParams params = (LayoutParams) web_view.getLayoutParams();
//        params.height = px;
//        web_view.setLayoutParams(params);
//        LayoutParams params1 = (LayoutParams) web_view.getLayoutParams();
//        params1.height = px;
//        youTubeView.setLayoutParams(params);
//        forSpecialMessage.setLayoutParams(params);
        // youTubeView.getLayoutParams().height =px*2;


        Log.d("", "height in dp" + px + displayMetrics.densityDpi);

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // ListView adapter
        GlobalArrayList.message = new ArrayList<EachMessage>();
        GlobalArrayList.message2 = new ArrayList<EachMessage>();

        // adapter = new ChatAdapter(MainActivity.this.getBaseContext(),
        // R.layout.user_chat_row, R.layout.client_chat_row,
        // GlobalArrayList.message);
        adapter = new ChatAdapter(MainActivity.this.getBaseContext(),
                R.layout.user_chat_row, R.layout.client_chat_row,
                GlobalArrayList.message);
        chatListView.setAdapter(adapter);

        // for search action on google or on website
        search_text
                .setOnEditorActionListener(new TextView.OnEditorActionListener() {

                    @Override
                    public boolean onEditorAction(TextView v, int actionId,
                                                  KeyEvent event) {
                        // search_text.setCursorVisible(true);
                        if (first_srch == 1)
                            search_url = srch_curl[0].substring(
                                    srch_curl[0].indexOf('|') + 1,
                                    srch_curl[0].length());
                        String s = search_url;
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            if (search_text.getText().toString().trim()
                                    .length() >= 1) {
                                Log.d("", "srchmsg" + search_url);
                                search_url = search_url
                                        .replace("%S", search_text.getText()
                                                .toString().trim());
                                Log.d("Hello", "Hello % " + search_url);
                                search_text.setText("");
                                Intent intent = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse(search_url));

                                startActivity(intent);
                                search_url = s;
                                return true;
                            } else {

                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(
                                        search_text.getWindowToken(), 0);
                                // search_text.setCursorVisible(false);
                            }
                        }
                        return false;
                    }
                });

        register.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String firstName = reg_firstName.getText().toString();
                String lastName = reg_lastName.getText().toString();
                String eMail = reg_email.getText().toString();
                String password = reg_password.getText().toString();
                if (firstName.length() > 0 && lastName.length() > 0
                        && eMail.length() > 0 && password.length() > 0) {
                    String[] registrationData = new String[4];
                    registrationData[0] = firstName;
                    registrationData[1] = lastName;
                    registrationData[2] = eMail;
                    registrationData[3] = password;

                    reg_firstName.setText("");
                    reg_lastName.setText("");
                    reg_email.setText("");
                    reg_password.setText("");

                    new Registration().execute(registrationData);

                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please fill all fields.", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });


        btnSignin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                String eMail = signinEmail.getText().toString();
                String password = signinPassword.getText().toString();
                String[] signinData = new String[2];
                signinData[0] = eMail;
                signinData[1] = password;
                new Signin().execute(signinData);
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                signinPassword.setText("");
            }
        });

        btnSendChatMessage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String message = edtChatMessage.getText().toString().trim();
                if (message.length() > 0) {
                    edtChatMessage.setText("");
                    String[] chatMessage = new String[1];
                    chatMessage[0] = message;
                    new SendChatMessage().execute(chatMessage);

                }

            }
        });
		/* Set click listeners for action. */
        // share button for share video
        share.setOnClickListener(this);

        // next button for msg navigation
        next.setOnClickListener(this);

        // send msg on click of msg icon
        send_msg.setOnClickListener(this);

        // send question on click of quetion icon
        send_ques.setOnClickListener(this);

        // previous button for msg navigation
        prev.setOnClickListener(this);

        // for opening the client's web address
        //web.setOnClickListener(this);
        main.setOnClickListener(this);

        sub.setOnClickListener(this);
        // implemeting call feature on call icon click
        call.setOnClickListener(this);

        // cancel Message view
        cancelQues.setOnClickListener(this);
        // cancel Question view
        cancelMessage.setOnClickListener(this);
        // cancel Registration view
        btnRegSignin.setOnClickListener(this);
        // for enabling Messge view
        msgg.setOnClickListener(this);
        // for enabling Question view
        ques.setOnClickListener(this);
        // for enabling Registration view
        imgChat.setOnClickListener(this);
        // for logout event
        txtLogout.setOnClickListener(this);

        btnSignup.setOnClickListener(this);
        // cancel signup button
        cancelled.setOnClickListener(this);

        GlobalArrayList.mHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                GlobalArrayList.isNotificationMessage = true;
                Bundle bundle = msg.getData();
                int pushMessage = bundle.getInt("pushMessage");
                String newMessage = bundle.getString("newMessage");
                new FetchData().execute();
                if ((relChatLayout.getVisibility() == View.INVISIBLE || relChatLayout
                        .getVisibility() == View.GONE) && pushMessage == 1) {
                    imgChat.setImageResource(R.drawable.chat_new_message);
                    getNewMessage = 1;
                    NotificationManager mNotificationManager = (NotificationManager) getApplicationContext()
                            .getSystemService(Context.NOTIFICATION_SERVICE);

                    Intent intentExtra = new Intent(MainActivity.this,
                            MainActivity.class);
                    intentExtra.putExtra("message", 1);

                    PendingIntent contentIntent = PendingIntent.getActivity(
                            MainActivity.this, 0, intentExtra, 0);

                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                            getApplicationContext())
                            .setSmallIcon(R.drawable.vip_push)
                            .setContentTitle(getString(R.string.app_name))
                            .setStyle(
                                    new NotificationCompat.BigTextStyle()
                                            .bigText(newMessage))
                            .setContentText(newMessage).setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_SOUND);

                    mBuilder.setContentIntent(contentIntent);
                    mNotificationManager.notify(1, mBuilder.build());
                    // change image on chat icon
                }
            }
        };


    }

    /**
     * onClick method for Button, TextView etc.
     */
    @Override
    public void onClick(View v) {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        switch (v.getId()) {
            case R.id.ques:
//              mP.setVisibility(View.GONE);
//                destroyPlayer();
//                forSpecialMessage.setVisibility(View.GONE);
//                ACTION = "question";
//                setInvisibleView();
//                isChat = false;
//                ques.setImageResource(R.drawable.questionfoccussedcopy_selected);
//                question.setVisibility(View.VISIBLE);
//                sc1.setVisibility(View.VISIBLE);

                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.ques_link)));
                startActivity(intent1);
                break;

            case R.id.img_msgg:
                destroyPlayer();
                mP.setVisibility(View.GONE);
                destroyPlayer();
                forSpecialMessage.setVisibility(View.GONE);
                ACTION = "message";
                setInvisibleView();
                isChat = false;
                msgg.setImageResource(R.drawable.messagefocuscopy_selected);
                msg.setVisibility(View.VISIBLE);
                sc2.setVisibility(View.VISIBLE);
                break;

            case R.id.img_call:
                forSpecialMessage.setVisibility(View.GONE);
                mP.setVisibility(View.GONE);
                destroyPlayer();
                ACTION = "call";
                setInvisibleView();
                isChat = false;
                callProcedure();
                break;

            case R.id.img_chat:
                mP.setVisibility(View.GONE);
                destroyPlayer();
                forSpecialMessage.setVisibility(View.GONE);
                getNewMessage = 0;
                setInvisibleView();
                isChat = !isChat;
                if (isChat) {
                    GlobalArrayList.isShowChat = true;
                    getNewMessage = 0;
                    imgChat.setImageResource(R.drawable.chat_icon_selected);
                    // ACTION = "chat";
                    if (GlobalArrayList.userId == 0) {
                        signin.setVisibility(View.VISIBLE);
                        sc4.setVisibility(View.VISIBLE);
                    } else {
                        // Toast.makeText(getApplicationContext(),
                        // "user_id:" + GlobalArrayList.userId, Toast.LENGTH_LONG)
                        // .show();

                        relChatLayout.setVisibility(View.VISIBLE);
                        showChatMessages();
                    }
                } else {
                    if (ACTION.equalsIgnoreCase("question")) {
                        ques.setImageResource(R.drawable.questionfoccussedcopy_selected);
                        ACTION = "question";
                        question.setVisibility(View.VISIBLE);
                        sc1.setVisibility(View.VISIBLE);
                    } else if (ACTION.equalsIgnoreCase("message")) {
                        msgg.setImageResource(R.drawable.messagefocuscopy_selected);
                        ACTION = "message";
                        msg.setVisibility(View.VISIBLE);
                        sc2.setVisibility(View.VISIBLE);
                    } else if (ACTION.equalsIgnoreCase("call")) {
                        ACTION = "call";
                        callProcedure();
                    } else if (ACTION.equalsIgnoreCase("")) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                        showMessages();
                        next.setVisibility(View.VISIBLE);
                        prev.setVisibility(View.VISIBLE);
                        share.setVisibility(View.VISIBLE);
                    }
                }

                break;

            case R.id.btn_regsignin:
                setInvisibleView();
                imgChat.setImageResource(R.drawable.chat_icon_selected);
                signin.setVisibility(View.VISIBLE);
                sc4.setVisibility(View.VISIBLE);

                break;

            case R.id.btn_signup:
                setInvisibleView();
                imgChat.setImageResource(R.drawable.chat_icon_selected);
                registration.setVisibility(View.VISIBLE);
                sc3.setVisibility(View.VISIBLE);
                break;

            case R.id.txt_logout:

                new SignOut().execute();

                break;
            case R.id.cancel_ques:
                setInvisibleView();
                ACTION = "";
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                question.setVisibility(View.INVISIBLE);
                msg.setVisibility(View.INVISIBLE);
                registration.setVisibility(View.INVISIBLE);
                signin.setVisibility(View.INVISIBLE);
                relChatLayout.setVisibility(View.INVISIBLE);

                ques_email.setText("");
                ques_name.setText("");
                ques_phone.setText("");
                ques_query.setText("");

                next.setVisibility(View.VISIBLE);
                prev.setVisibility(View.VISIBLE);
                share.setVisibility(View.VISIBLE);

                imm.hideSoftInputFromWindow(ques_email.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(ques_phone.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(ques_query.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(ques_name.getWindowToken(), 0);
                showMessages();
                break;
            case R.id.cancellinggg:
                setInvisibleView();
                ACTION = "";
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                question.setVisibility(View.INVISIBLE);
                msg.setVisibility(View.INVISIBLE);
                registration.setVisibility(View.INVISIBLE);
                signin.setVisibility(View.INVISIBLE);
                relChatLayout.setVisibility(View.INVISIBLE);

                ques_email.setText("");
                ques_name.setText("");
                ques_phone.setText("");
                ques_query.setText("");

                next.setVisibility(View.VISIBLE);
                prev.setVisibility(View.VISIBLE);
                share.setVisibility(View.VISIBLE);

                imm.hideSoftInputFromWindow(ques_email.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(ques_phone.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(ques_query.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(ques_name.getWindowToken(), 0);
                showMessages();
                break;

            case R.id.cancel_message:
                setInvisibleView();
                ACTION = "";
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                question.setVisibility(View.INVISIBLE);
                msg.setVisibility(View.INVISIBLE);
                registration.setVisibility(View.INVISIBLE);
                signin.setVisibility(View.INVISIBLE);
                relChatLayout.setVisibility(View.INVISIBLE);

                next.setVisibility(View.VISIBLE);
                prev.setVisibility(View.VISIBLE);
                share.setVisibility(View.VISIBLE);
                msg_email.setText("");
                msg_name.setText("");
                msg_phone.setText("");
                msg_query.setText("");

                imm.hideSoftInputFromWindow(msg_email.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(msg_phone.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(msg_query.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(msg_name.getWindowToken(), 0);

                showMessages();
                break;

//            case R.id.web:
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                startActivity(intent);
//                break;

            case R.id.main:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
                break;

            case R.id.sub:
                Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent2);
                break;

            case R.id.previous:
                if (index <= 0) {
                    prev.setClickable(false);
                    prev.setTextColor(Color.GRAY);
                } else {
                    --index;
                    if (mediaPlayer != null) {
                        handler.removeCallbacks(notification);

                        duration.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes((long) 0), TimeUnit.MILLISECONDS.toSeconds((long) 0) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) 0))));
                        seekBarProgress.setProgress(0);
                        seekBarProgress.setSecondaryProgress(0);
                        mediaPlayer.release();
                        mediaPlayer = null;
                        current = 0;
                        buttonPlayPause.setImageResource(android.R.drawable.ic_media_play);

                    }
                    showMessages();
                }
                break;

            case R.id.next:
                if (index >= text_list.size() - 1) {
                    next.setClickable(false);
                    next.setTextColor(Color.GRAY);
                } else {
                    index++;
                    if (mediaPlayer != null) {
                        handler.removeCallbacks(notification);

                        duration.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes((long) 0), TimeUnit.MILLISECONDS.toSeconds((long) 0) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) 0))));
                        seekBarProgress.setProgress(0);
                        seekBarProgress.setSecondaryProgress(0);
                        mediaPlayer.release();
                        mediaPlayer = null;
                        current = 0;
                        buttonPlayPause.setImageResource(android.R.drawable.ic_media_play);

                    }
                    showMessages();
                }
                break;

            case R.id.share:

                PopupMenu popup = new PopupMenu(MainActivity.this, search);

                popup.getMenuInflater().inflate(R.menu.menu2, popup.getMenu());

                // registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        // search_text.setText(item.getTitle());
                        String text = item.getTitle().toString();

                        if (text.equals("via Social Network")) {
                            shareSocial();

                            return true;

                        } else {

                            Intent i = new Intent(
                                    Intent.ACTION_SEND);
                            i.setType("message/rfc822");
                            i.putExtra(android.content.Intent.EXTRA_SUBJECT,getString(R.string.app_name));
                            i.putExtra(Intent.EXTRA_TEXT,
                                    "Check out this content " + full_list.get(index) + "\n\nGet the app on\n"
                                            + getString(R.string.share_app_link));

                            startActivity(Intent.createChooser(i, "Share via"));

                            return true;
                        }
                    }
                });

                popup.show();// showing popup menu

                break;

            case R.id.send_message:
                String msgName,
                        msgPhone,
                        msgEmail,
                        msgQuery;
                msgName = msg_name.getText().toString();
                msgPhone = msg_phone.getText().toString();
                msgEmail = msg_email.getText().toString();
                msgQuery = msg_query.getText().toString();

                if (msgName.length() <= 00 || msgPhone.length() <= 0
                        || msgEmail.length() <= 0 || msgQuery.length() <= 0) {
                    Toast t = Toast.makeText(MainActivity.this,
                            "Fields can't be empty", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    t.show();

                } else {

                    new SendData(msgName, msgPhone, msgEmail, msgQuery,
                            MainActivity.this.email);
                    question.setVisibility(View.INVISIBLE);
                    msg_name.setText("");
                    msg_phone.setText("");
                    msg_email.setText("");
                    msg_query.setText("");

                    imm.hideSoftInputFromWindow(msg_email.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(msg_phone.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(msg_query.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(msg_name.getWindowToken(), 0);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                    showMessages();
                    next.setVisibility(View.VISIBLE);
                    prev.setVisibility(View.VISIBLE);
                    share.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.send_ques:
                String quesName,
                        quesPhone,
                        quesEmail,
                        quesQuery;
                quesName = ques_name.getText().toString();
                quesPhone = ques_phone.getText().toString();
                quesEmail = ques_email.getText().toString();
                quesQuery = ques_query.getText().toString();

                if (quesName.length() <= 00 || quesPhone.length() <= 0
                        || quesEmail.length() <= 0 || quesQuery.length() <= 0) {
                    Toast t = Toast.makeText(MainActivity.this,
                            "Fields can't be empty", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    t.show();

                } else {
                    new SendData(quesName, quesPhone, quesEmail, quesQuery,
                            "support@yourcontentbank.com");
                    // question.setVisibility(View.GONE);
                    ques_name.setText("");
                    ques_phone.setText("");
                    ques_email.setText("");
                    ques_query.setText("");
                    imm.hideSoftInputFromWindow(ques_email.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(ques_phone.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(ques_query.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(ques_name.getWindowToken(), 0);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                    showMessages();
                    next.setVisibility(View.VISIBLE);
                    prev.setVisibility(View.VISIBLE);
                    share.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
        if (v.getId() == R.id.ButtonTestPlayPause) {
            /** ImageButton onClick event handler. Method which start/pause mediaplayer playing */
            try {
                mediaPlayer.setDataSource(editTextSongURL.getText().toString()); // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
                mediaPlayer.prepareAsync(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.

                if (progressDialog != null && !progressDialog.isShowing())
                    progressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

//            mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL

            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                buttonPlayPause.setImageResource(android.R.drawable.ic_media_pause);

            } else {
                mediaPlayer.pause();
                buttonPlayPause.setImageResource(R.drawable.play);
            }

            primarySeekBarProgressUpdater();
        }
    }

    private void callProcedure() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MainActivity.this);
        question.setVisibility(View.INVISIBLE);
        msg.setVisibility(View.INVISIBLE);
        registration.setVisibility(View.INVISIBLE);
        signin.setVisibility(View.INVISIBLE);
        relChatLayout.setVisibility(View.INVISIBLE);

        forweb.setVisibility(View.INVISIBLE);
        yutube.setVisibility(View.INVISIBLE);
        imm.hideSoftInputFromWindow(ques_email.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(ques_phone.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(ques_query.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(ques_name.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(msg_email.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(msg_phone.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(msg_query.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(msg_name.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(search_text.getWindowToken(), 0);
        alertDialog.setMessage(phone);

        alertDialog.setPositiveButton("Call Now",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                        phoneIntent.setData(Uri.parse("tel:" + phone));

                        try {
                            startActivity(phoneIntent);
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                            finish();

                        } catch (android.content.ActivityNotFoundException ex) {

                        }
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                        dialog.cancel();
                        showMessages();
                        next.setVisibility(View.VISIBLE);
                        prev.setVisibility(View.VISIBLE);
                        share.setVisibility(View.VISIBLE);
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

    private void setInvisibleView() {
        question.setVisibility(View.INVISIBLE);
        // vv.setVisibility(View.INVISIBLE);
        msg.setVisibility(View.INVISIBLE);
        sc2.setVisibility(View.INVISIBLE);
        sc1.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);
        prev.setVisibility(View.INVISIBLE);
        yutube.setVisibility(View.INVISIBLE);
        share.setVisibility(View.INVISIBLE);
        forweb.setVisibility(View.INVISIBLE);
        registration.setVisibility(View.INVISIBLE);
        sc3.setVisibility(View.INVISIBLE);
        signin.setVisibility(View.INVISIBLE);
        sc4.setVisibility(View.INVISIBLE);
        relChatLayout.setVisibility(View.INVISIBLE);
        txtLogout.setVisibility(View.INVISIBLE);

        if (ACTION == "" && getNewMessage == 1)
            showNewMessageDialog();

        if (getNewMessage == 1) {
            imgChat.setImageResource(R.drawable.chat_icon);

        } else
            imgChat.setImageResource(R.drawable.chat_icon);

        ques.setImageResource(R.drawable.questionfoccussedcopy);
        msgg.setImageResource(R.drawable.messagefocuscopy);

    }

    private void signoutProcedure() {
        CommanStoredPreferences.clearAllPrefsData(MainActivity.this);
        setInvisibleView();
        GlobalArrayList.message.clear();
        GlobalArrayList.message2.clear();
        imgChat.setImageResource(R.drawable.chat_icon_selected);
        signin.setVisibility(View.VISIBLE);

        sc4.setVisibility(View.VISIBLE);
        GlobalArrayList.userId = 0;

        if (timerUpdate != null) {
            timerUpdate.cancel();
            hourlyTaskUpdate.cancel();
            Log.d("", "timer stoped");
        }
    }

    public void shareSocial() {

        PopupMenu popup = new PopupMenu(MainActivity.this, search);

        popup.getMenuInflater().inflate(R.menu.menu3, popup.getMenu());

        // registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                // search_text.setText(item.getTitle());
                String text = item.getTitle().toString();

                if (text.equals("Twitter")) {
                    Intent shareIntent = findShareClient("com.twitter.android");
                    if (shareIntent != null) {

                        Log.e("Share Client Find", "Share Client Find");

                        startActivity(shareIntent);
                    } else {
                        Log.e("Share Client Find not", "Share Client Find not");
                        Toast toast = Toast.makeText(MainActivity.this,
                                "Share App not found.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                    return true;

                }

                if (text.equals("Facebook")) {
                    FacebookSdk.setApplicationId(getString(R.string.facebook_app_id));
                    FacebookSdk.sdkInitialize(getApplicationContext());
                    CallbackManager callbackManager = CallbackManager.Factory.create();
                    ShareDialog shareDialog = new ShareDialog(MainActivity.this);
                    ShareLinkContent content = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse(full_list.get(index)))
                            .setContentTitle(" " + getString(R.string.share_app_content))
                            .setContentDescription("Check out this content")
                            .build();
                    shareDialog.show(content);
//                    Intent shareIntent = findShareClient("com.facebook.katana");
//                    if (shareIntent != null) {
//
//                        Log.e("Share Client Find", "Share Client Find");
//                        startActivity(shareIntent);
//                    } else {
//                        Log.e("Share Client Find not", "Share Client Find not");
//                        Toast toast = Toast.makeText(MainActivity.this,
//                                "Share App not found.", Toast.LENGTH_SHORT);
//                        toast.setGravity(Gravity.CENTER, 0, 0);
//                        toast.show();
//                    }
                    return true;
                }

                if (text.equals("Google+")) {
                    Intent shareIntent = findShareClient("com.google.android.apps.plus");
                    if (shareIntent != null) {

                        Log.e("Share Client Find", "Share Client Find");

                        startActivity(shareIntent);
                    } else {
                        Log.e("Share Client Find not", "Share Client Find not");
                        Toast toast = Toast.makeText(MainActivity.this,
                                "Share App not found.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    return true;
                }

                return false;

            }
        });

        popup.show();// showing popup menu
    }

    // Updated for GCM - FCM
   /* public void getRegId() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging
                                .getInstance(getApplicationContext());
                    }
                    regid = gcm.register(PROJECT_NUMBER);
                    GlobalArrayList.regId = regid;
                    msg = "Device registered, registration ID=" + regid;
                    Log.i("GCM", msg);

                } catch (Exception ex) {
                    msg = "Error :" + ex.getMessage();

                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                // getRegId.setText(msg + "\n");
                new SendIntialInfo(regid, client_id);

            }
        }.execute(null, null, null);
    }*/

    // Check screen orientation or screen rotate event here
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            if ((msg_id.size() > 0)
                    && (msg_id.get(index).equals(GlobalArrayList.MSG_TYPE_VIDEO) || full_list.get(index)
                    .contains("watch?v") || full_list.get(index)
                    .contains("/embed/"))) {
                try {
                    YPlayer.setFullscreen(true);
                } catch (Exception e) {
                    if (ACTION != "")
                        setInvisibleView();
                    e.printStackTrace();
                }
                //for making video view fullscreen

                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                cstm_rel_bottom.setBackgroundResource(R.drawable.image22);
                cstm.setVisibility(View.GONE);
                upper.setVisibility(View.GONE);
                mid_below.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
                search_text.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                prev.setVisibility(View.GONE);
                share.setVisibility(View.GONE);
                cobrand.setVisibility(View.GONE);
                cbrand.setVisibility(View.GONE);
                forsearch.setVisibility(View.GONE);
                forbuttons.setVisibility(View.GONE);
                youTubeView.setVisibility(View.GONE);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

            }

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);


//
//
//            YPlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
//
//                private boolean interceptPlay = true;
//
//                @Override
//                public void onPlaying() {
//                    if (interceptPlay) {
//
//                    }
//                }
//
//                @Override
//                public void onStopped() {
//                    scrollView.scrollTo(0,0);
////                    YPlayer.play();
//                }
//
//                @Override
//                public void onSeekTo(int arg0) {
//                }
//
//                @Override
//                public void onPaused() {
//                    scrollView.scrollTo(0,0);
////                    YPlayer.play();
//                }
//
//                @Override
//                public void onBuffering(boolean arg0) {
//
//                }
//            });

            cstm_rel_bottom.setBackgroundResource(R.drawable.center);
            cstm.setVisibility(View.VISIBLE);
            upper.setVisibility(View.VISIBLE);
            mid_below.setVisibility(View.VISIBLE);
            search.setVisibility(View.VISIBLE);
            search_text.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
            prev.setVisibility(View.VISIBLE);
            share.setVisibility(View.VISIBLE);
            forsearch.setVisibility(View.VISIBLE);
            cobrand.setVisibility(View.VISIBLE);
            cbrand.setVisibility(View.VISIBLE);
            forbuttons.setVisibility(View.VISIBLE);
            youTubeView.setVisibility(View.VISIBLE);


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onInitializationFailure(Provider arg0,
                                        YouTubeInitializationResult error) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "Oh no! " + error.toString(), Toast.LENGTH_LONG)
                .show();
    }


    public Intent findShareClient(String packageName) {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_content) + " \n"+full_list.get(index) + "\n\n\nCheck out this content\n" + getString(R.string.share_app_link));


        final PackageManager packageManager = getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);

        for (ResolveInfo resolveInfo : list) {
            String p = resolveInfo.activityInfo.packageName;
            Log.d("", "pname" + p + "\n");
            if (p != null && p.startsWith(packageName)) {
                shareIntent.setPackage(p);
                return shareIntent;
            }
        }

        return null;
    }


    // display client's data on UI
    public void showDetails() {

        try{
        g = p1.getDetails();
        main.setText(g[0]);
        sub.setText(g[1]);
        shortName.setText(g[6]);
        uri = g[2];
        phone = g[3];
        srch = g[4];
        email = g[5];

        tag = srch;
        Log.d("", "lop" + srch);
        if(srch != null) {
            temp = srch.split("\\x7C");
            srch_curl = srch.split("#");
        }
        Log.d("", "qwerty" + srch_curl.length);
        for (int j = 0; j < srch_curl.length; j++)
            Log.d("", "tem22p" + srch_curl[j]);
        if (temp.length > 2)

        {
            search.setImageResource(R.drawable.srch_button_multiple);
        } else
            search.setImageResource(R.drawable.srch_button_single);
        for (int i = 1; i < temp.length; i++) {

            temp[i] = temp[i].substring(temp[i].indexOf('#') + 1,
                    temp[i].length());
            Log.d("", "temp" + temp[i]);
        }
        search_text.setHint(temp[0]);
        // tag = tag.substring(0, tag.indexOf('|'));
        // if (srch.indexOf('|') == srch.lastIndexOf('|'))
        // srch = srch.substring(srch.indexOf('|') + 1, srch.length());
        // else
        // srch = srch.substring(srch.indexOf('|') + 1, srch.indexOf("#"));

        search.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (temp.length > 2)

                {
                    // search.setImageResource(R.drawable.searchbuttonfocused);
                    first_srch = 0;
                    PopupMenu popup = new PopupMenu(MainActivity.this, search);

                    for (int i = 0; i < temp.length - 1; i++)

                        popup.getMenu().add(temp[i]);
                    popup.getMenuInflater().inflate(R.menu.popup_menu,
                            popup.getMenu());

                    // registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {

                            // search_text.setText(item.getTitle());
                            String text = item.getTitle().toString();
                            search_text.setHint(item.getTitle());
                            // Log.d("", "lopp" + item.getTitle());
                            search_text.setEnabled(true);

                            for (int i = 0; i < srch_curl.length; i++)
                                if ((srch_curl[i]).contains(text)) {
                                    search_url = srch_curl[i].substring(
                                            srch_curl[i].indexOf('|') + 1,
                                            srch_curl[i].length());
                                    return true;
                                }
                            return false;
                        }
                    });

                    popup.show();// showing popup menu
                } else {
                    // search.setImageResource(R.drawable.srchsingle);
                    search_text.setHint(temp[0]);
                    // Log.d("", "lopp" + item.getTitle());
                    search_text.setEnabled(true);
                    search_url = srch_curl[0].substring(
                            srch_curl[0].indexOf('|') + 1,
                            srch_curl[0].length());
                }
            }

        });
}
        catch (Exception e)
        {e.printStackTrace();}
    }

    // retrieve all the message of clients

    public void getList()
    {

        for (int i = 0; i < p2.getTextDetails().size(); i++) {
            String tmp = p2.getTextDetails().get(i);
            String tmp1 = p2.getVidDetails().get(i);
            String tmp2 = p2.getFullLink().get(i);
            text_list.add(tmp);
            vid_list.add(tmp1);
            full_list.add(tmp2);
            video_type.add(p2.getVideoType().get(i));
            msg_id.add(p2.getMsgId().get(i));
        }

		/*
		 * for (int i = 0; i < text_list.size(); i++) Log.d("", "msg" +
		 * vid_list.get(i));
		 */

    }

    // fetching next 10 contents
    public void showMessages() {

        if (index == pivot) {
            limit = limit + 10;
            pivot += 10;
            p2 = new ParseClientMessage(url1, MainActivity.this, limit);
            p2.execute();
        }
        updateUI();

        // Log.d("","jfvjdbgvjdb"+vid_listindex);

    }

    // displaying messgae according to youtube or html type
    public void updateUI() {
        mP.setVisibility(View.GONE);
        // mediaPlayer.stop();
//        if(mediaPlayer!=null)
//        {
//            handler.removeCallbacks(notification);
//
//            duration.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes((long) 0), TimeUnit.MILLISECONDS.toSeconds((long) 0) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) 0))));
//            seekBarProgress.setProgress(0);
//            seekBarProgress.setSecondaryProgress(0);
//            mediaPlayer.release();
//            mediaPlayer=null;
//            buttonPlayPause.setImageResource(android.R.drawable.ic_media_play);
//
//        }
        if (vid_list.size() > 0) {
            Log.d("", "Vid" + vid_list.size());
            Log.i("", "here id" + msg_id.get(index));

            if (msg_id.get(index).equals(GlobalArrayList.MSG_TYPE_VIDEO)){// && full_list.get(index).contains("watch?v")) {

                if(full_list.get(index).contains("watch?v")) {
                    forSpecialMessage.setVisibility(View.GONE);
                    refresh();
                }
                else
                    webMessage();
            } else
                webMessage();
        }
    }

    // for showing clients's ad image and handling click activity to redirect to
    // specified url
    public void showCobrand() {
        if (brandingDetails.urii().length() > 0)
            aq.id(R.id.cobrand).image(brandingDetails.urii());

        cobrand.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                        .parse(brandingDetails.linkk()));
                startActivity(intent);
            }
        });
    }

    // for displaying youtube view
    public void refresh() {

        youTubeView.setVisibility(View.VISIBLE);
        forSpecialMessage.setVisibility(View.GONE);

        if (YPlayer != null) {
            YPlayer.release();
        }
        yutube.setVisibility(View.VISIBLE);
        forweb.setVisibility(View.GONE);
        mP.setVisibility(View.GONE);
        if (next.isClickable() == false) {
            next.setClickable(true);
            next.setTextColor(Color.parseColor("#6499be"));
        }
        if (index == 0) {
            prev.setClickable(false);
            prev.setTextColor(Color.GRAY);
        } else {
            prev.setClickable(true);
            prev.setTextColor(Color.parseColor("#6499be"));
        }
        youTubeView.initialize(YoutubeDeveloperKey, this);
    }

    // for displaying web view
    public void webMessage() {
        u = "";
        yutube.setVisibility(View.GONE);
        forweb.setVisibility(View.VISIBLE);

        if (next.isClickable() == false) {
            next.setClickable(true);
            next.setTextColor(Color.parseColor("#6499be"));
        }
        if (index == 0) {
            prev.setClickable(false);
            prev.setTextColor(Color.GRAY);
        } else {
            prev.setClickable(true);
            prev.setTextColor(Color.parseColor("#6499be"));
        }

        if (vid_list.get(index).startsWith("http://")) {
            web_view.loadUrl(vid_list.get(index));
            mP.setVisibility(View.GONE);
        } else {
            buttonPlayPause.setImageResource(R.drawable.play);

            mP.setVisibility(View.GONE);

            Log.d("", "index:" + index + " " + msg_id.get(index));
            Log.d("text_list", text_list.get(index) + " ");
            Log.d("vid", vid_list.get(index) + " ");
            Log.d("full_list", full_list.get(index) + " ");


            if (msg_id.get(index).contains(GlobalArrayList.MSG_TYPE_AUDIO))

//for audio


            {


                u = "<b>" + text_list.get(index) + "</b><br>"
                        + vid_list.get(index) + "<br><a href='"
                        + full_list.get(index) + "'><b></a>";


                web_view.setVisibility(View.VISIBLE);
                forSpecialMessage.setVisibility(View.GONE);
//

                web_view.loadData(
                        "<style type=\"text/css\">img {width:auto; height: auto;max-width:"
                                + (px / 2 - 40) + "px;}</style>" + u, "text/html",
                        "UTF-8");
                mP.setVisibility(View.VISIBLE);
//                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                initView();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                        if (progressDialog != null && progressDialog.isShowing())
                            progressDialog.dismiss();

                        mediaFileLengthInMilliseconds = mediaPlayer.getDuration();

                        mediaPlayer.start();
                        Log.d("current from start", "" + current);
                        mediaPlayer.seekTo(current);
                        buttonPlayPause.setImageResource(android.R.drawable.ic_media_pause);
                        seekBarProgress.setMax(mediaFileLengthInMilliseconds);
                        timeElapsed = mediaPlayer.getCurrentPosition();
                        double timeRemaining = timeElapsed;
                        duration.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));

                        primarySeekBarProgressUpdater();

                    }
                });


//                if(full_list.get(index).endsWith(".mp3"))
//
//
//            web_view.loadUrl(full_list.get(index));
//
            }
            else if (msg_id.get(index).contains(GlobalArrayList.MSG_TYPE_BLOG))//("3"))
//for blog post
            {
                u = "<b>" + text_list.get(index) + "</b><br>"
                        + vid_list.get(index) + "<br><a href='"
                        + full_list.get(index) + "'><b>Continue Reading</a>";


                web_view.setVisibility(View.VISIBLE);
                forSpecialMessage.setVisibility(View.GONE);

                web_view.loadData(
                        "<style type=\"text/css\">img {width:auto; height: auto;max-width:"
                                + (px / 2 - 40) + "px;}</style>" + u, "text/html",
                        "UTF-8");
                mP.setVisibility(View.GONE);
//                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                initView();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                        if (progressDialog != null && progressDialog.isShowing())
                            progressDialog.dismiss();

                        mediaFileLengthInMilliseconds = mediaPlayer.getDuration();

                        mediaPlayer.start();
                        Log.d("current from start", "" + current);
                        mediaPlayer.seekTo(current);
                        buttonPlayPause.setImageResource(android.R.drawable.ic_media_pause);
                        seekBarProgress.setMax(mediaFileLengthInMilliseconds);
                        timeElapsed = mediaPlayer.getCurrentPosition();
                        double timeRemaining = timeElapsed;
                        duration.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));

                        primarySeekBarProgressUpdater();

                    }
                });


//                if(full_list.get(index).endsWith(".mp3"))
//
//
//            web_view.loadUrl(full_list.get(index));
//
            }
//            else
//            if (msg_id.get(index).equals("16"))
//            //for special message
//            {
//                buttonPlayPause.setImageResource( android.R.drawable.ic_media_play);
//
//                mP.setVisibility(View.GONE);
//                web_view.setVisibility(View.GONE);
//                forSpecialMessage.setVisibility(View.VISIBLE);
//                special_message.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri
//                                .parse(full_list.get(index)));
//                        startActivity(intent);
//                    }
//                });
//            }
            // else
            // if(full_list.get(index).endsWith(".mp3"))
            // u = "<b>" + text_list.get(index) + "</b><br>"+
            // vid_list.get(index) +
            // "<br><audio controls><source src='"+full_list.get(index)+"'type='audio/mp3'</audio>"+"<a href='"+
            // full_list.get(index) + "'></a>";
            //
            //
            else if (msg_id.get(index).contains(GlobalArrayList.MSG_TYPE_VIDEO))
            {
                if (YPlayer != null) {
                    YPlayer.release();
                }
                buttonPlayPause.setImageResource(android.R.drawable.ic_media_play);
                mP.setVisibility(View.GONE);
                forSpecialMessage.setVisibility(View.GONE);

                web_view.setWebChromeClient(new WebChromeClient());

                Log.v("MESSAGE ", "embed YES"); //allowfullscreen="true" webkitallowfullscreen="true" mozallowfullscreen="true"
                String html = "<iframe height='100%' width='100%' src='" + full_list.get(index) + "' frameborder='0' allowfullscreen webkitallowfullscreen mozallowfullscreen></iframe>";
                web_view.setVisibility(View.VISIBLE);

//                web_view.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);

                if(full_list.get(index).contains(".vimeo.")) {
                    web_view.loadDataWithBaseURL("https://www.vimeo.com", html, "text/html", "UTF-8", null);
                }
                else
                    web_view.loadDataWithBaseURL("https://www.youtube.com", html, "text/html", "UTF-8", null);

            }
            else {
                if (YPlayer != null) {
                    YPlayer.release();
                }
                buttonPlayPause.setImageResource(android.R.drawable.ic_media_play);
                mP.setVisibility(View.GONE);
                forSpecialMessage.setVisibility(View.GONE);
//                u = "<b>" + text_list.get(index) + "</b><br>"
//                        + vid_list.get(index) + "<br><a href='"
//                        + full_list.get(index) + "'></a>";
//                web_view.setVisibility(View.VISIBLE);
//                web_view.loadData(
//                        "<style type=\"text/css\">img {width:auto; height: auto;max-width:"
//                                + (px / 2 - 40) + "px;}</style>" + u, "text/html",
//                        "UTF-8");

//                if(!full_list.get(index).contains("/embed/") && !full_list.get(index).contains(".vimeo.")) {
                Log.v("MESSAGE ", "embed NO");
                u = "<b>" + text_list.get(index) + "</b><br>"
                        + vid_list.get(index) + "<br><a href='"
                        + full_list.get(index) + "'></a>";
                web_view.setVisibility(View.VISIBLE);
                web_view.loadData(
                        "<style type=\"text/css\">img {width:auto; height: auto;max-width:"
                                + (px / 2 - 40) + "px;}</style>" + u, "text/html",
                        "UTF-8");
            }


        }

    }

    private void initializeView() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        setInvisibleView();
        ACTION = "";
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        question.setVisibility(View.INVISIBLE);
        msg.setVisibility(View.INVISIBLE);
        registration.setVisibility(View.INVISIBLE);
        signin.setVisibility(View.INVISIBLE);
        relChatLayout.setVisibility(View.INVISIBLE);

        next.setVisibility(View.VISIBLE);
        prev.setVisibility(View.VISIBLE);
        share.setVisibility(View.VISIBLE);
        msg_email.setText("");
        msg_name.setText("");
        msg_phone.setText("");
        msg_query.setText("");

        imm.hideSoftInputFromWindow(msg_email.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(msg_phone.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(msg_query.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(msg_name.getWindowToken(), 0);

        if (isChat)
            isChat = !isChat;
        showMessages();
    }

    @Override
    public void onInitializationSuccess(Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
//		Toast.makeText(this, "success ", Toast.LENGTH_LONG).show();
        if (!wasRestored) {
            // Toast.makeText(this, "success ", Toast.LENGTH_LONG).show();
            initializeView();
            YPlayer = player;
//
//            YPlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
//
//                private boolean interceptPlay = true;
//
//                @Override
//                public void onPlaying() {
//                    if (interceptPlay) {
//                        YPlayer.pause();
//
//                        interceptPlay = false;
//                    }
//                }
//
//                @Override
//                public void onStopped() {
//                    if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
//                        //Do some stuff
//                        scrollView.scrollTo(0,0);
//                        YPlayer.play();
//                    }
//
//                }
//
//                @Override
//                public void onSeekTo(int arg0) {
//                }
//
//                @Override
//                public void onPaused() {
//                    if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
//                        //Do some stuff
//                        scrollView.scrollTo(0,0);
//                        YPlayer.play();
//                    }
//
//                }
//
//                @Override
//                public void onBuffering(boolean arg0) {
//                }
//            });

            if (vid_list.size() > 0)
                //YPlayer.loadVideo(vid_list.get(index));
                YPlayer.cueVideo(vid_list.get(index));

            // YPlayer.cueVideo(vid_list.get(0));
        }
    }

    protected Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Log.e("HAHA", "shouldOverrideUrlLoading");
            if (url.endsWith(".mp3")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(url), "audio/*");
                view.getContext().startActivity(intent);
                return true;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);

            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.e("HAHA", "onPageStarted");
            if (isInternetPresent)
                progressBar.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.e("HAHA", "onPageFinished");
            progressBar.setVisibility(View.GONE);

            Log.d("mp3", url);
            if (url.endsWith(".mp3")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(url), "audio/*");
                view.getContext().startActivity(intent);
            }
//                return true;

        }

    }

    private void showChatMessages() {
        // if (GlobalArrayList.message2.isEmpty()) {

        if (GlobalArrayList.message2.size() <= 0) {
            GlobalArrayList.message.clear();
            GlobalArrayList.message2.clear();
            relChatLayout.setVisibility(View.VISIBLE);
            txtLogout.setVisibility(View.VISIBLE);
            new FetchData().execute();

        } else {
            MainActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    try {
                        if (relChatLayout.getVisibility() == View.INVISIBLE) {
                            relChatLayout.setVisibility(View.VISIBLE);
                            txtLogout.setVisibility(View.VISIBLE);
                        }
                        txtLogout.setVisibility(View.VISIBLE);
                        adapter.data = GlobalArrayList.message2;
                        adapter.notifyDataSetChanged();
                        chatListView.setSelection(adapter.getCount() - 1);
                        Log.d("listupdate", "first time call listview update :"
                                + (adapter.getCount() - 1));
                    } catch (Exception e) {
                        Log.d("", "" + e);
                    }

                }
            });

        }
        // if (timerUpdate == null)
        // chatUpdate();
		/*
		 * } else { MainActivity.this.runOnUiThread(new Runnable() {
		 *
		 * @Override public void run() { try { adapter.data =
		 * GlobalArrayList.message2; adapter.notifyDataSetChanged();
		 * chatListView.setSelection(adapter.getCount() - 1);
		 * Log.d("listupdate", "first time call listview update"); } catch
		 * (Exception e) { Log.d("", "" + e); }
		 *
		 * } }); }
		 */
    }

    /**
     * Registration class to register new user and send on chat screen.
     */
    private class Registration extends AsyncTask<String, Void, String> {
        ProgressDialog registrationDialog;
        String eMail = "";
        String password = "";

        @SuppressLint("InlinedApi")
        @Override
        protected void onPreExecute() {
            registrationDialog = new ProgressDialog(MainActivity.this,
                    AlertDialog.THEME_DEVICE_DEFAULT_DARK);
            registrationDialog.setMessage("Registering...");
            registrationDialog.setCancelable(false);
            registrationDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String firstName = params[0];
            String lastName = params[1];
            eMail = params[2];
            password = params[3];
            HttpResponse response = null;
            String data = null;
            try {
                HttpClient client = new DefaultHttpClient();

                HttpPost request = new HttpPost(GlobalArrayList.BASE_URL
                        + "chat_users/create_new_user.json");

                // HttpPost request = new HttpPost(
                // "http://10.0.0.86:3000/chat_users/create_new_user.json");

                BasicNameValuePair clientIdBasicNameValuePair = new BasicNameValuePair(
                        "user[client_id]",
                        String.valueOf(getString(R.string.client_id)));
                BasicNameValuePair firstnameBasicNameValuePair = new BasicNameValuePair(
                        "user[first_name]", firstName);
                BasicNameValuePair lastnameBasicNameValuePair = new BasicNameValuePair(
                        "user[last_name]", lastName);
                BasicNameValuePair emailBasicNameValuePair = new BasicNameValuePair(
                        "user[email]", eMail);
                BasicNameValuePair passwordBasicNameValuePair = new BasicNameValuePair(
                        "user[password]", password);

                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                nameValuePairList.add(clientIdBasicNameValuePair);
                nameValuePairList.add(firstnameBasicNameValuePair);
                nameValuePairList.add(lastnameBasicNameValuePair);
                nameValuePairList.add(emailBasicNameValuePair);
                nameValuePairList.add(passwordBasicNameValuePair);

                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(
                        nameValuePairList);
                request.setEntity(urlEncodedFormEntity);

                response = client.execute(request);

                HttpEntity entity = response.getEntity();
                data = EntityUtils.toString(entity);
            } catch (Exception e) {
                Log.e("exception", "Register: " + e);
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("response", "Response:" + result);
            registrationDialog.dismiss();
            JSONObject jObject;
            try {
                jObject = new JSONObject(result);
                if (jObject.getBoolean("success")) {

                    String info = jObject.getString("info");
                    String dataString = jObject.getString("data");
                    JSONObject json_data = new JSONObject(dataString);
                    System.out.print(json_data);

                    int userID = json_data.getInt("user_id");
                    GlobalArrayList.userId = userID;
                    Log.d("", "info: ........: " + info + " userID:" + userID);

                    // String auth_token = json_data.getString("auth_token");
                    // GlobalArrayList.auth_token = auth_token;
                    Toast.makeText(getApplicationContext(), "" + info,
                            Toast.LENGTH_LONG).show();
                    relChatLayout.setVisibility(View.VISIBLE);

                    // Calling login background thread
                    String[] signinData = new String[2];
                    signinData[0] = eMail;
                    signinData[1] = password;
                    new Signin().execute(signinData);

                } else if (!jObject.getBoolean("success")) {
                    String info = jObject.getString("info");
                    Toast.makeText(getApplicationContext(), "" + info,
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.d("", "Error in Registration :" + e);
            }

        }
    }

    /**
     * Signin class to send user on chat screen.
     */
    private class Signin extends AsyncTask<String, Void, String> {
        ProgressDialog signinDialog;
        private String deviceToken = "";
        private String eMail = null;
        private String password = null;

        @SuppressLint("InlinedApi")
        @Override
        protected void onPreExecute() {
            deviceToken = Secure.getString(
                    MainActivity.this.getContentResolver(), Secure.ANDROID_ID);
            signinDialog = new ProgressDialog(MainActivity.this,
                    AlertDialog.THEME_DEVICE_DEFAULT_DARK);
            signinDialog.setMessage("Sign in...");
            signinDialog.setCancelable(false);
            if (GlobalArrayList.isShowChat)
                signinDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            eMail = params[0];
            password = params[1];
            HttpResponse response = null;
            String data = null;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost(GlobalArrayList.BASE_URL
                        + "chat_users/create_session.json");
                // HttpPost request = new HttpPost(
                // "http://10.0.0.86:3000/chat_users/create_session.json");

                BasicNameValuePair client_idBasicNameValuePair = new BasicNameValuePair(
                        "user[client_id]",
                        String.valueOf(getString(R.string.client_id)));
                BasicNameValuePair emailBasicNameValuePair = new BasicNameValuePair(
                        "user[email]", eMail);
                BasicNameValuePair passwordBasicNameValuePair = new BasicNameValuePair(
                        "user[password]", password);
                BasicNameValuePair tokenBasicNameValuePair = new BasicNameValuePair(
                        "user[device_token]", GlobalArrayList.regId);
                BasicNameValuePair isIPadBasicNameValuePair = new BasicNameValuePair(
                        "user[is_ipad]", String.valueOf(2));

                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                nameValuePairList.add(client_idBasicNameValuePair);
                nameValuePairList.add(emailBasicNameValuePair);
                nameValuePairList.add(passwordBasicNameValuePair);
                nameValuePairList.add(tokenBasicNameValuePair);
                nameValuePairList.add(isIPadBasicNameValuePair);

                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(
                        nameValuePairList);
                request.setEntity(urlEncodedFormEntity);

                response = client.execute(request);

                HttpEntity entity = response.getEntity();
                data = EntityUtils.toString(entity);

            } catch (Exception e) {
                Log.e("exception", "update: " + e);
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("response", "Response:" + result);
            signinDialog.dismiss();
            JSONObject jObject;
            try {
                jObject = new JSONObject(result);
                if (jObject.getBoolean("success")) {

                    // updatePage_count();

                    GlobalArrayList.message.clear();
                    GlobalArrayList.message2.clear();
                    String info = jObject.getString("info");
                    String dataString = jObject.getString("data");
                    JSONObject json_data = new JSONObject(dataString);
                    System.out.print(json_data);

                    int userID = json_data.getInt("id");
                    GlobalArrayList.userId = userID;
                    Log.d("", "info: ........: " + info + " userID:" + userID);
                    String auth_token = json_data.getString("auth_token");
                    GlobalArrayList.auth_token = auth_token;
                    Log.d("", "auth_token: ........: " + auth_token);
                    CommanStoredPreferences
                            .clearAllPrefsData(MainActivity.this);
                    CommanStoredPreferences.setSentenceStringPref(
                            MainActivity.this, "email", eMail);
                    CommanStoredPreferences.setSentenceStringPref(
                            MainActivity.this, "password", password);
                    CommanStoredPreferences.setSentenceIntPref(
                            getApplicationContext(), "userid", userID);

                    if (GlobalArrayList.isShowChat)
                        showChatMessages();

                } else if (!jObject.getBoolean("success")) {
                    String info = jObject.getString("info");
                    Toast.makeText(getApplicationContext(), "" + info,
                            Toast.LENGTH_LONG).show();

                    signoutProcedure();
                }
            } catch (Exception e) {
                Log.d("", "Error in Sign in :" + e);
            }
        }

    }

    /**
     * FetchData to get chat messages first time.
     */
    private class FetchData extends AsyncTask<String, Void, String> {
        ProgressDialog fetchMessageDialog;

        @SuppressLint("InlinedApi")
        @Override
        protected void onPreExecute() {
            fetchMessageDialog = new ProgressDialog(MainActivity.this,
                    AlertDialog.THEME_DEVICE_DEFAULT_DARK);
            fetchMessageDialog.setMessage("Loading...");
            fetchMessageDialog.setCancelable(false);
            if (!GlobalArrayList.isNotificationMessage) {
                fetchMessageDialog.show();
            } else {
                GlobalArrayList.isNotificationMessage = !GlobalArrayList.isNotificationMessage;
            }
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpResponse response = null;
            String data = null;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();

                request.setURI(new URI(GlobalArrayList.BASE_URL
                        + "messages/get_messages.json?message[client_id]="
                        + getString(R.string.client_id)));
                // request.setURI(new URI(
                // "http://10.0.0.86:3000/messages/get_messages.json?message[client_id]="
                // + getString(R.string.client_id)));

                request.addHeader("X-Api-Key", GlobalArrayList.auth_token);

                Log.d("request", "request:" + request);
                response = client.execute(request);

                HttpEntity entity = response.getEntity();
                data = EntityUtils.toString(entity);

            } catch (Exception e) {

                e.printStackTrace();

            }

            return data;
        }

        @SuppressLint("SimpleDateFormat")
        @Override
        protected void onPostExecute(String result) {
            Log.d("", "Result:" + result);
            fetchMessageDialog.dismiss();
            JSONObject jObject;
            try {
                jObject = new JSONObject(result);
                if (jObject.getBoolean("success")) {

                    currentDate = "";
                    String dataString = jObject.getString("data");
                    JSONObject json_data = new JSONObject(dataString);
                    JSONArray user_array = json_data.getJSONArray("messages");
                    GlobalArrayList.message.clear();
                    GlobalArrayList.message2.clear();

                    for (int i = 0; i < user_array.length(); i++) {
                        JSONObject json_message = user_array.getJSONObject(i);
                        String chatString = json_message
                                .getString("chat_message");
                        JSONObject chatJson = new JSONObject(chatString);
                        String dateString = chatJson.getString("updated_at");//"created_at");

                        String newDateFormat = "";
                        String date = "";
                        long ts = System.currentTimeMillis();
                        Date localTime = new Date(ts);

                        TimeZone tz = TimeZone.getTimeZone("UTC");
                        final SimpleDateFormat df = new SimpleDateFormat(
                                "yyyy-MM-dd'T'HH:mm:ssz");
                        df.setTimeZone(tz);
                        String nowAsISO = df.format(df.parse(dateString));

                        final SimpleDateFormat sdf = new SimpleDateFormat(
                                "yyyy-MM-dd'T'HH:mm:ss");

                        final Date dateObj = sdf.parse(nowAsISO);

                        final Date fromGMT = new Date(
                                dateObj.getTime()
                                        + TimeZone.getDefault().getOffset(
                                        localTime.getTime()));
                        newDateFormat = new SimpleDateFormat("KK:mm a")
                                .format(fromGMT);
                        date = new SimpleDateFormat("dd MMMM yyyy")
                                .format(fromGMT);
                        Log.d("newDateFormat:", "newDateFormat:"
                                + newDateFormat);

                        // if (!currentDate.equalsIgnoreCase(date)) {
                        adapter.addSectionHeaderItem(date);
                        // currentDate = date;
                        // Log.d("", "date:" + currentDate + " i:..." + i);
                        // }
                        GlobalArrayList.message2.add(new EachMessage(String
                                .valueOf(chatJson.getInt("user_id")), String
                                .valueOf(chatJson.getInt("client_id")),
                                chatJson.getString("text"), chatJson
                                .getBoolean("is_client"),
                                newDateFormat, date));
                    }

                    MainActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                adapter.data = GlobalArrayList.message2;
                                adapter.notifyDataSetChanged();
                                // chatListView.smoothScrollToPosition(adapter.getCount()
                                // - 1);
                                chatListView.setSelection(adapter.getCount() - 1);
                                Log.d("listupdate",
                                        "first time call listview update :"
                                                + (adapter.getCount() - 1));
                            } catch (Exception e) {
                                Log.d("", "" + e);
                            }

                        }
                    });
                } else if (jObject.getString("status").equals("unauthorized")) {
                    signoutProcedure();
                } else if (!jObject.getBoolean("success")) {
                    signoutProcedure();
                    String info = jObject.getString("info");
                    Toast.makeText(getApplicationContext(),
                            "FetchData:" + info, Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                signoutProcedure();
                e.printStackTrace();
                Log.d("", "Error in Fetch Data :" + e);
            }
        }
    }

    private class SendChatMessage extends AsyncTask<String, Void, String> {

        String message = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            message = params[0];
            HttpResponse response = null;
            String data = null;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost(GlobalArrayList.BASE_URL
                        + "messages/send_messages.json");

                // HttpPost request = new HttpPost(
                // "http://10.0.0.86:3000/messages/send_messages.json");

                request.addHeader("X-Api-Key", GlobalArrayList.auth_token);

                BasicNameValuePair client_idBasicNameValuePair = new BasicNameValuePair(
                        "message[client_id]",
                        String.valueOf(getString(R.string.client_id)));
                BasicNameValuePair messageBasicNameValuePair = new BasicNameValuePair(
                        "message[text]", message);

                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                nameValuePairList.add(client_idBasicNameValuePair);
                nameValuePairList.add(messageBasicNameValuePair);

                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(
                        nameValuePairList);
                request.setEntity(urlEncodedFormEntity);

                response = client.execute(request);

                HttpEntity entity = response.getEntity();
                data = EntityUtils.toString(entity);
            } catch (Exception e) {
                Log.e("exception", "send: " + e);
            }
            return data;
        }

        @SuppressLint("SimpleDateFormat")
        @Override
        protected void onPostExecute(String result) {

            JSONObject jObject;

            Calendar cal = Calendar.getInstance();
            Date currentLocalTime = cal.getTime();

            SimpleDateFormat time = new SimpleDateFormat("KK:mm a");
            SimpleDateFormat date = new SimpleDateFormat("dd MMMM yyyy");
            String localTime = time.format(currentLocalTime);
            String localDate = date.format(currentLocalTime);

            try {
                jObject = new JSONObject(result);
                if (jObject.getBoolean("success")) {

                    if (!currentDate.equalsIgnoreCase(localDate)) {
                        adapter.addSectionHeaderItem(localDate);
                        currentDate = localDate;
                    }
                    GlobalArrayList.message2.add(new EachMessage(String
                            .valueOf(GlobalArrayList.userId), String
                            .valueOf(getString(R.string.client_id)), message, false,
                            "" + localTime + "", "" + localDate + ""));

                    MainActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                adapter.data = GlobalArrayList.message2;
                                adapter.notifyDataSetChanged();
                                chatListView.smoothScrollToPosition(adapter
                                        .getCount() - 1);
                                // chatListView.setSelection(adapter.getCount()
                                // - 1);
                                Log.d("listupdate",
                                        "first time call listview update :"
                                                + (adapter.getCount() - 1));
                            } catch (Exception e) {
                                Log.d("", "" + e);
                            }

                        }
                    });
                } else if (!jObject.getBoolean("success")) {
                    String info = jObject.getString("info");
                    Toast.makeText(getApplicationContext(),
                            "send data:" + info, Toast.LENGTH_LONG).show();
                    signoutProcedure();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error in send Data",
                        Toast.LENGTH_LONG).show();
                signoutProcedure();
            }

        }
    }

    /**
     * SignOut class to send user on login screen.
     */
    private class SignOut extends AsyncTask<String, Void, String> {
        ProgressDialog signoutDialog;
        private String deviceToken = null;

        @SuppressLint("InlinedApi")
        @Override
        protected void onPreExecute() {
            deviceToken = Secure.getString(
                    MainActivity.this.getContentResolver(), Secure.ANDROID_ID);
            signoutDialog = new ProgressDialog(MainActivity.this,
                    AlertDialog.THEME_DEVICE_DEFAULT_DARK);
            signoutDialog.setMessage("Logout please wait...");
            signoutDialog.setCancelable(false);
            if (GlobalArrayList.isShowChat)
                signoutDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpResponse response = null;
            String data = null;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost(GlobalArrayList.BASE_URL
                        + "chat_users/logout.json");

                // HttpPost request = new HttpPost(
                // "http://10.0.0.86:3000/chat_users/logout.json");

                request.addHeader("X-Api-Key", GlobalArrayList.auth_token);

                BasicNameValuePair tokenBasicNameValuePair = new BasicNameValuePair(
                        "user[device_token]", GlobalArrayList.regId);
                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                nameValuePairList.add(tokenBasicNameValuePair);

                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(
                        nameValuePairList);
                request.setEntity(urlEncodedFormEntity);

                response = client.execute(request);

                HttpEntity entity = response.getEntity();
                data = EntityUtils.toString(entity);
            } catch (Exception e) {
                Log.e("exception", "update: " + e);
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            signoutDialog.dismiss();
            JSONObject jObject;
            try {
                jObject = new JSONObject(result);
                if (jObject.getBoolean("success")) {

                    signoutProcedure();
                    String info = jObject.getString("info");
                    String dataString = jObject.getString("data");
                    JSONObject json_data = new JSONObject(dataString);
                    System.out.print(json_data);

                    int userID = json_data.getInt("id");
                    GlobalArrayList.userId = 0;
                    Log.d("", "info: ........: " + info + " userID:" + userID);

                } else if (!jObject.getBoolean("success")) {
                    signoutProcedure();
                    String info = jObject.getString("info");
                    Toast.makeText(getApplicationContext(), "" + info,
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                signoutProcedure();
                Toast.makeText(getApplicationContext(), "Access denied",
                        Toast.LENGTH_LONG).show();

                Log.d("", "Error in Sign in :" + e);
            }
        }

    }

    // //////////////////////////////////////////////////////////////////////////////////////////////////updatePage_count()
    // method
    public void updatePage_count() {
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                timerUpdate = new Timer();
                hourlyTaskUpdate = new TimerTask() {
                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void run() {

                        HttpResponse response = null;
                        String data = null;
                        try {
                            HttpClient client = new DefaultHttpClient();
                            HttpGet request = new HttpGet();

                            request.setURI(new URI(
                                    GlobalArrayList.BASE_URL
                                            + "messages/get_messages.json?message[client_id]="
                                            + getString(R.string.client_id)));
                            // request.setURI(new URI(
                            // "http://10.0.0.86:3000/messages/get_messages.json?message[client_id]="
                            // + getString(R.string.client_id)));

                            request.addHeader("X-Api-Key",
                                    GlobalArrayList.auth_token);

                            Log.d("request", "request:" + request);
                            response = client.execute(request);

                            HttpEntity entity = response.getEntity();
                            data = EntityUtils.toString(entity);

                            JSONObject jObject;
                            try {
                                jObject = new JSONObject(data);
                                if (jObject.getBoolean("success")) {

                                    currentDate = "";
                                    String dataString = jObject
                                            .getString("data");
                                    JSONObject json_data = new JSONObject(
                                            dataString);
                                    JSONArray user_array = json_data
                                            .getJSONArray("messages");

                                    // if (GlobalArrayList.message2.size() <
                                    // user_array
                                    // .length()) {
                                    GlobalArrayList.message.clear();
                                    GlobalArrayList.message2.clear();
                                    adapter.notifyDataSetChanged();
                                    for (int i = 0; i < user_array.length(); i++) {
                                        JSONObject json_message = user_array
                                                .getJSONObject(i);
                                        Log.d("json_message", "json_message:"
                                                + json_message);
                                        String chatString = json_message
                                                .getString("chat_message");
                                        Log.d("chatString", "chatString:"
                                                + chatString);
                                        JSONObject chatJson = new JSONObject(
                                                chatString);
                                        String dateString = chatJson
                                                .getString("created_at");

                                        String newDateFormat = "";
                                        String date = "";
                                        long ts = System.currentTimeMillis();
                                        Date localTime = new Date(ts);

                                        TimeZone tz = TimeZone
                                                .getTimeZone("UTC");
                                        final SimpleDateFormat df = new SimpleDateFormat(
                                                "yyyy-MM-dd'T'HH:mm:ssz");
                                        df.setTimeZone(tz);
                                        String nowAsISO = df.format(df
                                                .parse(dateString));

                                        final SimpleDateFormat sdf = new SimpleDateFormat(
                                                "yyyy-MM-dd'T'HH:mm:ss");

                                        final Date dateObj = sdf
                                                .parse(nowAsISO);

                                        final Date fromGMT = new Date(
                                                dateObj.getTime()
                                                        + TimeZone
                                                        .getDefault()
                                                        .getOffset(
                                                                localTime
                                                                        .getTime()));
                                        newDateFormat = new SimpleDateFormat(
                                                "KK:mm a").format(fromGMT);
                                        date = new SimpleDateFormat(
                                                "dd MMMMM yyyy")
                                                .format(fromGMT);
                                        Log.d("newDateFormat:",
                                                "newDateFormat:"
                                                        + newDateFormat);

                                        // if
                                        // (!currentDate.equalsIgnoreCase(date))
                                        // {
                                        adapter.addSectionHeaderItem(date);
                                        // currentDate = date;
                                        // Log.d("", "date:" + currentDate +
                                        // " i:..." + i);
                                        // }
                                        GlobalArrayList.message2.add(new EachMessage(
                                                String.valueOf(chatJson
                                                        .getInt("user_id")),
                                                String.valueOf(chatJson
                                                        .getInt("client_id")),
                                                chatJson.getString("text"),
                                                chatJson.getBoolean("is_client"),
                                                newDateFormat, date));
                                    }

                                    MainActivity.this
                                            .runOnUiThread(new Runnable() {

                                                @Override
                                                public void run() {
                                                    try {
                                                        adapter.data = GlobalArrayList.message2;
                                                        adapter.notifyDataSetChanged();
                                                        chatListView
                                                                .setSelection(adapter
                                                                        .getCount() - 1);
                                                    } catch (Exception e) {
                                                        Log.d("", "" + e);
                                                    }

                                                }
                                            });
                                    // }
                                } else if (!jObject.getBoolean("success")) {
                                    String info = jObject.getString("info");
                                    Toast.makeText(getApplicationContext(),
                                            "FetchData:" + info,
                                            Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                Log.d("", "Error in Fetch Data :" + e);
                            }

                        } catch (Exception e) {

                            e.printStackTrace();

                        }

                    }

                };

                // ////////////////////////////////////////// schedule the task
                // to run starting now and then every 3 min...
                timerUpdate.schedule(hourlyTaskUpdate, 0l, 1000 * 3);

            }
        }, 5000);

    }

    private void executeSignIn() {

        try {
            if (CommanStoredPreferences.checkForPreferences(
                    getApplicationContext(), "email")) {
                String eMail = CommanStoredPreferences.getSentenceStringPref(
                        MainActivity.this, "email");
                String password = CommanStoredPreferences
                        .getSentenceStringPref(MainActivity.this, "password");
                GlobalArrayList.userId = CommanStoredPreferences
                        .getSentenceIntPref(getApplicationContext(), "userid");
                if (eMail != null) {
                    GlobalArrayList.isShowChat = false;
                    String[] signinData = new String[2];
                    signinData[0] = eMail;
                    signinData[1] = password;
                    new Signin().execute(signinData);
                }
                // InputMethodManager inputManager = (InputMethodManager)
                // getSystemService(Context.INPUT_METHOD_SERVICE);
                // inputManager.hideSoftInputFromWindow(getCurrentFocus()
                // .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        executeSignIn();
        super.onStart();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        showNewMessageDialog();
        super.onNewIntent(intent);
    }

    private void showNewMessageDialog() {
        if (imgChat != null)
            imgChat.setImageResource(R.drawable.chat_new_message);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(
                MainActivity.this);
        mBuilder.setMessage("Here is an message for user.");
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        // mBuilder.show();
    }

    @Override
    protected void onPause() {
        // if (adView != null) {
        // adView = null;
        // }
        super.onPause();
        if (mediaPlayer != null) {
            if (mediaPlayer != null) {

                double timeRemaining = 0;
                handler.removeCallbacks(notification);
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    current = mediaPlayer.getCurrentPosition();
                    buttonPlayPause.setImageResource(android.R.drawable.ic_media_play);
                    Log.d("current", "" + current);

                } else {
                    return;
                }

            }

        }
    }

    @Override
    protected void onResume() {

        // adView = new AdView(this);
        // adView.setAdSize(AdSize.BANNER);
        // adView.setAdUnitId(AD_UNIT_ID);
        //
        // adLayout.addView(adView);
        //
        // // Create an ad request. Check logcat output for the hashed device
        // // ID to
        // // get test ads on a physical device.
        // AdRequest adRequest = new AdRequest.Builder().build();
        //
        // // Start loading the ad in the background.
        // adView.loadAd(adRequest);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
//        if(mediaPlayer!=null)
//            mediaPlayer.release();
        if (timerUpdate != null) {
            timerUpdate.cancel();
            hourlyTaskUpdate.cancel();
            Log.d("", "timer stoped");
        }
        super.onDestroy();
    }

    private void initView() {

        buttonPlayPause.setOnClickListener(this);


        seekBarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (mediaPlayer.isPlaying()) {
                    // SeekBar sb = (SeekBar) view;
                    int playPositionInMillisecconds = i;
                    //   Log.e("HHHHH", "HHHH % " + sb+" "+playPositionInMillisecconds + " & " + sb.getProgress() + " & " + mediaFileLengthInMilliseconds);
                    if (b)
                        mediaPlayer.seekTo(playPositionInMillisecconds);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        // It means 100% .0-99
//        seekBarProgress.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                    if (mediaPlayer.isPlaying()) {
//                        SeekBar sb = (SeekBar) view;
//                        int playPositionInMillisecconds =  sb.getProgress();
//                        Log.e("HHHHH", "HHHH % " + sb+" "+playPositionInMillisecconds + " & " + sb.getProgress() + " & " + mediaFileLengthInMilliseconds);
//
//                        mediaPlayer.seekTo(playPositionInMillisecconds);
//
//                }
//                return false;
//            }
//        });


//        seekBarProgress.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    if(mediaPlayer.isPlaying()){
//                        SeekBar sb = (SeekBar)view;
//                        int playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100) * sb.getProgress();
//                        Log.e("HHHHH","HHHH % " + playPositionInMillisecconds);
//                        mediaPlayer.seekTo(playPositionInMillisecconds);
//                    }
//                }
//                catch (Exception e)
//                {
//                    Log.e("seekBarProgress ","setOnClickListener Exception");
//                    e.printStackTrace();
//                }
//            }
//        });

        audio_url = full_list.get(index);

        editTextSongURL = (TextView) findViewById(R.id.EditTextSongURL);
        editTextSongURL.setText(audio_url);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
                seekBarProgress.setSecondaryProgress((int) (float) (percent * mediaFileLengthInMilliseconds) / 100);
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                buttonPlayPause.setImageResource(android.R.drawable.ic_media_play);
            }
        });
    }

    /**
     * Method which updates the SeekBar primary progress by current song playing position
     */
    private void primarySeekBarProgressUpdater() {
        seekBarProgress.setProgress((int) mediaPlayer.getCurrentPosition()); // This math construction give a percentage of "was playing"/"song length"
        if (mediaPlayer.isPlaying()) {
            notification = new Runnable() {
                public void run() {
                    primarySeekBarProgressUpdater();
                    timeElapsed = mediaPlayer.getCurrentPosition();
                    double timeRemaining = timeElapsed;
                    duration.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));

                }
            };
            handler.postDelayed(notification, 1000);

        }
    }


    public void destroyPlayer() {

        if (mediaPlayer != null) {

            double timeRemaining = 0;
            handler.removeCallbacks(notification);
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                current = mediaPlayer.getCurrentPosition();
                Log.d("current", "" + current);

            } else {
                return;
            }


//            duration.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes((long) current), TimeUnit.MILLISECONDS.toSeconds((long) current) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) current))));
//            seekBarProgress.setProgress(current);
//            seekBarProgress.setSecondaryProgress(current);//
            buttonPlayPause.setImageResource(android.R.drawable.ic_media_play);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if(mediaPlayer!=null) {
//            handler.removeCallbacks(notification);
//            mediaPlayer.release();
//        }
        if (mediaPlayer != null) {

            double timeRemaining = 0;
            handler.removeCallbacks(notification);
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                current = mediaPlayer.getCurrentPosition();
                buttonPlayPause.setImageResource(android.R.drawable.ic_media_play);
                Log.d("current", "" + current);

            } else {
                return;
            }

        }

    }           }



