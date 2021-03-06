package com.example.lenovo.planner.applicationstart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digits.sdk.android.Digits;

import com.example.lenovo.planner.R;
import com.example.lenovo.planner.SharedPreps.UserDetails;
import com.example.lenovo.planner.forgotpassword.forgotpassword;
import com.example.lenovo.planner.UserHome.userhome;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;


public class SplashScreen extends AppCompatActivity {
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "KTrL1XbIuXI1umX7pFKiSyFh1";
    private static final String TWITTER_SECRET = "kXzxPsjT8JYRMuwCCKJpsk5npszppui6cLQYWThHB3a22o44XC";


    Animation animation,fade,translateLogin,translateLogo,fadeout;
    ImageView view;
    TextView textView;
    Typeface typeface;
    Animation left_in,right_in;
    CardView layout;
    RelativeLayout relativeLayout;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    UserDetails userDetails;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());
        setContentView(R.layout.activity_splash_screen);
        userDetails= new UserDetails(getApplicationContext());
        animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate);

        translateLogin= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translateforlogin);

        translateLogo= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translatelogo);

        fade= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        fadeout= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadeout);
        layout=(CardView) findViewById(R.id.frag_holder);
        left_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.left_in);
        right_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.right_in);
        relativeLayout =(RelativeLayout) findViewById(R.id.frag_holder2);

        login_fragment fragment= new login_fragment();

        fragmentManager = getSupportFragmentManager();
        transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.frag_holder2,fragment);
        transaction.commit();

        textView = (TextView) findViewById(R.id.appName);

        view= (ImageView) findViewById(R.id.logo);

        view.startAnimation(animation);
        view.bringToFront();
        typeface = Typeface.createFromAsset(getAssets(),"GreatVibes-Regular.ttf");
        textView.setTypeface(typeface);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                textView.startAnimation(fade);
                textView.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(userDetails.getIsActive())
                            {
                                Intent in = new Intent(getApplicationContext(),userhome.class);
                                startActivity(in);
                                overridePendingTransition(R.anim.left_in,R.anim.fadeout);
                            }
                            else {

                                textView.startAnimation(fadeout);
                                textView.setVisibility(View.GONE);

                                view.startAnimation(translateLogo);
                                layout.startAnimation(translateLogin);
                                layout.setVisibility(View.VISIBLE);
                                layout.bringToFront();
                            }

                        }
                    }, 1900);


            }
        },1200);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void callregister(View vi)
    {
        transaction = fragmentManager.beginTransaction();
        fragment_signup signups = new fragment_signup();
        transaction.setCustomAnimations(R.anim.fade,R.anim.fadeout);
        transaction.replace(R.id.frag_holder2,signups);
        transaction.commit();
    }
    public void calllogin(View vi)
    {
        transaction = fragmentManager.beginTransaction();
        login_fragment logins = new login_fragment();
        transaction.setCustomAnimations(R.anim.fade,R.anim.fadeout);
        transaction.replace(R.id.frag_holder2,logins);
        transaction.commit();
    }
    public void forgottransition(View vi)
    {
        Intent forgot = new Intent(this, forgotpassword.class);
        startActivity(forgot);
        overridePendingTransition(R.anim.left_in,R.anim.right_in);


    }
    public void callsignup(View vi)
    {
        Intent in = new Intent(this, userhome.class);
        startActivity(in);
        overridePendingTransition(R.anim.left_in,R.anim.right_in);
        finish();
    }
    public void logincall(View vi)
    {
        Intent intent2 = new Intent(this, userhome.class);
        startActivity(intent2);
        overridePendingTransition(R.anim.left_in,R.anim.right_in);
        finish();
    }



}
