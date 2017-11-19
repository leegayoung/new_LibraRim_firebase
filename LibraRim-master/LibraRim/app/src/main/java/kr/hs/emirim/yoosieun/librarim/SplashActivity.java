package kr.hs.emirim.yoosieun.librarim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Yoosieun on 2017-11-09.
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentContentView는 하지 않습니다.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
