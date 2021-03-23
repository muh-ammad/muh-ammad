import { element, by, ElementFinder } from 'protractor';

export class EmployeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-employe div table .btn-danger'));
  title = element.all(by.css('jhi-employe div h2#page-heading span')).first();
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

export class EmployeUpdatePage {
  pageTitle = element(by.id('jhi-employe-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  matriculeInput = element(by.id('field_matricule'));
  prenomsInput = element(by.id('field_prenoms'));
  nomInput = element(by.id('field_nom'));
  intituleEmployeInput = element(by.id('field_intituleEmploye'));
  dateNaissanceInput = element(by.id('field_dateNaissance'));
  lieuNaissanceInput = element(by.id('field_lieuNaissance'));
  numeroTelephoneInput = element(by.id('field_numeroTelephone'));
  adresseInput = element(by.id('field_adresse'));
  photoInput = element(by.id('file_photo'));
  emailInput = element(by.id('field_email'));
  dateEmbauchementInput = element(by.id('field_dateEmbauchement'));
  dateRetraiteInput = element(by.id('field_dateRetraite'));
  ageInput = element(by.id('field_age'));
  numeroCniInput = element(by.id('field_numeroCni'));
  numeroIpresInput = element(by.id('field_numeroIpres'));
  numeroCssInput = element(by.id('field_numeroCss'));
  groupeSanguinSelect = element(by.id('field_groupeSanguin'));
  situationMatrimonialSelect = element(by.id('field_situationMatrimonial'));
  sexeSelect = element(by.id('field_sexe'));
  disponibiliteSelect = element(by.id('field_disponibilite'));

  contratSelect = element(by.id('field_contrat'));
  serviceSelect = element(by.id('field_service'));
  serviceFrequenteSelect = element(by.id('field_serviceFrequente'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setMatriculeInput(matricule: string): Promise<void> {
    await this.matriculeInput.sendKeys(matricule);
  }

  async getMatriculeInput(): Promise<string> {
    return await this.matriculeInput.getAttribute('value');
  }

  async setPrenomsInput(prenoms: string): Promise<void> {
    await this.prenomsInput.sendKeys(prenoms);
  }

  async getPrenomsInput(): Promise<string> {
    return await this.prenomsInput.getAttribute('value');
  }

  async setNomInput(nom: string): Promise<void> {
    await this.nomInput.sendKeys(nom);
  }

  async getNomInput(): Promise<string> {
    return await this.nomInput.getAttribute('value');
  }

  async setIntituleEmployeInput(intituleEmploye: string): Promise<void> {
    await this.intituleEmployeInput.sendKeys(intituleEmploye);
  }

  async getIntituleEmployeInput(): Promise<string> {
    return await this.intituleEmployeInput.getAttribute('value');
  }

  async setDateNaissanceInput(dateNaissance: string): Promise<void> {
    await this.dateNaissanceInput.sendKeys(dateNaissance);
  }

  async getDateNaissanceInput(): Promise<string> {
    return await this.dateNaissanceInput.getAttribute('value');
  }

  async setLieuNaissanceInput(lieuNaissance: string): Promise<void> {
    await this.lieuNaissanceInput.sendKeys(lieuNaissance);
  }

  async getLieuNaissanceInput(): Promise<string> {
    return await this.lieuNaissanceInput.getAttribute('value');
  }

  async setNumeroTelephoneInput(numeroTelephone: string): Promise<void> {
    await this.numeroTelephoneInput.sendKeys(numeroTelephone);
  }

  async getNumeroTelephoneInput(): Promise<string> {
    return await this.numeroTelephoneInput.getAttribute('value');
  }

  async setAdresseInput(adresse: string): Promise<void> {
    await this.adresseInput.sendKeys(adresse);
  }

  async getAdresseInput(): Promise<string> {
    return await this.adresseInput.getAttribute('value');
  }

  async setPhotoInput(photo: string): Promise<void> {
    await this.photoInput.sendKeys(photo);
  }

  async getPhotoInput(): Promise<string> {
    return await this.photoInput.getAttribute('value');
  }

  async setEmailInput(email: string): Promise<void> {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput(): Promise<string> {
    return await this.emailInput.getAttribute('value');
  }

  async setDateEmbauchementInput(dateEmbauchement: string): Promise<void> {
    await this.dateEmbauchementInput.sendKeys(dateEmbauchement);
  }

  async getDateEmbauchementInput(): Promise<string> {
    return await this.dateEmbauchementInput.getAttribute('value');
  }

  async setDateRetraiteInput(dateRetraite: string): Promise<void> {
    await this.dateRetraiteInput.sendKeys(dateRetraite);
  }

  async getDateRetraiteInput(): Promise<string> {
    return await this.dateRetraiteInput.getAttribute('value');
  }

  async setAgeInput(age: string): Promise<void> {
    await this.ageInput.sendKeys(age);
  }

  async getAgeInput(): Promise<string> {
    return await this.ageInput.getAttribute('value');
  }

  async setNumeroCniInput(numeroCni: string): Promise<void> {
    await this.numeroCniInput.sendKeys(numeroCni);
  }

  async getNumeroCniInput(): Promise<string> {
    return await this.numeroCniInput.getAttribute('value');
  }

  async setNumeroIpresInput(numeroIpres: string): Promise<void> {
    await this.numeroIpresInput.sendKeys(numeroIpres);
  }

  async getNumeroIpresInput(): Promise<string> {
    return await this.numeroIpresInput.getAttribute('value');
  }

  async setNumeroCssInput(numeroCss: string): Promise<void> {
    await this.numeroCssInput.sendKeys(numeroCss);
  }

  async getNumeroCssInput(): Promise<string> {
    return await this.numeroCssInput.getAttribute('value');
  }

  async setGroupeSanguinSelect(groupeSanguin: string): Promise<void> {
    await this.groupeSanguinSelect.sendKeys(groupeSanguin);
  }

  async getGroupeSanguinSelect(): Promise<string> {
    return await this.groupeSanguinSelect.element(by.css('option:checked')).getText();
  }

  async groupeSanguinSelectLastOption(): Promise<void> {
    await this.groupeSanguinSelect.all(by.tagName('option')).last().click();
  }

  async setSituationMatrimonialSelect(situationMatrimonial: string): Promise<void> {
    await this.situationMatrimonialSelect.sendKeys(situationMatrimonial);
  }

  async getSituationMatrimonialSelect(): Promise<string> {
    return await this.situationMatrimonialSelect.element(by.css('option:checked')).getText();
  }

  async situationMatrimonialSelectLastOption(): Promise<void> {
    await this.situationMatrimonialSelect.all(by.tagName('option')).last().click();
  }

  async setSexeSelect(sexe: string): Promise<void> {
    await this.sexeSelect.sendKeys(sexe);
  }

  async getSexeSelect(): Promise<string> {
    return await this.sexeSelect.element(by.css('option:checked')).getText();
  }

  async sexeSelectLastOption(): Promise<void> {
    await this.sexeSelect.all(by.tagName('option')).last().click();
  }

  async setDisponibiliteSelect(disponibilite: string): Promise<void> {
    await this.disponibiliteSelect.sendKeys(disponibilite);
  }

  async getDisponibiliteSelect(): Promise<string> {
    return await this.disponibiliteSelect.element(by.css('option:checked')).getText();
  }

  async disponibiliteSelectLastOption(): Promise<void> {
    await this.disponibiliteSelect.all(by.tagName('option')).last().click();
  }

  async contratSelectLastOption(): Promise<void> {
    await this.contratSelect.all(by.tagName('option')).last().click();
  }

  async contratSelectOption(option: string): Promise<void> {
    await this.contratSelect.sendKeys(option);
  }

  getContratSelect(): ElementFinder {
    return this.contratSelect;
  }

  async getContratSelectedOption(): Promise<string> {
    return await this.contratSelect.element(by.css('option:checked')).getText();
  }

  async serviceSelectLastOption(): Promise<void> {
    await this.serviceSelect.all(by.tagName('option')).last().click();
  }

  async serviceSelectOption(option: string): Promise<void> {
    await this.serviceSelect.sendKeys(option);
  }

  getServiceSelect(): ElementFinder {
    return this.serviceSelect;
  }

  async getServiceSelectedOption(): Promise<string> {
    return await this.serviceSelect.element(by.css('option:checked')).getText();
  }

  async serviceFrequenteSelectLastOption(): Promise<void> {
    await this.serviceFrequenteSelect.all(by.tagName('option')).last().click();
  }

  async serviceFrequenteSelectOption(option: string): Promise<void> {
    await this.serviceFrequenteSelect.sendKeys(option);
  }

  getServiceFrequenteSelect(): ElementFinder {
    return this.serviceFrequenteSelect;
  }

  async getServiceFrequenteSelectedOption(): Promise<string> {
    return await this.serviceFrequenteSelect.element(by.css('option:checked')).getText();
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

export class EmployeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-employe-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-employe'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
