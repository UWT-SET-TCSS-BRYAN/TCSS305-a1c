/*
 * TCSS 305 Assignment 1 - UW Bookstore
 */

package edu.uw.tcss.view.event;

/**
 * A functional interface for observing item-related events in the bookstore UI.
 * <p>
 * Components that need to be notified of item events (such as quantity changes)
 * implement this interface. The sealed ItemEvent type ensures that handlers
 * can use exhaustive pattern matching to process all possible event types.
 * <p>
 * This interface enables loose coupling between UI components - item panels
 * don't need to know about the frame, cart, or how events are processed.
 * They simply fire ItemEvent instances and let the listener handle them.
 * <p>
 * Example implementation using pattern matching (Java 21+):
 * <pre>
 * public void onItemEvent(ItemEvent event) {
 *     switch (event) {
 *         case ItemEvent.QuantityChanged(Item item, int quantity) ->
 *             myCart.add(new ItemOrder(item, quantity));
 *     }
 * }
 * </pre>
 *
 * @author Charles Bryan
 * @version Winter 2025
 */
@FunctionalInterface
public interface ItemEventListener {

    /**
     * Called when an item-related event occurs.
     * <p>
     * Implementations should process the event appropriately. For example,
     * a QuantityChanged event should update the shopping cart with the new
     * quantity for the associated item.
     *
     * @param event the item event that occurred
     */
    void onItemEvent(ItemEvent event);
}
