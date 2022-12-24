import Vistos from "./vistos";
import { Header, Image, Button, Icon } from 'semantic-ui-react';
import { useContext } from "react";
import { GameContext } from "../context/game-context";

import { verInformacoes } from "../actions/api";

const Top = () => {
    const gameState = useContext(GameContext);
    const { jogador, fase, jogadores, setGameState } = gameState;
 
    return (fase == 2)?
        <div className="info">
            <Header as='h2'>{jogador.nome}</Header>
            <Button onClick={() => verInformacoes(gameState, setGameState)} color='twitter' icon labelPosition='right'>
                Ver função
                <Icon name='right arrow' />
            </Button>
        </div>: <></>
};


export default Top;
