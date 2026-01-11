package edu.uw.tcss.model;

import java.util.Objects;

/**
 * Represents a purchase order for an item - an immutable pairing of an Item
 * with a desired quantity.
 * <p>
 * This is a pure data holder implemented as a Java record. It does not contain
 * display logic or business logic - it simply holds the item and quantity.
 * The GUI or other code is responsible for formatting this data for display.
 * <p>
 * Being a record, this class automatically provides:
 * - Constructor with all fields
 * - Accessor methods: item() and quantity()
 * - equals() and hashCode() based on all fields
 * - toString() in debug format: "ItemOrder[item=..., quantity=...]"
 * - Immutability (all fields are final)
 *
 * @param item the Item being ordered
 * @param quantity the quantity of the item being ordered
 * @author Charles Bryan
 * @version Winter 2025
 */
public record ItemOrder(Item item, int quantity) {

    /**
     * Compact constructor that validates the item and quantity.
     *
     * @throws NullPointerException if item is null
     * @throws IllegalArgumentException if quantity is negative
     */
    public ItemOrder {
        Objects.requireNonNull(item, "Item cannot be null");
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative: "
                    + quantity);
        }
    }
}
