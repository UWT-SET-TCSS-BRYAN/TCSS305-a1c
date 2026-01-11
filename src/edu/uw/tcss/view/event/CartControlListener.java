/*
 * TCSS 305 Assignment 1 - UW Bookstore
 */

package edu.uw.tcss.view.event;

/**
 * A functional interface for observing cart control events.
 * <p>
 * Components interested in cart control actions (typically the main frame)
 * implement this interface to be notified when the clear button is pressed
 * or the membership checkbox state changes.
 *
 * @author Charles Bryan
 * @version Winter 2025
 */
public interface CartControlListener {

    /**
     * Called when the clear button is clicked.
     * <p>
     * Implementations should clear all items from the cart and
     * reset all quantity text fields.
     */
    void onClearRequested();

    /**
     * Called when the membership checkbox state changes.
     *
     * @param hasMembership true if membership is now enabled, false otherwise
     */
    void onMembershipChanged(boolean hasMembership);
}
