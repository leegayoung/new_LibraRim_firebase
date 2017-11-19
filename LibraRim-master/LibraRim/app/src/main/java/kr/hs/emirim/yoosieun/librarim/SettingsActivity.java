package kr.hs.emirim.yoosieun.librarim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Yoosieun on 2017-11-09.
 */

public class SettingsActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getWindow().setWindowAnimations(0); //전환효과 없애기

        getFragmentManager().beginTransaction().replace(R.id.set_content,new Settingsfragment()).commit();

        final RelativeLayout backButton=(RelativeLayout)findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}