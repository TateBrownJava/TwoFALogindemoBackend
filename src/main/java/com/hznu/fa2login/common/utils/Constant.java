package com.hznu.fa2login.common.utils;

/**
 * 常量
 * 
 * @author WheelChen
 */
public class Constant {
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;

	/**
	 * 资源类型
	 *
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 机构
         */
        ORG(1),
        /**
         * 流程
         */
        FLOW(2),
        /**
         * 按钮
         */
        BUTTON(3);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    



}
