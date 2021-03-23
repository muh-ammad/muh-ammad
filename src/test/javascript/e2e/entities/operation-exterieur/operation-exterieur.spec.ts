import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  OperationExterieurComponentsPage,
  OperationExterieurDeleteDialog,
  OperationExterieurUpdatePage,
} from './operation-exterieur.page-object';

const expect = chai.expect;

describe('OperationExterieur e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let operationExterieurComponentsPage: OperationExterieurComponentsPage;
  let operationExterieurUpdatePage: OperationExterieurUpdatePage;
  let operationExterieurDeleteDialog: OperationExterieurDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load OperationExterieurs', async () => {
    await navBarPage.goToEntity('operation-exterieur');
    operationExterieurComponentsPage = new OperationExterieurComponentsPage();
    await browser.wait(ec.visibilityOf(operationExterieurComponentsPage.title), 5000);
    expect(await operationExterieurComponentsPage.getTitle()).to.eq('gpecmanagerApp.operationExterieur.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(operationExterieurComponentsPage.entities), ec.visibilityOf(operationExterieurComponentsPage.noResult)),
      1000
    );
  });

  it('should load create OperationExterieur page', async () => {
    await operationExterieurComponentsPage.clickOnCreateButton();
    operationExterieurUpdatePage = new OperationExterieurUpdatePage();
    expect(await operationExterieurUpdatePage.getPageTitle()).to.eq('gpecmanagerApp.operationExterieur.home.createOrEditLabel');
    await operationExterieurUpdatePage.cancel();
  });

  it('should create and save OperationExterieurs', async () => {
    const nbButtonsBeforeCreate = await operationExterieurComponentsPage.countDeleteButtons();

    await operationExterieurComponentsPage.clickOnCreateButton();

    await promise.all([
      operationExterieurUpdatePage.setLieuOpexInput('lieuOpex'),
      operationExterieurUpdatePage.setAnneeOpexInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      operationExterieurUpdatePage.employeSelectLastOption(),
    ]);

    expect(await operationExterieurUpdatePage.getLieuOpexInput()).to.eq('lieuOpex', 'Expected LieuOpex value to be equals to lieuOpex');
    expect(await operationExterieurUpdatePage.getAnneeOpexInput()).to.contain(
      '2001-01-01T02:30',
      'Expected anneeOpex value to be equals to 2000-12-31'
    );

    await operationExterieurUpdatePage.save();
    expect(await operationExterieurUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await operationExterieurComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last OperationExterieur', async () => {
    const nbButtonsBeforeDelete = await operationExterieurComponentsPage.countDeleteButtons();
    await operationExterieurComponentsPage.clickOnLastDeleteButton();

    operationExterieurDeleteDialog = new OperationExterieurDeleteDialog();
    expect(await operationExterieurDeleteDialog.getDialogTitle()).to.eq('gpecmanagerApp.operationExterieur.delete.question');
    await operationExterieurDeleteDialog.clickOnConfirmButton();

    expect(await operationExterieurComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
