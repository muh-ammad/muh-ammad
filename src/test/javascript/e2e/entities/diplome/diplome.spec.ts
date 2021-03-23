import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DiplomeComponentsPage, DiplomeDeleteDialog, DiplomeUpdatePage } from './diplome.page-object';

const expect = chai.expect;

describe('Diplome e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let diplomeComponentsPage: DiplomeComponentsPage;
  let diplomeUpdatePage: DiplomeUpdatePage;
  let diplomeDeleteDialog: DiplomeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Diplomes', async () => {
    await navBarPage.goToEntity('diplome');
    diplomeComponentsPage = new DiplomeComponentsPage();
    await browser.wait(ec.visibilityOf(diplomeComponentsPage.title), 5000);
    expect(await diplomeComponentsPage.getTitle()).to.eq('gpecmanagerApp.diplome.home.title');
    await browser.wait(ec.or(ec.visibilityOf(diplomeComponentsPage.entities), ec.visibilityOf(diplomeComponentsPage.noResult)), 1000);
  });

  it('should load create Diplome page', async () => {
    await diplomeComponentsPage.clickOnCreateButton();
    diplomeUpdatePage = new DiplomeUpdatePage();
    expect(await diplomeUpdatePage.getPageTitle()).to.eq('gpecmanagerApp.diplome.home.createOrEditLabel');
    await diplomeUpdatePage.cancel();
  });

  it('should create and save Diplomes', async () => {
    const nbButtonsBeforeCreate = await diplomeComponentsPage.countDeleteButtons();

    await diplomeComponentsPage.clickOnCreateButton();

    await promise.all([
      diplomeUpdatePage.setLibelleDiplomeInput('libelleDiplome'),
      diplomeUpdatePage.setAnneeDiplomeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      diplomeUpdatePage.employeSelectLastOption(),
    ]);

    expect(await diplomeUpdatePage.getLibelleDiplomeInput()).to.eq(
      'libelleDiplome',
      'Expected LibelleDiplome value to be equals to libelleDiplome'
    );
    expect(await diplomeUpdatePage.getAnneeDiplomeInput()).to.contain(
      '2001-01-01T02:30',
      'Expected anneeDiplome value to be equals to 2000-12-31'
    );

    await diplomeUpdatePage.save();
    expect(await diplomeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await diplomeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Diplome', async () => {
    const nbButtonsBeforeDelete = await diplomeComponentsPage.countDeleteButtons();
    await diplomeComponentsPage.clickOnLastDeleteButton();

    diplomeDeleteDialog = new DiplomeDeleteDialog();
    expect(await diplomeDeleteDialog.getDialogTitle()).to.eq('gpecmanagerApp.diplome.delete.question');
    await diplomeDeleteDialog.clickOnConfirmButton();

    expect(await diplomeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
