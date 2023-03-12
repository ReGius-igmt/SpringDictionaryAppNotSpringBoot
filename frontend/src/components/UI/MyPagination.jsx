import React, {useMemo} from 'react';
import Pagination from "react-bootstrap/Pagination";

const MyPagination = ({totalPages, page, setPage}) => {
    const pagesArr = useMemo(() => {
        let arr = []
        let start = page < 4 ? 0 : page - 3
        let end = page + 2 > totalPages ? totalPages : page+2
        if(totalPages > 4 && end - start < 5) {
            if(start < 4) end = 5
            if(end > 4) start = end - 5
        }
        for (let i = start; i < end; i++) {
            arr.push(i+1);
        }
        return arr;
    }, [totalPages, page])

    if(totalPages < 2) return null
    return (
        <Pagination className="justify-content-end">
            {pagesArr.map(p => <Pagination.Item active={page === p} onClick={() => setPage(p)} key={p}>{p}</Pagination.Item>)}
        </Pagination>
    );
};

export default MyPagination;