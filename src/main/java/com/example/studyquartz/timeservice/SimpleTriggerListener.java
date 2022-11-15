package com.example.studyquartz.timeservice;

import com.example.studyquartz.info.TimerInfo;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

public class SimpleTriggerListener implements TriggerListener {
    private final SchedulerService schedulerService;

    public SimpleTriggerListener(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @Override
    public String getName() {
        return SimpleTriggerListener.class.getSimpleName();
    }

    /**
     * Trigger가 실행된 상태
     * 리스너 중에서 가장 먼저 실행됨
     *
     * job 하나가 실행될 때 마다 remainingFireCount 를 1씩 감소시켜서 스케줄러에 반영한다.
     */
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        final String timerId = trigger.getKey().getName();

        final JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        final TimerInfo info = (TimerInfo) jobDataMap.get(timerId);

        if (!info.isRunForever()) {
            int remainingFireCount = info.getRemainingFireCount();
            if (remainingFireCount == 0 ) {
                return;
            }

            info.setRemainingFireCount(remainingFireCount - 1);
        }

        schedulerService.updateTimer(timerId, info);
    }

    /**
     * Trigger 중단 여부를 확인하는 메소드
     * Job을 수행하기 전 상태
     *
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    /**
     * Trigger가 중단된 상태
     */
    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    /**
     * Trigger가 완료된 상태
     */
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {

    }
}
