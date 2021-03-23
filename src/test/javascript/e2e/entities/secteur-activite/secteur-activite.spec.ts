import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SecteurActiviteComponentsPage, SecteurActiviteDeleteDialog, SecteurActiviteUpdatePage } from './secteur-activite.page-object';

const expect = chai.expect;

describe('SecteurActivite e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let secteurActiviteComponentsPage: SecteurActiviteComponentsPage;
  let secteurActiviteUpdatePage: SecteurActiviteUpdatePage;
  let secteurActiviteDeleteDialog: SecteurActiviteDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load SecteurActivites', async () => {
    await navBarPage.goToEntity('secteur-activite');
    secteurActiviteComponentsPage = new SecteurActiviteComponentsPage();
    await browser.wait(ec.visibilityOf(secteurActiviteComponentsPage.title), 5000);
    expect(await secteurActiviteComponentsPage.getTitle()).to.eq('gpecmanagerApp.secteurActivite.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(secteurActiviteComponentsPage.entities), ec.visibilityOf(secteurActiviteComponentsPage.noResult)),
      1000
    );
  });

  it('should load create SecteurActivite page', async () => {
    await secteurActiviteComponentsPage.clickOnCreateButton();
    secteurActiviteUpdatePage = new SecteurActiviteUpdatePage();
    expect(await secteurActiviteUpdatePage.getPageTitle()).to.eq('gpecmanagerApp.secteurActivite.home.createOrEditLabel');
    await secteurActiviteUpdatePage.cancel();
  });

  it('should create and save SecteurActivites', async () => {
    const nbButtonsBeforeCreate = await secteurActiviteComponentsPage.countDeleteButtons();

    await secteurActiviteComponentsPage.clickOnCreateButton();

    await promise.all([secteurActiviteUpdatePage.setLibelleActiviteInput('libelleActivite')]);

    expect(await secteurActiviteUpdatePage.getLibelleActiviteInput()).to.eq(
      'libelleActivite',
      'Expected LibelleActivite value to be equals to libelleActivite'
    );

    await secteurActiviteUpdatePage.save();
    expect(await secteurActiviteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await secteurActiviteComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last SecteurActivite', async () => {
    const nbButtonsBeforeDelete = await secteurActiviteComponentsPage.countDeleteButtons();
    await secteurActiviteComponentsPage.clickOnLastDeleteButton();

    secteurActiviteDeleteDialog = new SecteurActiviteDeleteDialog();
    expect(await secteurActiviteDeleteDialog.getDialogTitle()).to.eq('gpecmanagerApp.secteurActivite.delete.question');
    await secteurActiviteDeleteDialog.clickOnConfirmButton();

    expect(await secteurActiviteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
