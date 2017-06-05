package com.scottlogic.jee.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scottlogic.common.matcher.Listing;
import com.scottlogic.common.util.CurrencyAmount;
import static org.apache.commons.lang3.builder.EqualsBuilder.*;
import static org.apache.commons.lang3.builder.HashCodeBuilder.*;
import static org.apache.commons.lang3.builder.ToStringBuilder.*;
import static org.apache.commons.lang3.builder.ToStringStyle.*;

public final class ListingDto {
    
    @JsonProperty private String account;
    @JsonProperty private long price;
    @JsonProperty private long quantity;

    public ListingDto()
    {
    }
    
    public ListingDto(Listing l)
    {
        this(l.account, l.quantity, l.price.asMinorUnits());
    }
    
    public ListingDto(String account, long quantity, long price)
    {
        this.account = account;
        this.price = price;
        this.quantity = quantity;
    }
    
    public Listing toListing()
    {
        return new Listing(account, quantity,
                CurrencyAmount.fromMinorUnits(price));
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

}
