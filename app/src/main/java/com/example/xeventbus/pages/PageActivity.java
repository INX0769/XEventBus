package com.example.xeventbus.pages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.xeventbus.R;
import com.example.xeventbus.XEventBus;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author INX
 * @date 2021/9/13
 */
public class PageActivity extends AppCompatActivity {

    private TextView tv_desc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        XEventBus.bind(this);
//        XEventBus.bindByResume(this);
        tv_desc = findViewById(R.id.tv_desc);
        findViewById(R.id.btnEvent).setOnClickListener(v -> {
            XEventBus.post(new PageEvent("PageEvent：" + System.currentTimeMillis()));
        });
        findViewById(R.id.btnNext).setOnClickListener(v -> {
            startActivity(new Intent(this, PageActivity.class));
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PageEvent event) {
        Log.d("PageActivity", this+"::"+event.msg);
    }

    public class PageEvent {
        String msg;

        public PageEvent(String msg) {
            this.msg = msg;
        }

        @Override
        public String toString() {
            return "PageEvent{" +
                    "msg='" + msg + '\'' +
                    '}';
        }
    }
}
