package com.reharu.ikaros.lingmar.domain;

/**
 * Created by Lingmar on 2017/3/26.
 */

public class Goods {
    private String goodsTitle;
    private String goodsContent;
    private String goodsPic;
    private String goodsURL;
    private String zanTotal;
    private String goodsId;
    private int page ;

    public Goods(int page) {
        this.page = page;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public String getZanTotal() {
        return zanTotal;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public String getGoodsContent() {
        return goodsContent;
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public String getGoodsURL() {
        return goodsURL;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public void setGoodsContent(String goodsContent) {
        this.goodsContent = goodsContent;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public void setGoodsURL(String goodsURL) {
        this.goodsURL = goodsURL;
    }

    public void setZanTotal(String zanTotal) {
        this.zanTotal = zanTotal;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsTitle='" + goodsTitle + '\'' +
                ", goodsContent='" + goodsContent + '\'' +
                ", goodsPic='" + goodsPic + '\'' +
                ", goodsURL='" + goodsURL + '\'' +
                ", zanTotal='" + zanTotal + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", page=" + page +
                '}';
    }
}
