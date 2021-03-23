import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'employe',
        loadChildren: () => import('./employe/employe.module').then(m => m.GpecmanagerEmployeModule),
      },
      {
        path: 'fonction',
        loadChildren: () => import('./fonction/fonction.module').then(m => m.GpecmanagerFonctionModule),
      },
      {
        path: 'specialite',
        loadChildren: () => import('./specialite/specialite.module').then(m => m.GpecmanagerSpecialiteModule),
      },
      {
        path: 'service-frequente',
        loadChildren: () => import('./service-frequente/service-frequente.module').then(m => m.GpecmanagerServiceFrequenteModule),
      },
      {
        path: 'membre-famille',
        loadChildren: () => import('./membre-famille/membre-famille.module').then(m => m.GpecmanagerMembreFamilleModule),
      },
      {
        path: 'contrat',
        loadChildren: () => import('./contrat/contrat.module').then(m => m.GpecmanagerContratModule),
      },
      {
        path: 'diplome',
        loadChildren: () => import('./diplome/diplome.module').then(m => m.GpecmanagerDiplomeModule),
      },
      {
        path: 'distinction',
        loadChildren: () => import('./distinction/distinction.module').then(m => m.GpecmanagerDistinctionModule),
      },
      {
        path: 'secteur-activite',
        loadChildren: () => import('./secteur-activite/secteur-activite.module').then(m => m.GpecmanagerSecteurActiviteModule),
      },
      {
        path: 'operation-exterieur',
        loadChildren: () => import('./operation-exterieur/operation-exterieur.module').then(m => m.GpecmanagerOperationExterieurModule),
      },
      {
        path: 'service-affecte',
        loadChildren: () => import('./service-affecte/service-affecte.module').then(m => m.GpecmanagerServiceAffecteModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class GpecmanagerEntityModule {}
