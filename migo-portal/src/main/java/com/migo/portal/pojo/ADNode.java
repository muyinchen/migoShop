package com.migo.portal.pojo;

/**
 * Author  知秋
 * Created by kauw on 2016/10/11.
 */
public class ADNode {
    private int height;
    private int width;
    private String src;
    private int heightB;
    private int widthB;
    private String srcB;
    private String alt;
    private String href;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeightB() {
        return heightB;
    }

    public void setHeightB(int heightB) {
        this.heightB = heightB;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSrcB() {
        return srcB;
    }

    public void setSrcB(String srcB) {
        this.srcB = srcB;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidthB() {
        return widthB;
    }

    public void setWidthB(int widthB) {
        this.widthB = widthB;
    }
}
