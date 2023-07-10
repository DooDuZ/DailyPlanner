import React, { useState, useEffect } from 'react';
import axios from 'axios';
import styles from '../css/login.css';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

export default function Login(props){

    function login(){
        let form = document.querySelector('.loginForm');
        let formData = new FormData(form);

        console.log(formData);

        axios.post("/user/login", formData, {headers : { 'Content-Type': 'multipart/form-data'} } )
            .then( re => {
                console.log(re);
                if(re.data.success==true){
                    // eslint-disable-next-line no-restricted-globals
                    window.location.href = "/";
                }
            })
    }

    function signUp(){
        const uid = document.querySelector('.signUpId').value;
        const upw = document.querySelector('.signUpPw').value;
        const uname = document.querySelector('.signUpName').value;
        const uemail = document.querySelector('.signUpEmail').value;

        let data = {
            "uid" : uid,
            "upassword" : upw,
            "uname" : uname,
            "uemail" : uemail,
        }

        console.log(data);

        axios.post( "/user/sign-up", data ).then( res => {console.log(res);});
    }

    useEffect( ()=>{
        const signUpButton = document.getElementById('signUp');
        const signInButton = document.getElementById('signIn');
        const container = document.getElementById('container');

        signUpButton.addEventListener('click', () => {
          container.classList.add("right-panel-active");
        });

        signInButton.addEventListener('click', () => {
          container.classList.remove("right-panel-active");
        });
    }, [] );

    return (
        <div className="login_wrap">
            <div className="container" id="container">
                <div className="form-container sign-up-container">
                    <form className="signUpForm">
                    <h1>Sign Up</h1>
                    <input type="text" placeholder="ID" className = "signUpId" />
                    <input type="password" placeholder="Password" className = "signUpPw" />
                    <input type="text" placeholder="Name" className = "signUpName" />
                    <input type="email" placeholder="Email" className = "signUpEmail" />
                    <button type="button" onClick={signUp}>Sign Up</button>
                    </form>
                </div>
                <div className="form-container sign-in-container">
                    <form className="loginForm" >
                    <h1>Sign in</h1>
                    <div className="social-container">
                        <img src="/img/etc/icon_google2.png" className="oauth_icon" />
                        <img src="/img/etc/icon_github.png" className="oauth_icon" />
                        <img src="/img/etc/icon_kakao.png"  className="oauth_icon" />
                    </div>
                    <input type="email" placeholder="ID" name="uid" />
                    <input type="password" placeholder="Password" name="upassword" />
                    <button type="button" onClick={login} >Sign In</button>
                    </form>
                </div>
                <div className="overlay-container">
                    <div className="overlay">
                        <div className="overlay-panel overlay-left">
                            <h1>Daily Planner</h1>
                            <p>이미 가입한 회원이라면</p>
                            <button className="ghost" id="signIn">Sign In</button>
                        </div>
                        <div className="overlay-panel overlay-right">
                            <h1>Daily Planner</h1>
                            <p>아직 데일리 플래너의 회원이 아니신가요?</p>
                            <button className="ghost" id="signUp">Sign Up</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}