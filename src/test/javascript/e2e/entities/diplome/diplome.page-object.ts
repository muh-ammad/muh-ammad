import { element, by, ElementFinder } from 'protractor';

export class DiplomeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-diplome div table .btn-danger'));
  title = element.all(by.css('jhi-diplome div h2#page-heading span')).first();
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

export class DiplomeUpdatePage {
  pageTitle = element(by.id('jhi-diplome-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  libelleDiplomeInput = element(by.id('field_libelleDiplome'));
  anneeDiplomeInput = element(by.id('field_anneeDiplome'));

  employeSelect = element(by.id('field_employe'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setLibelleDiplomeInput(libelleDiplome: string): Promise<void> {
    await this.libelleDiplomeInput.sendKeys(libelleDiplome);
  }

  async getLibelleDiplomeInput(): Promise<string> {
    return await this.libelleDiplomeInput.getAttribute('value');
  }

  async setAnneeDiplomeInput(anneeDiplome: string): Promise<void> {
    await this.anneeDiplomeInput.sendKeys(anneeDiplome);
  }

  async getAnneeDiplomeInput(): Promise<string> {
    return await this.anneeDiplomeInput.getAttribute('value');
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

export class DiplomeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-diplome-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-diplome'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
