package com.carlyu.apibackend.utils

import com.carlyu.apibackend.constant.BaseConstant.SUCCEED
import com.carlyu.apibackend.constant.BaseConstant.SUCCESS
import com.carlyu.apibackend.vo.ResultVO


object ResultVOUtil {
    /**
     * 成功
     *
     * @param object 传入一个实例
     * @return JSON
     */
    fun success(`object`: Any?): ResultVO = ResultVO(success = true, SUCCESS, SUCCEED, `object`)


    /**
     * 成功
     *
     * @param msg    信息
     * @param object 传入一个对象
     * @return JSON
     */
    fun success(
        msg: String,
        `object`: Any?
    ): ResultVO = ResultVO(success = true, SUCCESS, msg, `object`)


    /**
     * 成功
     *
     * @param isSuccess true/false
     * @param msg       信息
     * @param object    传入一个对象
     * @return JSON
     */
    fun success(
        code: Int,
        msg: String,
        `object`: Any?
    ): ResultVO = ResultVO(true, code, msg, `object`)


    /**
     * 成功
     *
     * @param code code
     * @param msg  信息
     * @return JSON
     */
    fun success(
        code: Int,
        msg: String?
    ): ResultVO = ResultVO(true, code, msg ?: SUCCEED, null)


    /**
     * 失败
     *
     * @param code code
     * @param msg  信息
     * @return JSON
     */
    fun fail(code: Int, msg: String): ResultVO =
        ResultVO(false, code, msg, null)


    /**
     * 失败
     *
     * @param code   错误代码
     * @param msg    信息
     * @param object 一个实例
     * @return JSON
     */
    fun fail(
        code: Int,
        msg: String,
        `object`: Any?
    ): ResultVO = ResultVO(false, code, msg, `object`)


}