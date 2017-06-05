package com.scottlogic.jee.matcher;

import com.scottlogic.common.matcher.InMemoryOrderMatcher;
import com.scottlogic.common.matcher.Listing;
import com.scottlogic.common.matcher.OrderMatcher;
import com.scottlogic.jee.util.ThreadSafe;
import java.util.stream.Stream;
import javax.inject.Singleton;

@Singleton
@ThreadSafe
public class ThreadSafeOrderMatcher implements OrderMatcher {
    
    private final OrderMatcher wrapped;

    public ThreadSafeOrderMatcher(OrderMatcher wrapped)
    {
        this.wrapped = wrapped;
    }
    
    public ThreadSafeOrderMatcher()
    {
        this.wrapped = new InMemoryOrderMatcher();
    }

    @Override
    public synchronized Stream<Listing> activeOrders()
    {
        return wrapped.activeOrders();
    }

    @Override
    public synchronized void listAsset(Listing a)
    {
        wrapped.listAsset(a);
    }

    @Override
    public synchronized void placeOrder(Listing o)
    {
        wrapped.placeOrder(o);
    }

    @Override
    public synchronized Stream<Listing> unsoldAssets()
    {
        return wrapped.unsoldAssets();
    }

}
