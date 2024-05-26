package com.carlyu.apibackend.enums

enum class ResultEnum(val code: Int?, val message: String) {
    ILLEGAL_REQUEST(null, "非法请求"),
    LOGIN_SUCCESS(null, "登陆成功"),
    REGISTER_SUCCESS(null, "注册成功"),
    ORDER_CREATE_SUCCESS(null, "创建订单成功"),
    QUERY_SUCCESS(null, "查询成功"),
    ADD_SUCCESS(null, "添加成功"),
    DEPOSIT_SUCCESS(null, "充值成功"),
    PAY_SUCCESS(null, "支付成功"),
    EDIT_SUCCESS(null, "编辑成功"),
    DELETE_SUCCESS(null, "删除成功"),
    DUPLICATE_STUDENT_ID(-3, "学号重复"),
    BALANCE_NOT_EFFICIENT(1, "余额不足"),
    PASSWORD_MISMATCH(2, "密码错误"),
    DEPOSIT_FAIL(3, "充值失败"),
    EDIT_FAIL(4, "修改失败"),
    ADD_FAIL(5, "添加失败"),
    QUERY_FAIL(6, "查询失败"),
    FOOD_NOT_EXIST(100, "该食物不存在"),
    FOOD_STATUS_ERROR(101, "食物上架状态错误"),
    ORDER_DETAIL_NOT_EXIST(102, "订单详情不存在"),
    STUDENT_NOT_EXIST(103, "学生不存在"),
    ADMIN_NOT_EXIST(null, "管理员不存在");
}