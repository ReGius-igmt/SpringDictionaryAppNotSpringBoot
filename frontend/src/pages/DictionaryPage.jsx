import React, {useEffect, useState} from 'react';
import DictionaryForm from "../components/DictionaryForm/DictionaryForm";
import {useNavigate, useParams} from "react-router-dom";
import {Toast, ToastContainer} from "react-bootstrap";
import DictionaryService from "../API/DictionaryService";

const DictionaryPage = () => {
    const navigate = useNavigate();
    let params = useParams();
    const wordID = params.id;
    const dictionaryID = params.dictionaryId;
    const [error, setError] = useState('')
    const [word, setWord] = useState({id: null, key: "", values: ""})
    
    useEffect(() => {
        if(!wordID) return;
        DictionaryService.getWordById(wordID).then(res => {
            if(res.success) {
                res.data.values = res.data.values.join("\n")
                setWord(res.data)
            } else {
                navigate('/')
            }
        })
    }, [wordID])

    const saveAction = async data => {
        data.id = wordID;
        let res = await (wordID
            ? DictionaryService.edit(wordID, data)
            : DictionaryService.add(dictionaryID, data))
        if(!res.success) setError(res.data)
        else navigate('/')
    }

    const deleteAction = async () => {
        let res = await DictionaryService.delete(wordID)
        if(!res.success) setError(res.data)
        else navigate('/')
    }

    return (
        <div className="content container">
            <h1>{wordID ? 'Редактирование' : 'Создание'}</h1>
            <DictionaryForm
                saveAction={saveAction}
                deleteAction={wordID ? deleteAction : null}
                word={word}
                setWord={setWord}/>
            <ToastContainer className="p-3" position="bottom-end">
                <Toast
                    show={error.length > 0}
                    onClose={() => setError('')}
                    delay={3000}
                    autohide
                    className="text-danger">
                    <Toast.Header closeButton={true}>
                        <strong className="me-auto">Ошибка</strong>
                    </Toast.Header>
                    <Toast.Body>{error}</Toast.Body>
                </Toast>
            </ToastContainer>
        </div>
    );
};

export default DictionaryPage;