import '@testing-library/cypress/add-commands'
import './commands'

// Custom commands can be added here
declare global {
  namespace Cypress {
    interface Chainable {
      // Add custom commands here if needed
    }
  }
} 