export interface IResponsable {
  id?: number;
  name?: string;
  lastName?: string;
  phone?: string;
  mail?: string;
}

export const defaultValue: Readonly<IResponsable> = {};
