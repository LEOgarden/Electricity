package android.leo.electricity.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.leo.electricity.R;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2017/8/2.
 */

public class   ElectricLineView extends View{
    //画笔
    private Paint axiPaint;//坐标
    private Paint pointPaint;//点
    private Paint textPaint;//文字
    private Paint linePaint;//折线
    //原点位置
    private int xPoint ;
    private int yPoint ;
    //刻度长度
    private int xScale ;
    private int yScale ;
    //x, y轴长度
    private int xLength ;
    private int yLength ;
    private int maxPadding;
    //X轴最多绘制的点
    private int maxDataSize = 13;
    //存放纵坐标的点
    private List<Integer> yData = new ArrayList<>();
    //y轴坐标刻度集合
    private String[] yLabel = new String[7] ;
    //x轴显示的数据集合
    private String[] xLabel = new String[]{"", "1月", "2月", "3月", "4月", "5月", "6月", "7月",
            "8月", "9月", "10月", "11月", "12月"};
    //显示数据
    private List<String> mData = null;
    //数据范围
    private float[] mRange = new float[2];

    public void setInfo(List<String> mData){
        this.mData = mData;
    }

    public void setRange(float[] range){
        this.mRange = range;
    }

    public ElectricLineView(Context context){
        super(context);
    }

    public ElectricLineView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);


        for(int i=0; i<7; i++){
            yLabel[i] = i*200.0+"";
        }
    }

    public ElectricLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    public ElectricLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //取maxPadding最大值作为padding
        maxPadding = Math.max(
                Math.max(getPaddingLeft(), getPaddingTop()),
                Math.max(getPaddingRight(), getPaddingBottom())
        );
        //原点坐标
        xPoint = maxPadding;
        yPoint = 7 * maxPadding;
        //x,y轴长度
        xLength = this.getMeasuredWidth() - 2 * maxPadding;
        yLength = this.getMeasuredHeight() - 2 * maxPadding;
        //x,y轴刻度长度
        xScale = xLength / 13;
        yScale = yLength / 7;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        init();
        drawGraphAxis(canvas);
        drawPoint(canvas);
    }

    /**
     * 绘制坐标点及折线
     * @param canvas
     */
    private void drawPoint(Canvas canvas){
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
        axiPaint.setColor(Color.GREEN);
        axiPaint.setTextSize(20);
        axiPaint.setTextAlign(Paint.Align.CENTER);
        //画点
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setColor(Color.BLUE);

        //画折线
        for(int i=0; i< mData.size(); i++){
            if(i>0 && yCoord(mData.get(i-1))!=-999 && yCoord(mData.get(i))!=-999) {
                canvas.drawLine(xPoint+i*xScale, yCoord(mData.get(i-1)), xPoint+(i+1)*xScale, yCoord(mData.get(i)), linePaint);
            }
        }
        //画点
        for(int i=0; i< mData.size(); i++){
            if(yCoord(mData.get(i))!=-999) {
                float data = Float.parseFloat(mData.get(i));
                if(data>0 && data<=200.0){
                    pointPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_green));
                }else if(data>200.0 && data<=450.0){
                    pointPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_yellow));
                }else if(data>450.0){
                    pointPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_red));
                }else{
                    pointPaint.setStrokeWidth(0);
                }
                canvas.drawCircle(xPoint+(i+1)*xScale, yCoord(mData.get(i)), 5, pointPaint);
            }
            //画纵坐标数据
            canvas.drawText(mData.get(i), xPoint+(i+1)*xScale, yCoord(mData.get(i))-15, axiPaint);
        }

    }

    /**
     * 绘制坐标轴
     * @param canvas
     */
    private void drawGraphAxis(Canvas canvas){
        axiPaint.setStyle(Paint.Style.STROKE);
        axiPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
        axiPaint.setTextSize(16);
        axiPaint.setTextAlign(Paint.Align.CENTER);
        //绘制Y轴
        canvas.drawLine(xPoint, yPoint-yLength, xPoint, yPoint, axiPaint);
        //绘制Y轴左右两边的箭头
        canvas.drawLine(xPoint, yPoint-yLength, xPoint-3,yPoint-yLength+6, axiPaint);
        canvas.drawLine(xPoint, yPoint-yLength, xPoint+3,yPoint-yLength+6, axiPaint);
        //绘制x轴
        canvas.drawLine(xPoint, yPoint, xPoint + xLength, yPoint, axiPaint);
        //绘制x轴箭头
        canvas.drawLine(xPoint+xLength, yPoint, xPoint+xLength-6, yPoint-3, axiPaint);
        canvas.drawLine(xPoint+xLength, yPoint, xPoint+xLength-6, yPoint+3, axiPaint);
        //Y轴上的刻度与文字
        for(int i = 0; i < 7; i++){
            canvas.drawLine(xPoint, yPoint-i*yScale, xPoint+5, yPoint-i*yScale, axiPaint);  //刻度
            canvas.drawText(yLabel[i], xPoint-20, yPoint-i*yScale, axiPaint);//文字
        }
        //X轴上的刻度与文字
        for(int i=0; i<xLabel.length; i++){
            canvas.drawLine(xPoint+i*xScale, yPoint, xPoint + i*xScale, yPoint-5, axiPaint);
            canvas.drawText(xLabel[i], xPoint+i*xScale, yPoint+20, axiPaint);
        }
    }

    /**
     * 计算绘制折线的y坐标
     * @param y0
     * @return
     */
    private float yCoord(String y0){
        float y;
        try{
            y = Float.parseFloat(y0);
        }catch (Exception e){
            return -999;
        }
        //纵坐标值
        return yPoint-y*yScale/Float.parseFloat(yLabel[1]);
    }

    private void init(){
        axiPaint = new Paint();
        axiPaint.setAntiAlias(true);//抗锯齿
        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(3);
    }
}
