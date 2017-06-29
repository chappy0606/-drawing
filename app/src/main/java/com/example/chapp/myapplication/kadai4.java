package com.example.chapp.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.Activity;
import android.widget.AdapterView.*;

public class kadai4 extends Activity {
    SampleView sv;
    Spinner sp;
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        setContentView(ll);

        TextView tv = new TextView(this);
        Button bt = new Button(this);
        bt.setText("削除");

        sp = new Spinner(this);
        sv = new SampleView(this);

        String[] str = {"直線", "矩形", "円"};
        ArrayAdapter<String> ad = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, str);
        ad.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        sp.setAdapter(ad);

        ll.addView(tv);
        ll.addView(bt);
        ll.addView(sp);
        ll.addView(sv);

        sp.setOnItemSelectedListener(new SampleItemSelectedListener());
        bt.setOnClickListener((View.OnClickListener) new SampleOnClickListener());
    }

    class SampleItemSelectedListener implements OnItemSelectedListener {


        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Spinner spinner = (Spinner) parent;
            sv.setCategory((String)spinner.getSelectedItem());
        }

        public void onNothingSelected(AdapterView<?> parent) {}
    }

    class  SampleView extends View {
        float x1, y1, x2, y2;
        int touch_count = 0;
        Paint p = new Paint();
        private String Category = "直線";
        boolean clearflg = false;

        public void setCategory(String category)
        {
            Category = category;
        }

        public void Clear()
        {
            touch_count = 0;
            clearflg = true;
            this.invalidate();
        }

        public SampleView(Context context) {
            super(context);
        }

        public boolean onTouchEvent(MotionEvent event) {
            if (touch_count == 0) {
                x1 = event.getX();
                y1 = event.getY();
                touch_count += 1;
                return false;
            } else if (touch_count == 1) {
                x2 = event.getX();
                y2 = event.getY();
                touch_count += 1;
                this.invalidate();
            } else {
                return true;
            }
            return true;
        }

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            p.setColor(Color.RED);
            p.setStyle(Paint.Style.FILL);
            p.setStrokeWidth(3);
            if (touch_count == 0 && clearflg == true)
            {
                canvas.drawColor(Color.TRANSPARENT);
                return;
            }

            switch (Category)
            {
                case "直線":
                    canvas.drawLine(x1, y1, x2, y2, p);
                    break;

                case "矩形":
                    canvas.drawRect(x1, y1, x2, y2, p);
                    break;

                case "円":
                    float rad = (float)Math.sqrt(Math.pow((double)x1 - x2, 2) + Math.pow((double)y1 - y2, 2)) / 2;
                    canvas.drawCircle(x2, y2, rad, p);
            }
        }
    }

    private class SampleOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            sv.Clear();
        }
    }
}












