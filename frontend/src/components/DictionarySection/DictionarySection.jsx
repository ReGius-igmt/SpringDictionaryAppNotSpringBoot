import Button from 'react-bootstrap/Button'
import styles from "./DictionarySection.module.css"
import Form from 'react-bootstrap/Form';
import {useEffect, useState} from "react";
import DictionaryService from "../../API/DictionaryService";
import {Link} from "react-router-dom";
import MyPagination from "../UI/MyPagination";
import {useTimeoutInput} from "../../hooks/useTimeoutInput";
import DictionaryList from "../DictionaryList";

export const DictionarySection = (props) => {
    const pageSize = 10;
    const [words, setWords] = useState([])
    const [page, setPage] = useState(1)
    const [totalPages, setTotalPages] = useState(0)
    const [totalItems, setTotalItems] = useState(0)
    const [search, searchTerm, setSearchTerm] = useTimeoutInput(() => setPage(1))


    useEffect(() => {
        DictionaryService.getDictionary(
            props.dictionaryId,
            pageSize,
            (page-1)*pageSize,
            search
        ).then(data => {
            setTotalItems(data.total)
            setTotalPages(Math.ceil(data.total/pageSize))
            setWords(data.items)
        })
    }, [props.dictionaryId, search, page])

    return (
        <div>
            <div>
                <label>Поиск: </label>
                <div className="d-flex justify-content-between">
                    <Form.Control value={searchTerm} onChange={e => setSearchTerm(e.target.value)} className={styles.searchInput} placeholder="Поиск"/>
                    <Link to={`/word/add/${props.dictionaryId}`}>
                        <Button variant="success">Добавить</Button>
                    </Link>
                </div>
            </div>
            <DictionaryList words={words}/>
            <div className="d-flex align-items-center justify-content-between">
                <div>
                    <span className="d-block text-primary">Всего элементов: {totalItems}</span>
                    <span className="d-block text-primary">На странице: {words.length}</span>
                </div>
                <MyPagination page={page} setPage={setPage} totalPages={totalPages}/>
            </div>
        </div>
    )
}