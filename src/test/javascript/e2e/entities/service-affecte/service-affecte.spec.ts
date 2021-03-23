import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ServiceAffecteComponentsPage, ServiceAffecteDeleteDialog, ServiceAffecteUpdatePage } from './service-affecte.page-object';

const expect = chai.expect;

describe('ServiceAffecte e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let serviceAffecteComponentsPage: ServiceAffecteComponentsPage;
  let serviceAffecteUpdatePage: ServiceAffecteUpdatePage;
  let serviceAffecteDeleteDialog: ServiceAffecteDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ServiceAffectes', async () => {
    await navBarPage.goToEntity('service-affecte');
    serviceAffecteComponentsPage = new ServiceAffecteComponentsPage();
    await browser.wait(ec.visibilityOf(serviceAffecteComponentsPage.title), 5000);
    expect(await serviceAffecteComponentsPage.getTitle()).to.eq('gpecmanagerApp.serviceAffecte.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(serviceAffecteComponentsPage.entities), ec.visibilityOf(serviceAffecteComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ServiceAffecte page', async () => {
    await serviceAffecteComponentsPage.clickOnCreateButton();
    serviceAffecteUpdatePage = new ServiceAffecteUpdatePage();
    expect(await serviceAffecteUpdatePage.getPageTitle()).to.eq('gpecmanagerApp.serviceAffecte.home.createOrEditLabel');
    await serviceAffecteUpdatePage.cancel();
  });

  it('should create and save ServiceAffectes', async () => {
    const nbButtonsBeforeCreate = await serviceAffecteComponentsPage.countDeleteButtons();

    await serviceAffecteComponentsPage.clickOnCreateButton();

    await promise.all([
      serviceAffecteUpdatePage.setCodeServiceInput('5'),
      serviceAffecteUpdatePage.setLibelleServiceInput('libelleService'),
      serviceAffecteUpdatePage.secteurActiviteSelectLastOption(),
    ]);

    expect(await serviceAffecteUpdatePage.getCodeServiceInput()).to.eq('5', 'Expected codeService value to be equals to 5');
    expect(await serviceAffecteUpdatePage.getLibelleServiceInput()).to.eq(
      'libelleService',
      'Expected LibelleService value to be equals to libelleService'
    );

    await serviceAffecteUpdatePage.save();
    expect(await serviceAffecteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await serviceAffecteComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ServiceAffecte', async () => {
    const nbButtonsBeforeDelete = await serviceAffecteComponentsPage.countDeleteButtons();
    await serviceAffecteComponentsPage.clickOnLastDeleteButton();

    serviceAffecteDeleteDialog = new ServiceAffecteDeleteDialog();
    expect(await serviceAffecteDeleteDialog.getDialogTitle()).to.eq('gpecmanagerApp.serviceAffecte.delete.question');
    await serviceAffecteDeleteDialog.clickOnConfirmButton();

    expect(await serviceAffecteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
