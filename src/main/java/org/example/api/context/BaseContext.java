package org.example.api.context;

public class BaseContext {

    public static ThreadLocal<Integer> threadLocal1 = new ThreadLocal<>();
    public static ThreadLocal<Integer> threadLocal2 = new ThreadLocal<>();
    // 用户id
    public static void setUserId(Integer id) {
        threadLocal1.set(id);
    }

    public static int getUserId() {
        return threadLocal1.get();
    }

    public static void removeUserId() {
        threadLocal1.remove();
    }
    // 身份id
    public static void setRoleId(Integer id) {
        threadLocal2.set(id);
    }

    public static int getRoleId() {
        return threadLocal2.get();
    }
    public static void removeRoleId() {
        threadLocal2.remove();
    }

}
