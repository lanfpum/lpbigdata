package lxpsee.lxpsee.javaday01.archiver.utils;

/**
 * 努力常态化  2018/6/24 23:17 The world always makes way for the dreamer
 */
public class FileUtils {

    public static byte[] int2Bytes(int i) {
        byte[] arr = new byte[4];
        arr[0] = (byte) i;
        arr[1] = (byte) (i >> 8);
        arr[2] = (byte) (i >> 16);
        arr[3] = (byte) (i >> 24);
        return arr;
    }

    public static int byteArr2Int(byte[] arr) {
        int i0 = arr[0];
        int i1 = (arr[1] & 0xFF) << 8;
        int i2 = (arr[2] & 0xFF) << 16;
        int i3 = (arr[3] & 0xFF) << 24;

        return i0 | i1 | i2 | i3;
    }

}
