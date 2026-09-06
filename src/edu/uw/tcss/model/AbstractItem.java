package edu.uw.tcss.model;

import java.math.BigDecimal;

// TODO add a class Javadoc comment here, including @author and @version tags.

// AbstractItem holds the state and behavior common to every Item: a name and a
// unit price. StoreItem and StoreBulkItem extend it and add their own pricing.
//
// Complete this class so that it provides:
//
//   protected AbstractItem(String name, BigDecimal price)
//       Validates and stores the name and price.
//       Throws NullPointerException if name or price is null.
//       Throws IllegalArgumentException if name is empty or price is negative.
//
//   public String getName()
//       Returns the name for this Item.
//
//   public BigDecimal getPrice()
//       Returns the unit price for this Item.
//
//   protected static final NumberFormat CURRENCY_FORMAT
//       A shared US-locale currency formatter. Initialize it here so that the
//       getFormattedDescription() methods in the subclasses can use it:
//       CURRENCY_FORMAT.format(getPrice()) returns "$2.00".
//
// Every member above needs its own Javadoc comment before this class will pass
// Checkstyle. See Requirement 1 of the assignment for the full specification.
public abstract sealed class AbstractItem
        implements Item
        permits StoreItem, StoreBulkItem {

    protected AbstractItem(final String name, final BigDecimal price) {
        super();
    }
}
