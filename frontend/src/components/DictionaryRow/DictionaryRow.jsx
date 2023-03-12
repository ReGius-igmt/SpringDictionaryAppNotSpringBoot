import styles from "./DictionaryRow.module.css";
import ListGroup from 'react-bootstrap/ListGroup';
import { useNavigate } from 'react-router-dom';

export const DictionaryRow = (props) => {
    let data = props.data;
    const navigate = useNavigate();

    const open = e => {
        e.preventDefault();
        navigate('/word/' + data.id)
    }

    return (
        <ListGroup.Item onClick={open} href={`/word/${data.id}`} as="a" className={styles.row}>
            <div>{data.key}</div>
            <div className={styles.values}>
                {data.values && data.values.map(v=><span className="ms-1" key={v}>{v}</span>)}
            </div>
        </ListGroup.Item>
    )
}