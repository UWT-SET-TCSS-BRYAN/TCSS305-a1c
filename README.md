# TCSS 305 – Assignment 1C: Bookstore Implementation

**UW Tacoma | Winter 2026**

## Assignment Instructions

Full instructions available at:
https://cfb3.github.io/TCSS305-GUIDES/assignments/a1c/

## Project Structure

```
src/edu/uw/tcss/
├── app/
│   └── BookstoreMain.java      (application entry point - provided)
├── io/
│   └── InventoryLoader.java    (file loading - provided)
├── model/
│   ├── AbstractItem.java       (complete this stub)
│   ├── Cart.java               (interface - provided)
│   ├── Item.java               (interface - provided)
│   ├── ItemOrder.java          (record - provided)
│   ├── StoreItem.java          (create this)
│   ├── StoreBulkItem.java      (create this)
│   └── StoreCart.java          (create this)
├── res/
│   └── R.java                  (constants - provided)
└── view/                       (GUI components - provided)

test/edu/uw/tcss/model/
├── ItemOrderTest.java          (provided example)
└── [your tests from 1b]        (copy here)

files/                          (inventory data - provided)

project root/
├── executive-summary.md        (your submission notes)
└── README.md                   (this file)
```

## Getting Started

1. Copy your test files from Assignment 1b into `test/edu/uw/tcss/model/`
2. Create the three missing model classes: `StoreItem`, `StoreBulkItem`, `StoreCart`
3. Complete the `AbstractItem` stub
4. Run your tests to verify your implementation
5. Run the application to see the GUI

## Running the Application

1. Implement all required classes so the project compiles
2. Run `BookstoreMain.java`
3. The GUI should display with items from the Tacoma campus

## Running Tests

1. Right-click on the `test` folder in IntelliJ
2. Select **Run 'All Tests'**
3. For coverage: Right-click → **Run with Coverage**

## What to Submit

- Completed `AbstractItem.java`
- Your implementations: `StoreItem.java`, `StoreBulkItem.java`, `StoreCart.java`
- Your test files from 1b (updated if needed)
- Updated `executive-summary.md`