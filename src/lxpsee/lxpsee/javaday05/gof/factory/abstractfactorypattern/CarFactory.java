package lxpsee.lxpsee.javaday05.gof.factory.abstractfactorypattern;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/7/16 16:25.
 * <p>
 * 车的总工厂
 */

public interface CarFactory {
    public BenzCar getBenzCar();

    public TeslaCar getTeslaCar();
}
