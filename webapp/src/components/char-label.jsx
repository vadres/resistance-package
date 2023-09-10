import { useContext, useState } from 'react';
import 'semantic-ui-css/semantic.min.css';
import { Label, Image, Button } from 'semantic-ui-react';

function CharLabel({ personagem, onClick }) {
    const [ color, setColor ] = useState("teal");

    return (
        <div style={{ margin: "10px 0" }}>
            <Label className="disable-select" as='a' color={color} onClick={() => setColor(onClick())}>
                {personagem}
            </Label>                
        </div>
    );
}

export default CharLabel;
