export const enum Criticallity {
  CRITICAL = 'CRITICAL',
  HIGH = 'HIGH',
  MEDIUM = 'MEDIUM',
  LOW = 'LOW'
}

export interface IEventType {
  id?: number;
  name?: string;
  description?: string;
  criticallity?: Criticallity;
}

export const defaultValue: Readonly<IEventType> = {};
