package com.sendinfo.androidkit.util;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.ColorInt;

import com.blankj.utilcode.util.ConvertUtils;

/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2019/04/11
 *     desc   :
 * </pre>
 */

public class DrawableBuilder {
    //默认线条粗细 1dp
    private static final int defaultLineWidth = 1;
    private static final int defaultLineColor = Color.parseColor("#e9e9e9");

    private static final int defaultCornerRadius= 2;
    //椭圆形圆角
    private static final int defaultRoundCornerRadius= 100;

    //默认虚线条单位长度 6dp
    private static final int defaultDashWidth = 6;
    //默认虚线条之间的间距 2dp
    private static final int defaultDashGap = 2;

    private int shape = GradientDrawable.RECTANGLE;
    private int lineWidth = 0;
    @ColorInt
    private int lineColor = Color.TRANSPARENT;
    private float cornerRadius = ConvertUtils.dp2px(2);
    /**
     * 背景颜色，默认透明
     */
    @ColorInt private  int bkColor = Color.TRANSPARENT;
    /**
     *  虚线边框每个单元的长度
     */
    private float dashWidth = 0;
    /**
     * 虚线边框每个单元之间的间距
     */
    private float dashGap = 0;

    public DrawableBuilder() {
    }

    public Drawable build() {
        final GradientDrawable bg = new GradientDrawable();
        bg.setShape(shape);
        bg.setStroke(lineWidth,lineColor,dashWidth,dashGap);
        bg.setCornerRadius(cornerRadius);
        bg.setColor(bkColor);
        return bg;
    }

    public DrawableBuilder shape(int shape) {
        this.shape = shape;
        return this;
    }

    /**
     * 默认生成一个 1 dp 939393 的园线
     * @return
     */
    public DrawableBuilder line() {
        return line(defaultLineWidth,defaultLineColor);
    }

    /**
     * 构造线框
     * @param width
     * @param color
     * @return
     */
    public DrawableBuilder line(int width,int color) {
        return lineWidth(width).lineColor(color);
    }

    public DrawableBuilder line(int width,String color) {
        return lineWidth(width).lineColor(color);
    }

    /**
     * 设置边框线条粗细 直接传入具体数值
     *
     * @return
     */
    public DrawableBuilder lineWidth(int lineWidth) {
        this.lineWidth = ConvertUtils.dp2px(lineWidth);
        return this;
    }

    public DrawableBuilder lineColor(int lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    public DrawableBuilder lineColor(String lineColor) {
        if (lineColor.charAt(0) != '#'){
            throw new IllegalArgumentException("color value must be start with # like #000000");
        }
        return lineColor(Color.parseColor(lineColor));
    }

    /**
     * 设置圆角度数，直接传入具体数值
     * @param cornerRadius
     * @return
     */
    public DrawableBuilder corner(float cornerRadius) {
        this.cornerRadius =ConvertUtils.dp2px(cornerRadius);
        return this;
    }

    /**
     * 配置默认的圆角度数 为2
     * @return
     */
    public DrawableBuilder corner() {
        return corner(defaultCornerRadius);
    }

    /**
     * 大圆角
     * @return
     */
    public DrawableBuilder roundCorner() {
        return corner(defaultRoundCornerRadius);
    }

    public DrawableBuilder fill(@ColorInt int bkColor) {
        this.bkColor = bkColor;
        return this;
    }

    public DrawableBuilder fill(String bkColor) {
        if (bkColor.charAt(0) != '#'){
            throw new IllegalArgumentException("color value must be start with # like #000000");
        }
        return fill(Color.parseColor(bkColor));
    }

    /**
     * 生成一个默认的虚线 shape
     * @return
     */
    public DrawableBuilder dash() {
        return dashWidth(defaultDashWidth).dashGap(defaultDashGap);
    }

    public DrawableBuilder dashWidth(float dashWidth) {
        this.dashWidth = ConvertUtils.dp2px(dashWidth);
        return this;
    }

    public DrawableBuilder dashGap(float dashGap) {
        this.dashGap = ConvertUtils.dp2px(dashGap);
        return this;
    }
}
