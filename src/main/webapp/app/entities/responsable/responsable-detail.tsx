import * as React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './responsable.reducer';
import { IResponsable } from 'app/shared/model/responsable.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IResponsableDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class ResponsableDetail extends React.Component<IResponsableDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { responsableEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="aehApp.responsable.detail.title">Responsable</Translate> [<b>{responsableEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="aehApp.responsable.name">Name</Translate>
              </span>
            </dt>
            <dd>{responsableEntity.name}</dd>
            <dt>
              <span id="lastName">
                <Translate contentKey="aehApp.responsable.lastName">Last Name</Translate>
              </span>
            </dt>
            <dd>{responsableEntity.lastName}</dd>
            <dt>
              <span id="phone">
                <Translate contentKey="aehApp.responsable.phone">Phone</Translate>
              </span>
            </dt>
            <dd>{responsableEntity.phone}</dd>
            <dt>
              <span id="mail">
                <Translate contentKey="aehApp.responsable.mail">Mail</Translate>
              </span>
            </dt>
            <dd>{responsableEntity.mail}</dd>
          </dl>
          <Button tag={Link} to="/entity/responsable" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/responsable/${responsableEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ responsable }: IRootState) => ({
  responsableEntity: responsable.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ResponsableDetail);
