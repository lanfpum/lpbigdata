package lxpsee.lxpsee.javaday05.gof.factory.factorymethodpattern;

import top.lxpsee.javaday05.gof.factory.base.DivOperation;
import top.lxpsee.javaday05.gof.factory.base.Operation;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/7/16 16:09.
 * <p>
 * 除法类工厂
 */

public class DivFactory implements IFactory {
    @Override
    public Operation CreateOption() {
        return new DivOperation();
    }
}
