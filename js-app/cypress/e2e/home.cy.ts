describe('Home Page', () => {
  beforeEach(() => {
    // Visit the home page before each test
    cy.visit('/')
  })

  it('should display loading state initially', () => {
    cy.contains('Loading restaurants...').should('be.visible')
  })

  it('should display the list of merchants after loading', () => {
    // Wait for the loading state to disappear
    cy.contains('Loading restaurants...').should('not.exist')
    
    // Check if the merchants list is rendered
    cy.get('[data-testid="merchant-list"]').should('exist')
  })

  it('should handle error state appropriately', () => {
    // Intercept the API call and force an error
    cy.intercept('GET', '**/merchants', {
      statusCode: 500,
      body: { error: 'Internal Server Error' }
    }).as('getMerchants')

    // Reload the page to trigger the error
    cy.reload()

    // Check list is empty
    cy.contains('No restaurants found').should('be.visible')
  })

  it('should navigate to merchant details and display content', () => {
    // Wait for the loading state to disappear
    cy.contains('Loading restaurants...').should('not.exist')
    
    // Find and click the McDonald's entry
    cy.get('[data-testid="merchant-list"]')
      .contains('h2', 'McDonald\'s')
      .click()

    // Verify we're on the merchant details page
    cy.url().should('include', '/merchant/')

    // Check for loading state
    cy.contains('Loading restaurant details...').should('be.visible')

    // Wait for the details to load
    cy.contains('Loading restaurant details...').should('not.exist')

    // Verify merchant details are displayed
    cy.get('h1').should('contain', 'McDonald\'s') // Verify it's McDonald's
    cy.contains('Menu').should('be.visible')
    cy.contains('Address').should('be.visible')
    cy.contains('Contact').should('be.visible')

    // Verify back navigation works
    cy.goBack()
    cy.url().should('eq', Cypress.config().baseUrl + '/')
  })
}) 