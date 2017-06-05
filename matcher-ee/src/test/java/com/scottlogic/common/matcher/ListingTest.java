package com.scottlogic.common.matcher;

import static com.scottlogic.common.util.CurrencyAmount.*;
import org.junit.*;
import org.junit.rules.ExpectedException;
import static org.junit.rules.ExpectedException.*;

public class ListingTest {
    
    @Rule public ExpectedException thrown = none();

    @Test
    public void mustHaveANoneZeroAmount() throws Throwable
    {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("quantity");
        
        new Listing("a", 0, fromMinorUnits(0));
    }
    
    @Test
    public void mustHaveANoneNegativeAmount() throws Throwable
    {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("quantity");
        
        new Listing("a", -1, fromMinorUnits(0));
    }
    
    @Test
    public void mustHaveANoneEmptyAccount() throws Throwable
    {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("account name");
        
        new Listing("", 1, fromMinorUnits(0));
    }
    
    @Test
    public void mustHaveANoneBlankAccount() throws Throwable
    {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("account name");
        
        new Listing(" ", 1, fromMinorUnits(0));
    }
    
    @Test
    public void quantityMustBeNoneZero() throws Throwable
    {
        thrown.expect(IllegalArgumentException.class);
        
        new Listing("a", 1, fromMinorUnits(0)).adjustQuantityTo(0);
    }
    
    @Test
    public void quantityMustBePositive() throws Throwable
    {
        thrown.expect(IllegalArgumentException.class);
        
        new Listing("a", 1, fromMinorUnits(0)).adjustQuantityTo(-1);
    }

}