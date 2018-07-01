import { IBranchOffice } from './branch-office.model';
import { IActivity } from './activity.model';

export interface ICompany {
  id?: number;
  name?: string;
  description?: string;
  logoContentType?: string;
  logo?: any;
  branchOffice?: IBranchOffice;
  activity?: IActivity;
}

export const defaultValue: Readonly<ICompany> = {};
