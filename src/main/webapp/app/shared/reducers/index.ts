import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import company, {
  CompanyState
} from 'app/entities/company/company.reducer';
// prettier-ignore
import branchOffice, {
  BranchOfficeState
} from 'app/entities/branch-office/branch-office.reducer';
// prettier-ignore
import activity, {
  ActivityState
} from 'app/entities/activity/activity.reducer';
// prettier-ignore
import responsable, {
  ResponsableState
} from 'app/entities/responsable/responsable.reducer';
// prettier-ignore
import event, {
  EventState
} from 'app/entities/event/event.reducer';
// prettier-ignore
import eventType, {
  EventTypeState
} from 'app/entities/event-type/event-type.reducer';
// prettier-ignore
import externalResponsable, {
  ExternalResponsableState
} from 'app/entities/external-responsable/external-responsable.reducer';
// prettier-ignore
import form, {
  FormState
} from 'app/entities/form/form.reducer';
// prettier-ignore
import template, {
  TemplateState
} from 'app/entities/template/template.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly company: CompanyState;
  readonly branchOffice: BranchOfficeState;
  readonly activity: ActivityState;
  readonly responsable: ResponsableState;
  readonly event: EventState;
  readonly eventType: EventTypeState;
  readonly externalResponsable: ExternalResponsableState;
  readonly form: FormState;
  readonly template: TemplateState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  company,
  branchOffice,
  activity,
  responsable,
  event,
  eventType,
  externalResponsable,
  form,
  template,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
