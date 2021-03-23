import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SpecialiteComponentsPage, SpecialiteDeleteDialog, SpecialiteUpdatePage } from './specialite.page-object';

const expect = chai.expect;

describe('Specialite e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let specialiteComponentsPage: SpecialiteComponentsPage;
  let specialiteUpdatePage: SpecialiteUpdatePage;
  let specialiteDeleteDialog: SpecialiteDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Specialites', async () => {
    await navBarPage.goToEntity('specialite');
    specialiteComponentsPage = new SpecialiteComponentsPage();
    await browser.wait(ec.visibilityOf(specialiteComponentsPage.title), 5000);
    expect(await specialiteComponentsPage.getTitle()).to.eq('gpecmanagerApp.specialite.home.title');
    await browser.wait(ec.or(ec.visibilityOf(specialiteComponentsPage.entities), ec.visibilityOf(specialiteComponentsPage.noResult)), 1000);
  });

  it('should load create Specialite page', async () => {
    await specialiteComponentsPage.clickOnCreateButton();
    specialiteUpdatePage = new SpecialiteUpdatePage();
    expect(await specialiteUpdatePage.getPageTitle()).to.eq('gpecmanagerApp.specialite.home.createOrEditLabel');
    await specialiteUpdatePage.cancel();
  });

  it('should create and save Specialites', async () => {
    const nbButtonsBeforeCreate = await specialiteComponentsPage.countDeleteButtons();

    await specialiteComponentsPage.clickOnCreateButton();

    await promise.all([
      specialiteUpdatePage.setLibelleSpecialiteInput('libelleSpecialite'),
      specialiteUpdatePage.fonctionSelectLastOption(),
    ]);

    expect(await specialiteUpdatePage.getLibelleSpecialiteInput()).to.eq(
      'libelleSpecialite',
      'Expected LibelleSpecialite value to be equals to libelleSpecialite'
    );

    await specialiteUpdatePage.save();
    expect(await specialiteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await specialiteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Specialite', async () => {
    const nbButtonsBeforeDelete = await specialiteComponentsPage.countDeleteButtons();
    await specialiteComponentsPage.clickOnLastDeleteButton();

    specialiteDeleteDialog = new SpecialiteDeleteDialog();
    expect(await specialiteDeleteDialog.getDialogTitle()).to.eq('gpecmanagerApp.specialite.delete.question');
    await specialiteDeleteDialog.clickOnConfirmButton();

    expect(await specialiteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
