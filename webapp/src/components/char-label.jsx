import { useContext, useState } from 'react';
import 'semantic-ui-css/semantic.min.css';
import { Image, Checkbox, Card } from 'semantic-ui-react';

import { upperFirst } from '../actions/string-util';

function CharLabel({ personagem, onClick }) {
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
                    <Card.Header>{upperFirst(personagem)}</Card.Header>
                    <Card.Description>
                        <Checkbox checked={checked} slider />
                    </Card.Description>

                    <Image style={{ position: "absolute", top: 25, right: 5 }} 
                           avatar src={require(`../images/${personagem}.png`)} />
                </Card.Content>
            </Card>         
        </div>
    );
}

export default CharLabel;
