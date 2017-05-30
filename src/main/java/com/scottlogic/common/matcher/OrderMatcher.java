package com.scottlogic.common.matcher;

import java.util.stream.Stream;

public interface OrderMatcher {

    Stream<Listing> activeOrders();
    void listAsset(Listing a);
    void placeOrder(Listing o);
    Stream<Listing> unsoldAssets();

}
