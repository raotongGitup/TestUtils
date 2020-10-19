package com.example.testutils.activity.moudle;

import com.example.testutils.base.http.ICallback;
import com.example.testutils.base.moudle.BaseMoudle;

/**
 * create at 2020/10/16
 * author raotong
 * Description :Usermvp的实例测试使用实例
 */
public class UserInfoMoudle extends BaseMoudle {

    private static final String TAG = "UserInfoMoudle";

    // 网络请求库
    public void gteToken(ICallback iCallback) {
        // 网络请求的返回参数
        iCallback.onSuccent("sss");

    }

}
