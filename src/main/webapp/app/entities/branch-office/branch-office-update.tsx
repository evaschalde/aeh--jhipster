import * as React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './branch-office.reducer';
import { IBranchOffice } from 'app/shared/model/branch-office.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { keysToValues } from 'app/shared/util/entity-utils';

export interface IBranchOfficeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface IBranchOfficeUpdateState {
  isNew: boolean;
}

export class BranchOfficeUpdate extends React.Component<IBranchOfficeUpdateProps, IBranchOfficeUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { branchOfficeEntity } = this.props;
      const entity = {
        ...branchOfficeEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      this.handleClose();
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/branch-office');
  };

  render() {
    const isInvalid = false;
    const { branchOfficeEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="aehApp.branchOffice.home.createOrEditLabel">
              <Translate contentKey="aehApp.branchOffice.home.createOrEditLabel">Create or edit a BranchOffice</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : branchOfficeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="branch-office-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="streetAddressLabel" for="streetAddress">
                    <Translate contentKey="aehApp.branchOffice.streetAddress">Street Address</Translate>
                  </Label>
                  <AvField id="branch-office-streetAddress" type="text" name="streetAddress" />
                </AvGroup>
                <AvGroup>
                  <Label id="postalCodeLabel" for="postalCode">
                    <Translate contentKey="aehApp.branchOffice.postalCode">Postal Code</Translate>
                  </Label>
                  <AvField id="branch-office-postalCode" type="text" name="postalCode" />
                </AvGroup>
                <AvGroup>
                  <Label id="cityLabel" for="city">
                    <Translate contentKey="aehApp.branchOffice.city">City</Translate>
                  </Label>
                  <AvField id="branch-office-city" type="text" name="city" />
                </AvGroup>
                <AvGroup>
                  <Label id="stateProvinceLabel" for="stateProvince">
                    <Translate contentKey="aehApp.branchOffice.stateProvince">State Province</Translate>
                  </Label>
                  <AvField id="branch-office-stateProvince" type="text" name="stateProvince" />
                </AvGroup>
                <AvGroup>
                  <Label id="phoneLabel" for="phone">
                    <Translate contentKey="aehApp.branchOffice.phone">Phone</Translate>
                  </Label>
                  <AvField id="branch-office-phone" type="text" name="phone" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/branch-office" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={isInvalid || updating}>
                  <FontAwesomeIcon icon="save" />&nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  branchOfficeEntity: storeState.branchOffice.entity,
  loading: storeState.branchOffice.loading,
  updating: storeState.branchOffice.updating
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BranchOfficeUpdate);
