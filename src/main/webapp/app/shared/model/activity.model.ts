import { ICompany } from './company.model';
import { IActivity } from './activity.model';

export interface IActivity {
  id?: number;
  name?: string;
  description?: string;
  activities?: ICompany[];
  activity?: IActivity;
  subActivities?: IActivity[];
}

export const defaultValue: Readonly<IActivity> = {};
