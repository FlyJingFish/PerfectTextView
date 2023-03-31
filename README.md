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
        implementation 'com.github.FlyJingFish:PerfectTextView:1.0.0'
    }
```
## 第三步，使用说明


```xml
 <com.flyjingfish.perfecttextviewlib.PerfectTextView
    android:id="@+id/hollowTextView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Hello"
    android:gravity="center"
    android:textStyle="bold"
    android:padding="10dp"
    app:perfect_drawableStart_width="40dp"
    app:perfect_drawableStart_height="40dp"
    app:perfect_drawableEnd_width="100dp"
    app:perfect_drawableEnd_height="100dp"
    app:perfect_drawableTop_width="20dp"
    app:perfect_drawableTop_height="20dp"
    app:perfect_drawableBottom_width="100dp"
    app:perfect_drawableBottom_height="100dp"
    android:drawablePadding="40dp"
    app:perfect_drawableStart_padding="20dp"
    app:perfect_drawableEnd_padding="10dp"
    app:perfect_drawableTop_padding="30dp"
    app:perfect_drawableBottom_padding="30dp"
    android:drawableTop="@mipmap/ic_launcher_round"
    android:drawableStart="@mipmap/bg_photo"
    android:drawableEnd="@mipmap/ic_launcher_round"
    android:drawableBottom="@mipmap/ic_launcher_round"
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
| perfect_selected_text          |     string      |                                        选中时文本                                         |
| perfect_default_text           |     string      |                                        未选中时文本                                        |
| perfect_text_background        | reference/color |                                        文本区域背景                                        |
| perfect_text_background_scope  |      enum       | wrappedText(紧紧包裹文本)/fitDrawablePadding(适应到四个方向的Drawable并与drawable距离为drawablePadding) |

### 设置监听

```java
//点击左侧图标监听
binding.hollowTextView.setOnDrawableStartClickListener(v -> {
    Toast.makeText(this,"setOnDrawableStartClickListener",Toast.LENGTH_SHORT).show();
});

//长按左侧图标监听
binding.hollowTextView.setOnDrawableStartLongClickListener(v -> {
    Toast.makeText(this,"setOnDrawableStartLongClickListener",Toast.LENGTH_SHORT).show();
    return false;
});

//双击左侧图标监听
binding.hollowTextView.setOnDrawableStartDoubleClickListener(v -> {
    Toast.makeText(this,"setOnDrawableStartDoubleClickListener",Toast.LENGTH_SHORT).show();
});
```

# 最后推荐我写的另一个库，轻松实现在应用内点击小图查看大图的动画放大效果

- [OpenImage](https://github.com/FlyJingFish/OpenImage) 

- [主页查看更多开源库](https://github.com/FlyJingFish)



