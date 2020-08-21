package com.example.gotolockers;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LockerList extends AppCompatActivity {

    private List<Pair<Integer, Integer>> containerInfo = new ArrayList<>();
    private List<Map<String, String>> infoTextList = new ArrayList<>();

    private ListView linearList;
    private SimpleAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locker_list);

        Intent intent = getIntent();
        String locker_name = intent.getStringExtra("Locker Name");

        // Change the title bar message
        setTitle(getString(R.string.locker_num, locker_name));

        linearList = findViewById(R.id.listDetail);

        arrayAdapter = new SimpleAdapter(this, infoTextList, android.R.layout.simple_list_item_2,
            new String[] {"num", "type"}, new int[] {android.R.id.text1, android.R.id.text2}) {

                public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = view.findViewById(android.R.id.text1);
                text1.setTextColor(Color.parseColor("#00b0f0"));
                Typeface typeface = ResourcesCompat.getFont(LockerList.this, R.font.rubik);
                text1.setTypeface(typeface);
                text1.setTextSize(16);

                TextView text2 = view.findViewById(android.R.id.text2);
                text2.setTextColor(Color.parseColor("#66e2ff"));
                text2.setTypeface(typeface);
                text2.setTextSize(14);

                return view;
            }
        };

        linearList.setAdapter(arrayAdapter);

        getContainersInfo();
        populateLayout();

        linearList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int containerNum = containerInfo.get(i).first;

                Intent intent = new Intent(LockerList.this, LockerControl.class);
                intent.putExtra("Container Number", containerNum);

                startActivity(intent);
            }
        });
    }

    private void getContainersInfo() {
        for (int locker_num = 1; locker_num <= 50; ++locker_num) {
            containerInfo.add(new Pair<>(locker_num, 0));
        }

        for (int locker_num = 51; locker_num <= 60; ++locker_num) {
            containerInfo.add(new Pair<>(locker_num, 1));
        }
    }

    private void populateLayout() {
        for (Pair pair : containerInfo) {
            Map<String, String> listItemMap = new HashMap<>();
            listItemMap.put("num", getString(R.string.container_num, (int) pair.first));
            listItemMap.put("type", getResources().getStringArray(R.array.container_type)[(int) pair.second]);

            infoTextList.add(listItemMap);
        }

        Log.d("INFO LIST", infoTextList.toString());

        arrayAdapter.notifyDataSetChanged();
    }
}
