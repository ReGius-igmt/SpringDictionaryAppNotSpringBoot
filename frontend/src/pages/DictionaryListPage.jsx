import React from 'react';
import {useEffect, useState} from "react";
import DictionaryService from "../API/DictionaryService";
import Accordion from "react-bootstrap/Accordion";
import {DictionarySection} from "../components/DictionarySection/DictionarySection";

const DictionaryListPage = () => {
    const [dictionaries, setDictionaries] = useState({});
    useEffect(() => {
        DictionaryService.getDictionaries().then(data => setDictionaries(data));
    }, []);
    return (
        <div className="content container">
            <h1 className="mt-4 mb-4">DictionarySpringNoBootApp</h1>
            <Accordion alwaysOpen>
                {Object.keys(dictionaries).map(d => (
                    <Accordion.Item key={d} eventKey={d}>
                        <Accordion.Header>{dictionaries[d]}</Accordion.Header>
                        <Accordion.Body>
                            <DictionarySection dictionaryId={d}/>
                        </Accordion.Body>
                    </Accordion.Item>
                ))}
            </Accordion>
        </div>
    );
};

export default DictionaryListPage;