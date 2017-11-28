// Copyright (c) YugaByte, Inc.

import React, { Component } from 'react';
import { Row, Col } from 'react-bootstrap';
import {browserHistory} from 'react-router';
import { YBButton } from 'components/common/forms/fields';
import { UniverseTableContainer } from 'components/universes';
import { HighlightedStatsPanelContainer } from '../../panels';
import './ListUniverse.css';

export default class ListUniverse extends Component {
  constructor(props) {
    super(props);
    this.createNewUniverse = this.createNewUniverse.bind(this);
  }

  createNewUniverse() {
    browserHistory.push("/universes/create");
  }

  render() {
    return (
      <div id="page-wrapper">
        <Row className="header-row">
          <Col xs={6}>
            <h2 className="content-title">Universes</h2>
            <HighlightedStatsPanelContainer />
          </Col>
          <Col xs={6} className="universe-table-header-action">
            <YBButton btnClass="universe-button btn btn-lg btn-orange"
                           btnText="Create Universe" btnIcon="fa fa-pencil"
                           onClick={this.createNewUniverse} />
          </Col>
        </Row>
        <UniverseTableContainer />
      </div>
    );
  }
}
