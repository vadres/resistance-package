import { useContext, useState, useRef } from 'react';
import 'semantic-ui-css/semantic.min.css';
import { Container, Button, Grid } from 'semantic-ui-react';

import { GameContext } from '../../context/game-context';
import CharLabel from '../../components/char-label';
import { selecionarChars } from '../../actions/api';

function SelectChar() {
    const personagens = [
        'GUARDA_COSTAS',
        'COMANDANTE',
        'ASSASSINO',
        'FALSO_COMANDANTE',
        'AGENTE_INVISIVEL'
    ]

    const [selected, setSelected] = useState([]);

    const gameState = useContext(GameContext);  
    const { setGameState } = gameState;   

    const handleSelecionarChar = async () => {
        console.log(selected)
        const infoJogo = await selecionarChars(selected);

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
            setSelected([...selected, player]);
        }
        
        return "olive";
        
    }
    
    return (
        <Container text style={{ display: "flex", flexDirection: "column"}}>
            <Grid columns={2}>
            <Grid.Row>
                {
                    personagens.map(personagem => (
                        <Grid.Column key={personagem}>
                            <CharLabel key={personagem} personagem={personagem} onClick={() => handleClickLabel(personagem)} />
                        </Grid.Column>
                    ))
                }
            </Grid.Row>
            </Grid>
            <Button onClick={() => handleSelecionarChar()} positive>Selecionar</Button>            
        </Container>
    );
}

export default SelectChar;
