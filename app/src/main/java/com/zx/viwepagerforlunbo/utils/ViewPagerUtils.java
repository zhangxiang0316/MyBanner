package com.zx.viwepagerforlunbo.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zx on 2016/5/12.11:17
 * 项目名称：ListLikeIOST
 * 类描述：
 * 备注：
 */
public class ViewPagerUtils {
    private int size;//轮播图数
    private List<View> advertViews;
    private PositionCallBack callBack;
    private Context context;
    private PagerIndicator pagerIndicator;
    private ViewPager advertViewPager;
    private List<Advert> pagerAdvertsList;

    public ViewPagerUtils(List<Advert> pagerAdvertsList, Context context, final ViewPager advertViewPager, final PagerIndicator pagerIndicator,PositionCallBack callBack) {
        this.pagerAdvertsList = pagerAdvertsList;
        this.context = context;
        this.pagerIndicator = pagerIndicator;
        this.advertViewPager = advertViewPager;
        this.callBack=callBack;
    }

    /**
     * 加载轮播图
     */
    public void setAdvers() {
        List<Advert> pagerAdvertsListData = new ArrayList<>();
        if (pagerAdvertsList.size() > 4) {
            for (int i = 0; i < pagerAdvertsList.size(); i++) {
                if (i < 8) {
                    pagerAdvertsListData.add(pagerAdvertsList.get(i));
                }
            }
        } else {
            for (int i = 0; i < pagerAdvertsList.size(); i++) {
                pagerAdvertsListData.add(pagerAdvertsList.get(i));
            }
        }
        size = pagerAdvertsListData.size();
        generateAdvertView(size);
        AdvertScrollAdapter adapter = new AdvertScrollAdapter(context, advertViews, pagerAdvertsListData,callBack);
        advertViewPager.setPageTransformer(true, new DepthPageTransformer());
        advertViewPager.setAdapter(adapter);
        if (size > 1) {
            advertViewPager.setCurrentItem(1, false);
            pagerIndicator.changeDotImageBg(0);
        }
        //设置第一张图左滑至最后一张，同理最后一张右滑至第一张
        advertViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {
                pagerIndicator.stopPlay();
                pagerIndicator.openPlay();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                //滑动过程中

            }

            @Override
            public void onPageSelected(int position) {
                if (size > 1) {
                    if (position < 1) {
                        position = size; // 切换到最后一张图片
                        advertViewPager.setCurrentItem(position, false);
                    } else if (position > size) {
                        advertViewPager.setCurrentItem(1, false); // false:不显示跳转过程的动画
                        position = 1;
                    } else {
                        int pos = position - 1;
                        pagerIndicator.changeDotImageBg(pos);
                    }
                } else {
                    pagerIndicator.changeDotImageBg(position);
                }
            }
        });
    }

    public void generateAdvertView(int size) {
        advertViews = new ArrayList<View>();
        pagerIndicator.setPagerSize(size);
        pagerIndicator.setViewPager(advertViewPager);
        pagerIndicator.show(context);
        pagerIndicator.setChangeInterval(3000);
        pagerIndicator.openPlay();
        for (int i = 0; i < size; i++) {
            ImageView advertImageView = new ImageView(context);
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width / 2);
            advertImageView.setLayoutParams(params);
            advertImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            // 正在加载时,选择的图片
            advertViews.add(advertImageView);
        }
    }


    //设置广告轮播图的宽度和高度

    public void setAdvertSize(View view) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = width / 2;
        view.setLayoutParams(params);
    }
}