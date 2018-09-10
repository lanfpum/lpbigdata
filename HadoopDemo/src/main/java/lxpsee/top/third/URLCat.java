package lxpsee.top.third;

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/9/9 23:41.
 */
public class URLCat {

    static {
        URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
    }

    public static void main(String[] args) {
        InputStream inputStream = null;

        try {
            inputStream = new URL(args[0]).openStream();
            IOUtils.copyBytes(inputStream, System.out, 2048, false);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(inputStream);
        }
    }
}
