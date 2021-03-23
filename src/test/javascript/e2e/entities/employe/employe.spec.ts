import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EmployeComponentsPage, EmployeDeleteDialog, EmployeUpdatePage } from './employe.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Employe e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employeComponentsPage: EmployeComponentsPage;
  let employeUpdatePage: EmployeUpdatePage;
  let employeDeleteDialog: EmployeDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Employes', async () => {
    await navBarPage.goToEntity('employe');
    employeComponentsPage = new EmployeComponentsPage();
    await browser.wait(ec.visibilityOf(employeComponentsPage.title), 5000);
    expect(await employeComponentsPage.getTitle()).to.eq('gpecmanagerApp.employe.home.title');
    await browser.wait(ec.or(ec.visibilityOf(employeComponentsPage.entities), ec.visibilityOf(employeComponentsPage.noResult)), 1000);
  });

  it('should load create Employe page', async () => {
    await employeComponentsPage.clickOnCreateButton();
    employeUpdatePage = new EmployeUpdatePage();
    expect(await employeUpdatePage.getPageTitle()).to.eq('gpecmanagerApp.employe.home.createOrEditLabel');
    await employeUpdatePage.cancel();
  });

  it('should create and save Employes', async () => {
    const nbButtonsBeforeCreate = await employeComponentsPage.countDeleteButtons();

    await employeComponentsPage.clickOnCreateButton();

    await promise.all([
      employeUpdatePage.setMatriculeInput('matricule'),
      employeUpdatePage.setPrenomsInput('prenoms'),
      employeUpdatePage.setNomInput('nom'),
      employeUpdatePage.setIntituleEmployeInput('intituleEmploye'),
      employeUpdatePage.setDateNaissanceInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      employeUpdatePage.setLieuNaissanceInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      employeUpdatePage.setNumeroTelephoneInput('numeroTelephone'),
      employeUpdatePage.setAdresseInput('adresse'),
      employeUpdatePage.setPhotoInput(absolutePath),
      employeUpdatePage.setEmailInput('email'),
      employeUpdatePage.setDateEmbauchementInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      employeUpdatePage.setDateRetraiteInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      employeUpdatePage.setAgeInput('5'),
      employeUpdatePage.setNumeroCniInput('numeroCni'),
      employeUpdatePage.setNumeroIpresInput('numeroIpres'),
      employeUpdatePage.setNumeroCssInput('numeroCss'),
      employeUpdatePage.groupeSanguinSelectLastOption(),
      employeUpdatePage.situationMatrimonialSelectLastOption(),
      employeUpdatePage.sexeSelectLastOption(),
      employeUpdatePage.disponibiliteSelectLastOption(),
      employeUpdatePage.contratSelectLastOption(),
      // employeUpdatePage.serviceSelectLastOption(),
      // employeUpdatePage.serviceFrequenteSelectLastOption(),
    ]);

    expect(await employeUpdatePage.getMatriculeInput()).to.eq('matricule', 'Expected Matricule value to be equals to matricule');
    expect(await employeUpdatePage.getPrenomsInput()).to.eq('prenoms', 'Expected Prenoms value to be equals to prenoms');
    expect(await employeUpdatePage.getNomInput()).to.eq('nom', 'Expected Nom value to be equals to nom');
    expect(await employeUpdatePage.getIntituleEmployeInput()).to.eq(
      'intituleEmploye',
      'Expected IntituleEmploye value to be equals to intituleEmploye'
    );
    expect(await employeUpdatePage.getDateNaissanceInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateNaissance value to be equals to 2000-12-31'
    );
    expect(await employeUpdatePage.getLieuNaissanceInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lieuNaissance value to be equals to 2000-12-31'
    );
    expect(await employeUpdatePage.getNumeroTelephoneInput()).to.eq(
      'numeroTelephone',
      'Expected NumeroTelephone value to be equals to numeroTelephone'
    );
    expect(await employeUpdatePage.getAdresseInput()).to.eq('adresse', 'Expected Adresse value to be equals to adresse');
    expect(await employeUpdatePage.getPhotoInput()).to.endsWith(
      fileNameToUpload,
      'Expected Photo value to be end with ' + fileNameToUpload
    );
    expect(await employeUpdatePage.getEmailInput()).to.eq('email', 'Expected Email value to be equals to email');
    expect(await employeUpdatePage.getDateEmbauchementInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateEmbauchement value to be equals to 2000-12-31'
    );
    expect(await employeUpdatePage.getDateRetraiteInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateRetraite value to be equals to 2000-12-31'
    );
    expect(await employeUpdatePage.getAgeInput()).to.eq('5', 'Expected age value to be equals to 5');
    expect(await employeUpdatePage.getNumeroCniInput()).to.eq('numeroCni', 'Expected NumeroCni value to be equals to numeroCni');
    expect(await employeUpdatePage.getNumeroIpresInput()).to.eq('numeroIpres', 'Expected NumeroIpres value to be equals to numeroIpres');
    expect(await employeUpdatePage.getNumeroCssInput()).to.eq('numeroCss', 'Expected NumeroCss value to be equals to numeroCss');

    await employeUpdatePage.save();
    expect(await employeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await employeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Employe', async () => {
    const nbButtonsBeforeDelete = await employeComponentsPage.countDeleteButtons();
    await employeComponentsPage.clickOnLastDeleteButton();

    employeDeleteDialog = new EmployeDeleteDialog();
    expect(await employeDeleteDialog.getDialogTitle()).to.eq('gpecmanagerApp.employe.delete.question');
    await employeDeleteDialog.clickOnConfirmButton();

    expect(await employeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
