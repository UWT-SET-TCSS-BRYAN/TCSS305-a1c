/*
 * TCSS 305 Assignment 1 - UW Bookstore
 */

package edu.uw.tcss.view;

import edu.uw.tcss.model.Cart;
import edu.uw.tcss.model.Item;
import edu.uw.tcss.model.ItemOrder;
import edu.uw.tcss.model.StoreCart;
import edu.uw.tcss.res.R;
import edu.uw.tcss.view.event.CampusSelectionListener;
import edu.uw.tcss.view.event.CartControlListener;
import edu.uw.tcss.view.event.ItemEvent;
import edu.uw.tcss.view.event.ItemEventListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.Serial;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * BookstoreFrame provides the user interface for a UW Bookstore program.
 * <p>
 * This refactored version decomposes the monolithic frame into focused,
 * reusable components following the Single Responsibility Principle.
 * The frame acts as an orchestrator, composing smaller panels and
 * coordinating their interactions through listener interfaces.
 * <p>
 * Architecture:
 * - CampusSelectorPanel: campus radio buttons
 * - CartSummaryPanel: total and item count display
 * - ItemListPanel: grid of item rows with quantity fields
 * - CartControlPanel: clear button and membership checkbox
 * <p>
 * The frame implements listener interfaces to handle events from
 * the child components and coordinate updates across panels.
 * <p>
 * Event Handling:
 * This version uses the sealed ItemEvent type system with pattern matching
 * to handle item-related events. This provides type-safe, exhaustive event
 * handling that the compiler can verify.
 *
 * @author Marty Stepp
 * @author Daniel M. Zimmerman (Formatting and Comments)
 * @author Alan Fowler (Numerous changes to code and comments including use of BigDecimal)
 * @author Charles Bryan (Added radio buttons to change campus locations/changed name)
 * @author Charles Bryan (Refactored into component-based architecture)
 * @version Winter 2025
 */
public final class BookstoreFrame extends JFrame
        implements CampusSelectionListener,
        CartControlListener,
        ItemEventListener {

    /** The Serialization ID. */
    @Serial
    private static final long serialVersionUID = 505198377375189354L;

    /** A ToolKit. */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();

    /** The Dimension of the screen. */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();

    /**
     * The shopping cart used by this GUI.
     */
    private final Cart myCart;

    /**
     * The map that stores each campus name and the campus's bookstore inventory.
     */
    private final Map<String, List<Item>> myCampusInventories;

    /**
     * The currently selected campus.
     */
    private String myCurrentCampus;

    /**
     * The cart summary panel (displays total and item count).
     */
    private CartSummaryPanel myCartSummary;

    /**
     * The item list panel (displays all items with quantity fields).
     */
    private ItemListPanel myItemList;

    /**
     * Initializes the bookstore GUI.
     *
     * @param campusInventories the map of campus names to item lists
     * @param currentCampus the campus that is originally selected when the app starts
     * @throws NullPointerException if any parameter is null
     */
    public BookstoreFrame(final Map<String, List<Item>> campusInventories,
                          final String currentCampus) {
        super();

        myCart = new StoreCart();
        myCampusInventories = Objects.requireNonNull(campusInventories,
                "Campus inventories cannot be null");
        myCurrentCampus = Objects.requireNonNull(currentCampus,
                "Current campus cannot be null");

        setupGUI();
    }

    /**
     * Sets up the various parts of the GUI.
     */
    private void setupGUI() {
        // Set up frame icon
        final ImageIcon img = new ImageIcon(
                R.SystemStrings.IO_FILE_LOCATION + R.SystemStrings.IO_ICON_FILE);
        setIconImage(img.getImage());

        setTitle(R.UIStrings.BF_FRAME_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create header panel (campus selector plus cart summary)
        final JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Create item list panel
        myItemList = new ItemListPanel(
                myCampusInventories.get(myCurrentCampus),
                this);
        add(myItemList, BorderLayout.CENTER);

        // Create footer panel (cart controls)
        final CartControlPanel cartControls = new CartControlPanel(this);
        add(cartControls, BorderLayout.SOUTH);

        // Adjust size to just fit
        pack();

        // Make the GUI so that it cannot be resized by the user dragging a corner
        setResizable(false);

        // Position the frame in the center of the screen
        setLocation(SCREEN_SIZE.width / 2 - getWidth() / 2,
                SCREEN_SIZE.height / 2 - getHeight() / 2);
        setVisible(true);
    }

    /**
     * Creates the header panel containing campus selector and cart summary.
     *
     * @return the header panel
     */
    private JPanel createHeaderPanel() {
        final JPanel panel = new JPanel(new BorderLayout());

        // Create campus selector panel
        final CampusSelectorPanel campusSelector = new CampusSelectorPanel(
                myCampusInventories.keySet(),
                myCurrentCampus,
                this);
        panel.add(campusSelector, BorderLayout.NORTH);

        // Create cart summary panel
        myCartSummary = new CartSummaryPanel();
        panel.add(myCartSummary, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Handles campus selection events from the CampusSelectorPanel.
     * <p>
     * When a different campus is selected, this method:
     * - Updates the current campus
     * - Rebuilds the item list with the new inventory
     * - Clears the shopping cart
     * - Updates the display
     * - Refreshes the UI layout
     *
     * @param campusName the name of the newly selected campus
     */
    @Override
    public void onCampusSelected(final String campusName) {
        myCurrentCampus = campusName;

        // Rebuild item list panel with new campus inventory
        remove(myItemList);
        myItemList = new ItemListPanel(
                myCampusInventories.get(myCurrentCampus),
                this);
        add(myItemList, BorderLayout.CENTER);

        // Clear shopping cart and update display
        myCart.clear();
        updateDisplay();

        // Refresh UI layout
        pack();
        revalidate();
    }

    /**
     * Handles clear button events from the CartControlPanel.
     * <p>
     * This is a controller-initiated action (downstream), so we:
     * <ol>
     *   <li>Clear the cart directly (we know what we're doing)</li>
     *   <li>Tell the UI to reset (silent, no events fired back)</li>
     *   <li>Update the display</li>
     * </ol>
     * <p>
     * Note: {@link ItemListPanel#clearAllQuantities()} does NOT fire events
     * because this is a controller-initiated change. Events are only needed
     * for user-initiated changes that the controller doesn't already know about.
     *
     * @see ItemListPanel#clearAllQuantities()
     */
    @Override
    public void onClearRequested() {
        myCart.clear();
        myItemList.clearAllQuantities();
        updateDisplay();
    }

    /**
     * Handles membership checkbox events from the CartControlPanel.
     * <p>
     * Updates the cart's membership status and recalculates the total.
     *
     * @param hasMembership true if membership is enabled, false otherwise
     */
    @Override
    public void onMembershipChanged(final boolean hasMembership) {
        myCart.setMembership(hasMembership);
        updateDisplay();
    }

    /**
     * Handles item events from ItemRowPanel instances.
     * <p>
     * Uses pattern matching to process different event types. The sealed
     * interface ensures exhaustive handling - the compiler verifies that
     * all possible event types are handled.
     * <p>
     * Currently handles:
     * - QuantityChanged: Updates the cart and refreshes the display
     *
     * @param event the item event that occurred
     */
    @Override
    public void onItemEvent(final ItemEvent event) {
        // Pattern matching on sealed type - compiler ensures exhaustiveness
        switch (event) {
            case ItemEvent.QuantityChanged(final Item item, final int quantity) -> {
                myCart.add(new ItemOrder(item, quantity));
                updateDisplay();
            }
            // No default needed - sealed interface ensures all cases are covered
            // If new event types are added, compiler will force us to handle them
        }
    }

    /**
     * Updates the cart summary display with current cart information.
     * <p>
     * This method is called whenever the cart contents change to refresh
     * the displayed total and item counts.
     */
    private void updateDisplay() {
        myCartSummary.updateSummary(myCart.calculateTotal(), myCart.getCartSize());
    }
}
