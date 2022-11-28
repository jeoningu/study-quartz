package com.example.studyquartz.info;

import java.io.Serializable;

public class TimerInfo implements Serializable {  // implements Serializable : 객체를 가지고 외부( 예를 들면 DB )와 통신하기 위해 직렬화 해준다
    private int totalFireCount; // timer가 몇번 실행될 것인지
    private int remainingFireCount; //남은 실행 횟수?
    private boolean runForever; // 영원히 실행되게 할건지(영원히 실행되게 할 거라면 totalFierCount는 필요없음)
    private long repeatIntervalMs; // 몇 Ms 마다 반복할지
    private long initialOffsetMs; // 0으로 하면 즉시 실행 100으로 하면 100Ms 뒤 실행
    private String callbackData; // callbackData

    public int getTotalFireCount() {
        return totalFireCount;
    }

    public void setTotalFireCount(int totalFireCount) {
        this.totalFireCount = totalFireCount;
    }

    public int getRemainingFireCount() {
        return remainingFireCount;
    }

    public void setRemainingFireCount(int remainingFireCount) {
        this.remainingFireCount = remainingFireCount;
    }

    public boolean isRunForever() {
        return runForever;
    }

    public void setRunForever(boolean runForever) {
        this.runForever = runForever;
    }

    public long getRepeatIntervalMs() {
        return repeatIntervalMs;
    }

    public void setRepeatIntervalMs(long repeatIntervalMs) {
        this.repeatIntervalMs = repeatIntervalMs;
    }

    public long getInitialOffsetMs() {
        return initialOffsetMs;
    }

    public void setInitialOffsetMs(long initialOffsetMs) {
        this.initialOffsetMs = initialOffsetMs;
    }

    public String getCallbackData() {
        return callbackData;
    }

    public void setCallbackData(String callbackData) {
        this.callbackData = callbackData;
    }
}
