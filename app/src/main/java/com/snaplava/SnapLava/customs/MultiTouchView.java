package com.snaplava.SnapLava.customs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.snaplava.SnapLava.R;
import com.snaplava.SnapLava.customs.MultiTouchController.MultiTouchObjectCanvas;
import com.snaplava.SnapLava.customs.MultiTouchController.PointInfo;
import com.snaplava.SnapLava.customs.MultiTouchController.PositionAndScale;

/**
 * Canvas View for the MultiTouch controller
 * @author 2dwarfs.com
 *
 */

public class MultiTouchView extends View implements MultiTouchObjectCanvas<PinchWidget> , View.OnClickListener{

    private static final int UI_MODE_ROTATE = 1;
    private static final int UI_MODE_ANISOTROPIC_SCALE = 2;
    private int mUIMode = UI_MODE_ROTATE;

    float centerX = 0.0f, centerY= 0.0f, floatX= 1f, floatY= 1f, angle=0f ;

    float centerX12= 0.0f, centerY12= 0.0f, floatX12= 1f, floatY12= 1f, angle12=0f ;

    float centerX1= 0.0f, centerY1= 0.0f, floatX1 = 1f, floatY1= 1f, angle1=0f ;
    private MultiTouchController<PinchWidget> mMultiTouchController = new MultiTouchController<PinchWidget>(this);
    private MultiTouchController<PinchWidget> mMultiTouchController1 = new MultiTouchController<PinchWidget>(this);
    private int mWidth, mHeight;
  int check = 0;
    private PinchWidget mPinchWidget;
    boolean checkMovable = true;
    private PinchWidget mPinchWidget2;
    private Context mContext;
    float cornRAD= 25;
int color = Color.RED;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }

    public void setCornRAD(float cornRAD) {
        this.cornRAD = cornRAD;
        invalidate();
    }

    public Context ActivityContext;
    private PinchWidget mPinchWidget3;
    Bitmap itemBitmap3 = ((BitmapDrawable)getResources().getDrawable(R.drawable.background)).getBitmap();
    public MultiTouchView(Context context) {
        super(context);
    }

    public MultiTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
    }

    public MultiTouchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void alterstate (boolean change){
        checkMovable = change;
    }
    public void setPinchWidget(Bitmap bitmap) {
        mPinchWidget = new PinchWidget(ActivityContext,bitmap);
        mPinchWidget.setCenterX(centerX);
        mPinchWidget.setCenterY(centerY);
     //   mPinchWidget.setScaleX(floatX);
       // mPinchWidget.setScaleY(floatY);
        mPinchWidget.setAngle(angle);
        mPinchWidget.init(mContext.getResources());
        mPinchWidget.setOnClickListener(this);

       // mPinchWidget.setPos(getHeight()/3, 0, 1f,1f,0f);
        mPinchWidget.setContext(ActivityContext);
    }

    public PinchWidget getmPinchWidget() {
        return mPinchWidget;
    }

    public PinchWidget getmPinchWidget2() {
        return mPinchWidget2;
    }

    public PinchWidget getmPinchWidget3() {
        return mPinchWidget3;
    }

    public void setPinchWidget2(Bitmap bitmap){
        mPinchWidget2 = new PinchWidget(ActivityContext,bitmap);
        mPinchWidget2.setCenterX(centerX1);
        mPinchWidget2.setCenterY(centerY1);
        //mPinchWidget2.setScaleX(floatX1);

       // mPinchWidget2.setScaleY(floatY1);
        mPinchWidget2.setAngle(angle1);
        mPinchWidget2.init(mContext.getResources());
        mPinchWidget2.setOnClickListener(this);
        mPinchWidget2.setContext(ActivityContext);
    }
    public void setPinchWidget3(Bitmap bitmap){
        mPinchWidget3 = new PinchWidget(ActivityContext,bitmap);
        mPinchWidget3.setCenterX(centerX12);
        mPinchWidget3.setCenterY(centerY12);
        mPinchWidget3.setScaleX(floatX12);
        mPinchWidget3.setScaleY(floatY12);
        mPinchWidget3.setAngle(angle12);
        mPinchWidget3.init(mContext.getResources());
        mPinchWidget3.setOnClickListener(this);

        mPinchWidget3.setContext(ActivityContext);

    }
    public void setActivityContext(Context context){
        this.ActivityContext = context;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        mHeight = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(color);


        //  canvas.clipPath(path);
       // canvas.drawBitmap(itemBitmap3,null,new Rect(0,0,this.getWidth(),this.getHeight()), null);
       // canvas.
        defaultdraw(canvas);

    }

    private void defaultdraw(Canvas canvas) {
        if(mPinchWidget2 != null){
            mPinchWidget2.draw(canvas);
            centerX = mPinchWidget.getCenterX();
            centerY = mPinchWidget.getCenterY();
          floatX = mPinchWidget.getScaleX();
          floatY = mPinchWidget.getScaleY();
            angle = mPinchWidget.getAngle();


        }
        if(mPinchWidget != null){
            mPinchWidget.draw(canvas);

            centerX1 = mPinchWidget2.getCenterX();
            centerY1 = mPinchWidget2.getCenterY();

            floatX1 = mPinchWidget2.getScaleX();
            floatY1 = mPinchWidget2.getScaleY();
            angle1 = mPinchWidget2.getAngle();

        }

        if(mPinchWidget3 != null){
            mPinchWidget3.draw(canvas);
            centerX12 = mPinchWidget3.getCenterX();
            centerY12 = mPinchWidget3.getCenterY();

            floatX12 = mPinchWidget3.getScaleX();
            floatY12 = mPinchWidget3.getScaleY();
            angle12 = mPinchWidget3.getAngle();

        }


    }

    public void RemovePinchImage(){
        mPinchWidget3 = null;
        invalidate();
        //setPinchWidget3(null);

}
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
      //  mPinchWidget.
        if(checkMovable) {
            return mMultiTouchController1.onTouchEvent(ev);
        }
        else {
            return false ;

        }
    }

    @Override
    public PinchWidget getDraggableObjectAtPoint(PointInfo pt) {
        float x = pt.getX(), y = pt.getY();

        if (mPinchWidget.containsPoint(x, y)) {
            check =1;
            return mPinchWidget;

        }else if(mPinchWidget2.containsPoint(x,y)){
            check =2;
            return  mPinchWidget2;
        }else if(mPinchWidget3.containsPoint(x,y)){
            check =3;
            return mPinchWidget3;
        }




        return null;
    }

    @Override
    public void getPositionAndScale(PinchWidget pinchWidget, PositionAndScale objPosAndScaleOut) {
        objPosAndScaleOut.set(pinchWidget.getCenterX(), pinchWidget.getCenterY(),
                (mUIMode & UI_MODE_ANISOTROPIC_SCALE) == 0,
                (pinchWidget.getScaleFactor() + pinchWidget.getScaleFactor()) / 2,
                (mUIMode & UI_MODE_ANISOTROPIC_SCALE) != 0,
                pinchWidget.getScaleFactor(),
                pinchWidget.getScaleFactor(),
                (mUIMode & UI_MODE_ROTATE) != 0,
                pinchWidget.getAngle());




    }

    @Override
    public boolean setPositionAndScale(PinchWidget pinchWidget, PositionAndScale newImgPosAndScale, PointInfo touchPoint) {
        boolean ok = pinchWidget.setPos(newImgPosAndScale, mUIMode, UI_MODE_ANISOTROPIC_SCALE, touchPoint.isMultiTouch());
        if(ok) {
            invalidate();
        }

        return ok;
    }

    @Override
    public void selectObject(PinchWidget pinchWidget, PointInfo touchPoint) {
        if(touchPoint.isDown()) {
           if(check ==1){
               mPinchWidget = pinchWidget;
           }else if (check ==2){
               mPinchWidget2 = pinchWidget;
           }else if (check == 3){
               mPinchWidget3 = pinchWidget;
           }

        }

        invalidate();
    }


    @Override
    public void onClick(View v) {

         mPinchWidget.performClick();
      //      Log.e( "onClick: ","clicked" );

    }
}