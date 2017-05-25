package com.zx.viwepagerforlunbo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.viwepagerforlunbo.utils.Advert;
import com.zx.viwepagerforlunbo.utils.PagerIndicator;
import com.zx.viwepagerforlunbo.utils.PositionCallBack;
import com.zx.viwepagerforlunbo.utils.ViewPagerUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager advertViewPager;
    private PagerIndicator pagerIndicator;
    private List<Advert> list;
    private LinearLayout layout_advert;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        advertViewPager = (ViewPager) findViewById(R.id.advertViewPager);
        pagerIndicator = (PagerIndicator) findViewById(R.id.pagerIndicator);
        layout_advert = (LinearLayout) findViewById(R.id.layout_advert);
        tv = (TextView) findViewById(R.id.tv);
        setData();
        ViewPagerUtils viewPagerUtils = new ViewPagerUtils(list, this, advertViewPager, pagerIndicator, new PositionCallBack() {
            @Override
            public void onOperationComplete(int position) {
                if (position==0){
                    tv.setText("这是第"+(list.size()-1)+"个");
                }else {
                    tv.setText("这是第"+(position-1)+"个");
                }
            }
        });
        viewPagerUtils.setAdvers();
        viewPagerUtils.setAdvertSize(layout_advert);
    }

    private void setData() {
        list = new ArrayList<>();
        Advert advert = new Advert();
        advert.url = "http://img14.poco.cn/mypoco/myphoto/20130131/22/17323571520130131221457027_640.jpg";
        advert.id = 1 + "";
        list.add(advert);

        Advert advert1 = new Advert();
        advert1.url = "http://p3.so.qhimg.com/dmfd/656_400_/t01a3f18bae851b08c8.jpg";
        advert1.id = 2 + "";
        list.add(advert1);

        Advert advert2 = new Advert();
        advert2.url = "http://p9.qhimg.com/dmfd/337_400_/t01523856aac103f27f.jpg";
        advert2.id = 3 + "";
        list.add(advert2);

        Advert advert3 = new Advert();
        advert3.url = "http://p4.so.qhimg.com/dmfd/490_350_/t01dde722464db4295e.jpg";
        advert3.id = 4 + "";
        list.add(advert3);


        Advert advert4 = new Advert();
        advert4.url = "http://p1.so.qhimg.com/dmfd/490_350_/t012190591a5467b16b.jpg";
        advert4.id = 5 + "";
        list.add(advert4);

    }
}
