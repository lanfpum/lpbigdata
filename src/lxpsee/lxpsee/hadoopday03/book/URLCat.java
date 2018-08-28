package lxpsee.lxpsee.hadoopday03.book;

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/3 10:46.
 */

public class URLCat {

    static {
        URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
    }

    public static void main(String[] args) throws IOException {
        InputStream inputStream = null;
        try {
            URL url = new URL(args[0]);
            inputStream = url.openStream();
            IOUtils.copyBytes(inputStream, System.out, 2048, false);
        } finally {
            IOUtils.closeStream(inputStream);
        }
    }
}
