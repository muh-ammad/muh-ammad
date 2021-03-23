import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DistinctionComponentsPage, DistinctionDeleteDialog, DistinctionUpdatePage } from './distinction.page-object';

const expect = chai.expect;

describe('Distinction e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let distinctionComponentsPage: DistinctionComponentsPage;
  let distinctionUpdatePage: DistinctionUpdatePage;
  let distinctionDeleteDialog: DistinctionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Distinctions', async () => {
    await navBarPage.goToEntity('distinction');
    distinctionComponentsPage = new DistinctionComponentsPage();
    await browser.wait(ec.visibilityOf(distinctionComponentsPage.title), 5000);
    expect(await distinctionComponentsPage.getTitle()).to.eq('gpecmanagerApp.distinction.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(distinctionComponentsPage.entities), ec.visibilityOf(distinctionComponentsPage.noResult)),
      1000
    );
  });

  it('should load create Distinction page', async () => {
    await distinctionComponentsPage.clickOnCreateButton();
    distinctionUpdatePage = new DistinctionUpdatePage();
    expect(await distinctionUpdatePage.getPageTitle()).to.eq('gpecmanagerApp.distinction.home.createOrEditLabel');
    await distinctionUpdatePage.cancel();
  });

  it('should create and save Distinctions', async () => {
    const nbButtonsBeforeCreate = await distinctionComponentsPage.countDeleteButtons();

    await distinctionComponentsPage.clickOnCreateButton();

    await promise.all([
      distinctionUpdatePage.setLibelleDistinctionInput('libelleDistinction'),
      distinctionUpdatePage.employeSelectLastOption(),
    ]);

    expect(await distinctionUpdatePage.getLibelleDistinctionInput()).to.eq(
      'libelleDistinction',
      'Expected LibelleDistinction value to be equals to libelleDistinction'
    );

    await distinctionUpdatePage.save();
    expect(await distinctionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await distinctionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Distinction', async () => {
    const nbButtonsBeforeDelete = await distinctionComponentsPage.countDeleteButtons();
    await distinctionComponentsPage.clickOnLastDeleteButton();

    distinctionDeleteDialog = new DistinctionDeleteDialog();
    expect(await distinctionDeleteDialog.getDialogTitle()).to.eq('gpecmanagerApp.distinction.delete.question');
    await distinctionDeleteDialog.clickOnConfirmButton();

    expect(await distinctionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
