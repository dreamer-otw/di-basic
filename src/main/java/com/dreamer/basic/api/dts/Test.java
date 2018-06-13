package com.dreamer.basic.api.dts;
import java.sql.Connection;


public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        DBUtils db = DBUtils.getInstance();
        Connection conn = db.getConnection();
        if (conn != null) {
            System.out.println("success...");
        } else {
            System.out.println("fail...");
        }
        db.closeDB();
    }

}