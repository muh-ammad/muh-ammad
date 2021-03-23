import { element, by, ElementFinder } from 'protractor';

export class SpecialiteComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-specialite div table .btn-danger'));
  title = element.all(by.css('jhi-specialite div h2#page-heading span')).first();
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

export class SpecialiteUpdatePage {
  pageTitle = element(by.id('jhi-specialite-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  libelleSpecialiteInput = element(by.id('field_libelleSpecialite'));

  fonctionSelect = element(by.id('field_fonction'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setLibelleSpecialiteInput(libelleSpecialite: string): Promise<void> {
    await this.libelleSpecialiteInput.sendKeys(libelleSpecialite);
  }

  async getLibelleSpecialiteInput(): Promise<string> {
    return await this.libelleSpecialiteInput.getAttribute('value');
  }

  async fonctionSelectLastOption(): Promise<void> {
    await this.fonctionSelect.all(by.tagName('option')).last().click();
  }

  async fonctionSelectOption(option: string): Promise<void> {
    await this.fonctionSelect.sendKeys(option);
  }

  getFonctionSelect(): ElementFinder {
    return this.fonctionSelect;
  }

  async getFonctionSelectedOption(): Promise<string> {
    return await this.fonctionSelect.element(by.css('option:checked')).getText();
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

export class SpecialiteDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-specialite-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-specialite'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
