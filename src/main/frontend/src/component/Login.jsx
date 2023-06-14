import react from 'react';
import axios from 'axios';

export default function Login(props){

    function login(){
        let form = document.querySelector('.loginForm');
        let formData = new FormData(form);

        axios.post("/user/login", formData, {headers : { 'Content-Type': 'multipart/form-data'} } )
            .then( re => console.log(re) )
    }

    return (
        <div className="login_wrap">
            <form className="loginForm">
                로그인 : <input type="text" name = "uid" />
                비밀번호 : <input type="password" name = "upassword"  />
                <button type="button" onClick={ ()=>{ login() } }> 로그인 </button>
            </form>
        </div>
    )
}