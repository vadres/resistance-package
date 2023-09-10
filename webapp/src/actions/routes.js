import { Fase } from "../models/variables";

export const gerenciarJogadores = () => {
    return {
        fase: Fase.GERENCIAR_JOGADORES,
    };
}

export const selecionarJogadores = () => {
    return {
        fase: Fase.PRE_JOGO,
    };
}