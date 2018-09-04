package lxpsee.lxpsee.javaday05.gof.factory.staticfactorymethod;

import top.lxpsee.javaday05.gof.factory.base.Operation;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/7/16 15:56.
 */

public class Run {
    public static void main(String[] args) {
        Operation operation = OperationFactory.createOperation("*");
        operation.setValue1(6);
        operation.setValue2(2);
        System.out.println(operation.getResult());
    }
}
