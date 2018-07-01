import * as React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ExternalResponsable from './external-responsable';
import ExternalResponsableDetail from './external-responsable-detail';
import ExternalResponsableUpdate from './external-responsable-update';
import ExternalResponsableDeleteDialog from './external-responsable-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ExternalResponsableUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ExternalResponsableUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ExternalResponsableDetail} />
      <ErrorBoundaryRoute path={match.url} component={ExternalResponsable} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ExternalResponsableDeleteDialog} />
  </>
);

export default Routes;
