package lxpsee.lxpsee.javaday05.gof.factory.factorymethodpattern;

import top.lxpsee.javaday05.gof.factory.base.Operation;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/7/16 16:05.
 * <p>
 * 工厂接口
 */

public interface IFactory {
    Operation CreateOption();
}
