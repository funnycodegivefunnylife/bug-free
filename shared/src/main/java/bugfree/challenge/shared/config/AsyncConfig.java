package bugfree.challenge.shared.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import bugfree.challenge.shared.async.MDCTaskDecorator;

/** Configuration for async task execution with MDC support */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

  @Override
  @Bean(name = "taskExecutor")
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(5);
    executor.setMaxPoolSize(20);
    executor.setQueueCapacity(100);
    executor.setThreadNamePrefix("async-");
    executor.setTaskDecorator(new MDCTaskDecorator());
    executor.initialize();
    return executor;
  }
}
