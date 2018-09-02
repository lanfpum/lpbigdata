package lxpsee.top;

import org.junit.Test;

import java.sql.*;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/29 09:36.
 */
public class HiveJDBCDemo {

    /**
     * 使用jdbc方式连接到hive数据仓库，数据仓库需要开启hiveserver2服务。
     */
    @Test
    public void test() throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        Connection connection = DriverManager.getConnection("jdbc:hive2://192.168.68.201:10000/mydb2");
        String sql = "select * from t";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1) + ":" + resultSet.getString(2) + ".." + resultSet.getInt(3));
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
