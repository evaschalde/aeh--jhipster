import * as React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BranchOffice from './branch-office';
import BranchOfficeDetail from './branch-office-detail';
import BranchOfficeUpdate from './branch-office-update';
import BranchOfficeDeleteDialog from './branch-office-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BranchOfficeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BranchOfficeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BranchOfficeDetail} />
      <ErrorBoundaryRoute path={match.url} component={BranchOffice} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={BranchOfficeDeleteDialog} />
  </>
);

export default Routes;
