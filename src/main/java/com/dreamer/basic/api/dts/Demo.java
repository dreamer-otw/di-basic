package com.dreamer.basic.api.dts;

import java.sql.*;

public class Demo {
    // 表示定义数据库的用户名
    private static String USERNAME = "HDCUS_XFJR";
    // 定义数据库的密码
    private static String PASSWORD = "HDCUS_XFJR123";
    // 定义数据库的驱动信息
    private static String DRIVER = "oracle.jdbc.OracleDriver";
    // 定义访问数据库的地址
    private static String URL = "jdbc:oracle:thin:@10.135.17.90:1521:EDW";

    /*// 表示定义数据库的用户名
    private static String USERNAME = "act";
    // 定义数据库的密码
    private static String PASSWORD = "act123";
    // 定义数据库的驱动信息
    private static String DRIVER = "oracle.jdbc.OracleDriver";
    // 定义访问数据库的地址
    private static String URL = "jdbc:oracle:thin:@10.164.197.235:1521:act";*/

    public static void main(String[] args){
        String sql = "select * from DM_HDCUS.DM_CUSTBD_XFJR_DATA_ORDER";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            pstmt = conn.prepareStatement(sql);
            resultSet = pstmt.executeQuery();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
