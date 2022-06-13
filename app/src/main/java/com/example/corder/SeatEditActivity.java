package com.example.corder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.corder.R;
import com.example.corder.SeatShowActivity;

import java.util.Vector;

public class SeatEditActivity extends AppCompatActivity /*implements View.OnTouchListener*/{

    //TextView textView;
    RelativeLayout seatLayout;
    //Button createSeat;
    Button saveButton;

    Button createNewOneSeat;

    Button initiateButton;

    int seatNum = 0;
    int setNum = 0;
    int sat = 0;
    Vector<SeatTextView> stvs = new Vector<SeatTextView>();

    Vector<SeatSet> seatSets = new Vector<SeatSet>();

    String adrmString = ""; // 저장될 실제 사용 좌석 액티비티에 전달될
    // 메세지

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_edit);

        Intent intent = getIntent();

        String adrmText = intent.getStringExtra("seatAdrMessage2");
        /*textView = (TextView)findViewById(R.id.seat1);
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });*/

        seatLayout = (RelativeLayout) findViewById(R.id.seatLayout);

        if(adrmText != null) {
            if (!adrmText.equals("")) {
                decodeMessage(adrmText);
            }
        }

        // 좌석 추가 버튼에 리스너 넣기
        /*createSeat = findViewById(R.id.createNewS);
        createSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewSeat();
            }
        });*/

        createNewOneSeat = findViewById(R.id.createSeatSet);
        createNewOneSeat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { createNewSeatSet(); }
        });

        initiateButton = findViewById(R.id.initiateButton);
        initiateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seatLayout.removeAllViewsInLayout();
                stvs.clear();
                seatSets.clear();
            }
        });
        //좌석 저장 버튼에 리스너 넣기
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePlace();
            }
        });
    }

    private void decodeMessage(String mes){ //메세지를 해독하여 그에 맞는 좌석들을 배치
        String[] perValue = new String[4];
        String[] perSetInfo = mes.split(" ");
        setNum = perSetInfo.length;

        SeatSet sts;
        SeatTextView s;
        int addWidth = 190;

        for(int i = 0; i < setNum; i++)
        {
            perValue = perSetInfo[i].split("_");
            sts = new SeatSet(getApplicationContext());
            sts.setX(Float.parseFloat(perValue[0]));
            sts.setY(Float.parseFloat(perValue[1]));
            int seatN = Integer.parseInt(perValue[2]);
            String satSeats = perValue[3];

            String[] perSeat = satSeats.split("/");

            seatNum += seatN;

            //seatSets.add(sts);
            seatLayout.addView(sts);


            for(int j = 0; j < seatN - 1; j++)
            {
                s = new SeatTextView(getApplicationContext());
                sts.getLayoutParams().width += addWidth;

                sts.seatList.add(s);
                sts.addView(s);

                sts.addView(new addSideSeatBt(getApplicationContext(), sts, "right"));
            }

            for(int j = 0; j< perSeat.length; j++)
            {
                if(perSeat[j].equals(""))
                {
                    continue;
                }
                SeatTextView sv = sts.seatList.get(Integer.parseInt(perSeat[j]));
                sv.isSat = true;
                sv.setBackgroundResource(R.drawable.rct_greenrct);
                sat++;
            }
        }
    }
    float oldXvalue;
    float oldYvalue;

    private void createNewSeat(){
        ++seatNum;

        SeatTextView newTvSeat = new SeatTextView(getApplicationContext());
        //stvs.add(newTvSeat);
        seatLayout.addView(newTvSeat);
    }

    private void createNewSeatSet(){
        SeatSet newSet = new SeatSet(getApplicationContext());
        seatLayout.addView(newSet);
    }

    private void savePlace() {
        Intent intent = new Intent(this, SeatShowActivity.class);

        SeatSet sts;
        SeatTextView stv;
        for(int i = 0; i < seatSets.size(); i++)
        {
            sts = seatSets.get(i);
            float getX = sts.getX();
            float getY = sts.getY();
            int seatNum = sts.seatList.size();

            adrmString += String.valueOf(getX) + '_' + String.valueOf(getY)
                    + '_' + String.valueOf(seatNum) + '_' + '/';
            for(int j = 0; j < sts.seatList.size(); j++)
            {
                if(sts.seatList.get(j).isSat){
                    adrmString+= String.valueOf(j) + '/';
                }
            }

            adrmString += ' ';
        }

        intent.putExtra("seatAdrMessage", adrmString);



        startActivity(intent);
        //메시지 초기화
        adrmString = "";
        stvs.clear();
        seatLayout.removeAllViewsInLayout();
        seatSets.clear();
        setNum = 0;
        sat = 0;
        //finish();
    }

    public class SeatSet extends LinearLayout{

        Vector<SeatTextView> seatList = new Vector<SeatTextView>();
        Vector<View> viewList = new Vector<View>();



        public SeatSet(Context context) {
            super(context);

            seatSets.add(this);

            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int widthPixels = metrics.widthPixels * 8/10;
            int heightPixels = widthPixels * 191/124;

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthPixels,heightPixels);
            params.width = 260;
            params.height = 120;

            setLayoutParams(params);
            setBackgroundResource(R.drawable.rct_emptyrct);

            addSideSeatBt bt1 = new addSideSeatBt(getApplicationContext(), this, "left");
            viewList.add(bt1);
            addView(bt1);

            SeatTextView seat1 = new SeatTextView(getApplicationContext());
            viewList.add(seat1);
            seatList.add(seat1);
            addView(seat1);

            addSideSeatBt bt2 = new addSideSeatBt(getApplicationContext(), this, "right");
            viewList.add(bt2);
            addView(bt2);

            //setBackgroundResource(R.drawable.rnd_rct_rndskbl);
            setOnTouchListener(new View.OnTouchListener(){
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return touchDrag(v, event);
                }
            });

            /*if(!isSat){
                setBackgroundResource(R.drawable.rct_greenrct);
            }
            else{
                setBackgroundResource(R.drawable.rct_emptyrct);
            }*/

        }
    }

    public class addSideSeatBt extends androidx.appcompat.widget.AppCompatButton{

        SeatSet ssl;
        String mes;
        int addWidth  = 190;
        public addSideSeatBt(Context context, SeatSet seatSet, String message) {
            super(context);

            ssl = seatSet;
            mes  = message;

            RelativeLayout.LayoutParams params
                    = new RelativeLayout.LayoutParams(70, 120);
            setLayoutParams(params);
            //setBackgroundResource(R.drawable.rct_emptyrct);
            setBackgroundColor(Color.WHITE);
            setText("+");
            setTextAlignment(TEXT_ALIGNMENT_CENTER);


            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mes.equals("left")){

                    }
                    if(mes.equals("right")){
                        int maxX = ((ViewGroup)ssl.getParent()).getWidth() - ssl.getWidth();
                        //만약 버튼 클릭 시 화면을 넘길 것이면 하지 않는다.
                        if(ssl.getX() > maxX - addWidth)
                            return;

                        RelativeLayout.LayoutParams prms =
                                new RelativeLayout.LayoutParams(
                                        ssl.getWidth() + addWidth, ssl.getHeight()
                                );
                        ssl.setLayoutParams(prms);
                        SeatTextView s = new SeatTextView(getApplicationContext());
                        ssl.seatList.add(s);
                        ssl.viewList.add(s);
                        ssl.addView(s);

                        addSideSeatBt a = new addSideSeatBt(getApplicationContext(), ssl, "right");
                        ssl.viewList.add(a);
                        ssl.addView(new addSideSeatBt(getApplicationContext(), ssl, "right"));
                        mes = "NONE";
                    }
                }
            });

        }

    }

    public class SeatTextView extends androidx.appcompat.widget.AppCompatTextView{

        boolean isSat = false;

        public SeatTextView(Context context) {
            super(context);
            setText("SEAT");
            setTextSize(15);
            stvs.add(this);

            setTypeface(null, Typeface.BOLD);
            setTextAlignment(TEXT_ALIGNMENT_CENTER);
            setId(seatNum);
            setBackgroundResource(R.drawable.rct_emptyrct);
            /*setTextColor(0xFFFFFF);*/

            int wd = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    getResources().getDimension(R.dimen.seatWidth),
                    getResources().getDisplayMetrics()
            );
            int hgt = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    getResources().getDimension(R.dimen.seatHeight),
                    getResources().getDisplayMetrics()
            );

            //레이아웃 설정
            LinearLayout.LayoutParams prms =
                    new LinearLayout.LayoutParams(
                            120, 120
                    );
            prms.topMargin = 0;
            setLayoutParams(prms);

        }
    }

    private boolean touchDrag(View v, MotionEvent event) {
        int width = ((ViewGroup) v.getParent()).getWidth() - v.getWidth();
        int height = ((ViewGroup) v.getParent()).getHeight() - v.getHeight();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.i("Tag1", v.getId() + ": Now X: " + v.getX());
            oldXvalue = event.getX();
            oldYvalue = event.getY();
            //  Log.i("Tag1", "Action Down X" + event.getX() + "," + event.getY());
            Log.i("Tag1", "Action Down rX " + event.getRawX() + "," + event.getRawY());
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            v.setX(event.getRawX() - oldXvalue);
            v.setY(v.getY() + event.getY()- v.getHeight()/2);
            //v.setY(event.getRawY() - (oldYvalue + v.getHeight() * 3));
            //  Log.i("Tag2", "Action Down " + me.getRawX() + "," + me.getRawY());
        } else if (event.getAction() == MotionEvent.ACTION_UP) {

            if (v.getX() > width && v.getY() > height) {
                v.setX(width);
                v.setY(height);
            } else if (v.getX() < 0 && v.getY() > height) {
                v.setX(0);
                v.setY(height);
            } else if (v.getX() > width && v.getY() < 0) {
                v.setX(width);
                v.setY(0);
            } else if (v.getX() < 0 && v.getY() < 0) {
                v.setX(0);
                v.setY(0);
            } else if (v.getX() < 0 || v.getX() > width) {
                if (v.getX() < 0) {
                    v.setX(0);
                    v.setY(event.getRawY() - oldYvalue - v.getHeight()*5/2);
                } else {
                    v.setX(width);
                    v.setY(event.getRawY() - oldYvalue - v.getHeight()* 5/2);
                }
            } else if (v.getY() < 0 || v.getY() > height) {
                if (v.getY() < 0) {
                    v.setX(event.getRawX() - oldXvalue);
                    v.setY(0);
                } else {
                    v.setX(event.getRawX() - oldXvalue);
                    v.setY(height);
                }
            }


        }
        return true;
    }

    private void addSideSeat(View view){

    }
}
