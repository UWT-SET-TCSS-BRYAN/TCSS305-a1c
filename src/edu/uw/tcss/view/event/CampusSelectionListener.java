/*
 * TCSS 305 Assignment 1 - UW Bookstore
 */

package edu.uw.tcss.view.event;

/**
 * A functional interface for observing campus selection changes.
 * <p>
 * Components interested in campus changes (typically the main frame)
 * implement this interface to be notified when the user selects a
 * different campus.
 *
 * @author Charles Bryan
 * @version Winter 2025
 */
@FunctionalInterface
public interface CampusSelectionListener {

    /**
     * Called when a campus is selected.
     *
     * @param campusName the name of the newly selected campus
     */
    void onCampusSelected(String campusName);
}
