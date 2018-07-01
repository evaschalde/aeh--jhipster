import * as React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEvent } from 'app/shared/model/event.model';
import { getEntities as getEvents } from 'app/entities/event/event.reducer';
import { IResponsable } from 'app/shared/model/responsable.model';
import { getEntities as getResponsables } from 'app/entities/responsable/responsable.reducer';
import { IExternalResponsable } from 'app/shared/model/external-responsable.model';
import { getEntities as getExternalResponsables } from 'app/entities/external-responsable/external-responsable.reducer';
import { ITemplate } from 'app/shared/model/template.model';
import { getEntities as getTemplates } from 'app/entities/template/template.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './form.reducer';
import { IForm } from 'app/shared/model/form.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { keysToValues } from 'app/shared/util/entity-utils';

export interface IFormUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface IFormUpdateState {
  isNew: boolean;
  eventId: number;
  responsableId: number;
  externalResponsableId: number;
  templateId: number;
}

export class FormUpdate extends React.Component<IFormUpdateProps, IFormUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      eventId: 0,
      responsableId: 0,
      externalResponsableId: 0,
      templateId: 0,
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getEvents();
    this.props.getResponsables();
    this.props.getExternalResponsables();
    this.props.getTemplates();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { formEntity } = this.props;
      const entity = {
        ...formEntity,
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
    this.props.history.push('/entity/form');
  };

  eventUpdate = element => {
    const id = element.target.value.toString();
    if (id === '') {
      this.setState({
        eventId: -1
      });
    } else {
      for (const i in this.props.events) {
        if (id === this.props.events[i].id.toString()) {
          this.setState({
            eventId: this.props.events[i].id
          });
        }
      }
    }
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

  externalResponsableUpdate = element => {
    const id = element.target.value.toString();
    if (id === '') {
      this.setState({
        externalResponsableId: -1
      });
    } else {
      for (const i in this.props.externalResponsables) {
        if (id === this.props.externalResponsables[i].id.toString()) {
          this.setState({
            externalResponsableId: this.props.externalResponsables[i].id
          });
        }
      }
    }
  };

  templateUpdate = element => {
    const id = element.target.value.toString();
    if (id === '') {
      this.setState({
        templateId: -1
      });
    } else {
      for (const i in this.props.templates) {
        if (id === this.props.templates[i].id.toString()) {
          this.setState({
            templateId: this.props.templates[i].id
          });
        }
      }
    }
  };

  render() {
    const isInvalid = false;
    const { formEntity, events, responsables, externalResponsables, templates, loading, updating } = this.props;
    const { isNew } = this.state;

    const { form, formContentType } = formEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="aehApp.form.home.createOrEditLabel">
              <Translate contentKey="aehApp.form.home.createOrEditLabel">Create or edit a Form</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : formEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="form-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="aehApp.form.name">Name</Translate>
                  </Label>
                  <AvField id="form-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="formLabel" for="form">
                      <Translate contentKey="aehApp.form.form">Form</Translate>
                    </Label>
                    <br />
                    {form ? (
                      <div>
                        <a onClick={openFile(formContentType, form)}>
                          <img src={`data:${formContentType};base64,${form}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {formContentType}, {byteSize(form)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('form')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_form" type="file" onChange={this.onBlobChange(true, 'form')} accept="image/*" />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label for="event.id">
                    <Translate contentKey="aehApp.form.event">Event</Translate>
                  </Label>
                  <AvInput id="form-event" type="select" className="form-control" name="event.id" onChange={this.eventUpdate}>
                    <option value="" key="0" />
                    {events
                      ? events.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvInput id="form-event" type="hidden" name="event.id" value={this.state.eventId} />
                </AvGroup>
                <AvGroup>
                  <Label for="responsable.id">
                    <Translate contentKey="aehApp.form.responsable">Responsable</Translate>
                  </Label>
                  <AvInput
                    id="form-responsable"
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
                  <AvInput id="form-responsable" type="hidden" name="responsable.id" value={this.state.responsableId} />
                </AvGroup>
                <AvGroup>
                  <Label for="externalResponsable.id">
                    <Translate contentKey="aehApp.form.externalResponsable">External Responsable</Translate>
                  </Label>
                  <AvInput
                    id="form-externalResponsable"
                    type="select"
                    className="form-control"
                    name="externalResponsable.id"
                    onChange={this.externalResponsableUpdate}
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
                    id="form-externalResponsable"
                    type="hidden"
                    name="externalResponsable.id"
                    value={this.state.externalResponsableId}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="template.id">
                    <Translate contentKey="aehApp.form.template">Template</Translate>
                  </Label>
                  <AvInput id="form-template" type="select" className="form-control" name="template.id" onChange={this.templateUpdate}>
                    <option value="" key="0" />
                    {templates
                      ? templates.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvInput id="form-template" type="hidden" name="template.id" value={this.state.templateId} />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/form" replace color="info">
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
  events: storeState.event.entities,
  responsables: storeState.responsable.entities,
  externalResponsables: storeState.externalResponsable.entities,
  templates: storeState.template.entities,
  formEntity: storeState.form.entity,
  loading: storeState.form.loading,
  updating: storeState.form.updating
});

const mapDispatchToProps = {
  getEvents,
  getResponsables,
  getExternalResponsables,
  getTemplates,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FormUpdate);
