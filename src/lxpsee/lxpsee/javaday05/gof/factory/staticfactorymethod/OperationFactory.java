package lxpsee.lxpsee.javaday05.gof.factory.staticfactorymethod;

import top.lxpsee.javaday05.gof.factory.base.*;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/7/16 15:51.
 * <p>
 * 简单工厂模式
 */

public class OperationFactory {

    public static Operation createOperation(String operation) {
        Operation oper = null;

        switch (operation) {
            case "+":
                oper = new AddOperation();
                break;
            case "-":
                oper = new SubOperation();
                break;
            case "*":
                oper = new MulOperation();
                break;
            case "/":
                oper = new DivOperation();
                break;
            default:
                throw new UnsupportedOperationException("不支持该操作");
        }

        return oper;
    }
}
