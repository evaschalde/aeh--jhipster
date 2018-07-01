import * as React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './form.reducer';
import { IForm } from 'app/shared/model/form.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFormDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class FormDetail extends React.Component<IFormDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { formEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="aehApp.form.detail.title">Form</Translate> [<b>{formEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="aehApp.form.name">Name</Translate>
              </span>
            </dt>
            <dd>{formEntity.name}</dd>
            <dt>
              <span id="form">
                <Translate contentKey="aehApp.form.form">Form</Translate>
              </span>
            </dt>
            <dd>
              {formEntity.form ? (
                <div>
                  <a onClick={openFile(formEntity.formContentType, formEntity.form)}>
                    <img src={`data:${formEntity.formContentType};base64,${formEntity.form}`} style={{ maxHeight: '30px' }} />
                  </a>
                  <span>
                    {formEntity.formContentType}, {byteSize(formEntity.form)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <Translate contentKey="aehApp.form.event">Event</Translate>
            </dt>
            <dd>{formEntity.event ? formEntity.event.id : ''}</dd>
            <dt>
              <Translate contentKey="aehApp.form.responsable">Responsable</Translate>
            </dt>
            <dd>{formEntity.responsable ? formEntity.responsable.id : ''}</dd>
            <dt>
              <Translate contentKey="aehApp.form.externalResponsable">External Responsable</Translate>
            </dt>
            <dd>{formEntity.externalResponsable ? formEntity.externalResponsable.id : ''}</dd>
            <dt>
              <Translate contentKey="aehApp.form.template">Template</Translate>
            </dt>
            <dd>{formEntity.template ? formEntity.template.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/form" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/form/${formEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ form }: IRootState) => ({
  formEntity: form.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FormDetail);
