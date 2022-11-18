package com.example.studyquartz.playground;

import com.example.studyquartz.info.TimerInfo;
import com.example.studyquartz.jobs.HelloWorldJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.studyquartz.timeservice.SchedulerService;

import java.util.List;

@Service
public class PlaygroundService {
    private final SchedulerService scheduler;

    @Autowired
    public PlaygroundService(final SchedulerService scheduler) {
        this.scheduler = scheduler;
    }

    public void runHelloWorldJob() {
        final TimerInfo info = new TimerInfo();
        info.setTotalFireCount(20);
        info.setRemainingFireCount(info.getTotalFireCount());
        info.setRepeatIntervalMs(2000);
        info.setInitialOffsetMs(1000);
        info.setCallbackData("My clallback data");

        scheduler.schedule(HelloWorldJob.class, info);
    }

    public List<TimerInfo> getAllRunningTimers() {
        return scheduler.getAllRunningTimers();
    }

    public TimerInfo getRunningTimers(String timerId) {
        return scheduler.getRunningTimers(timerId);
    }

    public boolean deleteTimer(String timerId) {
        return scheduler.deleteTimer(timerId);
    }

}
