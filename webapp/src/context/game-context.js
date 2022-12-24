import React from "react";

export const gameStateEmpty = {
    jogadores: [],
    jogador: {   
        personagem: "",
        info: "",
        revelados: []
    },
    fase: 0,
    resistencia: 0,
    espioes: 0
}

export const GameContext = React.createContext({
    ...gameStateEmpty,
    setGameState: (state) => {}
});