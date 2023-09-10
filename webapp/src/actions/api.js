import axios from "axios";
import { Fase } from "../models/variables";

axios.defaults.baseURL = "http://10.80.40.22:8080";

export const buscarTodosJogadores = async () => {
    const { data } = await axios.get("/jogador/all");
    const { jogadores, fase } = data;

    const { resistencia, espioes } = await placar();
    let jogador;

    if (fase == Fase.INFO_FECHADA) {
        jogador = jogadores.pop()
    }

    return {
        jogadores,
        jogador,
        resistencia,
        espioes,
        fase
    }
}

export const novoJogador = async (nome) => {
    await axios.post("jogador/novo/" + nome);
    return buscarTodosJogadores();
}


export const iniciarJogo = async (jogadores, personagens, tipoJogo) => {
    const { resistencia, espioes } = await placar();
    const { data } = await axios.post("jogo/iniciar", { jogadores, personagens, tipoJogo });

    const jogador = data.pop();

    return {
        jogadores: data,
        jogador,
        fase: Fase.INFO_FECHADA,
        resistencia,
        espioes
    };
}

export const selecionarChars = async (characters) => {
    return {
        characters,
        fase: Fase.PRE_JOGO,
    };
}


export const verInformacoes = (gameState, setGameState) => {
    setGameState({
        ...gameState,
        fase: Fase.INFO_ABERTA
    })
};

export const placar = async () => {
    const { data } = await axios.get("/placar");
    const { resistencia, espioes } = data;

    return {
        resistencia,
        espioes
    };
}

export const atualizarPlacar = async (vencedor, gameState, setGameState) => {
    await axios.post("placar", { vencedor });
    await axios.post("jogo/finalizarPartida");

    const { jogador, jogadores, fase } = await buscarTodosJogadores();
    setGameState({
        jogadores,
        jogador,
        fase
    });
}

export const next = (gameState, setGameState) => {
    const jogadores = gameState.jogadores;
    
    if (jogadores.length == 0) {
        setGameState({
            ...gameState,
            jogadores,
            fase: Fase.JOGO_INICIADO   
        })
        return;
    }

    const jogador = jogadores.pop()

    setGameState({
        ...gameState,
        jogadores,
        jogador,
        fase: Fase.INFO_FECHADA        
    }); 
}

export const result = async (gameState, setGameState) => {
    const { data } = await axios.get("/jogador/all");
    const { jogadores, fase } = data;

    setGameState({
        ...gameState,
        jogadores,
        fase: Fase.RESULTADO            
    });          
    
}

export const resetarJogo = async (gameState, setGameState) => {
    await axios.post("jogo/resetar");
    const { resistencia, espioes } = await placar();
    const { jogador, jogadores, fase } = await buscarTodosJogadores();

    setGameState({
        ...gameState,
        jogadores,
        jogador,
        resistencia,
        espioes,
        fase
    });
}