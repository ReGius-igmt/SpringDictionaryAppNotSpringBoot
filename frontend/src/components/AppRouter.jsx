import React from 'react';
import {Navigate, Route, Routes} from "react-router-dom";
import DictionaryListPage from "../pages/DictionaryListPage";
import DictionaryPage from "../pages/DictionaryPage";

const AppRouter = () => {
    return (
        <Routes>
            <Route exact="true" path="/" element={<DictionaryListPage/>}/>
            <Route exact="true" path="/word/add/:dictionaryId" element={<DictionaryPage/>}/>
            <Route exact="true" path="/word/:id" element={<DictionaryPage/>}/>
            <Route path="*" element={<Navigate to="/" replace={true} />}/>
        </Routes>
    );
};

export default AppRouter;