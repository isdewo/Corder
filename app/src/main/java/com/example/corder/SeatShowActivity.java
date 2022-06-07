package com.example.corder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Vector;

public class SeatShowActivity extends AppCompatActivity {

    int seatNum = 0;
    int setNum = 0;
    int emptySeats = 0;
    RelativeLayout seatLayout;
    Button editButton;
    Vector<SeatShowView> seats = new Vector<>();
    Vector<SeatShowSet> sets = new Vector<>();
    String adrmString = "";

    SeatShowSet showSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_show);
        Intent intent = getIntent();

        String adrmText = intent.getStringExtra("seatAdrMessage"); // 좌석 배치 위한 메세지 받기

        seatLayout = (RelativeLayout) findViewById(R.id.seatLayout);
        editButton = (Button) findViewById(R.id.editBt);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   startEdit();   }
        });

        if(!adrmText.equals("")) {
            decodeMessage(adrmText);
        }
    }

    private void decodeMessage(String mes){ //메세지를 해독하여 그에 맞는 좌석들을 배치
        String[] perValue = new String[3];
        String[] perSeatInfo = mes.split(" ");
        setNum = perSeatInfo.length;
        int addWidth = 190;
        for(int i = 0; i < setNum; i++){
            perValue = perSeatInfo[i].split("_");
            showSet = new SeatShowSet(getApplicationContext());
            showSet.setX(Float.parseFloat(perValue[0]));
            showSet.setY(Float.parseFloat(perValue[1]));
            int seatN = Integer.parseInt(perValue[2]);

            //레이아웃 세트마다 좌석 수 세기
            seatNum += seatN;

            sets.add(showSet);
            seatLayout.addView(showSet);

            SeatShowView s;

            //Set에 저장된 좌석 정보 저장
            for(int j = 0; j < seatN - 1; j++){
                s = new SeatShowView(getApplicationContext());
                //s에 번호 부여
                s.setId(i*setNum + j + 1);

                showSet.getLayoutParams().width += addWidth;

                //showSet.setLayoutParams(prms);
                showSet.seats++;
                showSet.seatList.add(s);
                showSet.addView(s);

                showSet.addView(new emptyTV(getApplicationContext()));
            }

            /*SeatShowView ssv = new SeatShowView(getApplicationContext());
            ssv.setId(Integer.parseInt(perValue[0]));

            ssv.setX(Float.parseFloat(perValue[1]));
            ssv.setY(Float.parseFloat(perValue[2]));*/

            //add 된 seats들을 화면에다 넣기.
            /*seats.add(ssv);
            seatLayout.addView(ssv);*/
            //Log.i("Tag1", ssv.getId() + ": Now X: " + ssv.getX());
        }
        emptySeats = seatNum;
    }

    private void startEdit(){
        Intent intent = new Intent(this, SeatEditActivity.class);

        for(int i = 0; i < sets.size(); i++){
            /*SeatShowView ssv = seats.get(i);
            adrmString += String.valueOf(ssv.getId()) + '_' + String.valueOf(ssv.getX())
                    +'_' + String.valueOf(ssv.getY()) + ' ';*/
            SeatShowSet s = sets.get(i);
            adrmString += String.valueOf(s.getX()) + '_'
                    + String.valueOf(s.getY()) + '_' + String.valueOf(s.seats) + ' ';
        }

        intent.putExtra("seatAdrMessage2", adrmString);

        startActivity(intent);
        adrmString = "";
        sets.clear();
        seatLayout.removeAllViewsInLayout();
    }

    //번호를 매개변수로 넣으면 그 번호에 해당하는 좌석의 빈 자리인지의 여부가 리턴
    public boolean getSeatIsSet(int i){
        return seats.get(i - 1).isSat;
    }

    public class SeatShowSet extends LinearLayout{

        Vector<SeatShowView> seatList = new Vector<SeatShowView>();
        //Vector<View> viewList = new Vector<View>();

        int seats = 0;

        public SeatShowSet(Context context) {
            super(context);

            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int widthPixels = metrics.widthPixels * 8/10;
            int heightPixels = widthPixels * 191/124;

            LayoutParams params = new LayoutParams(widthPixels,heightPixels);
            params.width = 260;
            params.height = 120;

            setLayoutParams(params);

            emptyTV bt1 = new emptyTV(getApplicationContext());
            addView(bt1);

            SeatShowView seat1 = new SeatShowView(getApplicationContext());
            seatList.add(seat1);
            addView(seat1);
            seats++;

            emptyTV bt2 = new emptyTV(getApplicationContext());
            addView(bt2);
        }
    }

    public class SeatShowView extends androidx.appcompat.widget.AppCompatButton{
        boolean isSat = false;

        public SeatShowView(@NonNull Context context) {
            super(context);

            setText("SEAT");
            setBackgroundResource(R.drawable.rct_emptyrct);
            setTextSize(15);
            setTypeface(null, Typeface.BOLD);
            setTextAlignment(TEXT_ALIGNMENT_CENTER);

            //레이아웃 설정
            LinearLayout.LayoutParams prms =
                    new LinearLayout.LayoutParams(
                            120, 120
                    );


            prms.topMargin = 0;
            setLayoutParams(prms);

            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isSat){
                        isSat = true;
                        setBackgroundResource(R.drawable.rct_greenrct);
                        emptySeats--;
                        //Toast.makeText(getApplicationContext(), "자리 참", Toast.LENGTH_SHORT).show();
                    }

                    else if(isSat){
                        isSat = false;
                        setBackgroundResource(R.drawable.rct_emptyrct);
                        emptySeats++;
                        //Toast.makeText(getApplicationContext(), "자리 빔", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getApplicationContext(), "빈 자리 : " + emptySeats, Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    public class emptyTV extends androidx.appcompat.widget.AppCompatTextView {

        String mes;

        public emptyTV(Context context) {
            super(context);

            RelativeLayout.LayoutParams params
                    = new RelativeLayout.LayoutParams(70, 120);
            setLayoutParams(params);
            //setBackgroundResource(R.drawable.rct_emptyrct);
            setBackgroundColor(Color.argb(0, 0, 0, 0));
        }
    }
}




