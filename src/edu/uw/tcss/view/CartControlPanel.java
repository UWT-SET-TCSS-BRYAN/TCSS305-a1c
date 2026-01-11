/*
 * TCSS 305 Assignment 1 - UW Bookstore
 */

package edu.uw.tcss.view;

import edu.uw.tcss.res.R;
import edu.uw.tcss.view.event.CartControlListener;
import java.io.Serial;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 * A panel containing cart control widgets (clear button and membership checkbox).
 * <p>
 * This component provides two user controls:
 * <ul>
 *   <li><b>Clear button:</b> requests that all items be removed from the cart</li>
 *   <li><b>Membership checkbox:</b> toggles membership pricing on/off</li>
 * </ul>
 *
 * <h2>Event Flow Pattern</h2>
 * This component is <b>upstream-only</b> (pure event source):
 * <ul>
 *   <li>User clicks Clear → fires {@link CartControlListener#onClearRequested}</li>
 *   <li>User toggles checkbox → fires {@link CartControlListener#onMembershipChanged}</li>
 *   <li>Controller receives events and updates model accordingly</li>
 * </ul>
 * <p>
 * There are no downstream (controller → UI) operations. The controller does
 * not programmatically modify this component's state. If future requirements
 * need programmatic reset (e.g., uncheck membership on campus switch), the
 * method should fire an event to keep the model in sync.
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Display clear button and membership checkbox</li>
 *   <li>Fire clear events when button is clicked</li>
 *   <li>Fire membership change events when checkbox state changes</li>
 * </ul>
 *
 * @author Charles Bryan
 * @version Winter 2025
 * @see CartControlListener
 */
public final class CartControlPanel extends JPanel {

    /** The Serialization ID. */
    @Serial
    private static final long serialVersionUID = 1098377375189354505L;

    /**
     * The listener to notify when controls are activated.
     */
    private final CartControlListener myListener;

    /**
     * The membership checkbox.
     */
    private final JCheckBox myMembershipCheckBox;

    /**
     * Constructs a CartControlPanel.
     *
     * @param listener the listener to notify on control events
     * @throws NullPointerException if listener is null
     */
    public CartControlPanel(final CartControlListener listener) {
        super();

        myListener = Objects.requireNonNull(listener, "Listener cannot be null");
        myMembershipCheckBox = new JCheckBox(R.UIStrings.BF_CHECKBOX_MEMBER);

        setupComponents();
    }

    /**
     * Sets up the visual components of this panel.
     */
    private void setupComponents() {
        setBackground(R.Colors.HEADER_FOOTER_BG);

        // Create and configure clear button
        final JButton clearButton = new JButton(R.UIStrings.BF_BUTTON_CLEAR);
        clearButton.addActionListener(event -> myListener.onClearRequested());
        add(clearButton);

        // Create and configure membership checkbox
        myMembershipCheckBox.setForeground(R.Colors.HEADER_FOOTER_TEXT);
        myMembershipCheckBox.setBackground(R.Colors.HEADER_FOOTER_BG);
        myMembershipCheckBox.addActionListener(event ->
                myListener.onMembershipChanged(myMembershipCheckBox.isSelected()));
        add(myMembershipCheckBox);
    }

}
