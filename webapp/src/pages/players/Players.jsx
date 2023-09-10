import 'semantic-ui-css/semantic.min.css';
import { useContext, useRef } from 'react';
import { List, Button, Grid, Container, Image, Form, Divider } from 'semantic-ui-react';
import { GameContext } from '../../context/game-context';
import { removerJogador, novoJogador } from '../../actions/api';

function Players() {
  const gameState = useContext(GameContext);  
  const { jogadores, setGameState } = gameState;
  const jogadorNovoRef = useRef("");

  const handleAdicionarJogador = async () => {
    const jogadores = await novoJogador(jogadorNovoRef.current.value);
    setGameState({
      ...gameState,
      jogadores
    });

  }

  const handleRemoverJogador = async (id) => {
    const jogadores = await removerJogador(id);
    setGameState({
      ...gameState,
      jogadores
    });
  }

  return <Container>
    <Grid>
    <Grid.Column computer={16} mobile={16}>
    <List divided verticalAlign='middle'>
       {
        jogadores.map(jogador => (
          
            <List.Item key={jogador.id}>
              <Image avatar  src={require(`../../images/user.png`)} />
              <List.Content as='a'>{ jogador.nome }</List.Content>
              <List.Content floated='right'>
                <Button onClick={() => handleRemoverJogador(jogador.id)}>Remover</Button>
              </List.Content>
            </List.Item>
        ))
      }
     </List>

     <Divider />
     
      <Form style={{ marginTop: 25 }}>
        <Form.Field>
            <label>Adicionar Jogador</label>
            <input ref={jogadorNovoRef} placeholder='Nome' />
        </Form.Field>
        <Button onClick={() => handleAdicionarJogador()} positive>Salvar</Button>
      </Form>
    </Grid.Column>
    </Grid>
  </Container>
}

export default Players;
