package com.zx.viwepagerforlunbo.utils;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zx.viwepagerforlunbo.R;


public class PagerIndicator extends LinearLayout {
    private LinearLayout pagerIndicator;
    private int pagerSize;
    private ImageView[] dotImageViews;
    private ViewPager viewPager;
    private boolean isAutoPlay = false;// 自动播放标识
    private int CHANGE_SECONDS = 2 * 1000;// 切换间隔

    public PagerIndicator(Context context) {
        super(context);
        init(context);
    }

    public PagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_pager_indicator, this);
        pagerIndicator = (LinearLayout) view.findViewById(R.id.pagerIndicator);
    }

    public void setPagerSize(int pagerSize) {
        this.pagerSize = pagerSize;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    /**
     * 设置自动轮播每页停留时间
     *
     * @param interval
     */
    public void setChangeInterval(int interval) {
        this.CHANGE_SECONDS = interval;
    }

    public void show(Context context) {
        pagerIndicator.removeAllViews();
        dotImageViews = new ImageView[pagerSize];
        pagerIndicator.removeAllViews();
        for (int i = 0; i < pagerSize; i++) {
            LayoutParams margin = new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            // 设置每个小圆点距离左边的间距
            margin.setMargins(10, 0, 0, 0);
            ImageView dotImageView = new ImageView(context);
            // 设置每个小圆点的宽高
            dotImageView.setLayoutParams(new LayoutParams(15, 15));
            dotImageViews[i] = dotImageView;
            if (i == 0) {
                // 默认选中第一张图片
                dotImageViews[i]
                        .setBackgroundResource(R.mipmap.advert_indicator_focused);
            } else {
                // 其他图片都设置未选中状态
                dotImageViews[i]
                        .setBackgroundResource(R.mipmap.advert_indicator_unfocused);
            }
            pagerIndicator.addView(dotImageViews[i], margin);
        }

        if (viewPager != null)
            viewPager.setOnPageChangeListener(new IndicatorChangeListener());
    }

    // 广告指引页面更改事件监听器
    class IndicatorChangeListener implements OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
            if (isAutoPlay) {
                cancelPlay();
                autoPlay();
            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            changeDotImageBg(position);
        }
    }

    public void changeDotImageBg(int position) {
        for (int i = 0; i < dotImageViews.length; i++) {
            if (position == i) {
                dotImageViews[position]
                        .setBackgroundResource(R.mipmap.advert_indicator_focused);
            } else {
                dotImageViews[i]
                        .setBackgroundResource(R.mipmap.advert_indicator_unfocused);
            }
        }
    }

    private void autoPlay() {
        if (pagerSize != 0) {
            handler.postDelayed(autoChange, CHANGE_SECONDS);
        }
    }

    private void cancelPlay() {
        handler.removeCallbacks(autoChange);
    }

    public void openPlay() {
        isAutoPlay = true;
        autoPlay();
    }

    public void stopPlay() {
        isAutoPlay = false;
        cancelPlay();
    }

    private Handler handler = new Handler();

    private Runnable autoChange = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            int curItem = viewPager.getCurrentItem();
            int count = pagerSize > 1 ? pagerSize + 2 : pagerSize;
            if (curItem < count - 1) {
                ++curItem;
            } else {
                curItem = 0;
            }
            viewPager.setCurrentItem(curItem, true);
        }
    };

}
