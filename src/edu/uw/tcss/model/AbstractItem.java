package edu.uw.tcss.model;

import java.math.BigDecimal;

public abstract sealed class AbstractItem
        implements Item
        permits StoreItem, StoreBulkItem {

    protected AbstractItem(final String name, final BigDecimal price) {
        super();
    }
}
