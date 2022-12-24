import { Button, Statistic, Icon } from "semantic-ui-react";
import { useContext } from "react";
import { GameContext } from "../context/game-context";
import { resetarJogo } from "../actions/api";

const Header = () => {
    const gameState = useContext(GameContext);   
    const { resistencia, espioes, setGameState } = gameState;

    const format = (n) => {
        return n < 9? "0" + n: n;
    }

    return (<div className="headerApp">
        <div style={{ display:"flex", flexDirection:"column", justifyContent:"center" }}>
            <Button color="twitter" icon onClick={() => resetarJogo(gameState, setGameState)}>
                <Icon name='sync alternate' />
            </Button>
        </div>
        <div>
            <Statistic color='green' size='tiny'>
                <Statistic.Value>{format(resistencia)}</Statistic.Value>
                <Statistic.Label>Resistência</Statistic.Label>
            </Statistic>
            <Statistic color='red' size='tiny'>
                <Statistic.Value>{format(espioes)}</Statistic.Value>
                <Statistic.Label>Espiões</Statistic.Label>
            </Statistic>
        </div>
    </div>
)

}

export default Header;