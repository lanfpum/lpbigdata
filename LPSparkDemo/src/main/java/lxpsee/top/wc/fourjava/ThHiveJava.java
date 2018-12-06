package lxpsee.top.wc.fourjava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/5 11:52.
 */
public class ThHiveJava {
    public static void main(String[] args) throws Exception {
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        Connection connection = DriverManager.getConnection("jdbc:hive2://ip201:10000");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from lpdb4hive.customers where age > 13");

        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            int age = resultSet.getInt(3);
            System.out.println(id + " : " + name + ": " + age);
        }

        resultSet.close();
        connection.close();
    }
}
