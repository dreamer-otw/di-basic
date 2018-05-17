package com.dreamer.basic.backstage.sys.utils;

/**
 * > 常量
 * author : dreamer
 * email : dreamers_otw@163.com
 * date : 2018/5/15 12:43
 */
public class Constant {
    public static final String MYSQL = "mysql";
    public static final String ORACLE = "oracle";
    /**
     * 菜单类型
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG("00"),
        /**
         * 菜单
         */
        MENU("01"),
        /**
         * 按钮
         */
        BUTTON("02");

        private String value;

        MenuType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
