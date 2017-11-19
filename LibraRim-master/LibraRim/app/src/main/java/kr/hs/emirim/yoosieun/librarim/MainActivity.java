package kr.hs.emirim.yoosieun.librarim;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    static String beforePage[]={"home","home"};
    RelativeLayout backButton;
    LinearLayout homeButton;
    LinearLayout classificationButton;
    LinearLayout applicationButton;
    LinearLayout infoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setWindowAnimations(0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getFragmentManager().beginTransaction().replace(R.id.main_frame,new Homefragment()).commit();

        homeButton=(LinearLayout)findViewById(R.id.home_button);
        classificationButton=(LinearLayout)findViewById(R.id.classification_button);
        applicationButton=(LinearLayout)findViewById(R.id.application_button);
        infoButton=(LinearLayout)findViewById(R.id.info_button);
        backButton=(RelativeLayout)findViewById(R.id.backbutton);

        backButton.setVisibility(View.INVISIBLE);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_frame,new Homefragment()).commit();
                backButton.setVisibility(View.INVISIBLE);
                beforePage[0]=beforePage[1];
                beforePage[1]="home";
            }
        });
        classificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_frame,new Classificationfragment()).commit();
                backButton.setVisibility(View.VISIBLE);
                beforePage[0]=beforePage[1];
                beforePage[1]="classification";
            }
        });
        applicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse( "https://goo.gl/forms/dp5wksHwR91MVEpE2" ));
                startActivity(intent);
                getFragmentManager().beginTransaction().replace(R.id.main_frame,new Applicationfragment()).commit();
                backButton.setVisibility(View.VISIBLE);
                beforePage[0]=beforePage[1];
                beforePage[1]="application";
            }
        });
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_frame,new Infofragment()).commit();
                backButton.setVisibility(View.VISIBLE);
                beforePage[0]=beforePage[1];
                beforePage[1]="info";
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(beforePage[0]==beforePage[1])beforePage[0]="home";
                switch (beforePage[0]){
                    case "home":
                        beforePage[0]=beforePage[1];
                        getFragmentManager().beginTransaction().replace(R.id.main_frame,new Homefragment()).commit();
                        backButton.setVisibility(View.INVISIBLE);
                        break;
                    case "classification":
                        beforePage[0]=beforePage[1];
                        getFragmentManager().beginTransaction().replace(R.id.main_frame,new Classificationfragment()).commit();
                        break;
                    case "application":
                        beforePage[0]=beforePage[1];
                        getFragmentManager().beginTransaction().replace(R.id.main_frame,new Applicationfragment()).commit();
                        break;
                    case "info":
                        beforePage[0]=beforePage[1];
                        getFragmentManager().beginTransaction().replace(R.id.main_frame,new Infofragment()).commit();
                        break;
                    default:
                        finish();
                }
                beforePage[0]="home";
                beforePage[1]="home";
            }
        });
    }

    public void SetBtnClick(View v){
        Intent intent=new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void getBookInfoPage(View v){
        Intent intent=new Intent(this, BookInfoActivity.class);
        startActivityForResult(intent,2);
    }

    public void getListPage(View v) {
        Intent intent=new Intent(this, BookListActivity.class);

        switch (v.getId()){
            case R.id.tofButton:
                intent.putExtra("group","tof");
                break;
            case R.id.artButton:
                intent.putExtra("group","art");
                break;
            case R.id.reliButton:
                intent.putExtra("group","reli");
                break;
            case R.id.sosciButton:
                intent.putExtra("group","sosci");
                break;
            case R.id.nasButton:
                intent.putExtra("group","nas");
                break;
            case R.id.philButton:
                intent.putExtra("group","phil");
                break;
            case R.id.dessciButton:
                intent.putExtra("group","dessci");
                break;
            case R.id.langButton:
                intent.putExtra("group","lang");
                break;
            case R.id.litButton:
                intent.putExtra("group","lit");
                break;
            case R.id.hisButton:
                intent.putExtra("group","his");
                break;
        }
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            switch (data.getStringExtra("move")) {
                case "home":
                    homeButton.callOnClick();
                    break;
                case "classification":
                    classificationButton.callOnClick();
                    break;
                case "application":
                    applicationButton.callOnClick();
                    break;
                case "info":
                    infoButton.callOnClick();
                    break;
            }
        }
    }
}
