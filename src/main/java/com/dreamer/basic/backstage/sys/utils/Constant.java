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
        CATALOG(-1),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private Integer value;

        MenuType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 超级管理员
     */
    public static final String ROOT_USER = "000000";
    /**
     * 根菜单ID
     */
    public static final String ROOT_MENU = "000001";
    /**
     * 根菜单父ID
     */
    public static final String ROOT_MENU_PARENT = "000000";

}
