package com.scottlogic.common.util;

import static com.google.common.collect.Lists.*;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class CurrencyAmountTest {

    @Test
    public void moreThan() throws Throwable
    {
        CurrencyAmount lhs = CurrencyAmount.fromMinorUnits(23);
        
        assertTrue(lhs.moreThan(CurrencyAmount.fromMinorUnits(22)));
        assertFalse(lhs.moreThan(CurrencyAmount.fromMinorUnits(23)));
        assertFalse(lhs.moreThan(CurrencyAmount.fromMinorUnits(24)));
    }
    
    @Test
    public void lessThan() throws Throwable
    {
        CurrencyAmount lhs = CurrencyAmount.fromMinorUnits(23);
        
        assertTrue(lhs.lessThan(CurrencyAmount.fromMinorUnits(24)));
        assertFalse(lhs.lessThan(CurrencyAmount.fromMinorUnits(23)));
        assertFalse(lhs.lessThan(CurrencyAmount.fromMinorUnits(22)));
    }
    
    @Test
    public void compareTo() throws Throwable
    {
        CurrencyAmount a0 = CurrencyAmount.fromMinorUnits(13);
        CurrencyAmount a1 = CurrencyAmount.fromMinorUnits(4);
        CurrencyAmount a2 = CurrencyAmount.fromMinorUnits(6);
        
        List<CurrencyAmount> l = newArrayList(a0, a1, a2);
        Collections.sort(l);
        
        assertThat(l, contains(a1, a2, a0));
    }

}