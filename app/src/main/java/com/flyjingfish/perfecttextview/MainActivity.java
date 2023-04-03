package com.flyjingfish.perfecttextview;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.flyjingfish.perfecttextview.databinding.ActivityMainBinding;
import com.flyjingfish.perfecttextviewlib.PerfectTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.hollowTextView.setOnClickListener(v -> {
            Toast.makeText(this,"setOnClickListener",Toast.LENGTH_SHORT).show();
            Log.e("onClickTouch", "单击 setOnClickListener");
        }, PerfectTextView.ClickScope.textScope);
        binding.hollowTextView.setOnLongClickListener(v -> {
            Toast.makeText(this,"setOnLongClickListener",Toast.LENGTH_SHORT).show();
            Log.e("onClickTouch", "单击 setOnLongClickListener");
            return false;
        });
        binding.hollowTextView.setOnDoubleClickListener(v -> {
            Toast.makeText(this,"setOnDoubleClickListener",Toast.LENGTH_SHORT).show();
            Log.e("onClickTouch", "单击 setOnDoubleClickListener");
        });

        binding.hollowTextView.setOnDrawableLeftClickListener(v -> {
            Toast.makeText(this,"setOnDrawableLeftClickListener",Toast.LENGTH_SHORT).show();
            Log.e("onClickTouch", "单击 setOnDrawableLeftClickListener");
            binding.hollowTextView.setDrawableLeftSelected(true);
        });
        binding.hollowTextView.setOnDrawableTopClickListener(v -> {
            Toast.makeText(this,"setOnDrawableTopClickListener",Toast.LENGTH_SHORT).show();
            Log.e("onClickTouch", "单击 setOnDrawableTopClickListener");
        });
        binding.hollowTextView.setOnDrawableRightClickListener(v -> {
            Toast.makeText(this,"setOnDrawableRightClickListener",Toast.LENGTH_SHORT).show();
            Log.e("onClickTouch", "单击 setOnDrawableRightClickListener");
            binding.hollowTextView.setDrawableRightSelected(true);
        });
        binding.hollowTextView.setOnDrawableBottomClickListener(v -> {
            Toast.makeText(this,"setOnDrawableBottomClickListener",Toast.LENGTH_SHORT).show();
            Log.e("onClickTouch", "单击 setOnDrawableBottomClickListener");
        });
        binding.hollowTextView.setOnDrawableStartClickListener(v -> {
            Toast.makeText(this,"setOnDrawableStartClickListener",Toast.LENGTH_SHORT).show();
            Log.e("onClickTouch", "单击 setOnDrawableStartClickListener");
            binding.hollowTextView.setDrawableStartSelected(true);

        });
        binding.hollowTextView.setOnDrawableEndClickListener(v -> {
            Toast.makeText(this,"setOnDrawableEndClickListener",Toast.LENGTH_SHORT).show();
            Log.e("onClickTouch", "单击 setOnDrawableEndClickListener");
            binding.hollowTextView.setDrawableEndSelected(true);
        });



        binding.hollowTextView.setOnDrawableLeftLongClickListener(v -> {
            Toast.makeText(this,"setOnDrawableLeftLongClickListener",Toast.LENGTH_SHORT).show();
            Log.e("onClickTouch", "单击 setOnDrawableLeftLongClickListener");

            return false;
        });
        binding.hollowTextView.setOnDrawableTopLongClickListener(v -> {
            Toast.makeText(this,"setOnDrawableTopLongClickListener",Toast.LENGTH_SHORT).show();
            Log.e("onClickTouch", "单击 setOnDrawableTopLongClickListener");
            return false;
        });
        binding.hollowTextView.setOnDrawableRightLongClickListener(v -> {
            Toast.makeText(this,"setOnDrawableRightLongClickListener",Toast.LENGTH_SHORT).show();
            Log.e("onClickTouch", "单击 setOnDrawableRightLongClickListener");
            return false;
        });
        binding.hollowTextView.setOnDrawableBottomLongClickListener(v -> {
            Toast.makeText(this,"setOnDrawableBottomLongClickListener",Toast.LENGTH_SHORT).show();
            Log.e("onClickTouch", "单击 setOnDrawableBottomLongClickListener");
            return false;
        });
        binding.hollowTextView.setOnDrawableStartLongClickListener(v -> {
            Toast.makeText(this,"setOnDrawableStartLongClickListener",Toast.LENGTH_SHORT).show();
            Log.e("onClickTouch", "单击 setOnDrawableStartLongClickListener");
            return false;
        });
        binding.hollowTextView.setOnDrawableEndLongClickListener(v -> {
            Toast.makeText(this,"setOnDrawableEndLongClickListener",Toast.LENGTH_SHORT).show();
            Log.e("onClickTouch", "单击 setOnDrawableEndLongClickListener");
            return false;
        });



        binding.hollowTextView.setOnDrawableLeftDoubleClickListener(v -> {
            Toast.makeText(this,"setOnDrawableLeftDoubleClickListener",Toast.LENGTH_SHORT).show();
            Log.e("onClickTouch", "单击 setOnDrawableLeftDoubleClickListener");
            binding.hollowTextView.setDrawableLeftSelected(false);
        });
        binding.hollowTextView.setOnDrawableTopDoubleClickListener(v -> {
            Toast.makeText(this,"setOnDrawableTopDoubleClickListener",Toast.LENGTH_SHORT).show();
            Log.e("onClickTouch", "单击 setOnDrawableTopDoubleClickListener");
        });
        binding.hollowTextView.setOnDrawableRightDoubleClickListener(v -> {
            Toast.makeText(this,"setOnDrawableRightDoubleClickListener",Toast.LENGTH_SHORT).show();
            Log.e("onClickTouch", "单击 setOnDrawableRightDoubleClickListener");
            binding.hollowTextView.setDrawableRightSelected(false);
        });
        binding.hollowTextView.setOnDrawableBottomDoubleClickListener(v -> {
            Toast.makeText(this,"setOnDrawableBottomDoubleClickListener",Toast.LENGTH_SHORT).show();
            Log.e("onClickTouch", "单击 setOnDrawableBottomDoubleClickListener");
        });
        binding.hollowTextView.setOnDrawableStartDoubleClickListener(v -> {
            Toast.makeText(this,"setOnDrawableStartDoubleClickListener",Toast.LENGTH_SHORT).show();
            Log.e("onClickTouch", "单击 setOnDrawableStartDoubleClickListener");
            binding.hollowTextView.setDrawableStartSelected(false);
        });
        binding.hollowTextView.setOnDrawableEndDoubleClickListener(v -> {
            Toast.makeText(this,"setOnDrawableEndDoubleClickListener",Toast.LENGTH_SHORT).show();
            Log.e("onClickTouch", "单击 setOnDrawableEndDoubleClickListener");
            binding.hollowTextView.setDrawableEndSelected(false);
        });


        binding.button1.setOnClickListener(v -> {
//            binding.hollowTextView.setDrawableStartWidthHeight(100,100);
            binding.hollowTextView.setSelected(!binding.hollowTextView.isSelected());
            binding.hollowTextView3.setSelected(!binding.hollowTextView3.isSelected());
        });
        binding.button2.setOnClickListener(v -> {
//            binding.hollowTextView.setDrawableStartPadding(100);
//            binding.hollowTextView.setCompoundDrawablePadding(100);
//            binding.hollowTextView.setText("333");
//            binding.hollowTextView.setDefaultText("111");
            binding.hollowTextView.setSelectedText("222");
            binding.hollowTextView.setTextBackgroundScope(PerfectTextView.TextBackgroundScope.fitDrawablePadding);
        });

        binding.hollowTextView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.hollowTextView.setDrawableLeft(null);
            }
        });
    }

    public int getScreenHeight() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getRealSize(point);
        return point.y;
    }
}