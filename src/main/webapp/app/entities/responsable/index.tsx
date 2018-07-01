import * as React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Responsable from './responsable';
import ResponsableDetail from './responsable-detail';
import ResponsableUpdate from './responsable-update';
import ResponsableDeleteDialog from './responsable-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ResponsableUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ResponsableUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ResponsableDetail} />
      <ErrorBoundaryRoute path={match.url} component={Responsable} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ResponsableDeleteDialog} />
  </>
);

export default Routes;
