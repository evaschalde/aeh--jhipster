export interface ITemplate {
  id?: number;
  name?: string;
  formContentType?: string;
  form?: any;
}

export const defaultValue: Readonly<ITemplate> = {};
