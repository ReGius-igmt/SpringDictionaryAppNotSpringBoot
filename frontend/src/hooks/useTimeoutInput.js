import {useEffect, useState} from "react";

export const useTimeoutInput = (action) => {
    const [search, setSearch] = useState('')
    const [searchTerm, setSearchTerm] = useState('')

    useEffect(() => {
        const delayDebounceFn = setTimeout(() => {
            setSearch(searchTerm)
            action()
        }, 500)
        return () => clearTimeout(delayDebounceFn)
    }, [searchTerm])

    return [search, searchTerm, setSearchTerm]
}