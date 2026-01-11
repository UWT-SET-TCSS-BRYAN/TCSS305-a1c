package edu.uw.tcss.model;

import java.math.BigDecimal;

/**
 * Represents a shopping cart that holds item orders.
 * <p>
 * A Cart manages a collection of ItemOrder objects and calculates the total
 * cost based on whether the customer has a store membership. The cart ensures
 * that only one order exists per unique Item - adding a new order for an
 * existing item replaces the previous order.
 *
 * @author Charles Bryan
 * @version Winter 2025
 */
public interface Cart {

    /**
     * Adds an order to the shopping cart.
     * <p>
     * If an order for the same item already exists, it is replaced with the new order.
     * If the order quantity is 0, the item is removed from the cart entirely.
     *
     * @param order the ItemOrder to add to the cart
     * @throws NullPointerException if order is null
     */
    void add(ItemOrder order);

    /**
     * Sets whether the customer has a store membership.
     * <p>
     * Membership status affects pricing - members receive bulk discounts on
     * eligible items.
     *
     * @param membership true if the customer has a membership, false otherwise
     */
    void setMembership(boolean membership);

    /**
     * Calculates the total cost of all items in the shopping cart.
     * <p>
     * The total is calculated by summing the cost of each ItemOrder, taking
     * into account the membership status. The returned value has a scale of 2
     * and uses HALF_EVEN (banker's) rounding.
     *
     * @return the total cost of the shopping cart
     */
    BigDecimal calculateTotal();

    /**
     * Removes all orders from the shopping cart.
     */
    void clear();

    /**
     * Returns the current size of the shopping cart.
     *
     * @return a CartSize record containing the number of unique item orders
     *         and the total quantity of all items
     */
    CartSize getCartSize();

    /**
     * A record representing the size of a shopping cart.
     *
     * @param itemOrderCount the number of unique ItemOrder objects in the cart
     * @param itemCount the total quantity of all items in the cart
     */
    record CartSize(int itemOrderCount, int itemCount) {
        // Record automatically provides constructor, accessors, equals, hashCode, toString
    }
}
