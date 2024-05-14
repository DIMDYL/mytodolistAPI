package org.example.api.context;

public class BaseContext {

    public static ThreadLocal<Integer> threadLocal1 = new ThreadLocal<>();
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
}
