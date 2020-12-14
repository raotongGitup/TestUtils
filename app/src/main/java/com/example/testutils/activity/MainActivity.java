package com.example.testutils.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testutils.R;
import com.example.testutils.base.banner.BannerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BannerView banner;
    private List<String> arrrar = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 5; i++) {
            arrrar.add("数据");
        }
        banner = ((BannerView) findViewById(R.id.banner_view));
        banner.setAdapter(arrrar);
        banner.setOnloadBannerList(new BannerView.onLoadBannerImageLister() {
            @Override
            public void onLoadBanner(ImageView imageView, String url) {
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageResource(R.mipmap.banner2);

            }
        });
//        banner.setAdapter(new BannerAdapter() {
//            @Override
//            public View getView(final int position, View contrtView) {
//                ImageView imageView = null;
//                if (contrtView == null) {
//
//                    imageView = new ImageView(MainActivity.this);
//                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                    imageView.setImageResource(R.mipmap.banner2);
//
//                } else {
//                    imageView = (ImageView) contrtView;
//                }
////                imageView.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        Toast.makeText(MainActivity.this, "点击" + position, Toast.LENGTH_SHORT).show();
////
////                    }
////                });
//
//                return imageView;
//            }
//
//            @Override
//            public int getContent() {
//                return arrrar.size();
//            }
//        });
        banner.setStartbanner();


    }
}
