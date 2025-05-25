describe("Merchant Details Page", () => {
  beforeEach(() => {
    // Visit McDonald's page directly
    cy.visit("/");

    // Wait for the loading state to disappear
    cy.contains("Loading restaurants...").should("not.exist");

    // Find and click the McDonald's entry
    cy.get('[data-testid="merchant-list"]')
      .contains("h2", "McDonald's")
      .click();

    // Verify we're on the merchant details page
    cy.url().should("include", "/merchant/");

    // Check for loading state
    cy.contains("Loading restaurant details...").should("be.visible");

    // Wait for the details to load
    cy.contains("Loading restaurant details...").should("not.exist");
  });

  it("should add a Big Mac, Medium French Fries, and Medium Coca-Cola to the cart", () => {
    // Add Big Mac
    cy.contains("h4", "Big Mac").click();
    cy.assertItemDetailsOpen();
    cy.itemDetails().should("contain", "Big Mac");
    cy.itemDetails().find("button").contains("Add to Cart").click();
    cy.assertItemDetailsClosed();

    cy.closeCart();

    // Add Medium French Fries
    cy.contains("h4", "Medium French Fries").click();
    cy.assertItemDetailsOpen();
    cy.itemDetails().should("contain", "Medium French Fries");
    cy.itemDetails().find("button").contains("Add to Cart").click();
    cy.assertItemDetailsClosed();

    // Close cart before adding next item
    cy.closeCart();

    // Add Medium Coca-Cola
    cy.contains("h4", "Medium Coca-Cola").click();
    cy.assertItemDetailsOpen();
    cy.itemDetails().should("contain", "Medium Coca-Cola");
    cy.itemDetails().find("button").contains("Add to Cart").click();
    cy.assertItemDetailsClosed();

    // Verify cart contents
    cy.shoppingCart().within(() => {
      cy.contains("Your Cart").should("be.visible");
      cy.contains("Big Mac").should("be.visible");
      cy.contains("Medium French Fries").should("be.visible");
      cy.contains("Medium Coca-Cola").should("be.visible");
      cy.contains("Qty: 1").should("be.visible");
    });
  });

  it("should edit an item and add an observation", () => {
    // First add a Big Mac to the cart
    cy.contains("h4", "Big Mac").click();
    cy.assertItemDetailsOpen();
    cy.itemDetails().find("button").contains("Add to Cart").click();
    cy.assertItemDetailsClosed();

    // Open cart and click edit on the Big Mac
    cy.assertCartOpen;
    cy.contains("Big Mac").parent().parent().contains("Edit").click();

    // Verify dialog is open and contains Big Mac with correct ARIA attributes
    cy.assertItemDetailsOpen();
    cy.itemDetails().should("contain", "Big Mac");

    // Add an observation
    const observation = "No onions please, extra pickles";
    cy.itemDetails().find("textarea").type(observation);

    // Update the item
    cy.itemDetails().find("button").contains("Update Item").click();
    cy.assertItemDetailsClosed();

    // Verify the observation is visible in the cart
    cy.assertCartOpen();
    cy.shoppingCart().contains(observation).should("be.visible");
  });

  it("should change item quantity in the cart", () => {
    // First add a Big Mac to the cart
    cy.contains("h4", "Big Mac").click();
    cy.assertItemDetailsOpen();
    cy.itemDetails().find("button").contains("Add to Cart").click();
    cy.assertItemDetailsClosed();

    // Open cart and click edit on the Big Mac
    cy.assertCartOpen();
    cy.shoppingCart()
      .contains("Big Mac").parent().parent().contains("Edit").click();;
    cy.assertCartClosed();

    // Verify dialog is open and contains Big Mac with correct ARIA attributes
    cy.assertItemDetailsOpen();
    cy.itemDetails().should("contain", "Big Mac");

    // Increase quantity to 3 using the buttons
    cy.get('[aria-label="Increase quantity"]').click();
    cy.get('[aria-label="Increase quantity"]').click();

    // Update the item
    cy.itemDetails().contains("Update Item").click();
    cy.assertItemDetailsClosed();

    cy.shoppingCart().contains("Qty: 3").should("be.visible")
  });

  it("should remove items from the cart", () => {
    // Add Big Mac
    cy.contains("h4", "Big Mac").click();
    cy.assertItemDetailsOpen();
    cy.itemDetails().find("button").contains("Add to Cart").click();
    cy.assertItemDetailsClosed();

    cy.closeCart();

    // Add Medium French Fries
    cy.contains("h4", "Medium French Fries").click();
    cy.itemDetails().contains("Add to Cart").click();
    cy.assertItemDetailsClosed();

    cy.closeCart();

    // Add Medium Coca-Cola
    cy.contains("h4", "Medium Coca-Cola").click();
    cy.itemDetails().contains("Add to Cart").click();
    cy.assertItemDetailsClosed();

    // Verify all items are in cart
    cy.shoppingCart().within(() => {
      cy.contains("Big Mac").should("be.visible");
      cy.contains("Medium French Fries").should("be.visible");
      cy.contains("Medium Coca-Cola").should("be.visible");

      // Remove French Fries
      cy.contains("Medium French Fries")
        .parent()
        .parent()
        .contains("Remove")
        .click();

      // Verify French Fries is removed
      cy.contains("Medium French Fries").should("not.exist");

      // Remove Coca-Cola
      cy.contains("Medium Coca-Cola")
        .parent()
        .parent()
        .contains("Remove")
        .click();

      // Verify Coca-Cola is removed
      cy.contains("Medium Coca-Cola").should("not.exist");

      // Verify only Big Mac remains
      cy.contains("Big Mac").should("be.visible");
      cy.contains("Qty: 1").should("be.visible");
    });
  });

  it("should clear the cart and show empty cart message", () => {
    // Add Big Mac
    cy.contains("h4", "Big Mac").click();
    cy.assertItemDetailsOpen();
    cy.itemDetails().find("button").contains("Add to Cart").click();
    cy.assertItemDetailsClosed();

    cy.closeCart();

    // Add Medium French Fries
    cy.contains("h4", "Medium French Fries").click();
    cy.itemDetails().contains("Add to Cart").click();
    cy.assertItemDetailsClosed();

    // Verify items are in cart
    cy.shoppingCart().within(() => {
      cy.contains("Big Mac").should("be.visible");
      cy.contains("Medium French Fries").should("be.visible");

      // Click clear cart button
      cy.contains("Clear Cart").click();
    });

    cy.confirmClearCart()

    // Verify empty cart message
    cy.shoppingCart().contains("Your cart is empty").should("be.visible");
  });

  it("should prevent adding items from different merchants to the cart", () => {
    // Add Big Mac from McDonald's
    cy.contains("h4", "Big Mac").click();
    cy.assertItemDetailsOpen();
    cy.itemDetails().find("button").contains("Add to Cart").click();
    cy.closeCart();

    // Go back to merchant list
    cy.goBack();

    // Wait for the loading state to disappear
    cy.contains("Loading restaurants...").should("not.exist");

    // Find and click the Walmart entry
    cy.get('[data-testid="merchant-list"]')
      .contains("h2", "Walmart")
      .click();

    // Wait for the details to load
    cy.contains("Loading restaurant details...").should("not.exist");

    // Try to add bananas from Walmart
    cy.contains("h4", "Bananas").click();
    cy.assertItemDetailsOpen();
    cy.itemDetails().find("button").contains("Add to Cart").click();

    // Verify error message is shown
    cy.itemDetails().contains("You can only add items from the same merchant to your cart.").should("be.visible");
  });
});
