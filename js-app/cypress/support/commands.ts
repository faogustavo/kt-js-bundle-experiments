/// <reference types="cypress" />

declare global {
  namespace Cypress {
    interface Chainable {
      goBack(): Chainable<void>

      itemDetails(): Chainable<JQuery<HTMLElement>>
      assertItemDetailsOpen(): Chainable<void>
      assertItemDetailsClosed(): Chainable<void>

      shoppingCart(): Chainable<JQuery<HTMLElement>>
      assertCartOpen(): Chainable<void>
      assertCartClosed(): Chainable<void>
      closeCart(): Chainable<void>

      clearCartConfirmation(): Chainable<JQuery<HTMLElement>>
      confirmClearCart(): Chainable<void>
    }
  }
}

// Navigation
Cypress.Commands.add('goBack', () => {
  cy.get('[data-testid="nav-back-button"]').click()
})

// Item Details
Cypress.Commands.add('itemDetails', () => {
  return cy.get('[role="dialog"][aria-label="Item Details"]')
})

Cypress.Commands.add('assertItemDetailsOpen', () => {
  cy.itemDetails().should('be.visible')
})

Cypress.Commands.add('assertItemDetailsClosed', () => {
  cy.itemDetails().should('not.exist')
})

// Shopping Cart
Cypress.Commands.add('shoppingCart', () => {
  return cy.get('[role="dialog"][aria-label="Shopping Cart"]')
})

Cypress.Commands.add('assertCartOpen', () => {
  cy.shoppingCart().should('be.visible')
})

Cypress.Commands.add('assertCartClosed', () => {
  cy.shoppingCart().should('not.be.visible')
})

Cypress.Commands.add('closeCart', () => {
  cy.shoppingCart()
    .should('be.visible')
    .within(() => {
      cy.get('button[aria-label="Close cart"]').click()
    })

  cy.assertCartClosed()
})

// Clear Cart
Cypress.Commands.add('clearCartConfirmation', () => {
  return cy.get('[role="dialog"][aria-label="Clear Cart Confirmation"]')
})

Cypress.Commands.add('confirmClearCart', () => {
  cy.clearCartConfirmation()
    .find('button')
    .contains('Clear')
    .click()
})

export {} 