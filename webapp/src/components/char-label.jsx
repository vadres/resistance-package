import { useContext, useState } from 'react';
import 'semantic-ui-css/semantic.min.css';
import { Image, Checkbox, Card } from 'semantic-ui-react';

function upperFirst(sentence) {
    const fn = (word) => {
        const [first, ...rest] = word;  
        return `${first.toUpperCase()}${rest.join('').toLowerCase()}`; 
    }
    
    return sentence.split(" ").map(word => fn(word)).join(" ");
}

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
                    <Card.Header>{upperFirst(personagem.replace("_", " "))}</Card.Header>
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
