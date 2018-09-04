package lxpsee.lxpsee.javaday05.gof.factory.factorymethodpattern;

import top.lxpsee.javaday05.gof.factory.base.Operation;
import top.lxpsee.javaday05.gof.factory.base.SubOperation;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/7/16 16:07.
 * <p>
 * 减法类工厂
 */

public class SubFactory implements IFactory {
    @Override
    public Operation CreateOption() {
        return new SubOperation();
    }
}
