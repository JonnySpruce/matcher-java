package com.scottlogic.jee.matcher;

import static com.google.common.collect.Lists.*;
import com.scottlogic.common.matcher.Listing;
import com.scottlogic.common.matcher.OrderMatcher;
import static com.scottlogic.common.util.CurrencyAmount.*;
import java.time.Duration;
import java.time.Instant;
import static java.time.Instant.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import static java.util.concurrent.Executors.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ThreadSafeOrderMatcherTest {

    private static final int THREADS = 4;
    private static final long WAIT_MILLIS = 50;
    
    private ThreadSafeOrderMatcher matcher;
    @Mock private OrderMatcher wrapped;
    
    private final ExecutorService threadPool = newFixedThreadPool(THREADS);
    private final CountDownLatch doneSignal = new CountDownLatch(THREADS);
    private final List<Instant> callTimestamps =
            newArrayListWithCapacity(THREADS);
    
    private final Listing listing = new Listing("a", 1123, fromMinorUnits(213));
    private final Stream<Listing> listingStream = Stream.of(listing);
    
    @Before
    public void matcher() throws Throwable
    {
        matcher = new ThreadSafeOrderMatcher(wrapped);
    }
    
    @Test
    public void activeOrdersThreadSafe() throws Throwable
    {
        when(wrapped.activeOrders()).thenAnswer(i -> {
            final Instant now = now();
            callTimestamps.add(now);
            while(now().isBefore(now.plusMillis(WAIT_MILLIS + 1))) {
                Thread.sleep(WAIT_MILLIS);
            }
            return listingStream;
        });
        
        for(int i = 0, j = THREADS; i < j; ++i) {
            threadPool.execute(() -> {
                assertThat("wrapper did not return wrapped response exactly",
                        matcher.activeOrders(), sameInstance(listingStream));
                doneSignal.countDown();
            });
        }
        
        doneSignal.await(1000, TimeUnit.SECONDS);
        
        assertCallTimesSufficientlySpaced();
    }
    
    @Test
    public void unsoldAssetsThreadSafe() throws Throwable
    {
        when(wrapped.unsoldAssets()).thenAnswer(i -> {
            final Instant now = now();
            callTimestamps.add(now);
            while(now().isBefore(now.plusMillis(WAIT_MILLIS + 1))) {
                Thread.sleep(WAIT_MILLIS);
            }
            return listingStream;
        });
        
        for(int i = 0, j = THREADS; i < j; ++i) {
            threadPool.execute(() -> {
                assertThat("wrapper did not return wrapped response exactly",
                        matcher.unsoldAssets(), sameInstance(listingStream));
                doneSignal.countDown();
            });
        }
        
        doneSignal.await(1000, TimeUnit.SECONDS);
        
        assertCallTimesSufficientlySpaced();
    }
    
    @Test
    public void listAssetThreadSafe() throws Throwable
    {
        doAnswer(i -> {
            final Instant now = now();
            callTimestamps.add(now);
            while(now().isBefore(now.plusMillis(WAIT_MILLIS + 1))) {
                Thread.sleep(WAIT_MILLIS);
            }
            return null;
        }).when(wrapped).listAsset(anyObject());
        
        for(int i = 0, j = THREADS; i < j; ++i) {
            threadPool.execute(() -> {
                matcher.listAsset(listing);
                doneSignal.countDown();
            });
        }
        
        doneSignal.await(1000, TimeUnit.SECONDS);
        
        verify(wrapped, times(THREADS)).listAsset(listing);
        
        assertCallTimesSufficientlySpaced();
    }
    
    @Test
    public void placeOrderThreadSafe() throws Throwable
    {
        doAnswer(i -> {
            final Instant now = now();
            callTimestamps.add(now);
            while(now().isBefore(now.plusMillis(WAIT_MILLIS + 1))) {
                Thread.sleep(WAIT_MILLIS);
            }
            return null;
        }).when(wrapped).placeOrder(anyObject());
        
        for(int i = 0, j = THREADS; i < j; ++i) {
            threadPool.execute(() -> {
                matcher.placeOrder(listing);
                doneSignal.countDown();
            });
        }
        
        doneSignal.await(1000, TimeUnit.SECONDS);
        
        verify(wrapped, times(THREADS)).placeOrder(listing);
        
        assertCallTimesSufficientlySpaced();
    }

    private void assertCallTimesSufficientlySpaced()
    {
        assertThat("wrong number of calls", callTimestamps, hasSize(THREADS));
        
        for(int i = 1, j = THREADS; i < j; ++i) {
            Instant earlierCall = callTimestamps.get(i - 1);
            Instant laterCall = callTimestamps.get(i);
            assertThat("calls were too frequent suggesting locking failed",
                    Duration.between(earlierCall, laterCall).toMillis(),
                    greaterThanOrEqualTo(WAIT_MILLIS));
        }
    }

}