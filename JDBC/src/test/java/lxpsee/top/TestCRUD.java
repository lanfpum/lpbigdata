package lxpsee.top;

import org.junit.Test;

import java.sql.*;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/22 15:30.
 * <p>
 * CREATE TABLE users ( id INT PRIMARY KEY auto_increment, NAME VARCHAR ( 20 ), age INT )
 * delete from users;  TRUNCATE users;
 */
public class TestCRUD {

    private final String driverClass = "com.mysql.jdbc.Driver";
    private final String url         = "jdbc:mysql://localhost:3306/big4";
    private final String userName    = "root";
    private final String passWord    = "123";

    @Test
    public void testStatement() throws ClassNotFoundException, SQLException {
        long start = System.currentTimeMillis();

        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, userName, passWord);
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        String sql;

        for (int i = 0; i < 10000; i++) {
            sql = "insert into users(name,age) values('tom" + i + "'," + i % 100 + ")";
            statement.execute(sql);
        }

        connection.commit();
        statement.close();
        connection.close();
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testPreparedStaatement() throws ClassNotFoundException, SQLException {
        long start = System.currentTimeMillis();

        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, userName, passWord);
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement("insert into users(name,age) value(?,?)");

        /*for (int i = 0; i < 10000; i++) {
            preparedStatement.setString(1, "jim" + i);
            preparedStatement.setInt(2, i % 100);
            preparedStatement.executeUpdate();
        }*/

        // 使用批次提交：每次设置完参数添加到批次内存中，满足条件统一批量提交，勿忘最后一次提交
        for (int i = 0; i < 10000; i++) {
            preparedStatement.setString(1, "top" + i);
            preparedStatement.setInt(2, i % 120);

            preparedStatement.addBatch();

            if (i % 3000 == 0) {
                preparedStatement.executeBatch();
            }
        }

        preparedStatement.executeBatch();
        connection.commit();
        preparedStatement.close();
        connection.close();
        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * mysql存储过程 两数相加的存储过程测试
     */
    @Test
    public void testSP_Add() throws ClassNotFoundException, SQLException {
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, userName, passWord);

        CallableStatement callableStatement = connection.prepareCall("CALL sp_add(?,?,?)");
        callableStatement.setInt(1, 5);
        callableStatement.setInt(2, 9);
        callableStatement.registerOutParameter(3, Types.INTEGER);

        callableStatement.execute();
        int out = callableStatement.getInt(3);

        callableStatement.close();
        connection.close();
        System.out.println(out);

    }

    /**
     * 百万数据插入
     */
    @Test
    public void testInsett() throws Exception {
        long start = System.currentTimeMillis();

        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, userName, passWord);
        connection.setAutoCommit(false);

        CallableStatement callableStatement = connection.prepareCall("{call sp_batchinsert(?)}");
        callableStatement.setInt(1, 1000000);
        callableStatement.execute();
        connection.commit();
        connection.close();

        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * 与2测试读未提交
     *
     * @throws Exception
     */
    @Test
    public void testReadUncommittedCMD() throws Exception {
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, userName, passWord);
        connection.setAutoCommit(false);

        String sql = "update users set age = ? where id = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 12);
        preparedStatement.setInt(2, 1);
        preparedStatement.execute();

        connection.commit();
        preparedStatement.close();
        connection.close();
        System.out.println("-----------");
    }

    @Test
    public void testReadUncommittedCMD2() throws Exception {
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, userName, passWord);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
        connection.setAutoCommit(false);
        String sql = "select age from users where id = 1";
        Statement statement = connection.createStatement();

        for (int i = 0; i < 2; i++) {
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();     // 应注意必须前移
            int age = resultSet.getInt(1);
            System.out.println(age);
        }

        connection.commit();
        statement.close();
        connection.close();
        System.out.println("--------");
    }
}
