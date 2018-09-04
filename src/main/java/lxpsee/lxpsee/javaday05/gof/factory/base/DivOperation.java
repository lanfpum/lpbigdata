package lxpsee.lxpsee.javaday05.gof.factory.base;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/7/16 15:46.
 * <p>
 * 除法
 */

public class DivOperation extends Operation {
    @Override
    public double getResult() {
        if (getValue2() != 0) {
            return getValue1() / getValue2();
        }

        throw new IllegalArgumentException("除数不能为零");
    }
}
