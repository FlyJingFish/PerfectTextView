# PerfectTextView
## 完善了TextView 四个方向的Drawable设置，可在布局中设置大小和间距，并且可以设置点击，双击，长按监听

[![](https://jitpack.io/v/FlyJingFish/PerfectTextView.svg)](https://jitpack.io/#FlyJingFish/PerfectTextView)
[![GitHub stars](https://img.shields.io/github/stars/FlyJingFish/PerfectTextView.svg)](https://github.com/FlyJingFish/PerfectTextView/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/FlyJingFish/PerfectTextView.svg)](https://github.com/FlyJingFish/PerfectTextView/network)
[![GitHub issues](https://img.shields.io/github/issues/FlyJingFish/PerfectTextView.svg)](https://github.com/FlyJingFish/PerfectTextView/issues)
[![GitHub license](https://img.shields.io/github/license/FlyJingFish/PerfectTextView.svg)](https://github.com/FlyJingFish/PerfectTextView/blob/master/LICENSE)


<img src="https://github.com/FlyJingFish/PerfectTextView/blob/master/screenshot/img.png" width="320px" height="640px" alt="show" />


## 第一步，根目录build.gradle

```gradle
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```
## 第二步，需要引用的build.gradle （最新版本[![](https://jitpack.io/v/FlyJingFish/PerfectTextView.svg)](https://jitpack.io/#FlyJingFish/PerfectTextView)）

```gradle
    dependencies {
        implementation 'com.github.FlyJingFish:PerfectTextView:1.1.9'
    }
```
## 第三步，使用说明


```xml
 <com.flyjingfish.perfecttextviewlib.PerfectTextView
    android:id="@+id/hollowTextView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Hello World!"
    android:background="@drawable/bg_select"
    android:gravity="center"
    android:textStyle="bold"
    android:padding="10dp"
    android:textColor="@color/press_color"
    android:layout_marginTop="10dp"
    app:perfect_drawableStart_width="40dp"
    app:perfect_drawableStart_height="40dp"
    app:perfect_drawableEnd_width="100dp"
    app:perfect_drawableEnd_height="100dp"
    app:perfect_drawableTop_width="20dp"
    app:perfect_drawableTop_height="20dp"
    app:perfect_drawableBottom_width="50dp"
    app:perfect_drawableBottom_height="50dp"
    android:drawablePadding="40dp"
    app:perfect_drawableStart_padding="20dp"
    app:perfect_drawableEnd_padding="10dp"
    app:perfect_drawableTop_padding="30dp"
    app:perfect_drawableBottom_padding="30dp"
    app:perfect_selected_text="@string/app_name"
    app:perfect_text_background="@drawable/bg_text_press"
    app:perfect_text_background_scope="wrappedText"
    android:drawableTop="@drawable/press_icon"
    android:drawableStart="@drawable/select_icon"
    android:drawableEnd="@drawable/select_icon"
    android:drawableBottom="@drawable/press_icon"
    android:textSize="30sp"/>
```


### 属性一览

| attr                           |     format      |                                     description                                      |
|--------------------------------|:---------------:|:------------------------------------------------------------------------------------:|
| perfect_drawableStart_width    |    dimension    |                                    左侧（Rtl：右侧）图片宽度                                    |
| perfect_drawableStart_height   |    dimension    |                                    左侧（Rtl：右侧）图片高度                                    |
| perfect_drawableStart_padding  |    dimension    |                                  左侧（Rtl：右侧）图片距离文本距离                                  |
| perfect_drawableEnd_width      |    dimension    |                                    右侧（Rtl：左侧）图片宽度                                    |
| perfect_drawableEnd_height     |    dimension    |                                    右侧（Rtl：左侧）图片高度                                    |
| perfect_drawableEnd_padding    |    dimension    |                                  右侧（Rtl：左侧）图片距离文本距离                                  |
| perfect_drawableLeft_width     |    dimension    |                                        左侧图片宽度                                        |
| perfect_drawableLeft_height    |    dimension    |                                        左侧图片高度                                        |
| perfect_drawableLeft_padding   |    dimension    |                                      左侧图片距离文本距离                                      |
| perfect_drawableRight_width    |    dimension    |                                        右侧图片宽度                                        |
| perfect_drawableRight_height   |    dimension    |                                        右侧图片高度                                        |
| perfect_drawableRight_padding  |    dimension    |                                      右侧图片距离文本距离                                      |
| perfect_drawableTop_width      |    dimension    |                                        上侧图片宽度                                        |
| perfect_drawableTop_height     |    dimension    |                                        上侧图片高度                                        |
| perfect_drawableTop_padding    |    dimension    |                                      上侧图片距离文本距离                                      |
| perfect_drawableBottom_width   |    dimension    |                                        下侧图片宽度                                        |
| perfect_drawableBottom_height  |    dimension    |                                        下侧图片高度                                        |
| perfect_drawableBottom_padding |    dimension    |                                      下侧图片距离文本距离                                      |
| perfect_selected_text          |     string      |                                选中时文本（原来的 text 是默认文本）                                 |
| perfect_selected_hint          |     string      |                               选中时提示文本（原来的 hint 是默认文本）                                |
| perfect_text_background        | reference/color |                                        文本区域背景                                        |
| perfect_text_background_scope  |      enum       | wrappedText(紧紧包裹文本)/fitDrawablePadding(适应到四个方向的Drawable并与drawable距离为drawablePadding) |

### 设置监听(部分示例)

```java
//点击左侧图标监听
binding.hollowTextView.setOnDrawableStartClickListener(v -> {
    Toast.makeText(this,"setOnDrawableStartClickListener",Toast.LENGTH_SHORT).show();
});

//长按左侧图标监听
binding.hollowTextView.setOnDrawableStartLongClickListener(v -> {
    Toast.makeText(this,"setOnDrawableStartLongClickListener",Toast.LENGTH_SHORT).show();
    return true;//false则会继续回调点击事件
});

//双击左侧图标监听
binding.hollowTextView.setOnDrawableStartDoubleClickListener(v -> {
    Toast.makeText(this,"setOnDrawableStartDoubleClickListener",Toast.LENGTH_SHORT).show();
});
```

### 更多功能

| attr                                                                        |               description                |
|-----------------------------------------------------------------------------|:----------------------------------------:|
| setTextBackground                                                           |                  文本区域背景                  |
| setTextBackgroundResource                                                   |                  文本区域背景                  |
| setTextBackgroundColor                                                      |                  文本区域背景                  |
| setTextBackgroundScope                                                      |                文本区域背景显示区域                |
| setDrawableStartWidthHeight/setDrawableEndWidthHeight等等                     |             单独为四个位置之一设置图片宽高              |
| setDrawableStartPadding/setDrawableEndPadding等等                             |           单独为四个位置之一设置图片距离文本的距离           |
| setDrawableStartSelected/setDrawableEndSelected等等                           |                   图片选中                   |
| setDrawableStart/setDrawableEnd等等                                           |              单独为四个位置之一设置图片               |
| setOnDrawableStartClickListener/setOnDrawableEndClickListener等等             |             单独为四个位置之一设置点击监听              |
| setOnDrawableStartLongClickListener/setOnDrawableEndLongClickListener等等     |             单独为四个位置之一设置长按监听              |
| setOnDrawableStartDoubleClickListener/setOnDrawableEndDoubleClickListener等等 |             单独为四个位置之一设置双击监听              |
| setOnClickListener(OnClickListener l, ClickScope clickScope)                |         点击监听，clickScoped点击的区域范围          |
| setOnLongClickListener(OnLongClickListener l, ClickScope clickScope)        |         长按监听，clickScoped点击的区域范围          |
| setOnDoubleClickListener(OnClickListener l, ClickScope clickScope)          |         双击监听，clickScoped点击的区域范围          |
| setSelectedText                                                             |                  设置选中文本                  |
| setText                                                                     |                   默认文本                   |
| setSelectedHint                                                             |       设置选中时提示文本（不建议再使用setHint方法了）        |
| setDefaultHint                                                              |       选中未选中时提示文本（不建议再使用setHint方法了）       |
| setSelectedIgnoreDrawable                                                   | 设置选中状态不会改变四个位置的状态（原来的 setSelected 方法会改变） |

# 最后推荐我写的另一个库，轻松实现在应用内点击小图查看大图的动画放大效果

- [OpenImage](https://github.com/FlyJingFish/OpenImage) 

- [主页查看更多开源库](https://github.com/FlyJingFish)



