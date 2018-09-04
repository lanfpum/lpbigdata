package lxpsee.lxpsee.javaday05.gof.factory.base;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/7/16 15:41.
 * <p>
 * 相加的计算类
 */
public class AddOperation extends Operation {
    @Override
    public double getResult() {
        return getValue1() + getValue2();
    }

}
