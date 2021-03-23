import { element, by, ElementFinder } from 'protractor';

export class OpexComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-opex div table .btn-danger'));
  title = element.all(by.css('jhi-opex div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class OpexUpdatePage {
  pageTitle = element(by.id('jhi-opex-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  lieuOpexInput = element(by.id('field_lieuOpex'));
  anneeOpexInput = element(by.id('field_anneeOpex'));

  employeSelect = element(by.id('field_employe'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setLieuOpexInput(lieuOpex: string): Promise<void> {
    await this.lieuOpexInput.sendKeys(lieuOpex);
  }

  async getLieuOpexInput(): Promise<string> {
    return await this.lieuOpexInput.getAttribute('value');
  }

  async setAnneeOpexInput(anneeOpex: string): Promise<void> {
    await this.anneeOpexInput.sendKeys(anneeOpex);
  }

  async getAnneeOpexInput(): Promise<string> {
    return await this.anneeOpexInput.getAttribute('value');
  }

  async employeSelectLastOption(): Promise<void> {
    await this.employeSelect.all(by.tagName('option')).last().click();
  }

  async employeSelectOption(option: string): Promise<void> {
    await this.employeSelect.sendKeys(option);
  }

  getEmployeSelect(): ElementFinder {
    return this.employeSelect;
  }

  async getEmployeSelectedOption(): Promise<string> {
    return await this.employeSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class OpexDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-opex-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-opex'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
