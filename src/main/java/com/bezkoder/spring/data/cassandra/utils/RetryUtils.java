package com.bezkoder.spring.data.cassandra.utils;

import com.datastax.oss.driver.api.core.NoNodeAvailableException;
import com.datastax.oss.driver.api.core.servererrors.QueryExecutionException;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

@Component
public class RetryUtils {

    public static <T> T retryOnException(Callable<T> operation, int maxRetries, long delay) throws NoNodeAvailableException, QueryExecutionException {
        int attempts = 0;
        while (attempts < maxRetries) {
            try {
                    return operation.call();
                } catch (NoNodeAvailableException | QueryExecutionException e) {
                attempts++;
                if (attempts >= maxRetries) {
                    throw e; // Rethrow the exception after max retries
                }
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                    throw new RuntimeException("Thread was interrupted", ie);
                }
            } catch (Exception e) {

            }
        }
        return null; // This line should never be reached
    }

}
