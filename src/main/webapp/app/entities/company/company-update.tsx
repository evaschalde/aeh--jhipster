import * as React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBranchOffice } from 'app/shared/model/branch-office.model';
import { getEntities as getBranchOffices } from 'app/entities/branch-office/branch-office.reducer';
import { IActivity } from 'app/shared/model/activity.model';
import { getEntities as getActivities } from 'app/entities/activity/activity.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './company.reducer';
import { ICompany } from 'app/shared/model/company.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { keysToValues } from 'app/shared/util/entity-utils';

export interface ICompanyUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface ICompanyUpdateState {
  isNew: boolean;
  branchOfficeId: number;
  activityId: number;
}

export class CompanyUpdate extends React.Component<ICompanyUpdateProps, ICompanyUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      branchOfficeId: 0,
      activityId: 0,
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
    this.props.getActivities();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { companyEntity } = this.props;
      const entity = {
        ...companyEntity,
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
    this.props.history.push('/entity/company');
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

  activityUpdate = element => {
    const id = element.target.value.toString();
    if (id === '') {
      this.setState({
        activityId: -1
      });
    } else {
      for (const i in this.props.activities) {
        if (id === this.props.activities[i].id.toString()) {
          this.setState({
            activityId: this.props.activities[i].id
          });
        }
      }
    }
  };

  render() {
    const isInvalid = false;
    const { companyEntity, branchOffices, activities, loading, updating } = this.props;
    const { isNew } = this.state;

    const { logo, logoContentType } = companyEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="aehApp.company.home.createOrEditLabel">
              <Translate contentKey="aehApp.company.home.createOrEditLabel">Create or edit a Company</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : companyEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="company-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="aehApp.company.name">Name</Translate>
                  </Label>
                  <AvField id="company-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="description">
                    <Translate contentKey="aehApp.company.description">Description</Translate>
                  </Label>
                  <AvField id="company-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="logoLabel" for="logo">
                      <Translate contentKey="aehApp.company.logo">Logo</Translate>
                    </Label>
                    <br />
                    {logo ? (
                      <div>
                        <a onClick={openFile(logoContentType, logo)}>
                          <img src={`data:${logoContentType};base64,${logo}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {logoContentType}, {byteSize(logo)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('logo')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_logo" type="file" onChange={this.onBlobChange(true, 'logo')} accept="image/*" />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label for="branchOffice.id">
                    <Translate contentKey="aehApp.company.branchOffice">Branch Office</Translate>
                  </Label>
                  <AvInput
                    id="company-branchOffice"
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
                  <AvInput id="company-branchOffice" type="hidden" name="branchOffice.id" value={this.state.branchOfficeId} />
                </AvGroup>
                <AvGroup>
                  <Label for="activity.id">
                    <Translate contentKey="aehApp.company.activity">Activity</Translate>
                  </Label>
                  <AvInput id="company-activity" type="select" className="form-control" name="activity.id" onChange={this.activityUpdate}>
                    <option value="" key="0" />
                    {activities
                      ? activities.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvInput id="company-activity" type="hidden" name="activity.id" value={this.state.activityId} />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/company" replace color="info">
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
  activities: storeState.activity.entities,
  companyEntity: storeState.company.entity,
  loading: storeState.company.loading,
  updating: storeState.company.updating
});

const mapDispatchToProps = {
  getBranchOffices,
  getActivities,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompanyUpdate);
