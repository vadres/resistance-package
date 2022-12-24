import axios from "axios";

axios.defaults.baseURL = "http://192.168.0.7:8080";

export const buscarTodosJogadores = async () => {
    const { data } = await axios.get("/jogador/all");
    const { jogadores, fase } = data;

    const { resistencia, espioes } = await placar();
    let jogador;

    if (fase == 2) {
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


export const iniciarJogo = async (jogadores, tipoJogo) => {
    const { resistencia, espioes } = await placar();
    const { data } = await axios.post("jogo/iniciar", { jogadores, tipoJogo });

    return {
        jogadores: data,
        jogador: data[data.length - 1],
        fase: 2,
        resistencia,
        espioes
    };
}

export const verInformacoes = (gameState, setGameState) => {
    setGameState({
        ...gameState,
        fase: 3
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
    const jogador = jogadores.pop()

    const newState = {
        ...gameState,
        jogadores,
        jogador,
        fase: 2         
    };

    if (jogadores.length == 0) {
        newState.fase = 4
    }

    setGameState(newState); 
}

export const result = async (gameState, setGameState) => {
    const { data } = await axios.get("/jogador/all");
    const { jogadores, fase } = data;

    setGameState({
        ...gameState,
        jogadores,
        fase: 5            
    });          
    
}

export const resetarJogo = async (gameState, setGameState) => {
    await axios.post("jogo/resetar");
    const { jogador, jogadores, fase } = await buscarTodosJogadores();

    setGameState({
        ...gameState,
        jogadores,
        jogador,
        fase
    });
}