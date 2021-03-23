import { element, by, ElementFinder } from 'protractor';

export class SecteurActiviteComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-secteur-activite div table .btn-danger'));
  title = element.all(by.css('jhi-secteur-activite div h2#page-heading span')).first();
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

export class SecteurActiviteUpdatePage {
  pageTitle = element(by.id('jhi-secteur-activite-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  libelleActiviteInput = element(by.id('field_libelleActivite'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setLibelleActiviteInput(libelleActivite: string): Promise<void> {
    await this.libelleActiviteInput.sendKeys(libelleActivite);
  }

  async getLibelleActiviteInput(): Promise<string> {
    return await this.libelleActiviteInput.getAttribute('value');
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

export class SecteurActiviteDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-secteurActivite-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-secteurActivite'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
