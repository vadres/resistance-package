import 'semantic-ui-css/semantic.min.css';
import { Container } from 'semantic-ui-react';

import Info from '../../components/info';
import Top from '../../components/top';
import Resultado from '../../components/resultado';
import Header from '../../components/header';

function SeeFunctions() {
  return (
    <>
      <Header />
      <Container text>
        <Top />
        <Info />
        <Resultado />
      </Container>
    </>
  );
}

export default SeeFunctions;
