import * as React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './template.reducer';
import { ITemplate } from 'app/shared/model/template.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITemplateDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class TemplateDetail extends React.Component<ITemplateDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { templateEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="aehApp.template.detail.title">Template</Translate> [<b>{templateEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="aehApp.template.name">Name</Translate>
              </span>
            </dt>
            <dd>{templateEntity.name}</dd>
            <dt>
              <span id="form">
                <Translate contentKey="aehApp.template.form">Form</Translate>
              </span>
            </dt>
            <dd>
              {templateEntity.form ? (
                <div>
                  <a onClick={openFile(templateEntity.formContentType, templateEntity.form)}>
                    <img src={`data:${templateEntity.formContentType};base64,${templateEntity.form}`} style={{ maxHeight: '30px' }} />
                  </a>
                  <span>
                    {templateEntity.formContentType}, {byteSize(templateEntity.form)}
                  </span>
                </div>
              ) : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/template" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/template/${templateEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ template }: IRootState) => ({
  templateEntity: template.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TemplateDetail);
