import { useContext, useState, useRef } from 'react';
import 'semantic-ui-css/semantic.min.css';
import { Divider, Form, Container, Button, Grid } from 'semantic-ui-react';

import { GameContext } from '../../context/game-context';
import { iniciarJogo, novoJogador } from '../../actions/api';
import PlayerLabel from '../../components/player-label';
import { gerenciarJogadores } from '../../actions/routes';

function SelectPlayers() {
    const [selected, setSelected] = useState([]);
    const [ disableIniciar, setDisableIniciar ] = useState(true);
    const tipoJogoRef = useRef(1);

    const gameState = useContext(GameContext);   
    const { jogadores, setGameState } = gameState;

    const handleGerenciarJogadores = async (event) => {
        const state = gerenciarJogadores();

        //const state = await novoJogador(jogadorNovoRef.current.value);

        setGameState({
            ...gameState,
            ...state           
        })
    }

    const handleIniciarJogo = async () => {
        const { characters } = gameState
        const infoJogo = await iniciarJogo(selected, characters, tipoJogoRef.current.value);

        setGameState({
            ...gameState,
            ...infoJogo            
        }); 
    }

    const handleClickLabel = (player) => {
        const index = selected.indexOf(player);

        if (index > -1) {
            selected.splice(index, 1);
            setSelected(selected);
        } else {
            selected.push(player);
            setSelected(selected);
        }

        setDisableIniciar(selected.length < 5);
    }
                        
    return (
        <Container text style={{ display: "flex", flexDirection: "column"}}>
            <Grid columns={2}>
            {
                jogadores.map(jogador => (
                    <Grid.Column key={jogador.id}>
                        <PlayerLabel key={jogador.id} jogador={jogador} onClick={() => handleClickLabel(jogador)} />
                    </Grid.Column>
                ))
            }
            </Grid>

            <Divider hidden />

            <Form>      
                <Button disabled={disableIniciar} onClick={() => handleIniciarJogo()} positive>Iniciar Partida</Button>            
                <Button onClick={() => handleGerenciarJogadores()} color='teal'>Gerenciar jogadores</Button>
            </Form>

            {/* <Form style={{ marginTop: 25 }}>
                <Divider />
                <Form.Field>
                    <label>Adicionar Jogador</label>
                    <input ref={jogadorNovoRef} placeholder='Nome' />
                </Form.Field>
                <Button onClick={() => handleNovoJogador()} positive>Salvar</Button>
            </Form> */}

        </Container>
    );
}

export default SelectPlayers;
