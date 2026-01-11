/*
 * TCSS 305 Assignment 1 - UW Bookstore
 */

package edu.uw.tcss.view;

import edu.uw.tcss.model.Item;
import edu.uw.tcss.res.R;
import edu.uw.tcss.view.event.ItemEvent;
import edu.uw.tcss.view.event.ItemEventListener;
import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.Serial;
import java.util.Objects;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * A panel representing a single item row with a quantity text field.
 * <p>
 * This component displays an item's description and provides a text field
 * for entering the desired quantity. When the user changes the quantity,
 * it fires an {@link ItemEvent.QuantityChanged} event to notify listeners.
 * <p>
 * This design follows the Single Responsibility Principle - the panel is
 * responsible only for displaying the item and capturing user input. It
 * does NOT know about the shopping cart or how the event will be processed.
 * This loose coupling makes the component reusable in different contexts.
 *
 * <h2>Bidirectional Event Flow Pattern</h2>
 * This component follows a bidirectional communication pattern common in
 * Swing and other UI frameworks:
 * <ul>
 *   <li><b>Upstream (User → Controller):</b> When the USER changes the
 *       quantity, an {@link ItemEvent.QuantityChanged} event is fired.
 *       The controller receives this event and updates the model. Events
 *       notify the controller of changes it doesn't already know about.</li>
 *   <li><b>Downstream (Controller → UI):</b> When the CONTROLLER initiates
 *       a change (e.g., clearing all quantities), it calls
 *       {@link #clearQuantity()} directly. No event is fired because the
 *       controller already knows what action it performed.</li>
 * </ul>
 * <p>
 * This pattern avoids redundant notifications: the controller only needs
 * to be notified of user-initiated changes, not its own actions.
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Display item description via {@code getFormattedDescription()}</li>
 *   <li>Validate quantity input (non-negative integers only)</li>
 *   <li>Fire {@link ItemEvent.QuantityChanged} on user input</li>
 *   <li>Provide {@link #clearQuantity()} for controller-initiated resets</li>
 * </ul>
 *
 * @author Charles Bryan
 * @version Winter 2025
 * @see ItemEvent
 * @see ItemEventListener
 */
public final class ItemRowPanel extends JPanel {

    /** The Serialization ID. */
    @Serial
    private static final long serialVersionUID = 6198377375189354505L;

    /**
     * The item displayed in this row.
     */
    private final Item myItem;

    /**
     * The listener to notify when item events occur.
     */
    private final ItemEventListener myListener;

    /**
     * The text field for entering the quantity.
     */
    private final JTextField myQuantityField;

    /**
     * Constructs an ItemRowPanel for the specified item.
     *
     * @param item the item to display
     * @param listener the listener to notify on item events
     * @throws NullPointerException if any parameter is null
     */
    public ItemRowPanel(final Item item,
                        final ItemEventListener listener) {
        super(new FlowLayout(FlowLayout.LEFT));

        myItem = Objects.requireNonNull(item, "Item cannot be null");
        myListener = Objects.requireNonNull(listener,
                "Listener cannot be null");

        myQuantityField = new JTextField(R.Dimensions.BF_TEXTFIELD_QUANTITY);

        setupComponents();
    }

    /**
     * Sets up the visual components of this panel.
     */
    private void setupComponents() {
        setBackground(R.Colors.CONTENT_BG);

        // Configure quantity field
        myQuantityField.setHorizontalAlignment(SwingConstants.CENTER);
        myQuantityField.addActionListener(event -> myQuantityField.transferFocus());
        myQuantityField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent event) {
                updateQuantity();
            }
        });

        // Create and configure label
        final JLabel label = new JLabel(myItem.getFormattedDescription());
        label.setForeground(R.Colors.HEADER_FOOTER_BG);

        // Add components
        add(myQuantityField);
        add(label);
    }

    /**
     * Fires a QuantityChanged event with the current quantity from the text field.
     * <p>
     * Validates the input and converts it to an integer. Invalid input
     * (negative numbers, non-numeric text) results in the quantity being
     * set to 0 and the text field being cleared.
     * <p>
     * This method does NOT directly modify the shopping cart - it simply
     * fires an event and lets the listener decide how to handle it. This
     * loose coupling makes the component more flexible and reusable.
     */
    private void updateQuantity() {
        final String text = myQuantityField.getText().trim();
        int quantity;
        try {
            quantity = Integer.parseInt(text);
            if (quantity < 0) {
                // Disallow negative numbers
                throw new NumberFormatException();
            }
        } catch (final NumberFormatException e) {
            quantity = 0;
            myQuantityField.setText("");
        }

        // Fire event - let the listener handle cart updates
        myListener.onItemEvent(new ItemEvent.QuantityChanged(myItem, quantity));
    }

    /**
     * Clears the quantity text field without firing an event.
     * <p>
     * This method is intentionally "silent" - it does NOT fire an
     * {@link ItemEvent.QuantityChanged} event. This is by design:
     * <ul>
     *   <li>This method is called by the controller (downstream)</li>
     *   <li>The controller already knows it initiated this action</li>
     *   <li>No notification is needed for controller-initiated changes</li>
     * </ul>
     * <p>
     * Typical usage: called when the "Clear" button is pressed or when
     * switching to a different campus.
     *
     * @see ItemListPanel#clearAllQuantities()
     */
    public void clearQuantity() {
        myQuantityField.setText("");
    }

}
