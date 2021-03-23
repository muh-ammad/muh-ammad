import { element, by, ElementFinder } from 'protractor';

export class ServiceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-service div table .btn-danger'));
  title = element.all(by.css('jhi-service div h2#page-heading span')).first();
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

export class ServiceUpdatePage {
  pageTitle = element(by.id('jhi-service-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  codeServiceInput = element(by.id('field_codeService'));
  libelleServiceInput = element(by.id('field_libelleService'));

  secteurActiviteSelect = element(by.id('field_secteurActivite'));

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

  async secteurActiviteSelectLastOption(): Promise<void> {
    await this.secteurActiviteSelect.all(by.tagName('option')).last().click();
  }

  async secteurActiviteSelectOption(option: string): Promise<void> {
    await this.secteurActiviteSelect.sendKeys(option);
  }

  getSecteurActiviteSelect(): ElementFinder {
    return this.secteurActiviteSelect;
  }

  async getSecteurActiviteSelectedOption(): Promise<string> {
    return await this.secteurActiviteSelect.element(by.css('option:checked')).getText();
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

export class ServiceDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-service-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-service'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
