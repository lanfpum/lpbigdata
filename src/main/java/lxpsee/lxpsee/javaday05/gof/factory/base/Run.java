package lxpsee.lxpsee.javaday05.gof.factory.base;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/7/16 15:48.
 */

public class Run {
    public static void main(String[] args) {
        AddOperation addOperation = new AddOperation();
        addOperation.setValue1(1);
        addOperation.setValue2(6);
        System.out.println(addOperation.getResult());
    }
}
