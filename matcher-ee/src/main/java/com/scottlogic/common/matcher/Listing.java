package com.scottlogic.common.matcher;

import static com.google.common.base.Preconditions.*;
import com.scottlogic.common.util.CurrencyAmount;
import java.util.Comparator;
import static org.apache.commons.lang3.StringUtils.*;
import static org.apache.commons.lang3.builder.EqualsBuilder.*;
import static org.apache.commons.lang3.builder.HashCodeBuilder.*;
import static org.apache.commons.lang3.builder.ToStringBuilder.*;
import static org.apache.commons.lang3.builder.ToStringStyle.*;

public final class Listing {
    
    public final String account;
    public final CurrencyAmount price;
    public final long quantity;

    public Listing(String account, long quantity, CurrencyAmount price)
    {
        checkArgument(!isBlank(account), "account name may not be blank");
        checkArgument(quantity > 0, "quantity must be greater than zero");
        
        this.account = account;
        this.price = price;
        this.quantity = quantity;
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

    public Listing adjustQuantityTo(long newQuantity)
    {
        return new Listing(account, newQuantity, price);
    }
    
    public final static Comparator<Listing> CHEAPEST_FIRST =
            (lhs, rhs) -> lhs.price.compareTo(rhs.price);
    
    public final static Comparator<Listing> DEAREST_FIRST =
            (lhs, rhs) -> -lhs.price.compareTo(rhs.price);

}
