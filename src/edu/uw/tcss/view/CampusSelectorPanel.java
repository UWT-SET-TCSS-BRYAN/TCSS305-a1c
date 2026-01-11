/*
 * TCSS 305 Assignment 1 - UW Bookstore
 */

package edu.uw.tcss.view;

import edu.uw.tcss.res.R;
import edu.uw.tcss.view.event.CampusSelectionListener;
import java.io.Serial;
import java.util.Objects;
import java.util.Set;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * A panel displaying radio buttons for campus selection.
 * <p>
 * This component allows users to select which campus bookstore they want to
 * shop from. When a different campus is selected, it fires a campus selection
 * event to notify interested parties (typically the main frame) so they can
 * load the appropriate inventory.
 *
 * <h2>Event Flow Pattern</h2>
 * This component is <b>upstream-only</b> (pure event source):
 * <ul>
 *   <li>User selects a campus radio button</li>
 *   <li>Component fires {@link CampusSelectionListener#onCampusSelected}</li>
 *   <li>Controller receives event and handles the campus switch</li>
 * </ul>
 * <p>
 * There are no downstream (controller → UI) operations because campus
 * selection is never programmatically changed after initialization.
 * The controller does not need to query or modify this component's state.
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Display radio buttons for each available campus</li>
 *   <li>Fire selection change events on user interaction</li>
 *   <li>Ensure only one campus is selected at a time (via ButtonGroup)</li>
 * </ul>
 *
 * @author Charles Bryan
 * @version Winter 2025
 * @see CampusSelectionListener
 */
public final class CampusSelectorPanel extends JPanel {

    /** The Serialization ID. */
    @Serial
    private static final long serialVersionUID = 8198377375189354505L;

    /**
     * The listener to notify when campus selection changes.
     */
    private final CampusSelectionListener myListener;

    /**
     * Constructs a CampusSelectorPanel with the specified campuses.
     *
     * @param campusNames the set of campus names to display
     * @param initialCampus the campus to select initially
     * @param listener the listener to notify on campus changes
     * @throws NullPointerException if any parameter is null
     * @throws IllegalArgumentException if campusNames is empty or
     *         initialCampus is not in campusNames
     */
    public CampusSelectorPanel(final Set<String> campusNames,
                               final String initialCampus,
                               final CampusSelectionListener listener) {
        super();

        Objects.requireNonNull(campusNames, "Campus names cannot be null");
        Objects.requireNonNull(initialCampus, "Initial campus cannot be null");
        myListener = Objects.requireNonNull(listener, "Listener cannot be null");

        if (campusNames.isEmpty()) {
            throw new IllegalArgumentException("Campus names cannot be empty");
        }
        if (!campusNames.contains(initialCampus)) {
            throw new IllegalArgumentException(
                    "Initial campus must be in campus names set");
        }

        setupComponents(campusNames, initialCampus);
    }

    /**
     * Sets up the visual components of this panel.
     *
     * @param campusNames the set of campus names to display
     * @param initialCampus the campus to select initially
     */
    private void setupComponents(final Set<String> campusNames,
                                 final String initialCampus) {
        setBackground(R.Colors.HEADER_FOOTER_BG);

        final ButtonGroup buttonGroup = new ButtonGroup();

        for (final String campus : campusNames) {
            final JRadioButton radioButton = new JRadioButton(campus);
            radioButton.setForeground(R.Colors.HEADER_FOOTER_TEXT);
            radioButton.setBackground(R.Colors.HEADER_FOOTER_BG);
            radioButton.setSelected(campus.equals(initialCampus));

            radioButton.addActionListener(event ->
                    myListener.onCampusSelected(radioButton.getText()));

            buttonGroup.add(radioButton);
            add(radioButton);
        }
    }

}
