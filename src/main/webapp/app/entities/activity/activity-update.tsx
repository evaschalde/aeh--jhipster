import * as React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntities as getActivities } from 'app/entities/activity/activity.reducer';
import { getEntity, updateEntity, createEntity, reset } from './activity.reducer';
import { IActivity } from 'app/shared/model/activity.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { keysToValues } from 'app/shared/util/entity-utils';

export interface IActivityUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface IActivityUpdateState {
  isNew: boolean;
  activityId: number;
  subActivityId: number;
}

export class ActivityUpdate extends React.Component<IActivityUpdateProps, IActivityUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      activityId: 0,
      subActivityId: 0,
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getActivities();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { activityEntity } = this.props;
      const entity = {
        ...activityEntity,
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
    this.props.history.push('/entity/activity');
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
    const { activityEntity, activities, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="aehApp.activity.home.createOrEditLabel">
              <Translate contentKey="aehApp.activity.home.createOrEditLabel">Create or edit a Activity</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : activityEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="activity-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="aehApp.activity.name">Name</Translate>
                  </Label>
                  <AvField id="activity-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="description">
                    <Translate contentKey="aehApp.activity.description">Description</Translate>
                  </Label>
                  <AvField id="activity-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label for="activity.id">
                    <Translate contentKey="aehApp.activity.activity">Activity</Translate>
                  </Label>
                  <AvInput id="activity-activity" type="select" className="form-control" name="activity.id" onChange={this.activityUpdate}>
                    <option value="" key="0" />
                    {activities
                      ? activities.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvInput id="activity-activity" type="hidden" name="activity.id" value={this.state.activityId} />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/activity" replace color="info">
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
  activities: storeState.activity.entities,
  activityEntity: storeState.activity.entity,
  loading: storeState.activity.loading,
  updating: storeState.activity.updating
});

const mapDispatchToProps = {
  getActivities,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ActivityUpdate);
