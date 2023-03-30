# PerfectTextView
## 镂空TextView，支持渐变色粗边，支持设置背景

[![](https://jitpack.io/v/FlyJingFish/PerfectTextView.svg)](https://jitpack.io/#FlyJingFish/PerfectTextView)
[![GitHub stars](https://img.shields.io/github/stars/FlyJingFish/PerfectTextView.svg)](https://github.com/FlyJingFish/PerfectTextView/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/FlyJingFish/PerfectTextView.svg)](https://github.com/FlyJingFish/PerfectTextView/network)
[![GitHub issues](https://img.shields.io/github/issues/FlyJingFish/PerfectTextView.svg)](https://github.com/FlyJingFish/PerfectTextView/issues)
[![GitHub license](https://img.shields.io/github/license/FlyJingFish/PerfectTextView.svg)](https://github.com/FlyJingFish/PerfectTextView/blob/master/LICENSE)


<img src="https://github.com/FlyJingFish/PerfectTextView/blob/master/screenshot/Screenshot_20221013_130230.jpg" width="405px" height="842px" alt="show" />


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
        implementation 'com.github.FlyJingFish:PerfectTextView:1.0.5'
    }
```
## 第三步，使用说明


```xml
<com.flyjingfish.perfecttextviewlib.PerfectTextView
    android:id="@+id/hollowTextView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:paddingVertical="8dp"
    android:paddingHorizontal="40dp"
    android:text="Hello World!"
    android:gravity="center"
    android:textStyle="bold|italic"
    android:background="@drawable/bg_hollow"
    android:textSize="30sp"/>
```


### 属性一览

| attr                           |  format   |    description     |
|--------------------------------|:---------:|:------------------:|
| perfect_drawableStart_width    | dimension |   左侧（Rtl：右侧）图片宽度   |
| perfect_drawableStart_height   | dimension |   左侧（Rtl：右侧）图片高度   |
| perfect_drawableStart_padding  | dimension | 左侧（Rtl：右侧）图片距离文本距离 |
| perfect_drawableEnd_width      | dimension |   右侧（Rtl：左侧）图片宽度   |
| perfect_drawableEnd_height     | dimension |   右侧（Rtl：左侧）图片高度   |
| perfect_drawableEnd_padding    | dimension | 右侧（Rtl：左侧）图片距离文本距离 |
| perfect_drawableLeft_width     | dimension |       左侧图片宽度       |
| perfect_drawableLeft_height    | dimension |       左侧图片高度       |
| perfect_drawableLeft_padding   | dimension |     左侧图片距离文本距离     |
| perfect_drawableRight_width    | dimension |       右侧图片宽度       |
| perfect_drawableRight_height   | dimension |       右侧图片高度       |
| perfect_drawableRight_padding  | dimension |     右侧图片距离文本距离     |
| perfect_drawableTop_width      | dimension |       上侧图片宽度       |
| perfect_drawableTop_height     | dimension |       上侧图片高度       |
| perfect_drawableTop_padding    | dimension |     上侧图片距离文本距离     |
| perfect_drawableBottom_width   | dimension |       下侧图片宽度       |
| perfect_drawableBottom_height  | dimension |       下侧图片高度       |
| perfect_drawableBottom_padding | dimension |     下侧图片距离文本距离     |


# 最后推荐我写的另一个库，轻松实现在应用内点击小图查看大图的动画放大效果

- [OpenImage](https://github.com/FlyJingFish/OpenImage) 

- [主页查看更多开源库](https://github.com/FlyJingFish)



