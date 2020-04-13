package com.example.testutils.base.state;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StateFrameLayout extends FrameLayout {
    /**
     * loading 加载id
     */
    public static final int LAYOUT_LOADING_ID = 0x001;

    /**
     * 异常id
     */
    public static final int LAYOUT_ERROR_ID = 0x011;
    /**
     * 网络异常id
     */
    public static final int LAYOUT_NETWORK_ERROR_ID = 0x100;
    /**
     * 空数据id
     */
    public static final int LAYOUT_EMPTY_DATA_ID = 0x101;

    /**
     * 存放5种数据状态集合
     */
    private SparseArray<View> layoutSparseArray = new SparseArray<>();
    /**
     * 布局管理器
     */
    private StateLayoutManager mStatusLayoutManager;

    public StateFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public StateFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置状态管理者manager
     */
    public void setStatusLayoutManager(StateLayoutManager statusLayoutManager) {
        mStatusLayoutManager = statusLayoutManager;
        //添加所有的布局到帧布局
        addAllLayoutToRootLayout();
    }

    private void addAllLayoutToRootLayout() {
//        if (mStatusLayoutManager.contentLayoutResId != 0) {
//            addLayoutResId(mStatusLayoutManager.contentLayoutResId, StateFrameLayout.LAYOUT_CONTENT_ID);
//        }
        if (mStatusLayoutManager.loadingLayoutResId != 0) {
            addLayoutResId(mStatusLayoutManager.loadingLayoutResId, StateFrameLayout.LAYOUT_LOADING_ID);
        }
        if (mStatusLayoutManager.emptyDataVs != null) {

        }
        if (mStatusLayoutManager.errorVs != null) {
            addView(mStatusLayoutManager.errorVs);
        }
        if (mStatusLayoutManager.netWorkErrorVs != null) {
            addView(mStatusLayoutManager.netWorkErrorVs);
        }


    }

    /**
     * 创建布局
     */
    private void addLayoutResId(int layoutResId, int id) {
        View resView = LayoutInflater.from(mStatusLayoutManager.context).inflate(layoutResId, null);
        if (id == StateFrameLayout.LAYOUT_LOADING_ID) {
            //如果是loading，则设置不可点击
            resView.setOnClickListener(null);
        }
        layoutSparseArray.put(id, resView);
        addView(resView);

    }

    /**
     * 关闭showLoading
     */
    public boolean goneLoading() {
        if (layoutSparseArray.get(LAYOUT_LOADING_ID) != null) {
            View view = layoutSparseArray.get(LAYOUT_LOADING_ID);
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    /**
     * 判断是否正在loading中
     * true 表示loading正在加载中
     */
    public boolean isLoading() {
        View view = layoutSparseArray.get(LAYOUT_LOADING_ID);
        if (view != null) {
            if (view.getVisibility() == View.VISIBLE) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    /**
     * 显示loading
     */
    public void showLoading() {
        if (layoutSparseArray.get(LAYOUT_LOADING_ID) != null) {
            showHideViewById(LAYOUT_LOADING_ID);
        }
    }

    /**
     * 显示内容
     */
//    public void showContent() {
//        if (layoutSparseArray.get(LAYOUT_CONTENT_ID) != null) {
//            showHideViewById(LAYOUT_CONTENT_ID);
//        }
//    }

    /**
     * 显示空数据
     */
    public void showEmptyData(int iconImage, String textTip) {
        if (inflateLayout(LAYOUT_EMPTY_DATA_ID)) {
            showHideViewById(LAYOUT_EMPTY_DATA_ID);
            emptyDataViewAddData(iconImage, textTip);
        }
    }

    /**
     * 显示网络异常
     */
    public void showNetWorkError() {
        if (inflateLayout(LAYOUT_NETWORK_ERROR_ID)) {
            showHideViewById(LAYOUT_NETWORK_ERROR_ID);
        }
    }

    /**
     * 显示异常
     */
    public void showError(int iconImage, String textTip) {
        if (inflateLayout(LAYOUT_ERROR_ID)) {
            showHideViewById(LAYOUT_ERROR_ID);
            errorViewAddData(iconImage, textTip);
        }
    }

    /**
     * 展示加载异常简单页面
     * iconImage                 image图片
     * textTip                   文案
     */
    private void errorViewAddData(int iconImage, String textTip) {
        if (iconImage == 0 && TextUtils.isEmpty(textTip)) {
            return;
        }
        View errorView = layoutSparseArray.get(LAYOUT_ERROR_ID);
        View iconImageView = errorView.findViewById(mStatusLayoutManager.errorIconImageId);
        View textView = errorView.findViewById(mStatusLayoutManager.errorTextTipId);
        if (iconImageView != null && iconImageView instanceof ImageView) {
            ((ImageView) iconImageView).setImageResource(iconImage);
        }
        if (textView != null && textView instanceof TextView) {
            ((TextView) textView).setText(textTip);
        }

    }

    /**
     * 展示错误
     *
     * @param objects
     */

    public void showLayoutError(Object... objects) {
        if (inflateLayout(LAYOUT_ERROR_ID)) {
            showHideViewById(LAYOUT_ERROR_ID);

            AbsViewStubLayout errorLayout = mStatusLayoutManager.errorLayout;
            if (errorLayout != null) {
                errorLayout.setData(objects);
            }
        }
    }


    /**
     * 展示空页面
     */
    public void showLayoutEmptyData(Object... objects) {
        if (inflateLayout(LAYOUT_EMPTY_DATA_ID)) {
            showHideViewById(LAYOUT_EMPTY_DATA_ID);

            AbsViewStubLayout emptyDataLayout = mStatusLayoutManager.emptyDataLayout;
            if (emptyDataLayout != null) {
                emptyDataLayout.setData(objects);
            }
        }

    }

    /**
     * 空数据并且设置页面简单数据
     * iconImage          空页面图片
     * textTip            文字
     */
    private void emptyDataViewAddData(int iconImage, String textTip) {
        if (iconImage == 0 && TextUtils.isEmpty(textTip)) {
            return;
        }
        View emptyDataView = layoutSparseArray.get(LAYOUT_EMPTY_DATA_ID);
        View iconImageView = emptyDataView.findViewById(mStatusLayoutManager.emptyDataIconImageId);
        View textView = emptyDataView.findViewById(mStatusLayoutManager.emptyDataTextTipId);
        if (iconImageView != null && iconImageView instanceof ImageView) {
            ((ImageView) iconImageView).setImageResource(iconImage);
        }
        if (textView != null && textView instanceof TextView) {
            ((TextView) textView).setText(textTip);
        }

    }

    /**
     * 这个是处理ViewStub的逻辑，主要有网络异常布局，加载异常布局，空数据布局
     */
    private boolean inflateLayout(int layoutEmptyDataId) {
        boolean isShow = true;
        if (layoutSparseArray.get(layoutEmptyDataId) == null) {
            return false;
        }
        switch (layoutEmptyDataId) {
            //网络异常
            case LAYOUT_NETWORK_ERROR_ID:
                if (mStatusLayoutManager.netWorkErrorVs != null) {
                    View view = mStatusLayoutManager.netWorkErrorVs.inflate();
                    view.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mStatusLayoutManager.onNetworkListener.onNetwork();

                        }
                    });
                    layoutSparseArray.put(layoutEmptyDataId, view);
                    isShow = true;
                } else {
                    isShow = false;
                }

                break;
            //加载异常
            case LAYOUT_ERROR_ID:
                if (mStatusLayoutManager.errorVs != null) {
                    //只有当展示的时候，才将ViewStub视图给inflate出来
                    View view = mStatusLayoutManager.errorVs.inflate();
                    if (mStatusLayoutManager.errorLayout != null) {
                        mStatusLayoutManager.errorLayout.setView(view);
                    }
                    view.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mStatusLayoutManager.onRetryListener.onRetry();
                        }
                    });
                    layoutSparseArray.put(layoutEmptyDataId, view);
                    isShow = true;
                } else {
                    isShow = false;
                }

                break;
            //空数据
            case LAYOUT_EMPTY_DATA_ID:
                if (mStatusLayoutManager.emptyDataVs != null) {
                    //只有当展示的时候，才将ViewStub视图给inflate出来
                    View view = mStatusLayoutManager.emptyDataVs.inflate();
                    if (mStatusLayoutManager.emptyDataLayout != null) {
                        mStatusLayoutManager.emptyDataLayout.setView(view);
                    }
                    view.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mStatusLayoutManager.onRetryListener.onRetry();
                        }
                    });
                    layoutSparseArray.put(layoutEmptyDataId, view);
                    isShow = true;
                } else {
                    isShow = false;
                }
                break;
            default:
                break;

        }


        return isShow;
    }

    /**
     * 根据ID显示隐藏布局
     * layoutLoadingId    layoutLoadingId 值
     */

    private void showHideViewById(int layoutLoadingId) {
        for (int i = 0; i < layoutSparseArray.size(); i++) {
            int key = layoutSparseArray.keyAt(i);
            View valueView = layoutSparseArray.valueAt(i);
            if (key == layoutLoadingId) {
                valueView.setVisibility(VISIBLE);
                if (mStatusLayoutManager.onShowHideViewListener != null) {
                    mStatusLayoutManager.onShowHideViewListener.onShowView(valueView, key);
                }

            } else {
                if (valueView.getVisibility() != View.GONE) {
                    valueView.setVisibility(GONE);
                }
                if (mStatusLayoutManager.onShowHideViewListener != null) {
                    mStatusLayoutManager.onShowHideViewListener.onHideView(valueView, key);
                }
            }


        }


    }


}
