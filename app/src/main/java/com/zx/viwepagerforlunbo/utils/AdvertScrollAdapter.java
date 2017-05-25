package com.zx.viwepagerforlunbo.utils;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zx.viwepagerforlunbo.utils.Advert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王学浩 on 2015/4/18.
 * <p/>
 * 内容摘要：AdvertScrollAdapter广告轮播图
 * 系统版本：
 * 版权所有：宝润兴业
 * 修改内容：
 * 修改日期：
 */
public class AdvertScrollAdapter extends PagerAdapter {

    private List<View> mAdvertViews;
    private Context mContext;
    private String[] imgUrls;
    private String[] urls;
    private PositionCallBack callBack;
    private String[] id;
    private static DisplayMetrics displayMetrics;

    public AdvertScrollAdapter(Context context, List<View> views, List<Advert> adverts,PositionCallBack callBack) {
        mContext = context;
        this.callBack=callBack;
        imgUrls = new String[adverts.size()];
        urls = new String[adverts.size()];
        id = new String[adverts.size()];
        for (int i = 0; i < adverts.size(); i++) {
            imgUrls[i] = adverts.get(i).url;
            id[i]=adverts.get(i).id;
        }
        mAdvertViews = views;
        //图片两张以上才循环，增加一前一后图片
        if (mAdvertViews.size() > 1) {
            mAdvertViews = new ArrayList<View>();
            ImageView imageView = new ImageView(context);
            LayoutParams params = new LayoutParams(getSceenInfo(context)[0],
                    LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mAdvertViews.add(imageView);

            for (int i = 0; i < views.size(); i++) {
                mAdvertViews.add(views.get(i));
            }
            ImageView imageView2 = new ImageView(context);
            imageView2.setLayoutParams(params);
            imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
            mAdvertViews.add(imageView2);
        } else {
            mAdvertViews = views;
        }
    }

    @Override
    public int getCount() {
        return mAdvertViews.size();
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        ImageView imageView = (ImageView) mAdvertViews.get(position);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        int positionForOthersExceptAdvertView = 0;
        if (mAdvertViews.size() > 1) {
            //第一张图片
            if (0 == position) {
                positionForOthersExceptAdvertView = imgUrls.length - 1;
            }
            //最后一张图片
            else if (position == (mAdvertViews.size() - 1)) {
                positionForOthersExceptAdvertView = 0;
            }
            //中间要显示的图片
            else {
                positionForOthersExceptAdvertView = position - 1;
            }
        } else {
            positionForOthersExceptAdvertView = position;
        }
        final String imgUrl = imgUrls[positionForOthersExceptAdvertView];
        ImageLoader.getInstance().displayImage(imgUrl, imageView);
        view.addView(imageView);
        final int positionForClick = positionForOthersExceptAdvertView;
        callBack.onOperationComplete(positionForClick);
        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.e("=========", "点击了" + id[positionForClick] + "");
                Toast.makeText(mContext,"你点击了" + id[positionForClick] + "",Toast.LENGTH_SHORT).show();
            }
        });
        return mAdvertViews.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View container) {
    }


    /**
     * 获取当前屏幕高度和宽度
     */
    public static int[] getSceenInfo(Context context) {
        int[] screen = new int[2];
        displayMetrics = context.getResources().getDisplayMetrics();
        screen[0] = displayMetrics.widthPixels;
        screen[1] = displayMetrics.heightPixels;
        return screen;
    }
}
