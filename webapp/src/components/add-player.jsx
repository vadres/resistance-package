import { useContext, useState } from "react";
import { Form, Checkbox } from 'semantic-ui-react'

import { GameContext } from "../context/game-context";

const AddPlayer = () => {
    const [ value, setValue ] = useState(false);
    const { players, fase, setGameState } = useContext(GameContext); 
    const gameState = { players, fase };

    if (fase == 4) {
        return <Form>
            {
                players.map(player => (
                    <Form.Field>
                    <Checkbox
                        label={player}
                        name='checkboxRadioGroup'
                        value={player}
                    />
                    </Form.Field>
                ))
            }
           
        </Form>
    }

    return <></>;
}

export default AddPlayer;