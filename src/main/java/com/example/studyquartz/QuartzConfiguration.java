package com.example.studyquartz;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
//@EnableAutoConfiguration
public class QuartzConfiguration {

    /*
    https://docs.spring.io/spring-boot/docs/2.1.1.RELEASE/reference/html/boot-features-quartz.html
    https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.quartz
    https://wannaqueen.gitbook.io/spring5/spring-boot/undefined-1/41.-quartz-scheduler

        -> 스프링부트에서 quartz 의 jobStore 을 사용하기 위한 설정 : @Bean과 @QuartzDataSource를 같이 사용하여 datasource 빈 생성 메소드를 만들어준다.

        -> @EnableAutoConfiguration, @QuartzDataSource 를 제거해도 정상 동작함.(버전 업 되면서 뭔가 수정 된 듯?)

        예상.
        1. @EnableAutoConfiguration 이 없어도 dataSource 객체는 spring bean으로 등록 됨
        2. @QuartzDataSource 가 없고 spring bean 이름이 quartzDataSource가 아니여도 어떤 내부 동작에 의해 Quartz JobStore가 dataSource 스프링 빈을 찾아 db 연결을 함
    */
    @Bean(name = "aa")
    //@QuartzDataSource
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource aa() {
    /*
    https://escapefromcoding.tistory.com/m/711
        -> 스프링 부트 환경에서 db와 연결하기 위한 dataSource 빈을 등록해준다.
     */
        return DataSourceBuilder.create().build();
    }
}
