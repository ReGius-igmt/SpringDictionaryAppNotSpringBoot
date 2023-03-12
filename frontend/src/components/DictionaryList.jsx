import React from 'react';
import styles from "./DictionarySection/DictionarySection.module.css";
import {DictionaryRow} from "./DictionaryRow/DictionaryRow";
import ListGroup from "react-bootstrap/ListGroup";

const DictionaryList = ({words}) => {
    if(!words.length) return <h4 className="text-center text-secondary mt-4">Словарь пуст</h4>
    return (
        <ListGroup className={styles.words} as="ul">
            {words.map(d => <DictionaryRow key={d.id} data={d}/>)}
        </ListGroup>
    );
};

export default DictionaryList;