import { useContext, useState } from 'react';
import 'semantic-ui-css/semantic.min.css';
import { Label, Image, Checkbox, Card } from 'semantic-ui-react';

function PlayerLabel({ jogador, onClick }) {
    const [ checked, setChecked ] = useState(false);
    const [ color, setColor ] = useState("");

    const resolve = (onClick) => {
        onClick();
        setChecked(!checked);

        if (!checked) {
            setColor("green");
        } else {
            setColor("");
        }
    }

    return (
        <div style={{ display: "flex", justifyContent: "center" }}>
            <Card color={color} onClick={() => resolve(onClick)}> 
                <Card.Content>
                    <Card.Header>{jogador.nome}</Card.Header>
                    <Card.Description>
                        <Checkbox checked={checked} slider />
                    </Card.Description>
                </Card.Content>
            </Card>    
            
        </div>
    );
}

export default PlayerLabel;
