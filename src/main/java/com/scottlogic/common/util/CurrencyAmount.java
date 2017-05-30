package com.scottlogic.common.util;

import static org.apache.commons.lang3.builder.EqualsBuilder.*;
import static org.apache.commons.lang3.builder.HashCodeBuilder.*;
import static org.apache.commons.lang3.builder.ToStringBuilder.*;
import static org.apache.commons.lang3.builder.ToStringStyle.*;

public final class CurrencyAmount implements Comparable<CurrencyAmount> {
    
    private final long minorUnits;

    private CurrencyAmount(long minorUnits)
    {
        this.minorUnits = minorUnits;
    }
    
    public static CurrencyAmount fromMinorUnits(long minorUnits)
    {
        return new CurrencyAmount(minorUnits);
    }
    
    public long asMinorUnits()
    {
        return minorUnits;
    }
    
    @Override
    public boolean equals(Object other)
    {
        return reflectionEquals(this, other);
    }
    
    @Override
    public int hashCode()
    {
        return reflectionHashCode(this);
    }
    
    @Override
    public String toString()
    {
        return reflectionToString(this, NO_CLASS_NAME_STYLE);
    }

    public boolean moreThan(CurrencyAmount other)
    {
        return minorUnits > other.minorUnits;
    }

    public boolean lessThan(CurrencyAmount other)
    {
        return minorUnits < other.minorUnits;
    }

    @Override
    public int compareTo(CurrencyAmount other)
    {
        return Long.compare(minorUnits, other.minorUnits);
    }
    
}
