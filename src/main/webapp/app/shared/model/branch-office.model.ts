import { IExternalResponsable } from './external-responsable.model';
import { ICompany } from './company.model';

export interface IBranchOffice {
  id?: number;
  streetAddress?: string;
  postalCode?: string;
  city?: string;
  stateProvince?: string;
  phone?: string;
  externalResponsables?: IExternalResponsable[];
  companies?: ICompany[];
}

export const defaultValue: Readonly<IBranchOffice> = {};
