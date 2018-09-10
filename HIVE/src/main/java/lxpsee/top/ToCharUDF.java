package lxpsee.top;

import org.apache.hadoop.hive.ql.exec.UDF;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/9/6 16:31.
 */
public class ToCharUDF extends UDF {
    String           format           = "yyyy/MM/dd hh:mm:ss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

    public String evaluate() {
        return simpleDateFormat.format(new Date());
    }

    public String evaluate(Date date) {
        return simpleDateFormat.format(date);
    }

    public String evaluate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

}
