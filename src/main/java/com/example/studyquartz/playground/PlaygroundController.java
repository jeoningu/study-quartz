package com.example.studyquartz.playground;


import com.example.studyquartz.info.TimerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timer")
public class PlaygroundController {

    private final PlaygroundService service;

    @Autowired
    public PlaygroundController(PlaygroundService service) {
        this.service = service;
    }

    @PostMapping("/runhellowold")
    public void runHelloWorldJob() {
        service.runHelloWorldJob();
    }

    @GetMapping
    public List<TimerInfo> getAllRunningTimers() {
        return service.getAllRunningTimers();
    }

    @GetMapping("/{timerId}")
    public TimerInfo getRunningTimers(@PathVariable String timerId) {
        return service.getRunningTimers(timerId);
    }

    @DeleteMapping("/{timerId}")
    public boolean deleteTimer(@PathVariable String timerId) {
        return service.deleteTimer(timerId);
    }



}
