package com.scottlogic.common.matcher;

import com.scottlogic.common.util.CurrencyAmount;
import static java.util.stream.Collectors.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class InMemoryOrderMatcherTest {

    private final InMemoryOrderMatcher matcher = new InMemoryOrderMatcher();
    
    @Test
    public void listingAssetWithNoOrders() throws Throwable
    {
        Listing a = new Listing("acc1", 2, CurrencyAmount.fromMinorUnits(23));
        
        matcher.listAsset(a);
        
        assertThat(matcher.unsoldAssets().collect(toList()), contains(a));
    }
    
    @Test
    public void placingOrderWithNoAssets() throws Throwable
    {
        Listing o = new Listing("acc1", 2, CurrencyAmount.fromMinorUnits(23));
        
        matcher.placeOrder(o);
        
        assertThat(matcher.activeOrders().collect(toList()), contains(o));
    }
    
    @Test
    public void placingOrderWithExactlyMatchingAsset() throws Throwable
    {
        Listing a = new Listing("acc1", 2, CurrencyAmount.fromMinorUnits(23));
        
        matcher.listAsset(a);
        
        Listing o = new Listing("acc1", 2, CurrencyAmount.fromMinorUnits(23));
        
        matcher.placeOrder(o);
        
        assertThat("assets", matcher.unsoldAssets().collect(toList()),
                hasSize(0));
        assertThat("orders", matcher.activeOrders().collect(toList()),
                hasSize(0));
    }
    
    @Test
    public void placingOrderWithPartiallyFulfillingAsset() throws Throwable
    {
        Listing a = new Listing("acc1", 4, CurrencyAmount.fromMinorUnits(23));
        
        matcher.listAsset(a);
        
        Listing o = new Listing("acc1", 7, CurrencyAmount.fromMinorUnits(23));
        
        matcher.placeOrder(o);
        
        assertThat("assets", matcher.unsoldAssets().collect(toList()),
                hasSize(0));
        assertThat("orders", matcher.activeOrders().collect(toList()),
                contains(new Listing("acc1", 3,
                        CurrencyAmount.fromMinorUnits(23))));
    }
    
    @Test
    public void placingOrderWithLargerQuantityAsset() throws Throwable
    {
        Listing a = new Listing("acc1", 8, CurrencyAmount.fromMinorUnits(22));
        
        matcher.listAsset(a);
        
        Listing o = new Listing("acc1", 3, CurrencyAmount.fromMinorUnits(23));
        
        matcher.placeOrder(o);
        
        assertThat("assets", matcher.unsoldAssets().collect(toList()),
                contains(new Listing("acc1", 5,
                        CurrencyAmount.fromMinorUnits(22))));
        assertThat("orders", matcher.activeOrders().collect(toList()),
                hasSize(0));
    }
    
    @Test
    public void placingOrderIgnoresAssetIfTooExpensive() throws Throwable
    {
        Listing a = new Listing("acc1", 2, CurrencyAmount.fromMinorUnits(24));
        
        matcher.listAsset(a);
        
        Listing o = new Listing("acc1", 2, CurrencyAmount.fromMinorUnits(23));
        
        matcher.placeOrder(o);
        
        assertThat("assets", matcher.unsoldAssets().collect(toList()),
                contains(new Listing("acc1", 2,
                        CurrencyAmount.fromMinorUnits(24))));
        assertThat("orders", matcher.activeOrders().collect(toList()),
                contains(new Listing("acc1", 2,
                        CurrencyAmount.fromMinorUnits(23))));
    }
    
    @Test
    public void placingOrderWithMultipleMatchingAssetsChoosesCheapest()
            throws Throwable
    {
        Listing a0 = new Listing("acc1", 5, CurrencyAmount.fromMinorUnits(24));
        Listing a1 = new Listing("acc2", 3, CurrencyAmount.fromMinorUnits(23));
        Listing a2 = new Listing("acc3", 2, CurrencyAmount.fromMinorUnits(22));
        
        matcher.listAsset(a0);
        matcher.listAsset(a1);
        matcher.listAsset(a2);
        
        Listing o = new Listing("acc4", 3, CurrencyAmount.fromMinorUnits(23));
        
        matcher.placeOrder(o);
        
        assertThat("assets", matcher.unsoldAssets().collect(toList()), contains(
                new Listing("acc1", 5, CurrencyAmount.fromMinorUnits(24)),
                new Listing("acc2", 2, CurrencyAmount.fromMinorUnits(23))
        ));
        assertThat("orders", matcher.activeOrders().collect(toList()),
                hasSize(0));
    }
    
    @Test
    public void listingAssetWithExactlyMatchingAsset() throws Throwable
    {
        Listing o = new Listing("acc1", 2, CurrencyAmount.fromMinorUnits(23));
        
        matcher.placeOrder(o);
        
        Listing a = new Listing("acc1", 2, CurrencyAmount.fromMinorUnits(23));
        
        matcher.listAsset(a);
        
        assertThat("assets", matcher.unsoldAssets().collect(toList()),
                hasSize(0));
        assertThat("orders", matcher.activeOrders().collect(toList()),
                hasSize(0));
    }
    
    @Test
    public void listingAssetWithPartiallyFulfillingAsset() throws Throwable
    {
        Listing o = new Listing("acc1", 4, CurrencyAmount.fromMinorUnits(23));
        
        matcher.placeOrder(o);
        
        Listing a = new Listing("acc1", 7, CurrencyAmount.fromMinorUnits(23));
        
        matcher.listAsset(a);
        
        assertThat("assets", matcher.unsoldAssets().collect(toList()),
                contains(new Listing("acc1", 3,
                        CurrencyAmount.fromMinorUnits(23))));
        assertThat("orders", matcher.activeOrders().collect(toList()),
                hasSize(0));
    }
    
    @Test
    public void listingAssetWithLargerQuantityAsset() throws Throwable
    {
        Listing o = new Listing("acc1", 8, CurrencyAmount.fromMinorUnits(23));
        
        matcher.placeOrder(o);
        
        Listing a = new Listing("acc1", 3, CurrencyAmount.fromMinorUnits(22));
        
        matcher.listAsset(a);
        
        assertThat("assets", matcher.unsoldAssets().collect(toList()),
                hasSize(0));
        assertThat("orders", matcher.activeOrders().collect(toList()),
                contains(new Listing("acc1", 5,
                        CurrencyAmount.fromMinorUnits(23))));
    }
    
    @Test
    public void listingAssetIgnoresOrderIfTooExpensive() throws Throwable
    {
        Listing o = new Listing("acc1", 2, CurrencyAmount.fromMinorUnits(23));
        
        matcher.placeOrder(o);
        
        Listing a = new Listing("acc1", 2, CurrencyAmount.fromMinorUnits(24));
        
        matcher.listAsset(a);
        
        assertThat("assets", matcher.unsoldAssets().collect(toList()),
                contains(new Listing("acc1", 2,
                        CurrencyAmount.fromMinorUnits(24))));
        assertThat("orders", matcher.activeOrders().collect(toList()),
                contains(new Listing("acc1", 2,
                        CurrencyAmount.fromMinorUnits(23))));
    }
    
    @Test
    public void listingAssetWithMultipleMatchingOrdersChoosesBiggestProfit()
            throws Throwable
    {
        Listing o0 = new Listing("acc1", 5, CurrencyAmount.fromMinorUnits(22));
        Listing o1 = new Listing("acc2", 3, CurrencyAmount.fromMinorUnits(23));
        Listing o2 = new Listing("acc3", 2, CurrencyAmount.fromMinorUnits(24));
        
        matcher.placeOrder(o0);
        matcher.placeOrder(o1);
        matcher.placeOrder(o2);
        
        Listing a = new Listing("acc4", 3, CurrencyAmount.fromMinorUnits(23));
        
        matcher.listAsset(a);
        
        assertThat("assets", matcher.unsoldAssets().collect(toList()),
                hasSize(0));
        assertThat("orders", matcher.activeOrders().collect(toList()), contains(
                new Listing("acc1", 5, CurrencyAmount.fromMinorUnits(22)),
                new Listing("acc2", 2, CurrencyAmount.fromMinorUnits(23))
        ));
    }

}