/*
 * TCSS 305 Assignment 1 - UW Bookstore
 */

package edu.uw.tcss.view;

import edu.uw.tcss.model.Cart;
import edu.uw.tcss.res.R;
import java.awt.BorderLayout;
import java.io.Serial;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A panel displaying the cart summary information (total cost and item counts).
 * <p>
 * This component shows two pieces of information:
 * <ul>
 *   <li><b>Order total:</b> the total cost of all items in the cart</li>
 *   <li><b>Item count:</b> displays "itemCount/itemOrderCount" format</li>
 * </ul>
 *
 * <h2>Event Flow Pattern</h2>
 * This component is <b>downstream-only</b> (pure display):
 * <ul>
 *   <li>Controller calls {@link #updateSummary} to push new data</li>
 *   <li>Component displays the data in read-only text fields</li>
 *   <li>No user interaction, no events fired upstream</li>
 * </ul>
 * <p>
 * This is the ideal pattern for display-only components: the controller
 * owns the data and pushes updates; the component simply renders them.
 * There are no getters because the controller already knows the values
 * (it provided them).
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Display order total in currency format</li>
 *   <li>Display item count and item order count</li>
 *   <li>Update display when {@link #updateSummary} is called</li>
 *   <li>Use read-only text fields (non-editable)</li>
 * </ul>
 *
 * @author Charles Bryan
 * @version Winter 2025
 */
public final class CartSummaryPanel extends JPanel {

    /** The Serialization ID. */
    @Serial
    private static final long serialVersionUID = 9198377375189354505L;

    /**
     * The text field displaying the order total.
     */
    private final JTextField myTotalField;

    /**
     * The text field displaying the item counts.
     */
    private final JTextField myItemCountField;

    /**
     * Constructs a CartSummaryPanel with initial values.
     */
    public CartSummaryPanel() {
        super(new BorderLayout());

        myTotalField = new JTextField(R.UIStrings.BF_TEXTFIELD_TOTAL,
                R.Dimensions.BF_TEXTFIELD_TOTAL);
        myItemCountField = new JTextField(R.UIStrings.BF_TEXTFIELD_ITEMS_IN_CART,
                R.Dimensions.BF_TEXTFIELD_ITEM_COUNT);

        setupComponents();
    }

    /**
     * Sets up the visual components of this panel.
     */
    private void setupComponents() {
        // Create total panel
        final JPanel totalPanel = createTotalPanel();
        add(totalPanel, BorderLayout.CENTER);

        // Create item count panel
        final JPanel itemCountPanel = createItemCountPanel();
        add(itemCountPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates the panel displaying the order total.
     *
     * @return the total panel
     */
    private JPanel createTotalPanel() {
        // Configure total field
        myTotalField.setEditable(false);
        myTotalField.setEnabled(false);
        myTotalField.setDisabledTextColor(R.Colors.CONTENT_TEXT);

        // Create panel
        final JPanel panel = new JPanel();
        panel.setBackground(R.Colors.HEADER_FOOTER_BG);

        final JLabel label = new JLabel(R.UIStrings.BF_LABEL_TOTAL);
        label.setForeground(R.Colors.HEADER_FOOTER_TEXT);

        panel.add(label);
        panel.add(myTotalField);

        return panel;
    }

    /**
     * Creates the panel displaying the item count.
     *
     * @return the item count panel
     */
    private JPanel createItemCountPanel() {
        // Configure item count field
        myItemCountField.setEditable(false);
        myItemCountField.setEnabled(false);
        myItemCountField.setDisabledTextColor(R.Colors.CONTENT_TEXT);

        // Create panel
        final JPanel panel = new JPanel();
        panel.setBackground(R.Colors.HEADER_FOOTER_BG);

        final JLabel label = new JLabel(R.UIStrings.BF_LABEL_ITEM_IN_CART);
        label.setForeground(R.Colors.HEADER_FOOTER_TEXT);

        panel.add(label);
        panel.add(myItemCountField);

        return panel;
    }

    /**
     * Updates the summary display with new cart information.
     * <p>
     * This method should be called whenever the cart contents change
     * to refresh the displayed total and item counts.
     *
     * @param total the new order total
     * @param size the new cart size (item order count and item count)
     * @throws NullPointerException if either parameter is null
     */
    public void updateSummary(final BigDecimal total, final Cart.CartSize size) {
        Objects.requireNonNull(total, "Total cannot be null");
        Objects.requireNonNull(size, "Size cannot be null");

        // Update total field
        myTotalField.setText(NumberFormat.getCurrencyInstance(Locale.US).format(total));

        // Update item count field (format: itemCount/itemOrderCount)
        myItemCountField.setText(size.itemCount() + "/" + size.itemOrderCount());
    }
}
