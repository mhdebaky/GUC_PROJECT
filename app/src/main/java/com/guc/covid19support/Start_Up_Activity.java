package com.guc.covid19support;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.guc.covid19support.registeration.SignIn_SingUp_Activity;

public class Start_Up_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_up);

        Animation anim = AnimationUtils.loadAnimation(this,R.anim.fade);
        TextView tv1=  (TextView) findViewById(R.id.textView1);
        TextView tv2 = (TextView) findViewById(R.id.textView2);
        anim.reset();
        anim.setDuration(3200);
        tv1.clearAnimation();
        tv1.startAnimation(anim);
        tv2.clearAnimation();
        tv2.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                Intent intent = new Intent(Start_Up_Activity.this, SignIn_SingUp_Activity.class);
                startActivity(intent);
                Start_Up_Activity.this.finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}