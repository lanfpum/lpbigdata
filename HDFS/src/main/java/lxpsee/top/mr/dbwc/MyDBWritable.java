package lxpsee.top.mr.dbwc;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/20 16:43.
 * <p>
 * 自定义values类
 */
public class MyDBWritable implements DBWritable, Writable {
    private int    id;
    private String name;
    private String txt;

    private String word;
    private int    wordCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public void write(DataOutput out) throws IOException {
        out.writeInt(id);
        out.writeUTF(name);
        out.writeUTF(txt);
        out.writeUTF(word);
        out.writeInt(wordCount);
    }

    public void readFields(DataInput in) throws IOException {
        id = in.readInt();
        name = in.readUTF();
        txt = in.readUTF();
        word = in.readUTF();
        wordCount = in.readInt();
    }

    /**
     * 写入数据库
     */
    public void write(PreparedStatement statement) throws SQLException {
        statement.setString(1, word);
        statement.setInt(2, wordCount);
    }

    /**
     * 从数据库读取
     */
    public void readFields(ResultSet resultSet) throws SQLException {
        id = resultSet.getInt(1);
        name = resultSet.getString(2);
        txt = resultSet.getString(3);
    }
}
