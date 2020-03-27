package com.example.uplibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.transition.Transition;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

public class IndicatorActivity extends AppCompatActivity {

    private IndicatorViewPager indicatorViewPager;
    ScrollIndicatorView indicatorView;
    ViewPager viewPager;
    private String[]tits = new String[]{"数学","语言","地理","物理","角度看附件当机立断放得开就","发动机可垃圾袋","就放大看房间的积分大姐夫等了快房价打开"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);
        indicatorView = findViewById(R.id.indicator_view);
        viewPager = findViewById(R.id.view_pager);
        indicatorView.setOnTransitionListener(new OnTransitionTextListener().setColor(0xFFE9573F, Color.BLACK)
        .setSize(SizeUtils.sp2px(5),SizeUtils.sp2px(4)));
        indicatorView.setScrollBar(new ColorBar(this,0xFFE9573F,SizeUtils.dp2px(2)));
        viewPager.setOffscreenPageLimit(2);

        indicatorViewPager = new IndicatorViewPager(indicatorView,viewPager);
        indicatorViewPager.setOnIndicatorItemClickListener(new Indicator.OnIndicatorItemClickListener() {
            @Override
            public boolean onItemClick(View clickItemView, int position) {
                ToastUtils.showLong(position+"");
                return false;
            }
        });
        indicatorViewPager.setOnIndicatorPageChangeListener(new IndicatorViewPager.OnIndicatorPageChangeListener() {
            @Override
            public void onIndicatorPageChange(int preItem, int currentItem) {

                ToastUtils.showLong("curr"+currentItem);
            }
        });
        indicatorViewPager.setCurrentItem(0,true);
        indicatorViewPager.setAdapter(new MyAdapter());
        indicatorViewPager.notifyDataSetChanged();



    }

    private class MyAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter{


        @Override
        public int getCount() {
            return tits.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {

            if (convertView == null){

                convertView = getLayoutInflater().inflate(R.layout.title_layout,container,false);
            }
            TextView textView = (TextView)convertView;

            textView.setText(tits[position]);
            int width = getTextWidth(textView);
            int padding = SizeUtils.dp2px(8);
            textView.setWidth((int) (width*1.3f)+padding);

            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {

            if (convertView == null) {

                convertView = getLayoutInflater().inflate(R.layout.card_layout,container,false);


            }
            TextView textView = convertView.findViewById(R.id.title_tv);
            textView.setText(tits[position]);
            return convertView;
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_UNCHANGED;
        }
    }
    private int getTextWidth(TextView textView) {
        if (textView == null) {
            return 0;
        }
        Rect bounds = new Rect();
        String text = textView.getText().toString();
        Paint paint = textView.getPaint();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int width = bounds.left + bounds.width();
        return width;
    }
}
