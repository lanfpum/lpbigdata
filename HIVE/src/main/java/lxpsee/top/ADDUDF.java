package lxpsee.top;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/9/5 17:06.
 * <p>
 * 自定义hive函数
 */
@Description(name = "lpadd",
        value = "lpadd(int a,int b) ==> return a+b",
        extended = "Example:\n" + "lpadd(1,1) ==> return 2\n" + "lpadd(1,2,3) ===> 6;")
public class ADDUDF extends UDF {
    public int evaluate(int a, int b) {
        return a + b;
    }

    public int evaluate(int a, int b, int c) {
        return a + b + c;
    }
}
