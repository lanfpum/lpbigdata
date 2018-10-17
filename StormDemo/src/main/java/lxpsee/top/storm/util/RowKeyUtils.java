package lxpsee.top.storm.util;

import java.text.DecimalFormat;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/10/8 12:32.
 */
public class RowKeyUtils {

    public static String getReNo(String callId, String callTime) {
        int hash = ((callId + callTime.substring(0, 6)).hashCode() & Integer.MAX_VALUE) % 100;
        DecimalFormat hashFormat = new DecimalFormat("00");
        return hashFormat.format(hash);
    }

}
