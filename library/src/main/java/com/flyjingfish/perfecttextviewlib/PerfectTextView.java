package com.flyjingfish.perfecttextviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.LayoutDirection;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.text.TextUtilsCompat;

import java.util.HashSet;
import java.util.Locale;

public class PerfectTextView extends AppCompatTextView {

    private int drawableStartWidth;
    private int drawableStartHeight;
    private int drawableTopWidth;
    private int drawableTopHeight;
    private int drawableEndWidth;
    private int drawableEndHeight;
    private int drawableBottomWidth;
    private int drawableBottomHeight;
    private int drawableLeftWidth;
    private int drawableLeftHeight;
    private int drawableRightWidth;
    private int drawableRightHeight;
    private int drawableStartPadding;
    private int drawableTopPadding;
    private int drawableEndPadding;
    private int drawableBottomPadding;
    private int drawableLeftPadding;
    private int drawableRightPadding;
    private final boolean isInitSuper;
    private final boolean isRtl;
    private CharSequence selectedText;
    private CharSequence defaultText;
    private CharSequence selectedHint;
    private CharSequence defaultHint;
    private Drawable textBackground;
    private TextBackgroundScope textBackgroundScope;
    private ClickScope clickScope = ClickScope.allScope;
    private ClickScope longClickScope = ClickScope.allScope;
    private ClickScope doubleClickScope = ClickScope.allScope;
    public enum TextBackgroundScope{
        /**
         * 包裹文本的区域
         */
        wrappedText,
        /**
         * 包裹文本且到四个方向 DrawablePadding 的位置
         */
        fitDrawablePadding
    }
    public enum ClickScope {
        /**
         * 文本区域
         */
        textScope,
        /**
         * 整个PerfectTextView的区域
         */
        allScope
    }

    public PerfectTextView(@NonNull Context context) {
        this(context, null);
    }

    public PerfectTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PerfectTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        isRtl = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == LayoutDirection.RTL;
        isInitSuper = true;
        int pad = getCompoundDrawablePadding();
        setCompoundDrawablePadding(0);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PerfectTextView);
        drawableStartWidth = typedArray.getDimensionPixelOffset(R.styleable.PerfectTextView_perfect_drawableStart_width, 0);
        drawableStartHeight = typedArray.getDimensionPixelOffset(R.styleable.PerfectTextView_perfect_drawableStart_height, 0);
        drawableStartPadding = typedArray.getDimensionPixelOffset(R.styleable.PerfectTextView_perfect_drawableStart_padding, pad);

        drawableTopWidth = typedArray.getDimensionPixelOffset(R.styleable.PerfectTextView_perfect_drawableTop_width, 0);
        drawableTopHeight = typedArray.getDimensionPixelOffset(R.styleable.PerfectTextView_perfect_drawableTop_height, 0);
        drawableTopPadding = typedArray.getDimensionPixelOffset(R.styleable.PerfectTextView_perfect_drawableTop_padding, pad);

        drawableEndWidth = typedArray.getDimensionPixelOffset(R.styleable.PerfectTextView_perfect_drawableEnd_width, 0);
        drawableEndHeight = typedArray.getDimensionPixelOffset(R.styleable.PerfectTextView_perfect_drawableEnd_height, 0);
        drawableEndPadding = typedArray.getDimensionPixelOffset(R.styleable.PerfectTextView_perfect_drawableEnd_padding, pad);

        drawableBottomWidth = typedArray.getDimensionPixelOffset(R.styleable.PerfectTextView_perfect_drawableBottom_width, 0);
        drawableBottomHeight = typedArray.getDimensionPixelOffset(R.styleable.PerfectTextView_perfect_drawableBottom_height, 0);
        drawableBottomPadding = typedArray.getDimensionPixelOffset(R.styleable.PerfectTextView_perfect_drawableBottom_padding, pad);

        drawableLeftWidth = typedArray.getDimensionPixelOffset(R.styleable.PerfectTextView_perfect_drawableLeft_width, 0);
        drawableLeftHeight = typedArray.getDimensionPixelOffset(R.styleable.PerfectTextView_perfect_drawableLeft_height, 0);
        drawableLeftPadding = typedArray.getDimensionPixelOffset(R.styleable.PerfectTextView_perfect_drawableLeft_padding, pad);

        drawableRightWidth = typedArray.getDimensionPixelOffset(R.styleable.PerfectTextView_perfect_drawableRight_width, 0);
        drawableRightHeight = typedArray.getDimensionPixelOffset(R.styleable.PerfectTextView_perfect_drawableRight_height, 0);
        drawableRightPadding = typedArray.getDimensionPixelOffset(R.styleable.PerfectTextView_perfect_drawableRight_padding, pad);

        selectedText = typedArray.getText(R.styleable.PerfectTextView_perfect_selected_text);
        selectedHint = typedArray.getText(R.styleable.PerfectTextView_perfect_selected_hint);
        textBackground = typedArray.getDrawable(R.styleable.PerfectTextView_perfect_text_background);
        int textBackgroundScopeInt = typedArray.getInt(R.styleable.PerfectTextView_perfect_text_background_scope,0);
        textBackgroundScope = TextBackgroundScope.values()[textBackgroundScopeInt];

        defaultText = getText();
        defaultHint = getHint();

        typedArray.recycle();

        initCompoundDrawables();

        super.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    setPressed(false, event);
                    setPressed(false);
                }
                if (onTouchListener != null) {
                    onTouchListener.onTouch(v, event);
                }
                return true;
            }
        });
    }

    private void initCompoundDrawables() {
        Drawable[] drawablesRelative = getCompoundDrawablesRelative();
        Drawable[] drawables = getCompoundDrawables();

        Drawable drawableLeft = drawables[0];
        Drawable drawableTop = drawables[1];
        Drawable drawableRight = drawables[2];
        Drawable drawableBottom = drawables[3];

        Drawable drawableStart = drawablesRelative[0];
        Drawable drawableEnd = drawablesRelative[2];

        if (isRtl){
            setCompoundDrawablesRelative(drawableStart != null ? drawableStart : drawableRight, drawableTop,
                    drawableEnd != null ? drawableEnd : drawableLeft, drawableBottom);
        }else {
            setCompoundDrawablesRelative(drawableStart != null ? drawableStart : drawableLeft, drawableTop,
                    drawableEnd != null ? drawableEnd : drawableRight, drawableBottom);
        }

    }

    private int getDrawablePadding(int index) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables[index] != null) {
            return getDrawablePaddings()[index];
        } else {
            return 0;
        }
    }

    private int[] getDrawablePaddings() {
        int[] paddings = new int[4];
        paddings[0] = drawableLeftPadding;
        paddings[1] = drawableTopPadding;
        paddings[2] = drawableRightPadding;
        paddings[3] = drawableBottomPadding;
        if (isRtl){
            paddings[0] = drawableEndPadding != 0 ? drawableEndPadding : paddings[0];
            paddings[2] = drawableStartPadding != 0 ? drawableStartPadding : paddings[2];
        }else {
            paddings[0] = drawableStartPadding != 0 ? drawableStartPadding : paddings[0];
            paddings[2] = drawableEndPadding != 0 ? drawableEndPadding : paddings[2];
        }
        return paddings;
    }

    private void setDrawableWidthHeight(Drawable drawable, int width, int height) {
        if (drawable != null) {
            width = width == 0 ? drawable.getIntrinsicWidth() : width;
            height = height == 0 ? drawable.getIntrinsicHeight() : height;
            drawable.setBounds(0, 0, width, height);
        }
    }

    @Override
    public void setCompoundDrawables(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        setDrawableWidthHeight(top, drawableTopWidth, drawableTopHeight);
        setDrawableWidthHeight(bottom, drawableBottomWidth, drawableBottomHeight);
        if (isRtl){
            setDrawableWidthHeight(left, drawableEndWidth != 0 ? drawableEndWidth : drawableLeftWidth, drawableEndHeight != 0 ? drawableEndHeight : drawableLeftHeight);
            setDrawableWidthHeight(right, drawableStartWidth != 0 ? drawableStartWidth : drawableRightWidth, drawableStartHeight != 0 ? drawableStartHeight : drawableRightWidth);
        }else {
            setDrawableWidthHeight(left, drawableStartWidth != 0 ? drawableStartWidth : drawableLeftWidth, drawableStartHeight != 0 ? drawableStartHeight : drawableLeftHeight);
            setDrawableWidthHeight(right, drawableEndWidth != 0 ? drawableEndWidth : drawableRightWidth, drawableEndHeight != 0 ? drawableEndHeight : drawableRightHeight);
        }
        super.setCompoundDrawables(left, top, right, bottom);
    }

    @Override
    public void setCompoundDrawablesRelative(@Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
        setDrawableWidthHeight(top, drawableTopWidth, drawableTopHeight);
        setDrawableWidthHeight(bottom, drawableBottomWidth, drawableBottomHeight);
        if (isRtl){
            setDrawableWidthHeight(start, drawableStartWidth != 0 ? drawableStartWidth : drawableRightWidth, drawableStartHeight != 0 ? drawableStartHeight : drawableRightHeight);
            setDrawableWidthHeight(end, drawableEndWidth != 0 ? drawableEndWidth : drawableLeftWidth, drawableEndHeight != 0 ? drawableEndHeight : drawableLeftHeight);
        }else {
            setDrawableWidthHeight(start, drawableStartWidth != 0 ? drawableStartWidth : drawableLeftWidth, drawableStartHeight != 0 ? drawableStartHeight : drawableLeftHeight);
            setDrawableWidthHeight(end, drawableEndWidth != 0 ? drawableEndWidth : drawableRightWidth, drawableEndHeight != 0 ? drawableEndHeight : drawableRightHeight);
        }
        super.setCompoundDrawablesRelative(start, top, end, bottom);
    }

    public void setDrawableStart(Drawable drawableStart){
        Drawable[] drawablesRelative = getCompoundDrawablesRelative();
        Drawable[] drawables = getCompoundDrawables();

        Drawable drawableLeft = drawables[0];
        Drawable drawableTop = drawables[1];
        Drawable drawableRight = drawables[2];
        Drawable drawableBottom = drawables[3];

        Drawable drawableEnd = drawablesRelative[2];

        if (isRtl){
            setCompoundDrawablesRelative(drawableStart, drawableTop,
                    drawableEnd != null ? drawableEnd : drawableLeft, drawableBottom);
        }else {
            setCompoundDrawablesRelative(drawableStart, drawableTop,
                    drawableEnd != null ? drawableEnd : drawableRight, drawableBottom);
        }
    }

    public void setDrawableEnd(Drawable drawableEnd){
        Drawable[] drawablesRelative = getCompoundDrawablesRelative();
        Drawable[] drawables = getCompoundDrawables();

        Drawable drawableLeft = drawables[0];
        Drawable drawableTop = drawables[1];
        Drawable drawableRight = drawables[2];
        Drawable drawableBottom = drawables[3];

        Drawable drawableStart = drawablesRelative[0];

        if (isRtl){
            setCompoundDrawablesRelative(drawableStart != null ? drawableStart : drawableRight, drawableTop,
                    drawableEnd, drawableBottom);
        }else {
            setCompoundDrawablesRelative(drawableStart != null ? drawableStart : drawableLeft, drawableTop,
                    drawableEnd, drawableBottom);
        }
    }

    public void setDrawableLeft(Drawable drawableLeft){
        Drawable[] drawables = getCompoundDrawables();

        Drawable drawableTop = drawables[1];
        Drawable drawableRight = drawables[2];
        Drawable drawableBottom = drawables[3];

        setCompoundDrawables(drawableLeft, drawableTop,drawableRight, drawableBottom);
    }

    public void setDrawableRight(Drawable drawableRight){
        Drawable[] drawables = getCompoundDrawables();

        Drawable drawableLeft = drawables[0];
        Drawable drawableTop = drawables[1];
        Drawable drawableBottom = drawables[3];

        setCompoundDrawables(drawableLeft, drawableTop,drawableRight, drawableBottom);
    }

    public void setDrawableTop(Drawable drawableTop){
        Drawable[] drawablesRelative = getCompoundDrawablesRelative();
        Drawable[] drawables = getCompoundDrawables();

        Drawable drawableLeft = drawables[0];
        Drawable drawableRight = drawables[2];
        Drawable drawableBottom = drawables[3];

        Drawable drawableStart = drawablesRelative[0];
        Drawable drawableEnd = drawablesRelative[2];

        if (isRtl){
            setCompoundDrawablesRelative(drawableStart != null ? drawableStart : drawableRight, drawableTop,
                    drawableEnd != null ? drawableEnd : drawableLeft, drawableBottom);
        }else {
            setCompoundDrawablesRelative(drawableStart != null ? drawableStart : drawableLeft, drawableTop,
                    drawableEnd != null ? drawableEnd : drawableRight, drawableBottom);
        }
    }

    public void setDrawableBottom(Drawable drawableBottom){
        Drawable[] drawablesRelative = getCompoundDrawablesRelative();
        Drawable[] drawables = getCompoundDrawables();

        Drawable drawableLeft = drawables[0];
        Drawable drawableTop = drawables[1];
        Drawable drawableRight = drawables[2];

        Drawable drawableStart = drawablesRelative[0];
        Drawable drawableEnd = drawablesRelative[2];

        if (isRtl){
            setCompoundDrawablesRelative(drawableStart != null ? drawableStart : drawableRight, drawableTop,
                    drawableEnd != null ? drawableEnd : drawableLeft, drawableBottom);
        }else {
            setCompoundDrawablesRelative(drawableStart != null ? drawableStart : drawableLeft, drawableTop,
                    drawableEnd != null ? drawableEnd : drawableRight, drawableBottom);
        }
    }

    public void setDrawableStart(@DrawableRes int drawableStart){
        setDrawableStart(getResources().getDrawable(drawableStart));
    }

    public void setDrawableEnd(@DrawableRes int drawableEnd){
        setDrawableEnd(getResources().getDrawable(drawableEnd));
    }

    public void setDrawableLeft(@DrawableRes int drawableLeft){
        setDrawableLeft(getResources().getDrawable(drawableLeft));
    }

    public void setDrawableRight(@DrawableRes int drawableRight){
        setDrawableRight(getResources().getDrawable(drawableRight));
    }

    public void setDrawableTop(@DrawableRes int drawableTop){
        setDrawableTop(getResources().getDrawable(drawableTop));
    }

    public void setDrawableBottom(@DrawableRes int drawableBottom){
        setDrawableBottom(getResources().getDrawable(drawableBottom));
    }

    @Override
    public void setCompoundDrawablePadding(int pad) {
        if (!isInitSuper) {
            super.setCompoundDrawablePadding(pad);
            return;
        }
        drawableStartPadding = pad;
        drawableTopPadding = pad;
        drawableEndPadding = pad;
        drawableBottomPadding = pad;
        drawableLeftPadding = pad;
        drawableRightPadding = pad;
        super.setCompoundDrawablePadding(0);
    }

    @Override
    public int getCompoundPaddingLeft() {
        return super.getCompoundPaddingLeft() + getDrawablePadding(0);
    }

    @Override
    public int getCompoundPaddingTop() {
        return super.getCompoundPaddingTop() + getDrawablePadding(1);
    }

    @Override
    public int getCompoundPaddingRight() {
        return super.getCompoundPaddingRight() + getDrawablePadding(2);
    }

    @Override
    public int getCompoundPaddingBottom() {
        return super.getCompoundPaddingBottom() + getDrawablePadding(3);
    }

    private final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            clickEvent(e, ClickType.LongClick);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            clickEvent(e, ClickType.DoubleClick);
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            clickEvent(e, ClickType.Click);
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);

        }

        @Override
        public boolean onDown(MotionEvent e) {
            setPressed(true, e);
            return super.onDown(e);
        }

        private void clickEvent(MotionEvent e, ClickType clickType) {
            Drawable[] drawablesRelative = getCompoundDrawablesRelative();
            Drawable[] drawables = getCompoundDrawables();

            Drawable drawableTop = drawables[1];
            Drawable drawableBottom = drawables[3];

            Drawable drawableLeft = drawables[0];
            Drawable drawableRight = drawables[2];
            boolean isClick = false;
            if (drawableLeft != null) {
                int left = getPaddingLeft();
                int right = left + drawableLeft.getBounds().width();
                int iconMiddleY = (getHeight() - getCompoundPaddingTop() - getCompoundPaddingBottom()) / 2 + getCompoundPaddingTop();
                int top = iconMiddleY - drawableLeft.getBounds().height() / 2;
                int bottom = iconMiddleY + drawableLeft.getBounds().height() / 2;
                if (e.getX() >= left && e.getX() <= right &&
                        e.getY() >= top && e.getY() <= bottom) {
                    if (clickType == ClickType.Click) {
                        if (onDrawableLeftClickListener != null) {
                            onDrawableLeftClickListener.onClick(PerfectTextView.this);
                        }
                        if (drawablesRelative[0] == drawableLeft) {
                            if (onDrawableStartClickListener != null) {
                                onDrawableStartClickListener.onClick(PerfectTextView.this);
                            }
                        } else {
                            if (onDrawableEndClickListener != null) {
                                onDrawableEndClickListener.onClick(PerfectTextView.this);
                            }
                        }
                    } else if (clickType == ClickType.LongClick) {
                        if (onDrawableLeftLongClickListener != null) {
                            boolean b = onDrawableLeftLongClickListener.onLongClick(PerfectTextView.this);
                            if (!b){
                                clickEvent(e,ClickType.Click);
                            }
                        }
                        if (drawablesRelative[0] == drawableLeft) {
                            if (onDrawableStartLongClickListener != null) {
                                boolean b = onDrawableStartLongClickListener.onLongClick(PerfectTextView.this);
                                if (!b){
                                    clickEvent(e,ClickType.Click);
                                }
                            }
                        } else {
                            if (onDrawableEndLongClickListener != null) {
                                boolean b = onDrawableEndLongClickListener.onLongClick(PerfectTextView.this);
                                if (!b){
                                    clickEvent(e,ClickType.Click);
                                }
                            }
                        }
                    } else if (clickType == ClickType.DoubleClick) {
                        if (onDrawableLeftDoubleClickListener != null) {
                            onDrawableLeftDoubleClickListener.onClick(PerfectTextView.this);
                        }
                        if (drawablesRelative[0] == drawableLeft) {
                            if (onDrawableStartDoubleClickListener != null) {
                                onDrawableStartDoubleClickListener.onClick(PerfectTextView.this);
                            }
                        } else {
                            if (onDrawableEndDoubleClickListener != null) {
                                onDrawableEndDoubleClickListener.onClick(PerfectTextView.this);
                            }
                        }
                    }

                    isClick = true;
                }
            }
            if (drawableTop != null) {
                int iconMiddleX = (getWidth() - getCompoundPaddingLeft() - getCompoundPaddingRight()) / 2 + getCompoundPaddingLeft();
                int left = iconMiddleX - drawableTop.getBounds().width() / 2;
                int right = iconMiddleX + drawableTop.getBounds().width() / 2;

                int top = getPaddingTop();
                int bottom = top + drawableTop.getBounds().height();
                if (e.getX() >= left && e.getX() <= right &&
                        e.getY() >= top && e.getY() <= bottom) {
                    if (clickType == ClickType.Click && onDrawableTopClickListener != null) {
                        onDrawableTopClickListener.onClick(PerfectTextView.this);
                    } else if (clickType == ClickType.LongClick && onDrawableTopLongClickListener != null) {
                        boolean b = onDrawableTopLongClickListener.onLongClick(PerfectTextView.this);
                        if (!b){
                            clickEvent(e,ClickType.Click);
                        }
                    } else if (clickType == ClickType.DoubleClick && onDrawableTopDoubleClickListener != null) {
                        onDrawableTopDoubleClickListener.onClick(PerfectTextView.this);
                    }
                    isClick = true;
                }
            }
            if (drawableRight != null) {
                int right = getWidth() - getPaddingRight();
                int left = right - drawableRight.getBounds().width();
                int iconMiddleY = (getHeight() - getCompoundPaddingTop() - getCompoundPaddingBottom()) / 2 + getCompoundPaddingTop();
                int top = iconMiddleY - drawableRight.getBounds().height() / 2;
                int bottom = iconMiddleY + drawableRight.getBounds().height() / 2;
                if (e.getX() >= left && e.getX() <= right &&
                        e.getY() >= top && e.getY() <= bottom) {
                    if (clickType == ClickType.Click) {
                        if (onDrawableRightClickListener != null) {
                            onDrawableRightClickListener.onClick(PerfectTextView.this);
                        }
                        if (drawablesRelative[2] == drawableRight) {
                            if (onDrawableEndClickListener != null) {
                                onDrawableEndClickListener.onClick(PerfectTextView.this);
                            }
                        } else {
                            if (onDrawableStartClickListener != null) {
                                onDrawableStartClickListener.onClick(PerfectTextView.this);
                            }
                        }
                    } else if (clickType == ClickType.LongClick) {
                        if (onDrawableRightLongClickListener != null) {
                            boolean b = onDrawableRightLongClickListener.onLongClick(PerfectTextView.this);
                            if (!b){
                                clickEvent(e,ClickType.Click);
                            }
                        }
                        if (drawablesRelative[2] == drawableRight) {
                            if (onDrawableEndLongClickListener != null) {
                                boolean b = onDrawableEndLongClickListener.onLongClick(PerfectTextView.this);
                                if (!b){
                                    clickEvent(e,ClickType.Click);
                                }
                            }
                        } else {
                            if (onDrawableStartLongClickListener != null) {
                                boolean b = onDrawableStartLongClickListener.onLongClick(PerfectTextView.this);
                                if (!b){
                                    clickEvent(e,ClickType.Click);
                                }
                            }
                        }
                    } else if (clickType == ClickType.DoubleClick) {
                        if (onDrawableRightDoubleClickListener != null) {
                            onDrawableRightDoubleClickListener.onClick(PerfectTextView.this);
                        }
                        if (drawablesRelative[2] == drawableRight) {
                            if (onDrawableEndDoubleClickListener != null) {
                                onDrawableEndDoubleClickListener.onClick(PerfectTextView.this);
                            }
                        } else {
                            if (onDrawableStartDoubleClickListener != null) {
                                onDrawableStartDoubleClickListener.onClick(PerfectTextView.this);
                            }
                        }
                    }

                    isClick = true;
                }
            }

            if (drawableBottom != null) {
                int bottom = getHeight() - getPaddingBottom();
                int top = bottom - drawableBottom.getBounds().height();
                int iconMiddleX = (getWidth() - getCompoundPaddingLeft() - getCompoundPaddingRight()) / 2 + getCompoundPaddingLeft();
                int left = iconMiddleX - drawableBottom.getBounds().width() / 2;
                int right = iconMiddleX + drawableBottom.getBounds().width() / 2;
                if (e.getX() >= left && e.getX() <= right &&
                        e.getY() >= top && e.getY() <= bottom) {
                    if (clickType == ClickType.Click && onDrawableBottomClickListener != null) {
                        onDrawableBottomClickListener.onClick(PerfectTextView.this);
                    } else if (clickType == ClickType.LongClick && onDrawableBottomLongClickListener != null) {
                        boolean b = onDrawableBottomLongClickListener.onLongClick(PerfectTextView.this);
                        if (!b){
                            clickEvent(e,ClickType.Click);
                        }
                    } else if (clickType == ClickType.DoubleClick && onDrawableBottomDoubleClickListener != null) {
                        onDrawableBottomDoubleClickListener.onClick(PerfectTextView.this);
                    }
                    isClick = true;
                }
            }

            if (!isClick) {
                int left = getCompoundPaddingLeft();
                int top = getCompoundPaddingTop();
                int right = getWidth()-getCompoundPaddingRight();
                int bottom = getHeight()-getCompoundPaddingBottom();
                boolean isInTextScope = e.getX() >= left && e.getX() <= right &&
                        e.getY() >= top && e.getY() <= bottom;
                if (clickType == ClickType.Click && onClickListener != null && (clickScope == ClickScope.allScope
                        ||(clickScope == ClickScope.textScope && isInTextScope))){
                    onClickListener.onClick(PerfectTextView.this);
                }
                if (clickType == ClickType.LongClick && onLongClickListener != null && (longClickScope == ClickScope.allScope
                        ||(longClickScope == ClickScope.textScope && isInTextScope))){
                    boolean b = onLongClickListener.onLongClick(PerfectTextView.this);
                    if (!b){
                        clickEvent(e,ClickType.Click);
                    }
                }
                if (clickType == ClickType.DoubleClick && onDoubleClickListener != null && (doubleClickScope == ClickScope.allScope
                        ||(doubleClickScope == ClickScope.textScope && isInTextScope))){
                    onDoubleClickListener.onClick(PerfectTextView.this);
                }
            }
        }

    });

    private enum ClickType {
        Click,
        DoubleClick,
        LongClick
    }

    private void setPressed(boolean pressed, MotionEvent e) {
        Drawable[] drawablesRelative = getCompoundDrawablesRelative();
        Drawable[] drawables = getCompoundDrawables();

        Drawable drawableTop = drawables[1];
        Drawable drawableBottom = drawables[3];

        Drawable drawableLeft = drawables[0];
        Drawable drawableRight = drawables[2];
        boolean isClick = false;
        if (drawableLeft != null) {
            int left = getPaddingLeft();
            int right = left + drawableLeft.getBounds().width();
            int iconMiddleY = (getHeight() - getCompoundPaddingTop() - getCompoundPaddingBottom()) / 2 + getCompoundPaddingTop();
            int top = iconMiddleY - drawableLeft.getBounds().height() / 2;
            int bottom = iconMiddleY + drawableLeft.getBounds().height() / 2;
            if (e.getX() >= left && e.getX() <= right &&
                    e.getY() >= top && e.getY() <= bottom) {
                if (drawablesRelative[0] == drawableLeft) {
                    if (onDrawableStartClickListener != null || onDrawableLeftClickListener != null) {
                        setPressed(drawableLeft, pressed);
                    }
                } else {
                    if (onDrawableEndClickListener != null || onDrawableLeftClickListener != null) {
                        setPressed(drawableLeft, pressed);
                    }
                }

                isClick = true;
            }
        }
        if (drawableTop != null) {
            int iconMiddleX = (getWidth() - getCompoundPaddingLeft() - getCompoundPaddingRight()) / 2 + getCompoundPaddingLeft();
            int left = iconMiddleX - drawableTop.getBounds().width() / 2;
            int right = iconMiddleX + drawableTop.getBounds().width() / 2;

            int top = getPaddingTop();
            int bottom = top + drawableTop.getBounds().height();
            if (e.getX() >= left && e.getX() <= right &&
                    e.getY() >= top && e.getY() <= bottom) {
                if (onDrawableTopClickListener != null) {
                    setPressed(drawableTop, pressed);
                }
                isClick = true;
            }
        }
        if (drawableRight != null) {
            int right = getWidth() - getPaddingRight();
            int left = right - drawableRight.getBounds().width();
            int iconMiddleY = (getHeight() - getCompoundPaddingTop() - getCompoundPaddingBottom()) / 2 + getCompoundPaddingTop();
            int top = iconMiddleY - drawableRight.getBounds().height() / 2;
            int bottom = iconMiddleY + drawableRight.getBounds().height() / 2;
            if (e.getX() >= left && e.getX() <= right &&
                    e.getY() >= top && e.getY() <= bottom) {
                if (drawablesRelative[2] == drawableRight) {
                    if (onDrawableEndClickListener != null || onDrawableRightClickListener != null) {
                        setPressed(drawableRight, pressed);
                    }
                } else {
                    if (onDrawableStartClickListener != null || onDrawableRightClickListener != null) {
                        setPressed(drawableRight, pressed);
                    }
                }

                isClick = true;
            }
        }

        if (drawableBottom != null) {
            int bottom = getHeight() - getPaddingBottom();
            int top = bottom - drawableBottom.getBounds().height();
            int iconMiddleX = (getWidth() - getCompoundPaddingLeft() - getCompoundPaddingRight()) / 2 + getCompoundPaddingLeft();
            int left = iconMiddleX - drawableBottom.getBounds().width() / 2;
            int right = iconMiddleX + drawableBottom.getBounds().width() / 2;
            if (e.getX() >= left && e.getX() <= right &&
                    e.getY() >= top && e.getY() <= bottom) {
                if (onDrawableBottomClickListener != null) {
                    setPressed(drawableBottom, pressed);
                }
                isClick = true;
            }
        }

        if (!pressed) {
            setPressed(drawableLeft, false);
            setPressed(drawableTop, false);
            setPressed(drawableRight, false);
            setPressed(drawableBottom, false);
        }
        int left = getCompoundPaddingLeft();
        int top = getCompoundPaddingTop();
        int right = getWidth()-getCompoundPaddingRight();
        int bottom = getHeight()-getCompoundPaddingBottom();
        if (!isClick && onClickListener != null) {
            if (clickScope == ClickScope.allScope || (clickScope == ClickScope.textScope && e.getX() >= left && e.getX() <= right &&
                    e.getY() >= top && e.getY() <= bottom)){
                setPressed(true);
            }
        }

    }

    private void setPressed(Drawable drawable, boolean pressed) {
        if (drawable != null && drawable.isStateful()) {
            int[] oldState = drawable.getState();
            int[] newState;
            if (oldState != null) {
                HashSet<Integer> set = new HashSet<>();
                for (int i : oldState) {
                    set.add(i);
                }
                if (pressed) {
                    set.add(android.R.attr.state_pressed);
                } else {
                    set.remove(android.R.attr.state_pressed);
                }
                newState = new int[set.size()];
                int index = 0;
                for (int i : set) {
                    newState[index] = i;
                    index++;
                }
            } else {
                newState = pressed ? new int[]{android.R.attr.state_pressed} : new int[]{};
            }

            drawable.setState(newState);
        }
    }

    private void setSelected(Drawable drawable, boolean selected) {
        if (drawable != null) {
            int[] oldState = drawable.getState();
            int[] newState;
            if (oldState != null) {
                HashSet<Integer> set = new HashSet<>();
                for (int i : oldState) {
                    set.add(i);
                }
                if (selected) {
                    set.add(android.R.attr.state_selected);
                } else {
                    set.remove(android.R.attr.state_selected);
                }
                newState = new int[set.size()];
                int index = 0;
                for (int i : set) {
                    newState[index] = i;
                    index++;
                }
            } else {
                newState = selected ? new int[]{android.R.attr.state_selected} : new int[]{};
            }
            drawable.setState(newState);
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable[] drawables = getCompoundDrawables();

        Drawable drawableTop = drawables[1];
        Drawable drawableBottom = drawables[3];

        Drawable drawableLeft = drawables[0];
        Drawable drawableRight = drawables[2];
        setPressed(drawableLeft, false);
        setPressed(drawableTop, false);
        setPressed(drawableRight, false);
        setPressed(drawableBottom, false);

        setDrawableLeftSelected(isDrawableLeftSelected);
        setDrawableTopSelected(isDrawableTopSelected);
        setDrawableRightSelected(isDrawableRightSelected);
        setDrawableBottomSelected(isDrawableBottomSelected);
    }


    @Override
    public void setOnTouchListener(OnTouchListener l) {
        onTouchListener = l;
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        setDrawableLeftSelected(selected);
        setDrawableTopSelected(selected);
        setDrawableRightSelected(selected);
        setDrawableBottomSelected(selected);
        setSelectText();
        setSelected(textBackground,selected);
    }

    /**
     * 设置是否选中时不会影响四个方向的Drawable的状态
     * @param selected 是否选中
     */
    public void setSelectedIgnoreDrawable(boolean selected) {
        super.setSelected(selected);
        setDrawableLeftSelected(isDrawableLeftSelected);
        setDrawableTopSelected(isDrawableTopSelected);
        setDrawableRightSelected(isDrawableRightSelected);
        setDrawableBottomSelected(isDrawableBottomSelected);
        setSelectText();
        setSelected(textBackground,selected);
    }

    private boolean isUpdateDefaultText = true;
    @Override
    public void setText(CharSequence text, BufferType type) {
        if (isUpdateDefaultText){
            defaultText = text;
        }
        if (!isUpdateDefaultText || !isSelected()){
            super.setText(text, type);
        }
        isUpdateDefaultText = true;
    }

    private void setSelectText(){
        isUpdateDefaultText = false;
        if (isSelected()){
            setText(selectedText);
            setHint(selectedHint);
        }else {
            setText(defaultText);
            setHint(defaultHint);
        }
    }

    /**
     * 设置选中时文本
     * @param selectedText
     */
    public void setSelectedText(CharSequence selectedText) {
        this.selectedText = selectedText;
        setSelectText();
    }

    /**
     * 设置选中时文本
     * @param resid
     */
    public void setSelectedText(@StringRes int resid) {
        setSelectedText(getContext().getResources().getText(resid));
    }

    /**
     * 设置默认状态时提示文本
     * @param defaultHint
     */
    public void setDefaultHint(CharSequence defaultHint) {
        this.defaultHint = defaultHint;
        setSelectText();
    }

    /**
     * 设置选中时提示文本
     * @param selectedHint
     */
    public void setSelectedHint(CharSequence selectedHint) {
        this.selectedHint = selectedHint;
        setSelectText();
    }

    /**
     * 设置默认状态时提示文本
     * @param resid
     */
    public void setDefaultHint(@StringRes int resid) {
        setDefaultHint(getContext().getResources().getText(resid));
    }

    /**
     * 设置选中时提示文本
     * @param resid
     */
    public void setSelectedHint(@StringRes int resid) {
        setSelectedHint(getContext().getResources().getText(resid));
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        setDrawableLeftSelected(isDrawableLeftSelected);
        setDrawableTopSelected(isDrawableTopSelected);
        setDrawableRightSelected(isDrawableRightSelected);
        setDrawableBottomSelected(isDrawableBottomSelected);
        setPressed(textBackground, pressed);
    }

    private boolean isDrawableLeftSelected;
    private boolean isDrawableTopSelected;
    private boolean isDrawableRightSelected;
    private boolean isDrawableBottomSelected;

    public void setDrawableStartSelected(boolean selected) {
        if (isRtl) {
            setDrawableRightSelected(selected);
        } else {
            setDrawableLeftSelected(selected);
        }
    }

    public void setDrawableEndSelected(boolean selected) {
        if (isRtl) {
            setDrawableLeftSelected(selected);
        } else {
            setDrawableRightSelected(selected);
        }
    }

    public void setDrawableLeftSelected(boolean selected) {
        Drawable[] drawables = getCompoundDrawables();
        Drawable drawableLeft = drawables[0];
        setSelected(drawableLeft, selected);
        isDrawableLeftSelected = selected;
    }

    public void setDrawableRightSelected(boolean selected) {
        Drawable[] drawables = getCompoundDrawables();
        Drawable drawableRight = drawables[2];
        setSelected(drawableRight, selected);
        isDrawableRightSelected = selected;
    }

    public void setDrawableTopSelected(boolean selected) {
        Drawable[] drawables = getCompoundDrawables();
        Drawable drawableTop = drawables[1];
        setSelected(drawableTop, selected);
        isDrawableTopSelected = selected;
    }

    public void setDrawableBottomSelected(boolean selected) {
        Drawable[] drawables = getCompoundDrawables();
        Drawable drawableBottom = drawables[3];
        setSelected(drawableBottom, selected);
        isDrawableBottomSelected = selected;
    }



    /**
     * 设置文本区域的背景
     * @param textBackground 背景Drawable
     */
    public void setTextBackground(Drawable textBackground) {
        this.textBackground = textBackground;
        Rect result = new Rect();

        result.left = getCompoundPaddingLeft();
        result.top = getCompoundPaddingTop();
        result.right = getWidth()-getCompoundPaddingRight();
        result.bottom = getHeight()-getCompoundPaddingBottom();

        invalidate(result);
    }

    /**
     * 设置文本区域的背景
     * @param textBackgroundRes 图片资源id
     */
    public void setTextBackgroundResource(@DrawableRes int textBackgroundRes) {
        this.setTextBackground(getResources().getDrawable(textBackgroundRes));
    }

    /**
     * 设置文本区域的背景
     * @param textBackgroundColor 颜色
     */
    public void setTextBackgroundColor(@ColorInt int textBackgroundColor) {
        this.setTextBackground(new ColorDrawable(textBackgroundColor));
    }

    public TextBackgroundScope getTextBackgroundScope() {
        return textBackgroundScope;
    }

    /**
     * 设置背景显示区域
     * @param textBackgroundScope
     */
    public void setTextBackgroundScope(TextBackgroundScope textBackgroundScope) {
        this.textBackgroundScope = textBackgroundScope;
        Rect result = new Rect();

        result.left = getCompoundPaddingLeft();
        result.top = getCompoundPaddingTop();
        result.right = getWidth()-getCompoundPaddingRight();
        result.bottom = getHeight()-getCompoundPaddingBottom();

        invalidate(result);
    }

    private Rect getTextBound() {
        Rect result = new Rect();
        if (textBackgroundScope == TextBackgroundScope.fitDrawablePadding || TextUtils.isEmpty(getText())){
            result.left = getCompoundPaddingLeft();
            result.top = getCompoundPaddingTop();
            result.right = getWidth()-getCompoundPaddingRight();
            result.bottom = getHeight()-getCompoundPaddingBottom();
        }else {
            Layout layout = getLayout();
            Drawable[] drawables = getCompoundDrawables();

            Drawable drawableLeft = drawables[0];
            Drawable drawableRight = drawables[2];

            int yOffset = 0;
            if (drawableLeft != null && drawableRight != null){
                if (drawableLeft.getBounds().height()>layout.getHeight() || drawableRight.getBounds().height()>layout.getHeight()){
                    yOffset = (Math.max(drawableLeft.getBounds().height(),drawableRight.getBounds().height()) - layout.getHeight())/2;
                }
            }else if (drawableLeft != null){
                if (drawableLeft.getBounds().height()>layout.getHeight()){
                    yOffset = (drawableLeft.getBounds().height() - layout.getHeight())/2;
                }
            }else if (drawableRight != null){
                if (drawableRight.getBounds().height()>layout.getHeight()){
                    yOffset = (drawableRight.getBounds().height() - layout.getHeight())/2;
                }
            }
            getLineLeftRight(result);
            result.top = result.top+yOffset;
            result.bottom =result.bottom  + yOffset;
        }
        return result;
    }

    private void getLineLeftRight(Rect bound){
        Layout layout = getLayout();
        int lineCount = layout.getLineCount();
        float left = getWidth();
        float right = 0;
        float top = getHeight();
        float bottom = 0;
        for (int i = 0; i < lineCount; i++) {
            float lineLeft = layout.getLineLeft(i);
            if (lineLeft<left){
                left = lineLeft;
            }
            float lineRight = layout.getLineRight(i);
            if (lineRight>right){
                right = lineRight;
            }

            float lineTop = layout.getLineTop(i);
            if (lineTop<top){
                top = lineTop;
            }
            float lineBottom = layout.getLineBottom(i);
            if (lineBottom>bottom){
                bottom = lineBottom;
            }

        }
//        bound.left = getCompoundPaddingLeft();
//        bound.right = getWidth() - getCompoundPaddingRight();
        bound.left = (int) left+getCompoundPaddingLeft();
        bound.right = (int) right+getCompoundPaddingLeft();

        bound.top = (int) top+getCompoundPaddingTop();
        bound.bottom = (int) bottom+getCompoundPaddingTop();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (textBackground != null){
            Rect result = getTextBound();
            textBackground.setBounds(result);
            textBackground.draw(canvas);
        }
        super.onDraw(canvas);
    }

    private OnClickListener onClickListener;
    private OnLongClickListener onLongClickListener;
    private OnClickListener onDoubleClickListener;
    private OnClickListener onDrawableStartClickListener;
    private OnClickListener onDrawableEndClickListener;
    private OnClickListener onDrawableLeftClickListener;
    private OnClickListener onDrawableTopClickListener;
    private OnClickListener onDrawableRightClickListener;
    private OnClickListener onDrawableBottomClickListener;

    private OnLongClickListener onDrawableStartLongClickListener;
    private OnLongClickListener onDrawableEndLongClickListener;
    private OnLongClickListener onDrawableLeftLongClickListener;
    private OnLongClickListener onDrawableTopLongClickListener;
    private OnLongClickListener onDrawableRightLongClickListener;
    private OnLongClickListener onDrawableBottomLongClickListener;

    private OnClickListener onDrawableStartDoubleClickListener;
    private OnClickListener onDrawableEndDoubleClickListener;
    private OnClickListener onDrawableLeftDoubleClickListener;
    private OnClickListener onDrawableTopDoubleClickListener;
    private OnClickListener onDrawableRightDoubleClickListener;
    private OnClickListener onDrawableBottomDoubleClickListener;

    private OnTouchListener onTouchListener;

    public void setOnDrawableStartLongClickListener(OnLongClickListener l) {
        onDrawableStartLongClickListener = l;
    }

    public void setOnDrawableEndLongClickListener(OnLongClickListener l) {
        onDrawableEndLongClickListener = l;
    }

    public void setOnDrawableLeftLongClickListener(OnLongClickListener l) {
        onDrawableLeftLongClickListener = l;
    }

    public void setOnDrawableTopLongClickListener(OnLongClickListener l) {
        onDrawableTopLongClickListener = l;
    }

    public void setOnDrawableRightLongClickListener(OnLongClickListener l) {
        onDrawableRightLongClickListener = l;
    }

    public void setOnDrawableBottomLongClickListener(OnLongClickListener l) {
        onDrawableBottomLongClickListener = l;
    }

    public void setOnDrawableStartDoubleClickListener(OnClickListener l) {
        onDrawableStartDoubleClickListener = l;
    }

    public void setOnDrawableEndDoubleClickListener(OnClickListener l) {
        onDrawableEndDoubleClickListener = l;
    }

    public void setOnDrawableLeftDoubleClickListener(OnClickListener l) {
        onDrawableLeftDoubleClickListener = l;
    }

    public void setOnDrawableTopDoubleClickListener(OnClickListener l) {
        onDrawableTopDoubleClickListener = l;
    }

    public void setOnDrawableRightDoubleClickListener(OnClickListener l) {
        onDrawableRightDoubleClickListener = l;
    }

    public void setOnDrawableBottomDoubleClickListener(OnClickListener l) {
        onDrawableBottomDoubleClickListener = l;
    }

    public void setOnDrawableStartClickListener(OnClickListener l) {
        onDrawableStartClickListener = l;
    }

    public void setOnDrawableEndClickListener(OnClickListener l) {
        onDrawableEndClickListener = l;
    }

    public void setOnDrawableLeftClickListener(OnClickListener l) {
        onDrawableLeftClickListener = l;
    }

    public void setOnDrawableTopClickListener(OnClickListener l) {
        onDrawableTopClickListener = l;
    }


    public void setOnDrawableRightClickListener(OnClickListener l) {
        onDrawableRightClickListener = l;
    }

    public void setOnDrawableBottomClickListener(OnClickListener l) {
        onDrawableBottomClickListener = l;
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        setOnClickListener(l, ClickScope.allScope);
    }

    public void setOnClickListener(@Nullable OnClickListener l, ClickScope clickScope) {
        onClickListener = l;
        this.clickScope = clickScope;
    }

    @Override
    public void setOnLongClickListener(@Nullable OnLongClickListener l) {
        setOnLongClickListener(l,ClickScope.allScope);
    }

    public void setOnLongClickListener(@Nullable OnLongClickListener l, ClickScope clickScope) {
        onLongClickListener = l;
        this.longClickScope = clickScope;
    }

    public void setOnDoubleClickListener(@Nullable OnClickListener l) {
        setOnDoubleClickListener(l,ClickScope.allScope);
    }

    public void setOnDoubleClickListener(@Nullable OnClickListener l, ClickScope clickScope) {
        onDoubleClickListener = l;
        this.doubleClickScope = clickScope;
    }



    public void setDrawableStartWidthHeight(int width, int height) {
        drawableStartWidth = width;
        drawableStartHeight = height;
        initCompoundDrawables();
    }

    public void setDrawableTopWidthHeight(int width, int height) {
        drawableTopWidth = width;
        drawableTopHeight = height;
        initCompoundDrawables();
    }

    public void setDrawableEndWidthHeight(int width, int height) {
        drawableEndWidth = width;
        drawableEndHeight = height;
        initCompoundDrawables();
    }

    public void setDrawableBottomWidthHeight(int width, int height) {
        drawableBottomWidth = width;
        drawableBottomHeight = height;
        initCompoundDrawables();
    }

    public void setDrawableLeftWidthHeight(int width, int height) {
        drawableLeftWidth = width;
        drawableLeftHeight = height;
        initCompoundDrawables();
    }

    public void setDrawableRightWidthHeight(int width, int height) {
        drawableRightWidth = width;
        drawableRightHeight = height;
        initCompoundDrawables();
    }

    public void setDrawableStartPadding(int drawableStartPadding) {
        this.drawableStartPadding = drawableStartPadding;
        super.setCompoundDrawablePadding(0);
    }

    public void setDrawableTopPadding(int drawableTopPadding) {
        this.drawableTopPadding = drawableTopPadding;
        super.setCompoundDrawablePadding(0);
    }

    public void setDrawableEndPadding(int drawableEndPadding) {
        this.drawableEndPadding = drawableEndPadding;
        super.setCompoundDrawablePadding(0);
    }

    public void setDrawableBottomPadding(int drawableBottomPadding) {
        this.drawableBottomPadding = drawableBottomPadding;
        super.setCompoundDrawablePadding(0);
    }

    public void setDrawableLeftPadding(int drawableLeftPadding) {
        this.drawableLeftPadding = drawableLeftPadding;
        super.setCompoundDrawablePadding(0);
    }

    public void setDrawableRightPadding(int drawableRightPadding) {
        this.drawableRightPadding = drawableRightPadding;
        super.setCompoundDrawablePadding(0);
    }

    public boolean isDrawableStartSelected() {
        return isRtl ? isDrawableRightSelected : isDrawableLeftSelected;
    }

    public boolean isDrawableEndSelected() {
        return isRtl ? isDrawableLeftSelected : isDrawableRightSelected;
    }

    public boolean isDrawableLeftSelected() {
        return isDrawableLeftSelected;
    }

    public boolean isDrawableTopSelected() {
        return isDrawableTopSelected;
    }

    public boolean isDrawableRightSelected() {
        return isDrawableRightSelected;
    }

    public boolean isDrawableBottomSelected() {
        return isDrawableBottomSelected;
    }

    public Drawable getTextBackground() {
        return textBackground;
    }

    public int getDrawableStartWidth() {
        return drawableStartWidth;
    }

    public int getDrawableStartHeight() {
        return drawableStartHeight;
    }

    public int getDrawableTopWidth() {
        return drawableTopWidth;
    }

    public int getDrawableTopHeight() {
        return drawableTopHeight;
    }

    public int getDrawableEndWidth() {
        return drawableEndWidth;
    }

    public int getDrawableEndHeight() {
        return drawableEndHeight;
    }

    public int getDrawableBottomWidth() {
        return drawableBottomWidth;
    }

    public int getDrawableBottomHeight() {
        return drawableBottomHeight;
    }

    public int getDrawableLeftWidth() {
        return drawableLeftWidth;
    }

    public int getDrawableLeftHeight() {
        return drawableLeftHeight;
    }

    public int getDrawableRightWidth() {
        return drawableRightWidth;
    }

    public int getDrawableRightHeight() {
        return drawableRightHeight;
    }

    public int getDrawableStartPadding() {
        return drawableStartPadding;
    }

    public int getDrawableTopPadding() {
        return drawableTopPadding;
    }

    public int getDrawableEndPadding() {
        return drawableEndPadding;
    }

    public int getDrawableBottomPadding() {
        return drawableBottomPadding;
    }

    public int getDrawableLeftPadding() {
        return drawableLeftPadding;
    }

    public int getDrawableRightPadding() {
        return drawableRightPadding;
    }
}
