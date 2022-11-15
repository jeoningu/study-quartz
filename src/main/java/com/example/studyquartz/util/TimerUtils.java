package com.example.studyquartz.util;

import com.example.studyquartz.info.TimerInfo;
import org.quartz.*;

import java.util.Date;

public class TimerUtils {
    private TimerUtils() {}

    // jobClass와 timerInfo를 받아서 JobDetail를 만든다.
    // jobClass의 simpleName을 key로 사용한다.
    public static JobDetail buildJobDetail(final Class jobClass, final TimerInfo info) {
        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobClass.getSimpleName(), info);

        return JobBuilder
                .newJob(jobClass)
                .withIdentity(jobClass.getSimpleName())
                .setJobData(jobDataMap)
                .build();
    }

    // jobClass와 timerInfo를 받아서 trigger를 만든다.
    public static Trigger buildTrigger(final Class jobClass, final TimerInfo info) {
        // 스케줄러 만들기
        // 1. 반복 간격 설정
        SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(info.getRepeatIntervalMs());

        // 2. 반복 횟수 설정
        if (info.isRunForever()) {
            builder = builder.repeatForever(); // 영원히 실행할 거면 repeatForever로 설정
        } else {
            builder = builder.withRepeatCount(info.getTotalFireCount() - 1); // 첫번째는 포함되지 않아서 5번 실행시키고 싶으면 4를 넣어줌
        }

        // 트리거에 작업스케줄러이름, 스케줄러, 시작시간을 설정
        return TriggerBuilder
                .newTrigger()
                .withIdentity(jobClass.getSimpleName())
                .withSchedule(builder)
                .startAt(new Date(System.currentTimeMillis() + info.getInitialOffsetMs()))
                .build();
    }

}
