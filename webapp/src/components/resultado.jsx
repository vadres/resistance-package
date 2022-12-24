import Vistos from "./vistos";
import { Header, Image, Button, Icon, List, Grid } from 'semantic-ui-react';
import { useContext } from "react";
import { GameContext } from "../context/game-context";

import { result, atualizarPlacar } from "../actions/api";

const Resultado = () => { 
    const vencedorResistencia = "RESISTENCIA";
    const vencedorEspioes = "ESPIOES";

    const gameState = useContext(GameContext); 
    const { jogadores, fase, setGameState } = gameState;

    if (fase == 4) {
        return <div className="result" style={{ display: "flex", alignItems: "center", flexDirection: "column" }}>
             <Header as='h2'>Jogo iniciado</Header>
             <Button onClick={() => result(gameState, setGameState)}  color='olive' icon labelPosition='right'>
                Revelar os Bandidim
                <Icon name='right arrow' />
            </Button>
        </div>;
    } else if (fase == 5) {
        return <div className="result">
            <List selection verticalAlign='middle'>
                {
                    jogadores.map(jogador => (
                        <List.Item key={jogador.nome}>
                        <Image avatar src={require(`../images/${jogador['personagem']}.png`)} />
                        <List.Content>
                            <List.Header as='a'>{jogador.nome}</List.Header>
                        </List.Content>
                        </List.Item>
                    ))
                }
            </List>    
            <Grid columns={2}>
                <Grid.Column>
                    <Button onClick={() => atualizarPlacar(vencedorResistencia, gameState, setGameState)}  color='facebook'>
                        Resistência
                    </Button>
                </Grid.Column>
                <Grid.Column>
                    <Button onClick={() => atualizarPlacar(vencedorEspioes, gameState, setGameState)} color='google plus'>
                        Espiões
                    </Button>
                </Grid.Column>

            </Grid>
        </div>;
    } else {
        return <></>;
    }
}

export default Resultado;
