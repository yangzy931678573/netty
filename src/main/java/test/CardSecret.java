package test;

import io.github.biezhi.excel.plus.annotation.ExcelField;

import java.math.BigDecimal;
import java.util.Date;

// 卡密 Model
public class CardSecret {

    @ExcelField(order = 0, columnName = "运营商类型",convertType = CardSecretConvert.class)
    private Integer cardType;

    @ExcelField(order = 1, columnName = "卡密")
    private String secret;

    @ExcelField(order = 2, columnName = "面额")
    private BigDecimal amount;

    @ExcelField(order = 3, columnName = "过期时间", datePattern = "yyyy年MM月dd日")
    private Date expiredDate;

    public CardSecret() {
    }

    public CardSecret(Integer cardType, String secret, BigDecimal amount, Date expiredDate) {
        this.cardType = cardType;
        this.secret = secret;
        this.amount = amount;
        this.expiredDate = expiredDate;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }
    @Override
    public String toString() {
        return "CardSecret{" +
                "cardType=" + cardType +
                ", secret='" + secret + '\'' +
                ", amount=" + amount +
                ", expiredDate=" + expiredDate +
                '}';
    }
}