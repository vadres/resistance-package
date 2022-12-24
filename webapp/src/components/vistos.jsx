import { Image, List } from 'semantic-ui-react';

const Vistos = ({ atores }) => (
    <List selection horizontal>
        {
            atores.map(ator => (
                <List.Item key={ator}>
                    <List.Header as={'a'}>{ator}</List.Header>
                </List.Item>
            ))
        }
    </List>   
);

export default Vistos;