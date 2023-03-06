package com.bjpowernode.secnkill.config;


import com.bjpowernode.secnkill.pojo.User;

//使用ThreadLocal存储用户
public class UserContext  {
    private static ThreadLocal<User>  userHandler=new ThreadLocal<>();
    public static User getUser(){
        return userHandler.get();
    }
    public static void setUser(User user){
        userHandler.set(user);
    }
}
