package lxpsee.top.java;

import scala.Tuple2;

import java.util.Comparator;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/6 16:31.
 */
public class Tuple2Comparator implements Comparator<Tuple2<String, Integer>> {

    public int compare(Tuple2<String, Integer> o1, Tuple2<String, Integer> o2) {
        return o2._2 - o1._2;
    }
}
