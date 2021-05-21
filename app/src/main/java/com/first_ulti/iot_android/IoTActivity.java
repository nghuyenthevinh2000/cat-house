package com.first_ulti.iot_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class IoTActivity extends AppCompatActivity {

    private DatabaseReference db;
    private Map<String, DataObj> objMap;
    private Map<String, Integer> controlMap;

    private int counter;

    private final int FAN_ID = R.id.fan_button;
    private final int LED_ID = R.id.led_button;
    private final int PUMP_ID = R.id.pump_button;

    private TableLayout table;
    private Button butt_fan;
    private Button butt_led;
    private Button butt_pump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseDatabase.getInstance().getReference();
        objMap = new HashMap<>();
        controlMap = new HashMap<>();
        controlMap.put("fan",0);
        controlMap.put("led",0);
        controlMap.put("pump",0);

        table = (TableLayout) findViewById(R.id.iot_table);
        butt_fan = (Button) findViewById(R.id.fan_button);
        butt_led = (Button) findViewById(R.id.led_button);
        butt_pump = (Button) findViewById(R.id.pump_button);

        counter = -1;

        db.child("data_in").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child_shot: snapshot.getChildren()) {
                    Log.d("Firebase", child_shot.toString());
                    DataObj obj = child_shot.getValue(DataObj.class);
                    if(obj != null){
                        String key = child_shot.getKey();
                        if(objMap.containsKey(key)) {
                            obj.setCounter(objMap.get(key).getCounter());
                            objMap.remove(key);
                            objMap.put(key, obj);
                            replaceTableRow(key);
                        }else{
                            counter++;
                            obj.setCounter(counter);
                            objMap.put(key, obj);
                            populateTableRow(key);
                        }


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(MainActivity.class.getName(), "load data_in failed:onCancelled", error.toException());
            }
        });

        db.child("control_out").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child_shot : snapshot.getChildren()){
                    Integer val = child_shot.getValue(Integer.class);
                    if(val == null)
                        continue;

                    String key = child_shot.getKey();

                    switch(key){
                        case "fan":
                            butt_fan.setText((val == 0) ? getResources().getText(R.string.Fan_Off) : getResources().getText(R.string.Fan_On));
                            break;
                        case "led":
                            butt_led.setText((val == 0) ? getResources().getText(R.string.Led_Off) : getResources().getText(R.string.Led_On));
                            break;
                        case "pump":
                            butt_pump.setText((val == 0) ? getResources().getText(R.string.Pump_Off) : getResources().getText(R.string.Pump_On));
                            break;
                    }

                    controlMap.remove(key);
                    controlMap.put(key, val);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(MainActivity.class.getName(), "load control_out failed:onCancelled", error.toException());
            }
        });
    }

    private void populateTableRow(String key){
        DataObj obj = objMap.get(key);

        int id = obj.getCounter();

        TableRow tr = new TableRow(this);
        tr.setId(id);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

        addTableChild(key, obj, tr);

        table.addView(tr);
    }

    private void replaceTableRow(String key){
        DataObj obj = objMap.get(key);
        int px = convertDPtoPX(3);

        int id = obj.getCounter();

        TableRow tr = (TableRow)findViewById(id);
        tr.removeAllViews();

        addTableChild(key, obj, tr);
    }

    private void addTableChild(String key, DataObj obj, TableRow tr){
        int px = convertDPtoPX(3);
        TableRow.LayoutParams layout = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, (float)4);

        TextView text1 = new TextView(this);
        text1.setText(key);
        text1.setLayoutParams(layout);
        text1.setPadding(px, px, px, px);
        text1.setGravity(Gravity.CENTER);

        layout.weight = (float)2;

        TextView text2 = new TextView(this);
        text2.setText(String.format(Locale.ENGLISH, "%d", obj.getFood()));
        text2.setLayoutParams(layout);
        text2.setPadding(px, px, px, px);
        text2.setGravity(Gravity.CENTER);

        TextView text3 = new TextView(this);
        text3.setText(String.format(Locale.ENGLISH, "%d", obj.getTemperature()));
        text3.setLayoutParams(layout);
        text3.setPadding(px, px, px, px);
        text3.setGravity(Gravity.CENTER);

        TextView text4 = new TextView(this);
        text4.setText(String.format(Locale.ENGLISH, "%d", obj.getWater()));
        text4.setLayoutParams(layout);
        text4.setPadding(px, px, px, px);
        text4.setGravity(Gravity.CENTER);

        tr.addView(text1);
        tr.addView(text2);
        tr.addView(text3);
        tr.addView(text4);
    }

    private int convertDPtoPX(int dp){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;

        return (int) Math.ceil(dp * logicalDensity);
    }

    public void control_handler(View view) {
        int id = view.getId();
        String key = "";
        switch(id){
            case FAN_ID:
                key = "fan";
                break;
            case LED_ID:
                key = "led";
                break;
            case PUMP_ID:
                key = "pump";
                break;
        }

        db.child("control_out").child(key).setValue(1- Objects.requireNonNull(controlMap.get(key)));
    }

}