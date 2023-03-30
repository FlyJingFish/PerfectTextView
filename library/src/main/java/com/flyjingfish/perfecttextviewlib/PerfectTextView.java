package com.flyjingfish.perfecttextviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.LayoutDirection;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.text.TextUtilsCompat;

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
    private boolean isRtl;

    public PerfectTextView(@NonNull Context context) {
        this(context, null);
    }

    public PerfectTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PerfectTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            isRtl = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == LayoutDirection.RTL;
        }
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

        typedArray.recycle();

        initCompoundDrawables();

        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

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


        switch (isRtl ? LAYOUT_DIRECTION_RTL : LAYOUT_DIRECTION_LTR) {
            default:
            case LAYOUT_DIRECTION_LTR:
                setCompoundDrawablesRelative(drawableStart != null ? drawableStart : drawableLeft, drawableTop,
                        drawableEnd != null ? drawableEnd : drawableRight, drawableBottom);
                break;
            case LAYOUT_DIRECTION_RTL:
                setCompoundDrawablesRelative(drawableStart != null ? drawableStart : drawableRight, drawableTop,
                        drawableEnd != null ? drawableEnd : drawableLeft, drawableBottom);
                break;
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
        switch (isRtl ? LAYOUT_DIRECTION_RTL : LAYOUT_DIRECTION_LTR) {
            default:
            case LAYOUT_DIRECTION_LTR:
                paddings[0] = drawableStartPadding != 0 ? drawableStartPadding : paddings[0];
                paddings[2] = drawableEndPadding != 0 ? drawableEndPadding : paddings[2];
                break;
            case LAYOUT_DIRECTION_RTL:
                paddings[0] = drawableEndPadding != 0 ? drawableEndPadding : paddings[0];
                paddings[2] = drawableStartPadding != 0 ? drawableStartPadding : paddings[2];
                break;
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
//        setDrawableWidthHeight(left, drawableLeftWidth, drawableLeftHeight);
//        setDrawableWidthHeight(right, drawableRightWidth, drawableRightHeight);
        setDrawableWidthHeight(top, drawableTopWidth, drawableTopHeight);
        setDrawableWidthHeight(bottom, drawableBottomWidth, drawableBottomHeight);

        switch (isRtl ? LAYOUT_DIRECTION_RTL : LAYOUT_DIRECTION_LTR) {
            default:
            case LAYOUT_DIRECTION_LTR:
                setDrawableWidthHeight(left, drawableStartWidth != 0 ? drawableStartWidth : drawableLeftWidth, drawableStartHeight != 0 ? drawableStartHeight : drawableLeftHeight);
                setDrawableWidthHeight(right, drawableEndWidth != 0 ? drawableEndWidth : drawableRightWidth, drawableEndHeight != 0 ? drawableEndHeight : drawableRightHeight);
                break;
            case LAYOUT_DIRECTION_RTL:
                setDrawableWidthHeight(left, drawableEndWidth != 0 ? drawableEndWidth : drawableLeftWidth, drawableEndHeight != 0 ? drawableEndHeight : drawableLeftHeight);
                setDrawableWidthHeight(right, drawableStartWidth != 0 ? drawableStartWidth : drawableRightWidth, drawableStartHeight != 0 ? drawableStartHeight : drawableRightWidth);
                break;
        }
        super.setCompoundDrawables(left, top, right, bottom);
    }

    @Override
    public void setCompoundDrawablesRelative(@Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
        setDrawableWidthHeight(top, drawableTopWidth, drawableTopHeight);
        setDrawableWidthHeight(bottom, drawableBottomWidth, drawableBottomHeight);
//        setDrawableWidthHeight(start, drawableStartWidth !=0 ?drawableStartWidth:drawableLeftWidth, drawableStartHeight!=0?drawableStartHeight:drawableLeftHeight);
//        setDrawableWidthHeight(end, drawableEndWidth, drawableEndHeight);
        switch (isRtl ? LAYOUT_DIRECTION_RTL : LAYOUT_DIRECTION_LTR) {
            default:
            case LAYOUT_DIRECTION_LTR:
                setDrawableWidthHeight(start, drawableStartWidth != 0 ? drawableStartWidth : drawableLeftWidth, drawableStartHeight != 0 ? drawableStartHeight : drawableLeftHeight);
                setDrawableWidthHeight(end, drawableEndWidth != 0 ? drawableEndWidth : drawableRightWidth, drawableEndHeight != 0 ? drawableEndHeight : drawableRightHeight);
                break;
            case LAYOUT_DIRECTION_RTL:
                setDrawableWidthHeight(start, drawableStartWidth != 0 ? drawableStartWidth : drawableRightWidth, drawableStartHeight != 0 ? drawableStartHeight : drawableRightHeight);
                setDrawableWidthHeight(end, drawableEndWidth != 0 ? drawableEndWidth : drawableLeftWidth, drawableEndHeight != 0 ? drawableEndHeight : drawableLeftHeight);
                break;
        }
        super.setCompoundDrawablesRelative(start, top, end, bottom);
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
        onClickListener = l;
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        onLongClickListener = l;
    }

    public void setOnDoubleClickListener(OnClickListener l) {
        onDoubleClickListener = l;
    }

    final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
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

        private void clickEvent(MotionEvent e, ClickType clickType) {
            Drawable[] drawablesRelative = getCompoundDrawablesRelative();
            Drawable[] drawables = getCompoundDrawables();
//
//            Drawable drawableLeft = drawables[0];
            Drawable drawableTop = drawables[1];
//            Drawable drawableRight = drawables[2];
            Drawable drawableBottom = drawables[3];

            Drawable drawableLeft = drawables[0];
            Drawable drawableRight = drawables[2];
            boolean isClick = false;
            if (drawableLeft != null) {
                int left = getPaddingLeft();
                int topOffset = 0;
                if (drawableRight != null) {
                    if (drawableRight.getBounds().height() > drawableLeft.getBounds().height()) {
                        topOffset = Math.abs(drawableRight.getBounds().height() - drawableLeft.getBounds().height()) / 2;
                    }
                }
                int top = getCompoundPaddingTop() + topOffset;
                int right = left + drawableLeft.getBounds().width();
                int bottom = top + drawableLeft.getBounds().height();
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
                            onDrawableLeftLongClickListener.onLongClick(PerfectTextView.this);
                        }
                        if (drawablesRelative[0] == drawableLeft) {
                            if (onDrawableStartLongClickListener != null) {
                                onDrawableStartLongClickListener.onLongClick(PerfectTextView.this);
                            }
                        } else {
                            if (onDrawableEndLongClickListener != null) {
                                onDrawableEndLongClickListener.onLongClick(PerfectTextView.this);
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
                int leftOffset = 0;
                if (drawableBottom != null) {
                    if (drawableBottom.getBounds().height() > drawableTop.getBounds().height()) {
                        leftOffset = Math.abs(drawableBottom.getBounds().height() - drawableTop.getBounds().height()) / 2;
                    }
                }
                int left = getCompoundPaddingLeft() + leftOffset;
                int top = getPaddingTop();
                int right = left + drawableTop.getBounds().width();
                int bottom = top + drawableTop.getBounds().height();
                if (e.getX() >= left && e.getX() <= right &&
                        e.getY() >= top && e.getY() <= bottom) {
                    if (clickType == ClickType.Click && onDrawableTopClickListener != null) {
                        onDrawableTopClickListener.onClick(PerfectTextView.this);
                    } else if (clickType == ClickType.LongClick && onDrawableTopLongClickListener != null) {
                        onDrawableTopLongClickListener.onLongClick(PerfectTextView.this);
                    } else if (clickType == ClickType.DoubleClick && onDrawableTopDoubleClickListener != null) {
                        onDrawableTopDoubleClickListener.onClick(PerfectTextView.this);
                    }
                    isClick = true;
                }
            }
            if (drawableRight != null) {
                int right = getWidth() - getPaddingRight();
                int left = right - drawableRight.getBounds().width();
                int topOffset = 0;
                if (drawableLeft != null) {
                    if (drawableLeft.getBounds().height() > drawableRight.getBounds().height()) {
                        topOffset = Math.abs(drawableRight.getBounds().height() - drawableLeft.getBounds().height()) / 2;
                    }
                }
                int top = getCompoundPaddingTop() + topOffset;
                int bottom = top + drawableRight.getBounds().height();
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
                            onDrawableRightLongClickListener.onLongClick(PerfectTextView.this);
                        }
                        if (drawablesRelative[2] == drawableRight) {
                            if (onDrawableEndLongClickListener != null) {
                                onDrawableEndLongClickListener.onLongClick(PerfectTextView.this);
                            }
                        } else {
                            if (onDrawableStartLongClickListener != null) {
                                onDrawableStartLongClickListener.onLongClick(PerfectTextView.this);
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
                int leftOffset = 0;
                if (drawableTop != null) {
                    if (drawableTop.getBounds().height() > drawableBottom.getBounds().height()) {
                        leftOffset = Math.abs(drawableTop.getBounds().height() - drawableBottom.getBounds().height()) / 2;
                    }
                }
                int left = getCompoundPaddingLeft() + leftOffset;
                int right = left + drawableBottom.getBounds().width();
                if (e.getX() >= left && e.getX() <= right &&
                        e.getY() >= top && e.getY() <= bottom) {
                    if (clickType == ClickType.Click && onDrawableBottomClickListener != null) {
                        onDrawableBottomClickListener.onClick(PerfectTextView.this);
                    } else if (clickType == ClickType.LongClick && onDrawableBottomLongClickListener != null) {
                        onDrawableBottomLongClickListener.onLongClick(PerfectTextView.this);
                    } else if (clickType == ClickType.DoubleClick && onDrawableBottomDoubleClickListener != null) {
                        onDrawableBottomDoubleClickListener.onClick(PerfectTextView.this);
                    }
                    isClick = true;
                }
            }

            if (!isClick){
                if (clickType == ClickType.Click && onClickListener != null) {
                    onClickListener.onClick(PerfectTextView.this);
                } else if (clickType == ClickType.LongClick && onLongClickListener != null) {
                    onLongClickListener.onLongClick(PerfectTextView.this);
                } else if (clickType == ClickType.DoubleClick && onDoubleClickListener != null) {
                    onDoubleClickListener.onClick(PerfectTextView.this);
                }
            }
        }

    });

    private enum ClickType {
        Click,
        DoubleClick,
        LongClick;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

}
