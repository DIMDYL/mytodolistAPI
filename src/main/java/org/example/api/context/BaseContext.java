package org.example.api.context;

public class BaseContext {

    public static ThreadLocal<Long> threadLocal1 = new ThreadLocal<>();
    // 用户id
    public static void setUserId(Long id) {
        threadLocal1.set(id);
    }

    public static Long getUserId() {
        return threadLocal1.get();
    }

    public static void removeUserId() {
        threadLocal1.remove();
    }
}
