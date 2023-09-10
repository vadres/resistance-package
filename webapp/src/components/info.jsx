import Vistos from "./vistos";
import { Header, Image, Button, Icon } from 'semantic-ui-react';
import { useContext } from "react";
import { GameContext } from "../context/game-context";

import { Fase } from "../models/variables";
import { next } from "../actions/api";
import { upperFirst } from "../actions/string-util";

const Info = () => { 
    const gameState = useContext(GameContext);   
    const { fase, jogador, setGameState } = gameState;

    return (fase == Fase.INFO_ABERTA)?
        <div className="info">
            <Header as='h2'>{jogador.nome}</Header>
            <Image src={require(`../images/${jogador.personagem}.png`)} circular size='tiny' />
            <Header color='orange' as='h3'>{upperFirst(jogador.personagem)}</Header>      
            <Header as='h4'>{jogador.info}</Header>
            <Vistos atores={jogador.revelados} />
            <br />
            <Button onClick={() => next(gameState, setGameState)} positive icon labelPosition='right'>
                Próximo
                <Icon name='right arrow' />
            </Button>
        </div>: <></>
}

export default Info;
