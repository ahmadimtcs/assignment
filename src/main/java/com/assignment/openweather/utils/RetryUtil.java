package com.assignment.openweather.utils;

import com.assignment.openweather.exception.ServiceException;
import java.time.Duration;
import reactor.core.Exceptions;
import reactor.util.retry.Retry;
import reactor.util.retry.RetrySpec;

public class RetryUtil {

  private RetryUtil() {
  }

  public static Retry retrySpec() {
    return RetrySpec.fixedDelay(3, Duration.ofSeconds(1))
        .filter((ex) -> ex instanceof ServiceException)
        .onRetryExhaustedThrow(
            ((retryBackoffSpec, retrySignal) -> Exceptions.propagate(retrySignal.failure())));

  }
}
