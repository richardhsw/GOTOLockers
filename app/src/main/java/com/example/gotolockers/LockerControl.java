package com.example.gotolockers;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class LockerControl extends AppCompatActivity {

    TextView status;
    TextView messsage;
    ImageView controller;
    boolean locked = true;
    int containerNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locker_control);

        Intent intent = getIntent();
        containerNum = intent.getIntExtra("Container Number", 0);

        TextView number = findViewById(R.id.lockerTV);
        number.setText(getString(R.string.container_num_col, containerNum));

        status = findViewById(R.id.statusTV);
        status.setText(getString(R.string.control_status, "locked"));

        messsage = findViewById(R.id.promptTV);

        controller = findViewById(R.id.lockedIV);
        controller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToggleLocker();
            }
        });
    }

    private void ToggleLocker() {
        RequestLockerInfo task = new RequestLockerInfo();
        String[] params = new String[] {"http://3.14.229.255/test"};
        task.execute(params);

        if (controller.getDrawable().getConstantState() ==
                ContextCompat.getDrawable(this, R.mipmap.ic_unlock_blue2).getConstantState()) {
            locked = false;
            controller.setImageResource(R.mipmap.ic_lock_blue2);
            status.setText(getString(R.string.control_status, "unlocked"));
            messsage.setText(getString(R.string.unlocked_message));
        }
        else {
            locked = true;
            controller.setImageResource(R.mipmap.ic_unlock_blue2);
            status.setText(getString(R.string.control_status, "locked"));
            messsage.setText(getString(R.string.locked_message));
        }
    }
}
