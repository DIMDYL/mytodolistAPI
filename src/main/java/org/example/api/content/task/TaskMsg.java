package org.example.api.content.task;

public class TaskMsg {

    public static final Integer IS_LIMITED = 1;
    public static final Integer NO_LIMITED = 0; //是否是限时任务
    public static final Integer FINISHED = 1;
    public static final Integer UNFINISHED = 0;  //是否完成
    public static final String ADD_TASK_SUCCESS = "添加任务成功";
    public static final String MODIFY_TASK_STATUS_SUCCESS = "修改任务状态成功";

    public static final String MODIFY_TASK_CONTENT_SUCCESS = "修改任务内容成功";
    public static final String NO_LIMITED_TIME = "没有添加限时的时间";
    public static final String DELETE_SUCCESS = "删除任务成功";
}
