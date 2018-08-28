package lxpsee.lxpsee.hadoopday03.book;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.net.URI;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/3 11:52.
 */

public class FileSystemCat {
    public static void main(String[] args) throws IOException {
        String url = args[0];
        Configuration configuration = new Configuration();

        FileSystem fileSystem = FileSystem.get(URI.create(url), configuration);
        FSDataInputStream fsDataInputStream = null;
     /*   try {
            fsDataInputStream = fileSystem.open(new Path(url));
            IOUtils.copyBytes(fsDataInputStream, System.out, 2048, true);
        } finally {
            IOUtils.closeStream(fsDataInputStream);
        }*/

        try {
            fsDataInputStream = fileSystem.open(new Path(url));
            IOUtils.copyBytes(fsDataInputStream, System.out, 1024, false);
            fsDataInputStream.seek(0);
            IOUtils.copyBytes(fsDataInputStream, System.out, 1024, false);
        } finally {
            IOUtils.closeStream(fsDataInputStream);
        }

    }
}
