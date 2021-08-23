package com.xktt.renovation.app

import com.blankj.utilcode.util.SPUtils

class UserManager private constructor(){
    private val USER_NICKNAME = "nickname"
    private var spUtil:SPUtils

    companion object{
        private var instance:UserManager?=null
        get() {
            if (field == null){
                field = UserManager()
            }
            return field
        }
        fun get():UserManager{
            return instance!!
        }
    }

    init {
        spUtil = SPUtils.getInstance("user_info")
    }

    fun getNickName():String{
        return spUtil.getString(USER_NICKNAME,"")
    }

    fun setNickName(nickname: String){
        spUtil.put(USER_NICKNAME,nickname)
    }

    fun clearUserInfo(){
        spUtil.remove(USER_NICKNAME)
    }
}