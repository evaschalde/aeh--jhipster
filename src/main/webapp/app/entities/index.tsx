import * as React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Company from './company';
import BranchOffice from './branch-office';
import Activity from './activity';
import Responsable from './responsable';
import Event from './event';
import EventType from './event-type';
import ExternalResponsable from './external-responsable';
import Form from './form';
import Template from './template';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/company`} component={Company} />
      <ErrorBoundaryRoute path={`${match.url}/branch-office`} component={BranchOffice} />
      <ErrorBoundaryRoute path={`${match.url}/activity`} component={Activity} />
      <ErrorBoundaryRoute path={`${match.url}/responsable`} component={Responsable} />
      <ErrorBoundaryRoute path={`${match.url}/event`} component={Event} />
      <ErrorBoundaryRoute path={`${match.url}/event-type`} component={EventType} />
      <ErrorBoundaryRoute path={`${match.url}/external-responsable`} component={ExternalResponsable} />
      <ErrorBoundaryRoute path={`${match.url}/form`} component={Form} />
      <ErrorBoundaryRoute path={`${match.url}/template`} component={Template} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
