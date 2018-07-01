import * as React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './branch-office.reducer';
import { IBranchOffice } from 'app/shared/model/branch-office.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBranchOfficeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class BranchOfficeDetail extends React.Component<IBranchOfficeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { branchOfficeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="aehApp.branchOffice.detail.title">BranchOffice</Translate> [<b>{branchOfficeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="streetAddress">
                <Translate contentKey="aehApp.branchOffice.streetAddress">Street Address</Translate>
              </span>
            </dt>
            <dd>{branchOfficeEntity.streetAddress}</dd>
            <dt>
              <span id="postalCode">
                <Translate contentKey="aehApp.branchOffice.postalCode">Postal Code</Translate>
              </span>
            </dt>
            <dd>{branchOfficeEntity.postalCode}</dd>
            <dt>
              <span id="city">
                <Translate contentKey="aehApp.branchOffice.city">City</Translate>
              </span>
            </dt>
            <dd>{branchOfficeEntity.city}</dd>
            <dt>
              <span id="stateProvince">
                <Translate contentKey="aehApp.branchOffice.stateProvince">State Province</Translate>
              </span>
            </dt>
            <dd>{branchOfficeEntity.stateProvince}</dd>
            <dt>
              <span id="phone">
                <Translate contentKey="aehApp.branchOffice.phone">Phone</Translate>
              </span>
            </dt>
            <dd>{branchOfficeEntity.phone}</dd>
          </dl>
          <Button tag={Link} to="/entity/branch-office" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/branch-office/${branchOfficeEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ branchOffice }: IRootState) => ({
  branchOfficeEntity: branchOffice.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BranchOfficeDetail);
