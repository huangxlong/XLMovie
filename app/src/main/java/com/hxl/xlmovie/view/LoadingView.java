package com.hxl.xlmovie.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxl.xlmovie.R;
import com.wang.avi.AVLoadingIndicatorView;



/**
 * 装有空视图，错误视图和加载视图的控件
 *
 * @author Administrator
 */
public class LoadingView extends ViewGroup {

    /**
     * 加载
     */
    public static final int STATUS_LOADING = 0;
    /**
     * 数据为空
     */
    public static final int STATUS_EMPTY = 1;
    /**
     * 加载错误
     */
    public static final int STATUS_ERROR = 2;
    /**
     * 加载完成
     */
    public static final int STATUS_DONE = 3;
    private RelativeLayout container;
    private LinearLayout btnRefresh;
    private ImageView img;
    private TextView text;
    private AVLoadingIndicatorView loadingView;
    /**
     * 显示的文字
     */
    private String msg;
    /**
     * 图片资源
     */
    private int imageRes;
    /**
     * 状态
     */
    private int status;
    private OnClickListener clickListener;

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
        // 获取自定义属性和默认值
        msg = mTypedArray.getString(R.styleable.LoadingView_text);
        imageRes = mTypedArray.getInt(R.styleable.LoadingView_imageResource, R.drawable.pic_empty);
        mTypedArray.recycle();
        init();
    }

    private void init() {
        container = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_include_error, null);
        this.addView(container, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        img = (ImageView) container.findViewById(R.id.imgTag);
        text = (TextView) container.findViewById(R.id.textTag);
        loadingView = (AVLoadingIndicatorView) container.findViewById(R.id.aviLoading);
        btnRefresh = (LinearLayout) container.findViewById(R.id.btnRefresh);
        if (imageRes != 0) {
            setImgRes(imageRes);
        }
        if (msg != null) {
            setText(msg);
        }
        setStatus(status);
    }

    /**
     * 设置错误页面的点击事件
     *
     * @param clickListener
     */
    public void setRefreshListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
        if (btnRefresh != null) {
            btnRefresh.setOnClickListener(clickListener);
        }
    }

    /**
     * 设置状态
     *
     * @param status
     */
    public void setStatus(int status) {
        switch (status) {
            case STATUS_LOADING:
                this.setVisibility(VISIBLE);
                loadingView.show();
                loadingView.setVisibility(View.VISIBLE);
                text.setVisibility(View.GONE);
                img.setVisibility(View.GONE);
                btnRefresh.setVisibility(View.GONE);
                setClickable(false);
                break;
            case STATUS_ERROR:
                this.setVisibility(VISIBLE);
                loadingView.hide();
                loadingView.clearAnimation();
                loadingView.setVisibility(View.GONE);
                text.setVisibility(View.VISIBLE);
                img.setVisibility(View.VISIBLE);
                btnRefresh.setVisibility(View.VISIBLE);
                setClickable(true);
                if (clickListener != null) {
                    btnRefresh.setOnClickListener(clickListener);
                }
                setImgRes(R.drawable.pic_error);
                setText(getContext().getResources().getString(R.string.text_loading_error));
                break;
            case STATUS_EMPTY:
                this.setVisibility(VISIBLE);
                loadingView.hide();
                loadingView.clearAnimation();
                loadingView.setVisibility(View.GONE);
                text.setVisibility(View.VISIBLE);
                img.setVisibility(View.VISIBLE);
                btnRefresh.setVisibility(View.GONE);
                setClickable(false);
                setImgRes(R.drawable.pic_empty);
                setText(getContext().getResources().getString(R.string.text_no_data));
                break;
            case STATUS_DONE:
                loadingView.hide();
                loadingView.clearAnimation();
                this.setVisibility(GONE);
                break;

            default:
                break;
        }
    }

    /**
     * 设置显示图片
     *
     * @param imgRes
     */
    public void setImgRes(int imgRes) {
        img.setImageResource(imgRes);
    }

    /**
     * text
     *
     * @param msg
     */
    public void setText(String msg) {
        text.setText(msg);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 记录总高度
        int mTotalHeight = 0;
        // 遍历所有子视图
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            // 获取在onMeasure中计算的视图尺寸
            int measureHeight = childView.getMeasuredHeight();
            int measuredWidth = childView.getMeasuredWidth();

            childView.layout(l, mTotalHeight, measuredWidth, mTotalHeight + measureHeight);

            mTotalHeight += measureHeight;

        }
    }

    /**
     * 计算控件的大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = measureWidth(widthMeasureSpec);
        int measureHeight = measureHeight(heightMeasureSpec);
        // 计算自定义的ViewGroup中所有子控件的大小
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        // 设置自定义的控件MyViewGroup的大小
        setMeasuredDimension(measureWidth, measureHeight);
    }

    private int measureWidth(int pWidthMeasureSpec) {
        int result = 0;
        int widthMode = MeasureSpec.getMode(pWidthMeasureSpec);// 得到模式
        int widthSize = MeasureSpec.getSize(pWidthMeasureSpec);// 得到尺寸

        switch (widthMode) {
            /**
             * mode共有三种情况，取值分别为MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY,
             * MeasureSpec.AT_MOST。
             *
             *
             * MeasureSpec.EXACTLY是精确尺寸，
             * 当我们将控件的layout_width或layout_height指定为具体数值时如andorid
             * :layout_width="50dip"，或者为FILL_PARENT是，都是控件大小已经确定的情况，都是精确尺寸。
             *
             *
             * MeasureSpec.AT_MOST是最大尺寸，
             * 当控件的layout_width或layout_height指定为WRAP_CONTENT时
             * ，控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可
             * 。因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。
             *
             *
             * MeasureSpec.UNSPECIFIED是未指定尺寸，这种情况不多，一般都是父控件是AdapterView，
             * 通过measure方法传入的模式。
             */
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = widthSize;
                break;
        }
        return result;
    }

    private int measureHeight(int pHeightMeasureSpec) {
        int result = 0;

        int heightMode = MeasureSpec.getMode(pHeightMeasureSpec);
        int heightSize = MeasureSpec.getSize(pHeightMeasureSpec);

        switch (heightMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = heightSize;
                break;
        }
        return result;
    }
}
