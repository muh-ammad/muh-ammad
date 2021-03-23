import { element, by, ElementFinder } from 'protractor';

export class ServiceFrequenteComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-service-frequente div table .btn-danger'));
  title = element.all(by.css('jhi-service-frequente div h2#page-heading span')).first();
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

export class ServiceFrequenteUpdatePage {
  pageTitle = element(by.id('jhi-service-frequente-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  codeServiceInput = element(by.id('field_codeService'));
  libelleServiceInput = element(by.id('field_libelleService'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCodeServiceInput(codeService: string): Promise<void> {
    await this.codeServiceInput.sendKeys(codeService);
  }

  async getCodeServiceInput(): Promise<string> {
    return await this.codeServiceInput.getAttribute('value');
  }

  async setLibelleServiceInput(libelleService: string): Promise<void> {
    await this.libelleServiceInput.sendKeys(libelleService);
  }

  async getLibelleServiceInput(): Promise<string> {
    return await this.libelleServiceInput.getAttribute('value');
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

export class ServiceFrequenteDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-serviceFrequente-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-serviceFrequente'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
