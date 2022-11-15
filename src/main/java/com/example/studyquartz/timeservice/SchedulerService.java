package com.example.studyquartz.timeservice;

import com.example.studyquartz.info.TimerInfo;
import com.example.studyquartz.util.TimerUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SchedulerService {

    private static final Logger LOG = LoggerFactory.getLogger(SchedulerService.class);

    private final Scheduler scheduler;

    @Autowired
    public SchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 작업클래스와 시간정보로 스케줄을 만든다.
     */
    public void schedule(Class jobClass, TimerInfo info) {
        final JobDetail jobDetail = TimerUtils.buildJobDetail(jobClass, info);
        final Trigger trigger = TimerUtils.buildTrigger(jobClass, info);

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 실행중인 모든 timer(스케줄러 작업)를 반환한다.
     */
    public List<TimerInfo> getAllRunningTimers() {
        try {
            return scheduler.getJobKeys(GroupMatcher.anyGroup())
                    .stream()
                    .map(jobKey -> {
                        try {
                            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                            return (TimerInfo) jobDetail.getJobDataMap().get(jobKey.getName());
                        } catch (SchedulerException e) {
                            LOG.error(e.getMessage(), e);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
            return Collections.emptyList();
        }

    }

    /**
     * timerId 를 key 로 하는 실행 중인 timer(스케줄러 작업)를 반환한다.
     */
    public TimerInfo getRunningTimers(String timerId) {
        try {
            final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));

            if (jobDetail == null) {
                LOG.error("Failed to find timer with ID '{}'", timerId);
                return null;
            }

            return (TimerInfo) jobDetail.getJobDataMap().get(timerId);
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * timerId로 jobDataMap 찾아서 TimerInfo 를 반영한다.
     */
    public void updateTimer(final String timerId, final TimerInfo info) {
        try {
            final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));

            if (jobDetail == null) {
                LOG.error("Failed to find timer with ID '{}'", timerId);
                return ;
            }
            jobDetail.getJobDataMap().put(timerId, info);

        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * timerId 로 timer(스케줄러 작업)를 scheduler 에서 삭제한다.
     */
    public boolean deleteTimer(final String timerId) {
        try {
            return scheduler.deleteJob(new JobKey(timerId));
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * PostConstruct 를 이용해서 스프링이 실행될 때
     * scheduler 를 실행시킨다.
     * scheduler 에 triggerListener 를 등록한다.
     */
    @PostConstruct
    public void init() {
        try {
            scheduler.start();

            scheduler.getListenerManager().addTriggerListener(new SimpleTriggerListener(this));
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * PreDestroy 를 이용해서 스프링이 종료되기 전 scheduler 를 종료시킨다.
     */
    @PreDestroy
    public void close() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
