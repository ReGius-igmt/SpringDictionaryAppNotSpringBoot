import React, {useState} from 'react';
import {Form, InputGroup} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import {useNavigate} from "react-router-dom";

const DictionaryForm = ({saveAction, deleteAction, word, setWord}) => {
    const [validated, setValidated] = useState(false);
    const navigate = useNavigate();

    const onSubmit = e => {
        e.preventDefault()
        e.stopPropagation()
        const form = e.currentTarget
        if(form.checkValidity()) {
            saveAction({...word, values: word.values.split('\n')})
        }
        setValidated(true)
    }

    return (
        <Form noValidate validated={validated} onSubmit={onSubmit}>
            <Form.Group className="mb-3">
                <Form.Label>Ключ</Form.Label>
                <InputGroup hasValidation>
                    <Form.Control value={word.key} onChange={e => setWord({...word, key: e.target.value})} type="text" required/>
                    <Form.Control.Feedback type="invalid">
                        Заполните это поле
                    </Form.Control.Feedback>
                </InputGroup>
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Значения</Form.Label>
                <InputGroup hasValidation>
                    <Form.Control
                        value={word.values}
                        onChange={e => setWord({...word, values: e.target.value})}
                        as="textarea" rows={3} required/>
                    <Form.Control.Feedback type="invalid">
                        Заполните это поле
                    </Form.Control.Feedback>
                </InputGroup>
            </Form.Group>
            <div className="d-flex">
                {deleteAction && <Button variant="danger" type="button" onClick={deleteAction}>Удалить</Button>}
                <Button variant="secondary" className="ms-auto me-1" type="button" onClick={() => navigate('/')}>Назад</Button>
                <Button type="submit">Сохранить</Button>
            </div>
        </Form>
    );
};

export default DictionaryForm;