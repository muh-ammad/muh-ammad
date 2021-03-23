import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OpexComponentsPage, OpexDeleteDialog, OpexUpdatePage } from './opex.page-object';

const expect = chai.expect;

describe('Opex e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let opexComponentsPage: OpexComponentsPage;
  let opexUpdatePage: OpexUpdatePage;
  let opexDeleteDialog: OpexDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Opexes', async () => {
    await navBarPage.goToEntity('opex');
    opexComponentsPage = new OpexComponentsPage();
    await browser.wait(ec.visibilityOf(opexComponentsPage.title), 5000);
    expect(await opexComponentsPage.getTitle()).to.eq('gpecmanagerApp.opex.home.title');
    await browser.wait(ec.or(ec.visibilityOf(opexComponentsPage.entities), ec.visibilityOf(opexComponentsPage.noResult)), 1000);
  });

  it('should load create Opex page', async () => {
    await opexComponentsPage.clickOnCreateButton();
    opexUpdatePage = new OpexUpdatePage();
    expect(await opexUpdatePage.getPageTitle()).to.eq('gpecmanagerApp.opex.home.createOrEditLabel');
    await opexUpdatePage.cancel();
  });

  it('should create and save Opexes', async () => {
    const nbButtonsBeforeCreate = await opexComponentsPage.countDeleteButtons();

    await opexComponentsPage.clickOnCreateButton();

    await promise.all([
      opexUpdatePage.setLieuOpexInput('lieuOpex'),
      opexUpdatePage.setAnneeOpexInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      opexUpdatePage.employeSelectLastOption(),
    ]);

    expect(await opexUpdatePage.getLieuOpexInput()).to.eq('lieuOpex', 'Expected LieuOpex value to be equals to lieuOpex');
    expect(await opexUpdatePage.getAnneeOpexInput()).to.contain('2001-01-01T02:30', 'Expected anneeOpex value to be equals to 2000-12-31');

    await opexUpdatePage.save();
    expect(await opexUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await opexComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Opex', async () => {
    const nbButtonsBeforeDelete = await opexComponentsPage.countDeleteButtons();
    await opexComponentsPage.clickOnLastDeleteButton();

    opexDeleteDialog = new OpexDeleteDialog();
    expect(await opexDeleteDialog.getDialogTitle()).to.eq('gpecmanagerApp.opex.delete.question');
    await opexDeleteDialog.clickOnConfirmButton();

    expect(await opexComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
