import { element, by, ElementFinder } from 'protractor';

export class ContratComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-contrat div table .btn-danger'));
  title = element.all(by.css('jhi-contrat div h2#page-heading span')).first();
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

export class ContratUpdatePage {
  pageTitle = element(by.id('jhi-contrat-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  numeroContratInput = element(by.id('field_numeroContrat'));
  libelleContratInput = element(by.id('field_libelleContrat'));
  dateDebutInput = element(by.id('field_dateDebut'));
  dateFinInput = element(by.id('field_dateFin'));
  niveauEtudeSelect = element(by.id('field_niveauEtude'));
  typeContratSelect = element(by.id('field_typeContrat'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNumeroContratInput(numeroContrat: string): Promise<void> {
    await this.numeroContratInput.sendKeys(numeroContrat);
  }

  async getNumeroContratInput(): Promise<string> {
    return await this.numeroContratInput.getAttribute('value');
  }

  async setLibelleContratInput(libelleContrat: string): Promise<void> {
    await this.libelleContratInput.sendKeys(libelleContrat);
  }

  async getLibelleContratInput(): Promise<string> {
    return await this.libelleContratInput.getAttribute('value');
  }

  async setDateDebutInput(dateDebut: string): Promise<void> {
    await this.dateDebutInput.sendKeys(dateDebut);
  }

  async getDateDebutInput(): Promise<string> {
    return await this.dateDebutInput.getAttribute('value');
  }

  async setDateFinInput(dateFin: string): Promise<void> {
    await this.dateFinInput.sendKeys(dateFin);
  }

  async getDateFinInput(): Promise<string> {
    return await this.dateFinInput.getAttribute('value');
  }

  async setNiveauEtudeSelect(niveauEtude: string): Promise<void> {
    await this.niveauEtudeSelect.sendKeys(niveauEtude);
  }

  async getNiveauEtudeSelect(): Promise<string> {
    return await this.niveauEtudeSelect.element(by.css('option:checked')).getText();
  }

  async niveauEtudeSelectLastOption(): Promise<void> {
    await this.niveauEtudeSelect.all(by.tagName('option')).last().click();
  }

  async setTypeContratSelect(typeContrat: string): Promise<void> {
    await this.typeContratSelect.sendKeys(typeContrat);
  }

  async getTypeContratSelect(): Promise<string> {
    return await this.typeContratSelect.element(by.css('option:checked')).getText();
  }

  async typeContratSelectLastOption(): Promise<void> {
    await this.typeContratSelect.all(by.tagName('option')).last().click();
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

export class ContratDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-contrat-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-contrat'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
