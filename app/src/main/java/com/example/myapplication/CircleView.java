package com.example.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CircleView extends View {
    private Context mContext;
    private int mCircleColor;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleView, 0, 0);
        Log.i("CircleView", " typedArray:" + typedArray);
        try {
            mCircleColor = typedArray.getColor(R.styleable.CircleView_circleColor, Color.RED);
            Log.i("CircleView", "CircleView mCircleColor:" + mCircleColor);
        } finally {
            typedArray.recycle();
        }
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("CircleView", "onDraw mCircleColor:" + mCircleColor);
        mPaint.setColor(mCircleColor);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 180, mPaint);
        Log.i("CircleView", "onDraw mPaint:" + mPaint);
    }
}
