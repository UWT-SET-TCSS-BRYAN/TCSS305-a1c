/*
 * TCSS 305 Assignment 1 - UW Bookstore
 */

package edu.uw.tcss.view;

import edu.uw.tcss.model.Item;
import edu.uw.tcss.res.R;
import edu.uw.tcss.view.event.ItemEventListener;
import java.awt.GridLayout;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * A panel containing a grid of ItemRowPanel components.
 * <p>
 * This component manages the display of all available items in the current
 * campus inventory. It creates an ItemRowPanel for each item and arranges
 * them in a vertical grid layout.
 * <p>
 * This design follows the Single Responsibility Principle - the panel is
 * responsible only for displaying items and passing events to the listener.
 * It does NOT know about the shopping cart or how events will be processed.
 * This loose coupling makes the component reusable in different contexts.
 * <p>
 * Responsibilities:
 * - Create ItemRowPanel instances for each item
 * - Arrange panels in a grid layout
 * - Provide clearAllQuantities() to reset all quantity fields
 * - Pass ItemEventListener to child panels for event propagation
 *
 * @author Charles Bryan
 * @version Winter 2025
 */
public final class ItemListPanel extends JPanel {

    /** The Serialization ID. */
    @Serial
    private static final long serialVersionUID = 7198377375189354505L;

    /**
     * The list of ItemRowPanel components managed by this panel.
     */
    private final List<ItemRowPanel> myItemRows;

    /**
     * Constructs an ItemListPanel with the specified items.
     *
     * @param items the list of items to display
     * @param listener the listener to notify on item events
     * @throws NullPointerException if any parameter is null
     */
    public ItemListPanel(final List<Item> items,
                         final ItemEventListener listener) {
        super();

        Objects.requireNonNull(items, "Items list cannot be null");
        Objects.requireNonNull(listener, "Listener cannot be null");

        myItemRows = new ArrayList<>();

        setupComponents(items, listener);
    }

    /**
     * Sets up the visual components of this panel.
     *
     * @param items the list of items to display
     * @param listener the listener to notify on item events
     */
    private void setupComponents(final List<Item> items,
                                  final ItemEventListener listener) {
        // Set up grid layout with one column
        setLayout(new GridLayout(items.size(), R.Dimensions.BF_ITEMS_COLS));

        // Add padding around the panel
        setBorder(BorderFactory.createEmptyBorder(
                R.Dimensions.V_PADDING,
                R.Dimensions.H_PADDING,
                R.Dimensions.V_PADDING,
                R.Dimensions.H_PADDING));

        setBackground(R.Colors.CONTENT_BG);

        // Create an ItemRowPanel for each item
        for (final Item item : items) {
            final ItemRowPanel rowPanel = new ItemRowPanel(item, listener);
            myItemRows.add(rowPanel);
            add(rowPanel);
        }
    }

    /**
     * Clears all quantity text fields in all item rows without firing events.
     * <p>
     * This method delegates to {@link ItemRowPanel#clearQuantity()} for each
     * row, which is intentionally "silent" (no events fired). This follows
     * the bidirectional event flow pattern:
     * <ul>
     *   <li>User-initiated changes fire events (upstream notification)</li>
     *   <li>Controller-initiated changes do not (controller already knows)</li>
     * </ul>
     * <p>
     * The controller calling this method is responsible for updating the
     * model (e.g., calling {@code Cart.clear()}) and refreshing the display.
     * <p>
     * Typical usage: called when the "Clear" button is pressed.
     *
     * @see ItemRowPanel#clearQuantity()
     */
    public void clearAllQuantities() {
        for (final ItemRowPanel row : myItemRows) {
            row.clearQuantity();
        }
    }
}
