package kr.hs.emirim.yoosieun.librarim;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * Created by Yoosieun on 2017-11-08.
 */


public class BookListActivity extends AppCompatActivity {

    private String img;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getWindow().setWindowAnimations(0); //전환효과 없애기

        ListView bookList=(ListView)findViewById(R.id.book_listview);

        Intent intent=getIntent();

        ImageView groupImg=(ImageView)findViewById(R.id.classification_img);
        img=intent.getStringExtra("group");

        final RelativeLayout backButton=(RelativeLayout)findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                setResult(0,intent);
                finish();
            }
        });

        switch (img){
            case "tof":
                groupImg.setImageResource(R.drawable.group_tof);
                groupImg.invalidate();
                break;
            case "art":
                groupImg.setImageResource(R.drawable.group_art);
                groupImg.invalidate();
                break;
            case "reli":
                groupImg.setImageResource(R.drawable.group_rel);
                groupImg.invalidate();
                break;
            case "sosci":
                groupImg.setImageResource(R.drawable.group_sosci);
                groupImg.invalidate();
                break;
            case "nas":
                groupImg.setImageResource(R.drawable.group_nasci);
                groupImg.invalidate();
                break;
            case "phil":
                groupImg.setImageResource(R.drawable.group_phil);
                groupImg.invalidate();
                break;
            case "dessci":
                groupImg.setImageResource(R.drawable.group_dessci);
                groupImg.invalidate();
                break;
            case "lang":
                groupImg.setImageResource(R.drawable.group_lang);
                groupImg.invalidate();
                break;
            case "lit":
                groupImg.setImageResource(R.drawable.group_lit);
                groupImg.invalidate();
                break;
            case "his":
                groupImg.setImageResource(R.drawable.group_his);
                groupImg.invalidate();
                break;
        }
    }
    public void SetBtClick(View v){
        Intent intent=new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
    public void getBookInfoPage(View v){
        Intent intent=new Intent(this, BookInfoActivity.class);
        startActivityForResult(intent,3);
    }
    public void onMenuBtnClick(View v){
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.home_button:
                intent.putExtra("move","home");
                break;
            case R.id.classification_button:
                intent.putExtra("move","classification");
                break;
            case R.id.application_button:
                intent.putExtra("move","application");
                break;
            case R.id.info_button:
                intent.putExtra("move","info");
                break;
        }
        setResult(1,intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent2=new Intent();
        if(requestCode==3&&resultCode==1){
            intent2.putExtra("move",data.getStringExtra("move"));
            setResult(1,intent2);
            finish();
        }else {
            setResult(0,intent2);
            finish();
        }
    }
}