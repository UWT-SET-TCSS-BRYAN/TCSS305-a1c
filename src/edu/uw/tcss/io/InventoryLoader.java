/*
 * TCSS 305 Assignment 1 - UW Bookstore
 */

package edu.uw.tcss.io;

import static edu.uw.tcss.app.BookstoreMain.LOGGER;
import static edu.uw.tcss.res.R.ItemsFile.ITEM_BULK_PRICE;
import static edu.uw.tcss.res.R.ItemsFile.ITEM_BULK_QUANTITY;
import static edu.uw.tcss.res.R.ItemsFile.ITEM_NAME;
import static edu.uw.tcss.res.R.ItemsFile.ITEM_PRICE;

import edu.uw.tcss.model.Item;
import edu.uw.tcss.model.StoreBulkItem;
import edu.uw.tcss.model.StoreItem;
import edu.uw.tcss.res.R;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * A utility class for loading inventory and configuration data from files.
 * <p>
 * This class demonstrates the Factory pattern - the {@link #createItem(String[])}
 * method encapsulates the logic for creating the appropriate Item subtype
 * based on the input data, hiding the concrete class selection from callers.
 *
 * @author Charles Bryan
 * @version Winter 2025
 */
public final class InventoryLoader {

    /**
     * The number of fields in a bulk item record (name, price, bulk quantity, bulk price).
     */
    private static final int BULK_ITEM_FIELD_COUNT = 4;

    /**
     * A private constructor, to prevent external instantiation.
     */
    private InventoryLoader() {
        super();
    }

    /**
     * Reads item information from a file and returns a List of Item objects.
     * <p>
     * This method uses the Factory pattern via {@link #createItem(String[])} to
     * create the appropriate Item subtype (StoreItem or StoreBulkItem) based on
     * the number of fields in each line.
     *
     * @param file the name of the file to load into a List of Items
     * @return a List of Item objects created from data in an input file;
     *         returns an empty list if the file cannot be read or is empty
     * @throws NullPointerException if file is null
     * @throws IllegalArgumentException if file is empty
     */
    public static List<Item> readItemsFromFile(final String file) {
        validateFilePath(file);
        final List<Item> items = new ArrayList<>();

        try (final Scanner input = new Scanner(Paths.get(file))) {
            while (input.hasNextLine()) {
                final String line = input.nextLine();
                try {
                    final String[] parts = line.split(R.SystemStrings.IO_FILE_DELIMITER);
                    items.add(createItem(parts));
                } catch (final NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    LOGGER.warning("Skipping malformed line: " + line + " (" + e + ")");
                }
            }
        } catch (final IOException e) {
            LOGGER.severe(e.toString());
        }
        return items;
    }

    /**
     * Creates an Item from the parsed parts of a file line.
     * <p>
     * This is a Factory method that encapsulates the decision of which concrete
     * Item subtype to create. If the parts array contains bulk pricing information
     * (4 fields), a StoreBulkItem is created; otherwise, a StoreItem is created.
     *
     * @param parts the semicolon-delimited parts of a file line
     * @return the appropriate Item subtype based on the input data
     * @throws NumberFormatException if price or quantity fields are not valid numbers
     * @throws ArrayIndexOutOfBoundsException if parts has fewer than 2 elements
     */
    private static Item createItem(final String[] parts) {
        final String itemName = parts[ITEM_NAME.ordinal()];
        final BigDecimal itemPrice = new BigDecimal(parts[ITEM_PRICE.ordinal()]);

        final Item result;
        if (parts.length >= BULK_ITEM_FIELD_COUNT) {
            final int bulkQuantity = Integer.parseInt(parts[ITEM_BULK_QUANTITY.ordinal()]);
            final BigDecimal bulkPrice = new BigDecimal(parts[ITEM_BULK_PRICE.ordinal()]);
            result = new StoreBulkItem(itemName, itemPrice, bulkQuantity, bulkPrice);
        } else {
            result = new StoreItem(itemName, itemPrice);
        }
        return result;
    }

    /**
     * Reads configuration information from a file and returns a List of campus names.
     * <p>
     * Lines starting with the comment character (defined in R.SystemStrings) are ignored.
     *
     * @param file the name of the configuration file to load
     * @return a List of campus name strings from the configuration file;
     *         returns an empty list if the file cannot be read or is empty
     * @throws NullPointerException if file is null
     * @throws IllegalArgumentException if file is empty
     */
    public static List<String> readConfigurationFromFile(final String file) {
        validateFilePath(file);
        final List<String> results = new ArrayList<>();

        try (final Scanner input = new Scanner(Paths.get(file))) {
            while (input.hasNextLine()) {
                final String line = input.nextLine();
                if (!line.startsWith(R.SystemStrings.IO_FILE_COMMENT)) {
                    results.add(line);
                }
            }
        } catch (final IOException e) {
            LOGGER.severe(e.toString());
        }
        return results;
    }

    /**
     * Validates that the file path is not null or empty.
     *
     * @param file the file path to validate
     * @throws NullPointerException if file is null
     * @throws IllegalArgumentException if file is empty
     */
    private static void validateFilePath(final String file) {
        if (Objects.requireNonNull(file, "File path cannot be null").isEmpty()) {
            throw new IllegalArgumentException("File path cannot be empty");
        }
    }
}
