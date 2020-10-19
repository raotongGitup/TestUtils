package com.example.testutils.base.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewbinding.ViewBinding;

import com.example.testutils.base.proxy.IMvpProxy;
import com.example.testutils.utils.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * create at 2020/10/19
 * author raotong
 * Description : fragment的懒加载封装
 */
public abstract class LazyFragment<T extends ViewBinding> extends Fragment implements BaseView {
    public String TAG = "---";
    boolean mIsViewCreated = false;//Fragment是否已经创建，
    boolean mIsFirstVisiable = true;//Fragment是否第一次可见
    boolean mCurrentVisiableState = false;//标记保存Fragment的可见状态，表示当前Fragment是否分发过  可见到不可见  不可见到可见才可以调用 disPatchVisibaleHint  防止重复调用
    private IMvpProxy iMvpProxy;
    public T binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = getBinding();
        }
        //1：创建了Fragment  控制下面分发的前提，因为分发事件由setUserVisibleHint方法控制，而setUserVisibleHint最先执行
        mIsViewCreated = true;
        //2：对于默认Fragment的加载，可以在此分发一下，可见才分发
        if (getUserVisibleHint() && !isHidden()) {
            //可见
            disPatchVisibaleHint(true);
        }

        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iMvpProxy = new IMvpProxy(this);
        iMvpProxy.crAttachPresenter();
        initView();
        initData();
    }

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void onResume() {
        Log.e(TAG, "onResume");
        //只有当前可见的Fragment  才更新shuju  点击home键又返回
        if (!mCurrentVisiableState && getUserVisibleHint() && !isHidden()) {
            disPatchVisibaleHint(true);
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e(TAG, "onPause");
        if (mCurrentVisiableState && getUserVisibleHint()) {
            disPatchVisibaleHint(false);
        }
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (iMvpProxy != null) {
            iMvpProxy.onDetachPresenter();
        }
        //将所有的变量复位
        mIsFirstVisiable = true;
        mCurrentVisiableState = false;
        mIsViewCreated = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        //用户是否可见
        Log.e(TAG, "setUserVisibleHint----" + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
        /**
         * 1：分发的前提是FragmentView已经创建，因为setUserVisibleHint在fragment 的声明周期之前执行，
         * 如果页面没创建就去分发容易造成空指针异常
         *
         * 2：因为此方法是由setUserVisibleHint方法调用，而setUserVisibleHint方法由系统的Viewpager调用多次，我们没办法控制它，
         *    但是我们可以控制disPatchVisibaleHint的分发
         */
        if (mIsViewCreated) {
            //只有Fragment创建了后才进行分发
            if (isVisibleToUser && !mCurrentVisiableState) {//防止重复调用disPatchVisibaleHint !mCurrentVisiableState表示没有分发过时才分发
                //用户可见时进行分发事件
                disPatchVisibaleHint(true);
            } else if (mCurrentVisiableState && !isVisibleToUser) {
                //用户不可见时不分发事件
                disPatchVisibaleHint(false);
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.e(TAG, "onHiddenChanged");
//        FragmentTransaction管理Fragment时
        super.onHiddenChanged(hidden);
        if (hidden) {
            disPatchVisibaleHint(false);
        } else {
            disPatchVisibaleHint(true);
        }
    }

    /**
     * 分发可见
     * 调用的前提：可见--》不可见    或者不可见--》可见
     *
     * @param isVisiable
     */
    private void disPatchVisibaleHint(boolean isVisiable) {
        Log.e(TAG, "disPatchVisibaleHint----" + isVisiable);

        if (mCurrentVisiableState == isVisiable) {
            //防止调用两次更新，存在当 mCurrentVisiableState=isVisiable;执行前setUserVisibleHint可能被调用两次
            return;
        }
        mCurrentVisiableState = isVisiable;//进行赋值操作
        if (isVisiable) {
            //可见
            if (mIsFirstVisiable) {
                mIsFirstVisiable = false;
                onFragmentFirstVisiable();
            }
            onFragmentResume();
            //处理子ViewPager嵌套使用时，子fragment懒加载的实现
            dispatChChildVisiableState(true);
        } else {
            onFragmentPause();
            dispatChChildVisiableState(false);
        }

    }

    /**
     * 第一次可见时特殊处理
     */
    public void onFragmentFirstVisiable() {
        Log.e(TAG, "onFragmentFirstVisiable");

    }

    /**
     * 不可见时处理相关动作  停止数据的加载
     */
    public void onFragmentPause() {
        Log.e(TAG, "onFragmentPause");

    }

    /**
     * 表面可见时  加载数据
     */
    public void onFragmentResume() {
        Log.e(TAG, "onFragmentResume");

    }

    /**
     * ViewPage嵌套使用时处理子的Fragment懒加载逻辑
     * 通过getChildFragmentManager();获取子Fragment
     *
     * @param visiable
     */
    private void dispatChChildVisiableState(boolean visiable) {
        Log.e(TAG, "dispatChChildVisiableState ===== " + visiable);
        FragmentManager childFragmentManager = getChildFragmentManager();
        List<Fragment> childFragmentManagerFragments = childFragmentManager.getFragments();
        if (childFragmentManagerFragments != null) {
            for (Fragment fragment : childFragmentManagerFragments) {
                //进行类型校验，只有继承了LazyFragment才进行懒加载的处理
                if (fragment instanceof LazyFragment && !fragment.isHidden() && fragment.getUserVisibleHint()) {
                    ((LazyFragment) fragment).disPatchVisibaleHint(visiable);
                }
            }
        }
    }

    protected T getBinding() {
        try {
            Type superClass = getClass().getGenericSuperclass();
            Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
            Class<?> clazz = ClassUtils.getRawType(type);
            Method method = clazz.getMethod("inflate", LayoutInflater.class);
            return (T) method.invoke(null, getLayoutInflater());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
