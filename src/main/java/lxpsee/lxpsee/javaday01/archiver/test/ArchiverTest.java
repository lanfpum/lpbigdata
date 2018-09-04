package lxpsee.lxpsee.javaday01.archiver.test;

import org.junit.Test;

import java.util.Arrays;

/**
 * 努力常态化  2018/6/24 15:08 The world always makes way for the dreamer
 */
public class ArchiverTest {

    @Test
    public void test() {
        byte[] bytes = int2Bytes(1000);
        System.out.println(Arrays.toString(bytes));
        System.out.println(byteArr2Int(bytes));
        byte[] bytes1 = int2Bytes(-127);
        System.out.println(Arrays.toString(bytes1));
        System.out.println(byteArr2Int(bytes1));
    }

    private byte[] int2Bytes(int i) {
        byte[] arr = new byte[4];
        arr[0] = (byte) i;
        arr[1] = (byte) (i >> 8);
        arr[2] = (byte) (i >> 16);
        arr[3] = (byte) (i >> 24);
        return arr;
    }

    private int byteArr2Int(byte[] arr) {
        int i0 = arr[0] & 0xFF;
        int i1 = (arr[1] & 0xFF) << 8;
        int i2 = (arr[2] & 0xFF) << 16;
        int i3 = (arr[3] & 0xFF) << 24;

        return i0 | i1 | i2 | i3;
    }

    @Test
    public void test02() {
        System.out.println(5 & 10);
        System.out.println(5 | 10);
        System.out.println(~5);
        System.out.println(5 ^ 10);
    }

}
