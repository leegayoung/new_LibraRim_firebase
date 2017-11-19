package kr.hs.emirim.yoosieun.librarim;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Yoosieun on 2017-11-08.
 */


public class BookInfoActivity extends AppCompatActivity {

    static boolean calledAlready = false;
    ImageView imView;
    String imgUrl = "http://dnllab.incheon.ac.kr/appimg/";
    Bitmap bmImg;
    back task;


    String[] words = {"총류","철학","종교","사회과학","자연과학","기술과학","예술","언어","문학","역사"};



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        if (!calledAlready) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true); // 다른 인스턴스보다 먼저 실행되어야 한다.
            calledAlready = true;
        }
        getWindow().setWindowAnimations(0); //전환효과 없애기

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("newbook");
        Toast.makeText(getApplicationContext(), "hello~", Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();
        //String book = intent.getStringExtra("bookName");
        //String type = intent.getStringExtra("type");
        final String book = "새는 왜 날개를 가지고 있을까?.48";
        String type = "40";
        final TextView titleOfBook = (TextView)findViewById(R.id.titleOfBook);
        final TextView writerOfBook = (TextView)findViewById(R.id.writerOfBook);
        final TextView publisherOfBook = (TextView)findViewById(R.id.publisherOfBook);
        final TextView groupOfBook = (TextView)findViewById(R.id.groupOfBook);
        final TextView locationOfBook = (TextView)findViewById(R.id.locationOfBook);
        final TextView statusOfBook = (TextView)findViewById(R.id.statusOfBook);

        if(book.equals(null))
        {
            Toast.makeText(getApplicationContext(), "책이름이 반환되지 않았습니다.", Toast.LENGTH_SHORT).show();
            this.finish();
        } else if (type.equals(null)) {
            Toast.makeText(getApplicationContext(), "타입이 불분명합니다.", Toast.LENGTH_SHORT).show();
            this.finish();
        }


        if(type.equals("100"))
        {
            for(int i =0; i<10; i++)
            {
                type = ""+i+"0";
               // Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();
                mDatabase =FirebaseDatabase.getInstance().getReference("bookInfo/"+type);
                final String finalType = type;
                final DatabaseReference finalMDatabase = mDatabase;
                new Thread(new Runnable() { @Override public void run() { // TODO Auto-generated method stub
                    finalMDatabase.orderByChild("title").equalTo(book).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Toast.makeText(getApplicationContext(), "여기들어옴시", Toast.LENGTH_SHORT).show();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String img = snapshot.child("img").getValue(String.class);
                                String location = snapshot.child("location").getValue(String.class);
                                String pub = snapshot.child("pub").getValue(String.class);
                                String status = snapshot.child("status").getValue(String.class);
                                String title = snapshot.child("title").getValue(String.class);
                                String group = snapshot.child("group").getValue(String.class);
                                String wrtier;
                                Toast.makeText(getApplicationContext(), img, Toast.LENGTH_SHORT).show();

                                if(finalType.equals("00") || finalType.equals("40")||finalType.equals("80"))
                                {
                                    wrtier = snapshot.child("witer").getValue(String.class);
                                }
                                else
                                {
                                    wrtier = snapshot.child("writer").getValue(String.class);
                                }
                                //Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                                titleOfBook.setText(title);
                                writerOfBook.setText(wrtier);
                                publisherOfBook.setText(pub);
                                groupOfBook.setText(words[group.charAt(0)]);
                                locationOfBook.setText(location);
                                statusOfBook.setText(status);

                                task = new back();
                                imView = (ImageView) findViewById(R.id.bookImg);

                                task.execute(img);
                                break;
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } }).start();


            }
        }
        else if(type.equals("bestbook") || type.equals("newbook"))
        {
            Toast.makeText(getApplicationContext(), "여기들어옴~", Toast.LENGTH_SHORT).show();
            final String finalType = type;
            mDatabase =FirebaseDatabase.getInstance().getReference(type);
            Toast.makeText(getApplicationContext(), book, Toast.LENGTH_SHORT).show();
            mDatabase.orderByChild("title").equalTo(book).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String img = snapshot.child("img").getValue(String.class);
                        String location = snapshot.child("location").getValue(String.class);
                        String pub = snapshot.child("pub").getValue(String.class);
                        String status = snapshot.child("status").getValue(String.class);
                        String title = snapshot.child("title").getValue(String.class);
                        String group = null;
                        if(finalType.equals("bestbook"))
                        {
                            group = "인기도서";
                        }
                        else
                        {
                            group = "신간도서";
                        }
                        String wrtier;
                        Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();

                            wrtier = snapshot.child("witer").getValue(String.class);


                        //Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                        titleOfBook.setText(title);
                        writerOfBook.setText(wrtier);
                        publisherOfBook.setText(pub);
                        groupOfBook.setText(group);
                        locationOfBook.setText(location);
                        statusOfBook.setText(status);
                        ImageView imgView = (ImageView)findViewById(R.id.bookImg);

                        task = new back();
                        imView = (ImageView) findViewById(R.id.bookImg);

                        task.execute(img);
                        break;
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "여기들어옴~", Toast.LENGTH_SHORT).show();
            final String finalType = type;
            mDatabase =FirebaseDatabase.getInstance().getReference("bookInfo/"+type);
            Toast.makeText(getApplicationContext(), book, Toast.LENGTH_SHORT).show();
            mDatabase.orderByChild("title").equalTo(book).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String img = snapshot.child("img").getValue(String.class);
                        String location = snapshot.child("location").getValue(String.class);
                        String pub = snapshot.child("pub").getValue(String.class);
                        String status = snapshot.child("status").getValue(String.class);
                        String title = snapshot.child("title").getValue(String.class);
                        String group = snapshot.child("group").getValue(String.class);
                        String wrtier;
                        Toast.makeText(getApplicationContext(), group, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), words[(Integer.parseInt(group)/10)], Toast.LENGTH_SHORT).show();

                        if(finalType.equals("00") || finalType.equals("40")||finalType.equals("80"))
                        {
                            wrtier = snapshot.child("witer").getValue(String.class);
                        }
                        else
                        {
                            wrtier = snapshot.child("writer").getValue(String.class);
                        }
                        //Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                        titleOfBook.setText(title);
                        writerOfBook.setText(wrtier);
                        publisherOfBook.setText(pub);
                        groupOfBook.setText(words[(Integer.parseInt(group)/10)]);
                        locationOfBook.setText(location);
                        statusOfBook.setText(status);
                        ImageView imgView = (ImageView)findViewById(R.id.bookImg);

                        task = new back();
                        imView = (ImageView) findViewById(R.id.bookImg);

                        task.execute(img);
                        break;
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }



        mDatabase.orderByChild(type).equalTo(book).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String str = snapshot.child("pub").getValue(String.class);
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        final RelativeLayout backButton=(RelativeLayout)findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                setResult(0,intent);
                finish();
            }
        });
    }

    public void SetButtClick(View v){
        Intent intent=new Intent(this, SettingsActivity.class);
        startActivity(intent);
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

    private class back extends AsyncTask<String, Integer,Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            // TODO Auto-generated method stub
            try{
                URL myFileUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();

                bmImg = BitmapFactory.decodeStream(is);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmImg;
        }

        protected void onPostExecute(Bitmap img){
            imView.setImageBitmap(bmImg);
        }

    }



}