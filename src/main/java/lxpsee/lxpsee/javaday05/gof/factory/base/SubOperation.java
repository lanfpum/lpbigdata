package lxpsee.lxpsee.javaday05.gof.factory.base;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/7/16 15:43.
 * <p>
 * 减法的额计算类
 */
public class SubOperation extends Operation {
    @Override
    public double getResult() {
        return getValue1() - getValue2();
    }
}
