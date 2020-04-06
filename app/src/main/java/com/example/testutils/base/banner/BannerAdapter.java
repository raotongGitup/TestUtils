package com.example.testutils.base.banner;

import android.view.View;

public abstract class BannerAdapter {
    public abstract View getView(int position,View convertView);

    public abstract int getContent();
}
