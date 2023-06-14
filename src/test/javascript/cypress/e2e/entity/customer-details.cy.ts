import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('CustomerDetails e2e test', () => {
  const customerDetailsPageUrl = '/customer-details';
  const customerDetailsPageUrlPattern = new RegExp('/customer-details(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const customerDetailsSample = {
    gender: 'FEMALE',
    phone: '791-735-3320 x5020',
    addressLine1: 'Global',
    city: 'Port Vernbury',
    country: 'Namibia',
  };

  let customerDetails;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/customer-details+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/customer-details').as('postEntityRequest');
    cy.intercept('DELETE', '/api/customer-details/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (customerDetails) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/customer-details/${customerDetails.id}`,
      }).then(() => {
        customerDetails = undefined;
      });
    }
  });

  it('CustomerDetails menu should load CustomerDetails page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('customer-details');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CustomerDetails').should('exist');
    cy.url().should('match', customerDetailsPageUrlPattern);
  });

  describe('CustomerDetails page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(customerDetailsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CustomerDetails page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/customer-details/new$'));
        cy.getEntityCreateUpdateHeading('CustomerDetails');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', customerDetailsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/customer-details',
          body: customerDetailsSample,
        }).then(({ body }) => {
          customerDetails = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/customer-details+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/customer-details?page=0&size=20>; rel="last",<http://localhost/api/customer-details?page=0&size=20>; rel="first"',
              },
              body: [customerDetails],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(customerDetailsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CustomerDetails page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('customerDetails');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', customerDetailsPageUrlPattern);
      });

      it('edit button click should load edit CustomerDetails page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CustomerDetails');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', customerDetailsPageUrlPattern);
      });

      it('edit button click should load edit CustomerDetails page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CustomerDetails');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', customerDetailsPageUrlPattern);
      });

      it('last delete button click should delete instance of CustomerDetails', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('customerDetails').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', customerDetailsPageUrlPattern);

        customerDetails = undefined;
      });
    });
  });

  describe('new CustomerDetails page', () => {
    beforeEach(() => {
      cy.visit(`${customerDetailsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CustomerDetails');
    });

    it('should create an instance of CustomerDetails', () => {
      cy.get(`[data-cy="gender"]`).select('MALE');

      cy.get(`[data-cy="phone"]`).type('521.403.0989 x0384').should('have.value', '521.403.0989 x0384');

      cy.get(`[data-cy="addressLine1"]`).type('Small circuit model').should('have.value', 'Small circuit model');

      cy.get(`[data-cy="addressLine2"]`).type('Towels Analyst').should('have.value', 'Towels Analyst');

      cy.get(`[data-cy="city"]`).type('Leuschkebury').should('have.value', 'Leuschkebury');

      cy.get(`[data-cy="country"]`).type('Estonia').should('have.value', 'Estonia');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        customerDetails = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', customerDetailsPageUrlPattern);
    });
  });
});
