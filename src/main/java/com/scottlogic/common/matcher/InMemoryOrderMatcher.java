package com.scottlogic.common.matcher;

import static com.google.common.collect.Lists.*;
import static com.scottlogic.common.matcher.Listing.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import static java.util.stream.Collectors.*;
import java.util.stream.Stream;

public final class InMemoryOrderMatcher implements OrderMatcher {
    
    private final List<Listing> assets = newArrayList();
    private final List<Listing> orders = newArrayList();

    @Override
    public void listAsset(Listing a)
    {
        AtomicLong amountLeft = new AtomicLong(a.quantity);
        List<Listing> matched = orders.stream()
                .filter(o -> !o.price.lessThan(a.price))
                .sorted(DEAREST_FIRST)
                .filter(o -> amountLeft.getAndAdd(-o.quantity) > 0)
                .collect(toList());
        
        orders.removeAll(matched);
        
        if(amountLeft.get() < 0) {
            Listing partialMatch = matched.get(matched.size() - 1);
            long stillRequired = -amountLeft.get();
            orders.add(partialMatch.adjustQuantity(stillRequired));
        }
        
        if(amountLeft.get() > 0) {
            assets.add(a.adjustQuantity(amountLeft.get()));
        }
    }

    @Override
    public Stream<Listing> unsoldAssets()
    {
        return assets.stream();
    }

    @Override
    public void placeOrder(Listing o)
    {
        AtomicLong amountStillRequired = new AtomicLong(o.quantity);
        List<Listing> matched = assets.stream()
                .filter(a -> !a.price.moreThan(o.price))
                .sorted(CHEAPEST_FIRST)
                .filter(a -> amountStillRequired.getAndAdd(-a.quantity) > 0)
                .collect(toList());
        
        assets.removeAll(matched);
        
        if(amountStillRequired.get() < 0) {
            Listing partialMatch = matched.get(matched.size() - 1);
            long quantityLeft = -amountStillRequired.get();
            assets.add(partialMatch.adjustQuantity(quantityLeft));
        }
        
        if(amountStillRequired.get() > 0) {
            orders.add(o.adjustQuantity(amountStillRequired.get()));
        }
    }

    @Override
    public Stream<Listing> activeOrders()
    {
        return orders.stream();
    }

}
