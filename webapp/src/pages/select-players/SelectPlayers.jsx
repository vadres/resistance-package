import { useContext, useState, useRef } from 'react';
import 'semantic-ui-css/semantic.min.css';
import { Divider, Form, Segment, Container, Button, Select, Grid, Ref } from 'semantic-ui-react';

import { GameContext } from '../../context/game-context';
import { iniciarJogo, novoJogador } from '../../actions/api';
import PlayerLabel from '../../components/player-label';


function SelectPlayers() {
    const tipos = [
        { key: '1', text: 'Simples', value: '1' },
        { key: '2', text: 'Com comandante', value: '2' },
        { key: '3', text: 'Com falso comandante', value: '3' },
        { key: '4', text: 'Com falso comandante e assassino', value: '4' },
        { key: '5', text: 'Com falso comandante e agente invisível', value: '5' }
    ]

    const [selected, setSelected] = useState([]);
    const jogadorNovoRef = useRef("");
    const tipoJogoRef = useRef(1);

    const gameState = useContext(GameContext);   
    const { jogadores, setGameState } = gameState;

    const handleNovoJogador = async (event) => {
        const state = await novoJogador(jogadorNovoRef.current.value);

        setGameState({
            ...gameState,
            ...state           
        })
    }

    const handleIniciarJogo = async () => {
        const infoJogo = await iniciarJogo(selected, tipoJogoRef.current.value);

        setGameState({
            ...gameState,
            ...infoJogo            
        }); 
    }

    const handleClickLabel = (player) => {
        const index = selected.indexOf(player);

        if (index > -1) {
            selected.slice(index, 1);
            setSelected(selected);
            return "teal";
        } else {
            setSelected([...selected, player]);
            return "olive";
        }
    }
    
    const getPage = (jogadores) => {
        return jogadores && jogadores.length >= 5? 
        <Form>
            <Form.Field>
                <label>Tipo de Jogo</label>
                <select ref={tipoJogoRef} className="ui fluid search dropdown">
                    <option value={1}>Simples</option>
                    <option value={2}>Com comandante</option>
                    <option value={3}>Com falso comandante</option>
                    <option value={4}>Com falso comandante e assassino</option>
                    <option value={5}>Com falso comandante e agente invisível</option>
                </select>
            </Form.Field>
            <Button disabled={selected.length < 5} onClick={() => handleIniciarJogo()} positive>Iniciar Partida</Button>            
        </Form>
        : 
        <>
           <Segment>Jogadores insuficientes para jogar!</Segment>
           <Divider />
        </>
   }
                        
    return (
        <Container text style={{ display: "flex", flexDirection: "column"}}>
            
            {
                getPage(jogadores)
            }

            <Grid columns={4}>
            {
                jogadores.map(jogador => (
                    <Grid.Column key={jogador.id}>
                        <PlayerLabel key={jogador.id} jogador={jogador} onClick={() => handleClickLabel(jogador)} />
                    </Grid.Column>
                ))
            }
            </Grid>


            <Form>
                <Divider />
                <Form.Field>
                    <label>Adicionar Jogador</label>
                    <input ref={jogadorNovoRef} placeholder='Nome' />
                </Form.Field>
                <Button onClick={() => handleNovoJogador()} positive>Salvar</Button>
            </Form>

        </Container>
    );
}

export default SelectPlayers;
