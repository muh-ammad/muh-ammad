import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ServiceFrequenteComponentsPage, ServiceFrequenteDeleteDialog, ServiceFrequenteUpdatePage } from './service-frequente.page-object';

const expect = chai.expect;

describe('ServiceFrequente e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let serviceFrequenteComponentsPage: ServiceFrequenteComponentsPage;
  let serviceFrequenteUpdatePage: ServiceFrequenteUpdatePage;
  let serviceFrequenteDeleteDialog: ServiceFrequenteDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ServiceFrequentes', async () => {
    await navBarPage.goToEntity('service-frequente');
    serviceFrequenteComponentsPage = new ServiceFrequenteComponentsPage();
    await browser.wait(ec.visibilityOf(serviceFrequenteComponentsPage.title), 5000);
    expect(await serviceFrequenteComponentsPage.getTitle()).to.eq('gpecmanagerApp.serviceFrequente.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(serviceFrequenteComponentsPage.entities), ec.visibilityOf(serviceFrequenteComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ServiceFrequente page', async () => {
    await serviceFrequenteComponentsPage.clickOnCreateButton();
    serviceFrequenteUpdatePage = new ServiceFrequenteUpdatePage();
    expect(await serviceFrequenteUpdatePage.getPageTitle()).to.eq('gpecmanagerApp.serviceFrequente.home.createOrEditLabel');
    await serviceFrequenteUpdatePage.cancel();
  });

  it('should create and save ServiceFrequentes', async () => {
    const nbButtonsBeforeCreate = await serviceFrequenteComponentsPage.countDeleteButtons();

    await serviceFrequenteComponentsPage.clickOnCreateButton();

    await promise.all([
      serviceFrequenteUpdatePage.setCodeServiceInput('5'),
      serviceFrequenteUpdatePage.setLibelleServiceInput('libelleService'),
    ]);

    expect(await serviceFrequenteUpdatePage.getCodeServiceInput()).to.eq('5', 'Expected codeService value to be equals to 5');
    expect(await serviceFrequenteUpdatePage.getLibelleServiceInput()).to.eq(
      'libelleService',
      'Expected LibelleService value to be equals to libelleService'
    );

    await serviceFrequenteUpdatePage.save();
    expect(await serviceFrequenteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await serviceFrequenteComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ServiceFrequente', async () => {
    const nbButtonsBeforeDelete = await serviceFrequenteComponentsPage.countDeleteButtons();
    await serviceFrequenteComponentsPage.clickOnLastDeleteButton();

    serviceFrequenteDeleteDialog = new ServiceFrequenteDeleteDialog();
    expect(await serviceFrequenteDeleteDialog.getDialogTitle()).to.eq('gpecmanagerApp.serviceFrequente.delete.question');
    await serviceFrequenteDeleteDialog.clickOnConfirmButton();

    expect(await serviceFrequenteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
