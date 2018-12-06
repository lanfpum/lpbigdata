package lxpsee.top.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/5 19:54.
 */
public class Config {
    static Properties properties;

    static {
        properties = new Properties();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("UserDraw.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // UserDraw
    public String ProcedureTime = properties.getProperty("ProcedureTime");
    public String Separator     = properties.getProperty("Separator");
    public String Date          = properties.getProperty("Date");

    public String MDN   = properties.getProperty("MDN");
    public String appID = properties.getProperty("appID");
    public String count = properties.getProperty("count");

    // hbase
    public String consite = properties.getProperty("consite");
    public String hbaseip = properties.getProperty("hbaseip");
    public String coftime = properties.getProperty("coftime");

    public String time      = properties.getProperty("time");
    public String tableDraw = properties.getProperty("tableDraw");
}
