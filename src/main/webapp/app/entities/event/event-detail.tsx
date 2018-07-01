import * as React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './event.reducer';
import { IEvent } from 'app/shared/model/event.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEventDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class EventDetail extends React.Component<IEventDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { eventEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="aehApp.event.detail.title">Event</Translate> [<b>{eventEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="observations">
                <Translate contentKey="aehApp.event.observations">Observations</Translate>
              </span>
            </dt>
            <dd>{eventEntity.observations}</dd>
            <dt>
              <span id="startDateProgramed">
                <Translate contentKey="aehApp.event.startDateProgramed">Start Date Programed</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={eventEntity.startDateProgramed} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="finishDateProgramed">
                <Translate contentKey="aehApp.event.finishDateProgramed">Finish Date Programed</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={eventEntity.finishDateProgramed} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="startDateValidity">
                <Translate contentKey="aehApp.event.startDateValidity">Start Date Validity</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={eventEntity.startDateValidity} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="finishDateValidity">
                <Translate contentKey="aehApp.event.finishDateValidity">Finish Date Validity</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={eventEntity.finishDateValidity} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="startDateCompleted">
                <Translate contentKey="aehApp.event.startDateCompleted">Start Date Completed</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={eventEntity.startDateCompleted} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="finishDateCompleted">
                <Translate contentKey="aehApp.event.finishDateCompleted">Finish Date Completed</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={eventEntity.finishDateCompleted} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="criticallity">
                <Translate contentKey="aehApp.event.criticallity">Criticallity</Translate>
              </span>
            </dt>
            <dd>{eventEntity.criticallity}</dd>
            <dt>
              <span id="satte">
                <Translate contentKey="aehApp.event.satte">Satte</Translate>
              </span>
            </dt>
            <dd>{eventEntity.satte}</dd>
            <dt>
              <Translate contentKey="aehApp.event.responsable">Responsable</Translate>
            </dt>
            <dd>{eventEntity.responsable ? eventEntity.responsable.id : ''}</dd>
            <dt>
              <Translate contentKey="aehApp.event.branchOffice">Branch Office</Translate>
            </dt>
            <dd>{eventEntity.branchOffice ? eventEntity.branchOffice.id : ''}</dd>
            <dt>
              <Translate contentKey="aehApp.event.eventType">Event Type</Translate>
            </dt>
            <dd>{eventEntity.eventType ? eventEntity.eventType.id : ''}</dd>
            <dt>
              <Translate contentKey="aehApp.event.externalResposable">External Resposable</Translate>
            </dt>
            <dd>{eventEntity.externalResposable ? eventEntity.externalResposable.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/event" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/event/${eventEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ event }: IRootState) => ({
  eventEntity: event.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EventDetail);
