package yanshan.com.xiaokebiao.entity;

import java.util.List;

import yanshan.com.xiaokebiao.common.App;
import yanshan.com.xiaokebiao.util.DateUtils;

/**
 * 数据封装实体
 *
 * @author itning
 */
public class DataEntity {
    private final List<ClassSchedule> classScheduleList;
    private final List<String> timeList;

    public DataEntity(App app) {
        classScheduleList = app.getDaoSession().getClassScheduleDao().loadAll();
        timeList = DateUtils.INSTANCE.getTimeList();
    }

    public List<ClassSchedule> getClassScheduleList() {
        return classScheduleList;
    }

    public List<String> getTimeList() {
        return timeList;
    }

    @Override
    public String toString() {
        return "DataEntity{" +
                "classScheduleList=" + classScheduleList +
                ", timeList=" + timeList +
                '}';
    }
}
