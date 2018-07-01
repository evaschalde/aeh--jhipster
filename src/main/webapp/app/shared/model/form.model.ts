import { IEvent } from './event.model';
import { IResponsable } from './responsable.model';
import { IExternalResponsable } from './external-responsable.model';
import { ITemplate } from './template.model';

export interface IForm {
  id?: number;
  name?: string;
  formContentType?: string;
  form?: any;
  event?: IEvent;
  responsable?: IResponsable;
  externalResponsable?: IExternalResponsable;
  template?: ITemplate;
}

export const defaultValue: Readonly<IForm> = {};
