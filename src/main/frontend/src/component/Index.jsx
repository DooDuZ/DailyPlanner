import React, { useState, useEffect } from 'react';
import Calendar from './Calendar.jsx';
import styles from '../css/Index.css';
import Login from './Login.jsx';
import axios from 'axios';
import Footer from './Footer.jsx';

export default function Index(props){

    const [ isLogin, setIsLogIn ] = useState(null);

    function getUser(){
        axios.get("/user/login-info").then(
            (re) =>{ console.log(re); setIsLogIn(re.data); }
        );
    }

    useEffect( ()=> { getUser() }, [] );

    return (
        <div>
            { isLogin ? <Calendar /> : <Login /> }
            <Footer />
        </div>
    )
}