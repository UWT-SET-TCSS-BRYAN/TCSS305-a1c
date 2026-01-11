/*
 * TCSS 305 Assignment 1 - UW Bookstore
 */

package edu.uw.tcss.view.event;

import edu.uw.tcss.model.Item;
import java.util.Objects;

/**
 * Represents an event related to an item in the bookstore UI.
 * <p>
 * This is a sealed interface using Java 17's sealed types feature, which creates
 * a closed hierarchy of event types. The compiler knows all possible event types,
 * enabling exhaustive pattern matching and preventing unexpected extensions.
 * <p>
 * Currently, the only event type is QuantityChanged, which is fired when a user
 * modifies the quantity for an item. Future event types could include:
 * - ItemSelected (when user clicks on an item)
 * - ItemFavorited (when user marks an item as favorite)
 * - ItemReviewed (when user rates an item)
 * <p>
 * This design demonstrates the Information Expert pattern - events carry all the
 * data needed to process them, eliminating the need for event handlers to query
 * additional state.
 *
 * @author Charles Bryan
 * @version Winter 2025
 */
public sealed interface ItemEvent permits ItemEvent.QuantityChanged {

    /**
     * Returns the item associated with this event.
     * <p>
     * All item events have an associated item, so this method is part of
     * the sealed interface rather than specific event types.
     *
     * @return the item associated with this event
     */
    Item item();

    /**
     * Represents a quantity change event for an item.
     * <p>
     * This event is fired when a user enters a new quantity for an item
     * in the shopping interface. It carries both the item and the new quantity,
     * allowing event handlers to update the shopping cart appropriately.
     * <p>
     * Being a record, this class automatically provides:
     * - Constructor with all fields
     * - Accessor methods: item() and quantity()
     * - equals() and hashCode() based on all fields
     * - toString() in debug format: "QuantityChanged[item=..., quantity=...]"
     * - Immutability (all fields are final)
     * <p>
     * Example usage:
     * <pre>
     * ItemEvent event = new ItemEvent.QuantityChanged(myItem, 5);
     * listener.onItemEvent(event);
     * </pre>
     *
     * @param item the item whose quantity changed
     * @param quantity the new quantity for the item (0 to remove from cart)
     */
    record QuantityChanged(Item item, int quantity) implements ItemEvent {
        /**
         * Constructor for the QuantityChanged record.
         * Validates input to ensure the item is not null and the quantity is non-negative.
         *
         * @param item the item associated with this event; must not be null
         * @param quantity the new quantity for the item; must not be negative
         * @throws NullPointerException if the item is null
         * @throws IllegalArgumentException if the quantity is negative
         */
        public QuantityChanged {
            Objects.requireNonNull(item, "Item cannot be null");
            if (quantity < 0) {
                throw new IllegalArgumentException("Quantity cannot be negative");
            }
        }
    }
}
