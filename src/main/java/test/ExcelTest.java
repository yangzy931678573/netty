package test;

import io.github.biezhi.excel.plus.ExcelPlus;
import io.github.biezhi.excel.plus.exception.ExcelException;
import io.github.biezhi.excel.plus.reader.ExcelResult;
import io.github.biezhi.excel.plus.reader.Reader;
import io.github.biezhi.excel.plus.reader.ValidRow;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/5/17.
 * Description : excel plus test
 */
public class ExcelTest {
    public static void main(String[] args) throws ExcelException {
        ExcelPlus excelPlus = new ExcelPlus();
        List<CardSecret> cardSecrets = new ArrayList<>();
        cardSecrets.add(new CardSecret(1, "vlfdzepjmlz2y43z7er4", new BigDecimal("20"), new Date()));
        cardSecrets.add(new CardSecret(2, "rasefq2rzotsmx526z6g", new BigDecimal("10"), new Date()));
        cardSecrets.add(new CardSecret(2, "2ru44qut6neykb2380wt", new BigDecimal("50"), new Date()));
        cardSecrets.add(new CardSecret(1, "srcb4c9fdqzuykd6q4zl", new BigDecimal("15"), new Date()));

        excelPlus.export(cardSecrets).writeAsFile(new File("src/main/resources/卡密列表.xlsx"));

        List<CardSecret> cardList = excelPlus.read(CardSecret.class,
                Reader.create().excelFile(new File("src/main/resources/卡密列表.xlsx")))
                .asList();
        cardList.forEach(System.out::println);
        //数据过滤
        List<CardSecret> readList = excelPlus.read(CardSecret.class,
                Reader.create()
                        .excelFile(new File("src/main/resources/卡密列表.xlsx")))
                .filter(cardSecret -> !cardSecret.getSecret().isEmpty())
                .collect(Collectors.toList());
       /* //数据校验
        ExcelResult<CardSecret> result = excelPlus
                .read(CardSecret.class,
                        Reader.create().excelFile(new File("卡密列表.xls"))
                .valid(cardSecret -> {
                    if (cardSecret.getAmount().doubleValue() < 20) {
                        return ValidRow.fail("最小金额为20");
                    }
                    return ValidRow.ok();
                }).asResult();

        if (!result.isValid()) {
            result.errors().forEach(System.out::println);
        } else {
            System.out.println(result.rows().size());
        }*/
    }
}
