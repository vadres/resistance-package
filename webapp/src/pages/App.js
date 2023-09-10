import { useEffect, useState } from 'react';
import 'semantic-ui-css/semantic.min.css';

import './App.css';
import { GameContext, gameStateEmpty } from '../context/game-context';
import { buscarTodosJogadores, placar } from '../actions/api';
import GetInfo from './get-info/GetInfo';
import SelectPlayers from './select-players/SelectPlayers';
import SelectChar from './select-characters/SelectChar';
import Header from '../components/header';
import { Fase } from '../models/variables';

function App() {
  const [ gameState, setGameState ] = useState(gameStateEmpty);

  const getPage = () => {
    const { fase } = gameState;

    if (fase == Fase.SEL_JOGADORES)
      return <SelectChar />

    return fase <= Fase.PRE_JOGO? <SelectPlayers />: <GetInfo />
  }

  useEffect(() => {
    const fetchData = async () => {
      let state = await buscarTodosJogadores();
      
      setGameState({
        ...gameState,
        ...state           
      }); 
    }

    fetchData();
  }, [buscarTodosJogadores]);

  return (
    <GameContext.Provider value={{ ...gameState, setGameState }}>
      <Header />
      { getPage() }
    </GameContext.Provider>
  );
}

export default App;
