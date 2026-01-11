/*
 * TCSS 305 Assignment 1 - UW Bookstore
 */

package edu.uw.tcss.res;

import java.awt.Color;

/**
 * Resources class containing all constants for the UW Bookstore application.
 * <p>
 * General naming convention: constant names start with the abbreviation for the
 * class they are associated with (e.g., BF = BookstoreFrame).
 *
 * @author Charles Bryan
 * @version Winter 2025
 */
@SuppressWarnings("NewClassNamingConvention")
public final class R {

    private R() {
        super();
    }

    /**
     * Resource enum for array index values in the Items File.
     *
     * @author Charles Bryan
     * @version Winter 2025
     */
    public enum ItemsFile {

        /** The index of the Item name in the Items files. */
        ITEM_NAME,

        /** The index of the Item price in the Items files. */
        ITEM_PRICE,

        /** The index of the Item bulk quantity in the Items files. */
        ITEM_BULK_QUANTITY,

        /** The index of the Item bulk price in the Items files. */
        ITEM_BULK_PRICE

    }

    /**
     * Resource class for UW color schemes.
     * <p>
     * For the UW color palette and other UW branding information see
     * <a href="http://www.washington.edu/marketing/files/2012/09/WebColorPalette1.pdf">
     * UW Web Color Palette</a>.
     *
     * @author Charles Bryan
     * @version Winter 2025
     */
    public static final class Colors {

        /** The background color for content containers (UW Gold). */
        public static final Color CONTENT_BG = new Color(199, 153, 0);

        /** The background color for headers and footers (UW Purple). */
        public static final Color HEADER_FOOTER_BG = new Color(57, 39, 91);

        /** The text color for content containers. */
        public static final Color CONTENT_TEXT = HEADER_FOOTER_BG;

        /** The text color for headers and footers. */
        public static final Color HEADER_FOOTER_TEXT = Color.WHITE;

        private Colors() {
            super();
        }
    }

    /**
     * Resource class for UI dimensions.
     *
     * @author Charles Bryan
     * @version Winter 2025
     */
    public static final class Dimensions {

        /** The number of columns for Item Panels in the Bookstore frame. */
        public static final int BF_ITEMS_COLS = 1;

        /** General horizontal padding. */
        public static final int H_PADDING = 10;

        /** General vertical padding. */
        public static final int V_PADDING = 5;

        /** The width of the total text field in the BookStore GUI. */
        public static final int BF_TEXTFIELD_TOTAL = 12;

        /** The width of the item count text field in the BookStore GUI. */
        public static final int BF_TEXTFIELD_ITEM_COUNT = 8;

        /** The width of the quantity text field in the BookStore GUI. */
        public static final int BF_TEXTFIELD_QUANTITY = 3;

        private Dimensions() {
            super();
        }
    }

    /**
     * System-level string constants for internal use.
     * <p>
     * These strings are used for configuration, file I/O, and internal operations.
     * They should not be displayed directly to end users and are not candidates
     * for internationalization (i18n).
     *
     * @author Charles Bryan
     * @version Winter 2025
     */
    public static final class SystemStrings {

        /** The filename of the file containing the campus configuration. */
        public static final String IO_CONFIG_FILE = "config.txt";

        /** The filename of the application icon. */
        public static final String IO_ICON_FILE = "w.gif";

        /** The local path of the configuration files. */
        public static final String IO_FILE_LOCATION = "files/";

        /** The file extension for text files. */
        public static final String IO_FILE_EXTENSION = ".txt";

        /** The delimiter used in text files. */
        public static final String IO_FILE_DELIMITER = ";";

        /** The character used to mark a single line comment in text files. */
        public static final String IO_FILE_COMMENT = "#";

        private SystemStrings() {
            super();
        }
    }

    /**
     * User interface string constants displayed to end users.
     * <p>
     * These strings appear in the GUI and should be clear, friendly, and grammatically
     * correct. In a production application, these would typically be externalized to
     * ResourceBundle .properties files for internationalization (i18n) support.
     * For this educational project, they are kept as constants for simplicity.
     * <p>
     * Naming convention: CLASS_COMPONENT_DESCRIPTION (e.g., BF = BookstoreFrame).
     *
     * @author Charles Bryan
     * @version Winter 2025
     */
    public static final class UIStrings {

        /** Initial text in total text field. */
        public static final String BF_TEXTFIELD_TOTAL = "$0.00";

        /** Initial text in items in cart text field. */
        public static final String BF_TEXTFIELD_ITEMS_IN_CART = "0/0";

        /** Title for the BookstoreFrame. */
        public static final String BF_FRAME_TITLE = "UW Bookstore";

        /** Label text for order total. */
        public static final String BF_LABEL_TOTAL = "order total:";

        /** Label text for item count. */
        public static final String BF_LABEL_ITEM_IN_CART = "items/item orders:";

        /** Text on clear button. */
        public static final String BF_BUTTON_CLEAR = "Clear";

        /** Text on membership checkbox. */
        public static final String BF_CHECKBOX_MEMBER = "customer has store membership";

        private UIStrings() {
            super();
        }
    }
}
