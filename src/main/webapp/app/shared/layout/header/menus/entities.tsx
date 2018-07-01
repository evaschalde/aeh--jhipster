import * as React from 'react';
import { DropdownItem } from 'reactstrap';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/company">
      <FontAwesomeIcon icon="asterisk" />&nbsp; Company
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/branch-office">
      <FontAwesomeIcon icon="asterisk" />&nbsp; Branch Office
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/activity">
      <FontAwesomeIcon icon="asterisk" />&nbsp; Activity
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/responsable">
      <FontAwesomeIcon icon="asterisk" />&nbsp; Responsable
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/event">
      <FontAwesomeIcon icon="asterisk" />&nbsp; Event
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/event-type">
      <FontAwesomeIcon icon="asterisk" />&nbsp; Event Type
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/external-responsable">
      <FontAwesomeIcon icon="asterisk" />&nbsp; External Responsable
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/form">
      <FontAwesomeIcon icon="asterisk" />&nbsp; Form
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/template">
      <FontAwesomeIcon icon="asterisk" />&nbsp; Template
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
