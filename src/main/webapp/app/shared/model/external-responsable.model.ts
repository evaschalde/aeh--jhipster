import { IBranchOffice } from './branch-office.model';

export interface IExternalResponsable {
  id?: number;
  name?: string;
  lastName?: string;
  phone?: string;
  branchOffice?: IBranchOffice;
}

export const defaultValue: Readonly<IExternalResponsable> = {};
