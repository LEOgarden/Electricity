package android.leo.electricity.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.leo.electricity.R;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Leo on 2017/8/11.
 * 仿电表表盘
 */

public class ElectricityBoardView extends View {

    private int mRadius; // 表盘扇形半径
    private int mStartAngle = 135; // 起始角度135°
    private int mSweepAngle = 270; // 绘制角度270°
    private float mMin = 0.0f; // 最小值
    private float mMax = 999.9f; // 最大值
    private int mSection = 6; // 值域（mMax-mMin）等分份数
    private int mPortion = 2; // 一个mSection等分份数
    private String mHeaderText = "kW·h"; // 表头单位符号
    private String commomText = "本月用电量";
    private float mQuantity = mMin; // 实时电量
    private int mStrokeWidth; // 画笔宽度
    private int mLength1; // 长刻度的相对圆弧的长度
    private int mLength2; // 刻度读数顶部的相对圆弧的长度
    private int mPLRadius; // 指针长半径
    private int mPSRadius; // 指针短半径

    private int mPadding;
    private float mCenterX, mCenterY; // 圆心坐标
    private Paint mPaint;
    private RectF mRectFArc;
    private RectF mRectFInnerArc;
    private Rect mRectText;
    private String[] mTexts;//分段刻度读数
    private int[] mColors;//分段颜色]

    private float mAngleWhenAnim;
    private boolean isAnimFinish = false;

    public ElectricityBoardView(Context context) {
        this(context, null);
    }

    public ElectricityBoardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ElectricityBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mStrokeWidth = dp2px(3);
        mLength1 = dp2px(8) + mStrokeWidth;
        mLength2 = mLength1 + dp2px(4);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);//画笔风格，等分为圆形

        mRectFArc = new RectF();
        mRectFInnerArc = new RectF();
        mRectText = new Rect();

        mTexts = new String[]{"0", "100", "200", "350", "450", "550", "1000"};

        mColors = new int[]{
                ContextCompat.getColor(getContext(), R.color.color_green),
                ContextCompat.getColor(getContext(), R.color.color_yellow),
                ContextCompat.getColor(getContext(), R.color.color_red)
        };
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //取padding最大值作为padding
        mPadding = Math.max(
                Math.max(getPaddingLeft(), getPaddingTop()),
                Math.max(getPaddingRight(), getPaddingBottom())
        );
        setPadding(mPadding, mPadding, mPadding, mPadding);
        int width = resolveSize(dp2px(200), widthMeasureSpec);
        mRadius = (width - mPadding*2 - mStrokeWidth*2)/2;
        //起始角度决定的高度
        float[] pointStart = getCoordinatePoint(mRadius, mStartAngle);
        //结束角度确定高度
        float[] pointEnd = getCoordinatePoint(mRadius, mStartAngle+mSweepAngle);
        int height = (int) Math.max(pointStart[1] + mRadius/4,
                pointEnd[1] + mRadius/4);
        setMeasuredDimension(width, height + getPaddingTop() + getPaddingBottom());
        mCenterX = mCenterY = getMeasuredWidth()/2f;
        //矩形
        mRectFArc.set(getPaddingLeft() + mStrokeWidth,
                getPaddingTop() + mStrokeWidth,
                getMeasuredWidth() - getPaddingRight() - mStrokeWidth,
                getMeasuredWidth() - getPaddingBottom() - mStrokeWidth);

        //
        mPaint.setTextSize(sp2px(40));
        mPaint.getTextBounds("0", 0, "0".length(), mRectText);
        mRectFInnerArc.set(
                getPaddingLeft() + mLength2 + mRectText.height() + dp2px(30),
                getPaddingTop() + mLength2 + mRectText.height() + dp2px(30),
                getMeasuredWidth() - getPaddingRight() - mLength2 - mRectText.height() - dp2px(30),
                getMeasuredWidth() - getPaddingBottom() - mLength2 - mRectText.height() - dp2px(30)
        );

        mPLRadius = mRadius - dp2px(30);
        mPSRadius = dp2px(25);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画背景颜色
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.color_dark));
        /**
         * 画圆弧
         */
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_light));
        canvas.drawArc(mRectFArc, mStartAngle, mSweepAngle, false, mPaint);

        /**
         * 画长刻度
         * 画好起始角度的一条刻度后通过canvas绕着原点旋转来画剩下的长刻度
         */
        double cos = Math.cos(Math.toRadians(mStartAngle - 180));
        double sin = Math.sin(Math.toRadians(mStartAngle - 180));
        float x0 = (float) (mPadding + mStrokeWidth + mRadius * (1 - cos));
        float y0 = (float) (mPadding + mStrokeWidth + mRadius * (1 - sin));
        float x1 = (float) (mPadding + mStrokeWidth + mRadius - (mRadius - mLength1) * cos);
        float y1 = (float) (mPadding + mStrokeWidth + mRadius - (mRadius - mLength1) * sin);

        canvas.save();
        canvas.drawLine(x0, y0, x1, y1, mPaint);
        float angle = mSweepAngle * 1f / mSection;
        for (int i = 0; i < mSection; i++) {
            canvas.rotate(angle, mCenterX, mCenterY);
            canvas.drawLine(x0, y0, x1, y1, mPaint);
        }
        canvas.restore();

        /**
         * 画短刻度
         * 同样采用canvas的旋转原理
         */
        canvas.save();
        mPaint.setStrokeWidth(mStrokeWidth / 2f);
        float x2 = (float) (mPadding + mStrokeWidth + mRadius - (mRadius - 2 * mLength1 / 3f) * cos);
        float y2 = (float) (mPadding + mStrokeWidth + mRadius - (mRadius - 2 * mLength1 / 3f) * sin);
        canvas.drawLine(x0, y0, x2, y2, mPaint);
        angle = mSweepAngle * 1f / (mSection * mPortion);
        for (int i = 1; i < mSection * mPortion; i++) {
            canvas.rotate(angle, mCenterX, mCenterY);
            if (i % mPortion == 0) { // 避免与长刻度画重合
                continue;
            }
            canvas.drawLine(x0, y0, x2, y2, mPaint);
        }
        canvas.restore();

        /**
         * 画长刻度读数
         */
        mPaint.setTextSize(sp2px(12));
        mPaint.setStyle(Paint.Style.FILL);
        float α;
        float[] p;
        angle = mSweepAngle * 1f / mSection;
        for (int i = 0; i <= mSection; i++) {
            α = mStartAngle + angle * i;
            p = getCoordinatePoint(mRadius - mLength2 - 5, α);
            if (α % 360 > 135 && α % 360 < 225) {
                mPaint.setTextAlign(Paint.Align.LEFT);
            } else if ((α % 360 >= 0 && α % 360 < 45) || (α % 360 > 315 && α % 360 <= 360)) {
                mPaint.setTextAlign(Paint.Align.RIGHT);
            } else {
                mPaint.setTextAlign(Paint.Align.CENTER);
            }
            mPaint.getTextBounds(mHeaderText, 0, mTexts[i].length(), mRectText);
            int txtH = mRectText.height();
            if (i <= 1 || i >= mSection - 1) {
                canvas.drawText(mTexts[i], p[0], p[1] + txtH / 2, mPaint);
            } else if (i == 3) {
                canvas.drawText(mTexts[i], p[0] + txtH / 2, p[1] + txtH, mPaint);
            } else if (i == mSection - 3) {
                canvas.drawText(mTexts[i], p[0] - txtH / 2, p[1] + txtH, mPaint);
            } else {
                canvas.drawText(mTexts[i], p[0], p[1] + txtH, mPaint);
            }
        }

        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dp2px(10));
        mPaint.setShader(generateSweepGradient());
        canvas.drawArc(mRectFInnerArc, mStartAngle + 1, mSweepAngle - 2, false, mPaint);

        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShader(null);

        /**
         * 画表头
         * 没有表头就不画
         */
        if (!TextUtils.isEmpty(mHeaderText)) {
            mPaint.setTextSize(sp2px(16));
            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.getTextBounds(mHeaderText, 0, mHeaderText.length(), mRectText);
            canvas.drawText(mHeaderText, mCenterX, mCenterY + dp2px(80), mPaint);
            canvas.drawText(commomText, mCenterX, mCenterY + dp2px(160), mPaint);
        }

        /**
         * 画指针
         */

        float θ = mStartAngle + calculateRelativeAngleWithValue(mQuantity); // 指针与水平线夹角
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_red));
        int r = mRadius / 8;
        canvas.drawCircle(mCenterX, mCenterY, r, mPaint);
        mPaint.setStrokeWidth(r / 9);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_red));
        float[] p1 = getCoordinatePoint(mPLRadius, θ);
        canvas.drawLine(p1[0], p1[1], mCenterX, mCenterY, mPaint);
        float[] p2 = getCoordinatePoint(mPSRadius, θ + 180);
        canvas.drawLine(mCenterX, mCenterY, p2[0], p2[1], mPaint);

        /**
         * 画实时度数值
         */
        String str = String.valueOf(mQuantity);
        int index = str.indexOf(".");
        String str1 = str.substring(0,index);
        String str2 = str.substring(index+1);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.primary));
        mPaint.setStrokeWidth(dp2px(2));
        float xOffset = dp2px(22);
        int partInt = Integer.parseInt(str1);
        int partFloat = Integer.parseInt(str2);
        //整数部分
        if (partInt >= 100) {
            drawDigitalTube(canvas, partInt / 100, -xOffset);
            drawDigitalTube(canvas, (partInt % 100) / 10, 0);
            drawDigitalTube(canvas, partInt % 100 % 10, xOffset);
        } else if (partInt >= 10) {
            drawDigitalTube(canvas, -1, -xOffset);
            drawDigitalTube(canvas, partInt / 10, 0);
            drawDigitalTube(canvas, partInt % 10, xOffset);
        } else {
            drawDigitalTube(canvas, -1, -xOffset);
            drawDigitalTube(canvas, -1, 0);
            drawDigitalTube(canvas, partInt, xOffset);
        }

        //小数部分
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_red));
        drawDigitalTube(canvas, partFloat, xOffset*2.5f);
        //小数点
        mPaint.setAlpha(225);
        canvas.drawPoint(mCenterX + 1.7f*xOffset,
                mCenterY + dp2px(100) + dp2px(2) * 3 + dp2px(10) * 2, mPaint);
    }

    private Shader generateSweepGradient() {
        SweepGradient sweepGradient = new SweepGradient(mCenterX, mCenterY,
                mColors,
                new float[]{0, 140 / 360f, mSweepAngle / 360f}
        );

        Matrix matrix = new Matrix();
        matrix.setRotate(mStartAngle - 3, mCenterX, mCenterY);
        sweepGradient.setLocalMatrix(matrix);

        return sweepGradient;
    }

    /**
     * 通过半径和起始角度确定起始点
     * @param mRadius
     * @param angle
     * @return
     */
    private float[] getCoordinatePoint(int mRadius, float angle) {
        float[] point = new float[2];
        double arcAngle = Math.toRadians(angle); //将角度转换为弧度
        point[0] = (float) (mCenterX + Math.cos(arcAngle) * mRadius);
        point[1] = (float) (mCenterY + Math.sin(arcAngle) * mRadius);
        return point;
    }

    /**
     * dp转化为px
     * @param dp
     * @return
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    /**
     *
     * @param sp
     * @return
     */
    private float sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                Resources.getSystem().getDisplayMetrics());
    }

    /**
     * 数码管读数
     * @param canvas
     * @param num
     * @param xOffset
     */
    //      1
    //      ——
    //   2 |  | 3
    //      —— 4
    //   5 |  | 6
    //      ——
    //       7
    private void drawDigitalTube(Canvas canvas, int num, float xOffset) {
        float x = mCenterX + xOffset;
        float y = mCenterY + dp2px(100);
        int lx = dp2px(5);
        int ly = dp2px(10);
        int gap = dp2px(2);

        // 1
        mPaint.setAlpha(num == -1 || num == 1 || num == 4 ? 25 : 255);
        canvas.drawLine(x - lx, y, x + lx, y, mPaint);
        // 2
        mPaint.setAlpha(num == -1 || num == 1 || num == 2 || num == 3 || num == 7 ? 25 : 255);
        canvas.drawLine(x - lx - gap, y + gap, x - lx - gap, y + gap + ly, mPaint);
        // 3
        mPaint.setAlpha(num == -1 || num == 5 || num == 6 ? 25 : 255);
        canvas.drawLine(x + lx + gap, y + gap, x + lx + gap, y + gap + ly, mPaint);
        // 4
        mPaint.setAlpha(num == -1 || num == 0 || num == 1 || num == 7 ? 25 : 255);
        canvas.drawLine(x - lx, y + gap * 2 + ly, x + lx, y + gap * 2 + ly, mPaint);
        // 5
        mPaint.setAlpha(num == -1 || num == 1 || num == 3 || num == 4 || num == 5 || num == 7
                || num == 9 ? 25 : 255);
        canvas.drawLine(x - lx - gap, y + gap * 3 + ly,
                x - lx - gap, y + gap * 3 + ly * 2, mPaint);
        // 6
        mPaint.setAlpha(num == -1 || num == 2 ? 25 : 255);
        canvas.drawLine(x + lx + gap, y + gap * 3 + ly,
                x + lx + gap, y + gap * 3 + ly * 2, mPaint);
        // 7
        mPaint.setAlpha(num == -1 || num == 1 || num == 4 || num == 7 ? 25 : 255);
        canvas.drawLine(x - lx, y + gap * 4 + ly * 2, x + lx, y + gap * 4 + ly * 2, mPaint);
    }

    /**
     * 相对起始角度计算电量所对应的角度大小
     */
    private float calculateRelativeAngleWithValue(float value) {
        float degreePerSection = 1f * mSweepAngle / mSection;
        if (value > 550.0) {
            return 5 * degreePerSection + degreePerSection / 450 * (value - 550);
        } else if (value > 450.0) {
            return 4 * degreePerSection + degreePerSection / 100 * (value - 450);
        } else if (value > 350.0) {
            return 3 * degreePerSection + degreePerSection / 100 * (value - 350);
        } else if (value > 200.0) {
            return 2 * degreePerSection + degreePerSection / 150 * (value - 200);
        } else {
            return 2 * degreePerSection / 200 * (value - 0);
        }
    }


    public float getQuantity() {
        return mQuantity;
    }

    public void setQuantity(float quantity){
        if (mQuantity == quantity || quantity < mMin || quantity > mMax) {
            return;
        }
        mQuantity = quantity;
        postInvalidate();
    }

    /**
     *
     * @param quantity
     */
    public void setQuantityWithAnim(float quantity) {
        if (mQuantity == quantity || quantity < mMin || quantity > mMax) {
            return;
        }
        mQuantity = quantity;
        ValueAnimator electricValueAnimator = ValueAnimator.ofFloat(0.0f, mQuantity);
        // 计算最终值对应的角度，以扫过的角度的线性变化来播放动画
        float degree = calculateRelativeAngleWithValue(mQuantity);
        ObjectAnimator degreeValueAnimator =  ObjectAnimator.ofFloat(this, "www", mStartAngle, mStartAngle+degree);
        degreeValueAnimator.setDuration(1500);
        degreeValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAngleWhenAnim = (float) animation.getAnimatedValue();
            }
        });
        long delay = 1500;
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(delay).play(degreeValueAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isAnimFinish = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimFinish = true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                isAnimFinish = true;
            }
        });
        animatorSet.start();
        postInvalidate();
    }
}
