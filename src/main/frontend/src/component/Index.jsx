import React, { useState, useEffect } from 'react';
import { HashRouter, BrowserRouter, Routes, Route, Link,  Router } from "react-router-dom";
import Footer from './Footer.jsx';
import Login from './Login.jsx';
import Main from './Main.jsx';
import axios from 'axios';

export default function Index(props){

    const [ isLogin, setIsLogIn ] = useState(null);

    function getUser(){
        axios.get("/user/login-info").then(
            (re) =>{
                console.log(re);
                if(re.data!=""){
                    setIsLogIn(re.data);
                }else{
                    console.log("로그인이 필요합니다.");
                    // eslint-disable-next-line no-restricted-globals
                }
            }
        );
    }

    useEffect( ()=> { getUser() }, [] );

    return(
        <div className="app_wrap">
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<Main setIsLogIn={setIsLogIn} />} />
                    <Route path="/login" element={ <Login isLogin={isLogin} /> } />
                </Routes>
                <Footer />
            </BrowserRouter>
        </div>
    )
}