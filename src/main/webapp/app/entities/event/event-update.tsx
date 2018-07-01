import * as React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IResponsable } from 'app/shared/model/responsable.model';
import { getEntities as getResponsables } from 'app/entities/responsable/responsable.reducer';
import { IBranchOffice } from 'app/shared/model/branch-office.model';
import { getEntities as getBranchOffices } from 'app/entities/branch-office/branch-office.reducer';
import { IEventType } from 'app/shared/model/event-type.model';
import { getEntities as getEventTypes } from 'app/entities/event-type/event-type.reducer';
import { IExternalResponsable } from 'app/shared/model/external-responsable.model';
import { getEntities as getExternalResponsables } from 'app/entities/external-responsable/external-responsable.reducer';
import { getEntity, updateEntity, createEntity, reset } from './event.reducer';
import { IEvent } from 'app/shared/model/event.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { keysToValues } from 'app/shared/util/entity-utils';

export interface IEventUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface IEventUpdateState {
  isNew: boolean;
  responsableId: number;
  branchOfficeId: number;
  eventTypeId: number;
  externalResposableId: number;
}

export class EventUpdate extends React.Component<IEventUpdateProps, IEventUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      responsableId: 0,
      branchOfficeId: 0,
      eventTypeId: 0,
      externalResposableId: 0,
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getResponsables();
    this.props.getBranchOffices();
    this.props.getEventTypes();
    this.props.getExternalResponsables();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { eventEntity } = this.props;
      const entity = {
        ...eventEntity,
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
    this.props.history.push('/entity/event');
  };

  responsableUpdate = element => {
    const id = element.target.value.toString();
    if (id === '') {
      this.setState({
        responsableId: -1
      });
    } else {
      for (const i in this.props.responsables) {
        if (id === this.props.responsables[i].id.toString()) {
          this.setState({
            responsableId: this.props.responsables[i].id
          });
        }
      }
    }
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

  eventTypeUpdate = element => {
    const id = element.target.value.toString();
    if (id === '') {
      this.setState({
        eventTypeId: -1
      });
    } else {
      for (const i in this.props.eventTypes) {
        if (id === this.props.eventTypes[i].id.toString()) {
          this.setState({
            eventTypeId: this.props.eventTypes[i].id
          });
        }
      }
    }
  };

  externalResposableUpdate = element => {
    const id = element.target.value.toString();
    if (id === '') {
      this.setState({
        externalResposableId: -1
      });
    } else {
      for (const i in this.props.externalResponsables) {
        if (id === this.props.externalResponsables[i].id.toString()) {
          this.setState({
            externalResposableId: this.props.externalResponsables[i].id
          });
        }
      }
    }
  };

  render() {
    const isInvalid = false;
    const { eventEntity, responsables, branchOffices, eventTypes, externalResponsables, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="aehApp.event.home.createOrEditLabel">
              <Translate contentKey="aehApp.event.home.createOrEditLabel">Create or edit a Event</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : eventEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="event-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="observationsLabel" for="observations">
                    <Translate contentKey="aehApp.event.observations">Observations</Translate>
                  </Label>
                  <AvField id="event-observations" type="text" name="observations" />
                </AvGroup>
                <AvGroup>
                  <Label id="startDateProgramedLabel" for="startDateProgramed">
                    <Translate contentKey="aehApp.event.startDateProgramed">Start Date Programed</Translate>
                  </Label>
                  <AvField id="event-startDateProgramed" type="date" className="form-control" name="startDateProgramed" />
                </AvGroup>
                <AvGroup>
                  <Label id="finishDateProgramedLabel" for="finishDateProgramed">
                    <Translate contentKey="aehApp.event.finishDateProgramed">Finish Date Programed</Translate>
                  </Label>
                  <AvField id="event-finishDateProgramed" type="date" className="form-control" name="finishDateProgramed" />
                </AvGroup>
                <AvGroup>
                  <Label id="startDateValidityLabel" for="startDateValidity">
                    <Translate contentKey="aehApp.event.startDateValidity">Start Date Validity</Translate>
                  </Label>
                  <AvField id="event-startDateValidity" type="date" className="form-control" name="startDateValidity" />
                </AvGroup>
                <AvGroup>
                  <Label id="finishDateValidityLabel" for="finishDateValidity">
                    <Translate contentKey="aehApp.event.finishDateValidity">Finish Date Validity</Translate>
                  </Label>
                  <AvField id="event-finishDateValidity" type="date" className="form-control" name="finishDateValidity" />
                </AvGroup>
                <AvGroup>
                  <Label id="startDateCompletedLabel" for="startDateCompleted">
                    <Translate contentKey="aehApp.event.startDateCompleted">Start Date Completed</Translate>
                  </Label>
                  <AvField id="event-startDateCompleted" type="date" className="form-control" name="startDateCompleted" />
                </AvGroup>
                <AvGroup>
                  <Label id="finishDateCompletedLabel" for="finishDateCompleted">
                    <Translate contentKey="aehApp.event.finishDateCompleted">Finish Date Completed</Translate>
                  </Label>
                  <AvField id="event-finishDateCompleted" type="date" className="form-control" name="finishDateCompleted" />
                </AvGroup>
                <AvGroup>
                  <Label id="criticallityLabel">
                    <Translate contentKey="aehApp.event.criticallity">Criticallity</Translate>
                  </Label>
                  <AvInput
                    id="event-criticallity"
                    type="select"
                    className="form-control"
                    name="criticallity"
                    value={(!isNew && eventEntity.criticallity) || 'CRITICAL'}
                  >
                    <option value="CRITICAL">CRITICAL</option>
                    <option value="HIGH">HIGH</option>
                    <option value="MEDIUM">MEDIUM</option>
                    <option value="LOW">LOW</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="satteLabel">
                    <Translate contentKey="aehApp.event.satte">Satte</Translate>
                  </Label>
                  <AvInput
                    id="event-satte"
                    type="select"
                    className="form-control"
                    name="satte"
                    value={(!isNew && eventEntity.satte) || 'PROGRAMED'}
                  >
                    <option value="PROGRAMED">PROGRAMED</option>
                    <option value="FINISHED">FINISHED</option>
                    <option value="REPROGRAMED">REPROGRAMED</option>
                    <option value="CANCELLED">CANCELLED</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="responsable.id">
                    <Translate contentKey="aehApp.event.responsable">Responsable</Translate>
                  </Label>
                  <AvInput
                    id="event-responsable"
                    type="select"
                    className="form-control"
                    name="responsable.id"
                    onChange={this.responsableUpdate}
                  >
                    <option value="" key="0" />
                    {responsables
                      ? responsables.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvInput id="event-responsable" type="hidden" name="responsable.id" value={this.state.responsableId} />
                </AvGroup>
                <AvGroup>
                  <Label for="branchOffice.id">
                    <Translate contentKey="aehApp.event.branchOffice">Branch Office</Translate>
                  </Label>
                  <AvInput
                    id="event-branchOffice"
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
                  <AvInput id="event-branchOffice" type="hidden" name="branchOffice.id" value={this.state.branchOfficeId} />
                </AvGroup>
                <AvGroup>
                  <Label for="eventType.id">
                    <Translate contentKey="aehApp.event.eventType">Event Type</Translate>
                  </Label>
                  <AvInput id="event-eventType" type="select" className="form-control" name="eventType.id" onChange={this.eventTypeUpdate}>
                    <option value="" key="0" />
                    {eventTypes
                      ? eventTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvInput id="event-eventType" type="hidden" name="eventType.id" value={this.state.eventTypeId} />
                </AvGroup>
                <AvGroup>
                  <Label for="externalResposable.id">
                    <Translate contentKey="aehApp.event.externalResposable">External Resposable</Translate>
                  </Label>
                  <AvInput
                    id="event-externalResposable"
                    type="select"
                    className="form-control"
                    name="externalResposable.id"
                    onChange={this.externalResposableUpdate}
                  >
                    <option value="" key="0" />
                    {externalResponsables
                      ? externalResponsables.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvInput
                    id="event-externalResposable"
                    type="hidden"
                    name="externalResposable.id"
                    value={this.state.externalResposableId}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/event" replace color="info">
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
  responsables: storeState.responsable.entities,
  branchOffices: storeState.branchOffice.entities,
  eventTypes: storeState.eventType.entities,
  externalResponsables: storeState.externalResponsable.entities,
  eventEntity: storeState.event.entity,
  loading: storeState.event.loading,
  updating: storeState.event.updating
});

const mapDispatchToProps = {
  getResponsables,
  getBranchOffices,
  getEventTypes,
  getExternalResponsables,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EventUpdate);
