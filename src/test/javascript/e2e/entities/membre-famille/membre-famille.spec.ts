import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MembreFamilleComponentsPage, MembreFamilleDeleteDialog, MembreFamilleUpdatePage } from './membre-famille.page-object';

const expect = chai.expect;

describe('MembreFamille e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let membreFamilleComponentsPage: MembreFamilleComponentsPage;
  let membreFamilleUpdatePage: MembreFamilleUpdatePage;
  let membreFamilleDeleteDialog: MembreFamilleDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load MembreFamilles', async () => {
    await navBarPage.goToEntity('membre-famille');
    membreFamilleComponentsPage = new MembreFamilleComponentsPage();
    await browser.wait(ec.visibilityOf(membreFamilleComponentsPage.title), 5000);
    expect(await membreFamilleComponentsPage.getTitle()).to.eq('gpecmanagerApp.membreFamille.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(membreFamilleComponentsPage.entities), ec.visibilityOf(membreFamilleComponentsPage.noResult)),
      1000
    );
  });

  it('should load create MembreFamille page', async () => {
    await membreFamilleComponentsPage.clickOnCreateButton();
    membreFamilleUpdatePage = new MembreFamilleUpdatePage();
    expect(await membreFamilleUpdatePage.getPageTitle()).to.eq('gpecmanagerApp.membreFamille.home.createOrEditLabel');
    await membreFamilleUpdatePage.cancel();
  });

  it('should create and save MembreFamilles', async () => {
    const nbButtonsBeforeCreate = await membreFamilleComponentsPage.countDeleteButtons();

    await membreFamilleComponentsPage.clickOnCreateButton();

    await promise.all([
      membreFamilleUpdatePage.setPrenomsInput('prenoms'),
      membreFamilleUpdatePage.setNomInput('nom'),
      membreFamilleUpdatePage.setDateNaissanceInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      membreFamilleUpdatePage.setLieuNaissanceInput('lieuNaissance'),
      membreFamilleUpdatePage.employeSelectLastOption(),
    ]);

    expect(await membreFamilleUpdatePage.getPrenomsInput()).to.eq('prenoms', 'Expected Prenoms value to be equals to prenoms');
    expect(await membreFamilleUpdatePage.getNomInput()).to.eq('nom', 'Expected Nom value to be equals to nom');
    expect(await membreFamilleUpdatePage.getDateNaissanceInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateNaissance value to be equals to 2000-12-31'
    );
    expect(await membreFamilleUpdatePage.getLieuNaissanceInput()).to.eq(
      'lieuNaissance',
      'Expected LieuNaissance value to be equals to lieuNaissance'
    );
    const selectedEpoux = membreFamilleUpdatePage.getEpouxInput();
    if (await selectedEpoux.isSelected()) {
      await membreFamilleUpdatePage.getEpouxInput().click();
      expect(await membreFamilleUpdatePage.getEpouxInput().isSelected(), 'Expected epoux not to be selected').to.be.false;
    } else {
      await membreFamilleUpdatePage.getEpouxInput().click();
      expect(await membreFamilleUpdatePage.getEpouxInput().isSelected(), 'Expected epoux to be selected').to.be.true;
    }
    const selectedEpouse = membreFamilleUpdatePage.getEpouseInput();
    if (await selectedEpouse.isSelected()) {
      await membreFamilleUpdatePage.getEpouseInput().click();
      expect(await membreFamilleUpdatePage.getEpouseInput().isSelected(), 'Expected epouse not to be selected').to.be.false;
    } else {
      await membreFamilleUpdatePage.getEpouseInput().click();
      expect(await membreFamilleUpdatePage.getEpouseInput().isSelected(), 'Expected epouse to be selected').to.be.true;
    }
    const selectedEnfant = membreFamilleUpdatePage.getEnfantInput();
    if (await selectedEnfant.isSelected()) {
      await membreFamilleUpdatePage.getEnfantInput().click();
      expect(await membreFamilleUpdatePage.getEnfantInput().isSelected(), 'Expected enfant not to be selected').to.be.false;
    } else {
      await membreFamilleUpdatePage.getEnfantInput().click();
      expect(await membreFamilleUpdatePage.getEnfantInput().isSelected(), 'Expected enfant to be selected').to.be.true;
    }

    await membreFamilleUpdatePage.save();
    expect(await membreFamilleUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await membreFamilleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last MembreFamille', async () => {
    const nbButtonsBeforeDelete = await membreFamilleComponentsPage.countDeleteButtons();
    await membreFamilleComponentsPage.clickOnLastDeleteButton();

    membreFamilleDeleteDialog = new MembreFamilleDeleteDialog();
    expect(await membreFamilleDeleteDialog.getDialogTitle()).to.eq('gpecmanagerApp.membreFamille.delete.question');
    await membreFamilleDeleteDialog.clickOnConfirmButton();

    expect(await membreFamilleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
