import * as React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './external-responsable.reducer';
import { IExternalResponsable } from 'app/shared/model/external-responsable.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IExternalResponsableDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class ExternalResponsableDetail extends React.Component<IExternalResponsableDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { externalResponsableEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="aehApp.externalResponsable.detail.title">ExternalResponsable</Translate> [<b>
              {externalResponsableEntity.id}
            </b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="aehApp.externalResponsable.name">Name</Translate>
              </span>
            </dt>
            <dd>{externalResponsableEntity.name}</dd>
            <dt>
              <span id="lastName">
                <Translate contentKey="aehApp.externalResponsable.lastName">Last Name</Translate>
              </span>
            </dt>
            <dd>{externalResponsableEntity.lastName}</dd>
            <dt>
              <span id="phone">
                <Translate contentKey="aehApp.externalResponsable.phone">Phone</Translate>
              </span>
            </dt>
            <dd>{externalResponsableEntity.phone}</dd>
            <dt>
              <Translate contentKey="aehApp.externalResponsable.branchOffice">Branch Office</Translate>
            </dt>
            <dd>{externalResponsableEntity.branchOffice ? externalResponsableEntity.branchOffice.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/external-responsable" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/external-responsable/${externalResponsableEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ externalResponsable }: IRootState) => ({
  externalResponsableEntity: externalResponsable.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ExternalResponsableDetail);
