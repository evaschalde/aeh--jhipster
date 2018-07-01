import * as React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBranchOffice } from 'app/shared/model/branch-office.model';
import { getEntities as getBranchOffices } from 'app/entities/branch-office/branch-office.reducer';
import { getEntity, updateEntity, createEntity, reset } from './external-responsable.reducer';
import { IExternalResponsable } from 'app/shared/model/external-responsable.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { keysToValues } from 'app/shared/util/entity-utils';

export interface IExternalResponsableUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface IExternalResponsableUpdateState {
  isNew: boolean;
  branchOfficeId: number;
}

export class ExternalResponsableUpdate extends React.Component<IExternalResponsableUpdateProps, IExternalResponsableUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      branchOfficeId: 0,
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getBranchOffices();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { externalResponsableEntity } = this.props;
      const entity = {
        ...externalResponsableEntity,
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
    this.props.history.push('/entity/external-responsable');
  };

  branchOfficeUpdate = element => {
    const id = element.target.value.toString();
    if (id === '') {
      this.setState({
        branchOfficeId: -1
      });
    } else {
      for (const i in this.props.branchOffices) {
        if (id === this.props.branchOffices[i].id.toString()) {
          this.setState({
            branchOfficeId: this.props.branchOffices[i].id
          });
        }
      }
    }
  };

  render() {
    const isInvalid = false;
    const { externalResponsableEntity, branchOffices, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="aehApp.externalResponsable.home.createOrEditLabel">
              <Translate contentKey="aehApp.externalResponsable.home.createOrEditLabel">Create or edit a ExternalResponsable</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : externalResponsableEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="external-responsable-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="aehApp.externalResponsable.name">Name</Translate>
                  </Label>
                  <AvField id="external-responsable-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="lastNameLabel" for="lastName">
                    <Translate contentKey="aehApp.externalResponsable.lastName">Last Name</Translate>
                  </Label>
                  <AvField id="external-responsable-lastName" type="text" name="lastName" />
                </AvGroup>
                <AvGroup>
                  <Label id="phoneLabel" for="phone">
                    <Translate contentKey="aehApp.externalResponsable.phone">Phone</Translate>
                  </Label>
                  <AvField id="external-responsable-phone" type="text" name="phone" />
                </AvGroup>
                <AvGroup>
                  <Label for="branchOffice.id">
                    <Translate contentKey="aehApp.externalResponsable.branchOffice">Branch Office</Translate>
                  </Label>
                  <AvInput
                    id="external-responsable-branchOffice"
                    type="select"
                    className="form-control"
                    name="branchOffice.id"
                    onChange={this.branchOfficeUpdate}
                  >
                    <option value="" key="0" />
                    {branchOffices
                      ? branchOffices.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvInput id="external-responsable-branchOffice" type="hidden" name="branchOffice.id" value={this.state.branchOfficeId} />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/external-responsable" replace color="info">
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
  branchOffices: storeState.branchOffice.entities,
  externalResponsableEntity: storeState.externalResponsable.entity,
  loading: storeState.externalResponsable.loading,
  updating: storeState.externalResponsable.updating
});

const mapDispatchToProps = {
  getBranchOffices,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ExternalResponsableUpdate);
