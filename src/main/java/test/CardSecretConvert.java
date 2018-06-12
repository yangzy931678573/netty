package test;

import io.github.biezhi.excel.plus.converter.Converter;

// 运营商类型转换器
public class CardSecretConvert implements Converter<Integer> {

    @Override
    public String write(Integer value) {
        return value.equals(1) ? "联通" : "移动";
    }

    @Override
    public Integer read(String value) {
        return value.equals("联通") ? 1 : 2;
    }
}