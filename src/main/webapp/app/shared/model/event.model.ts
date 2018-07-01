import { Moment } from 'moment';
import { IResponsable } from './responsable.model';
import { IBranchOffice } from './branch-office.model';
import { IEventType } from './event-type.model';
import { IExternalResponsable } from './external-responsable.model';

export const enum Criticallity {
  CRITICAL = 'CRITICAL',
  HIGH = 'HIGH',
  MEDIUM = 'MEDIUM',
  LOW = 'LOW'
}

export const enum State {
  PROGRAMED = 'PROGRAMED',
  FINISHED = 'FINISHED',
  REPROGRAMED = 'REPROGRAMED',
  CANCELLED = 'CANCELLED'
}

export interface IEvent {
  id?: number;
  observations?: string;
  startDateProgramed?: Moment;
  finishDateProgramed?: Moment;
  startDateValidity?: Moment;
  finishDateValidity?: Moment;
  startDateCompleted?: Moment;
  finishDateCompleted?: Moment;
  criticallity?: Criticallity;
  satte?: State;
  responsable?: IResponsable;
  branchOffice?: IBranchOffice;
  eventType?: IEventType;
  externalResposable?: IExternalResponsable;
}

export const defaultValue: Readonly<IEvent> = {};
