import React, { useState, useEffect } from 'react';
import styles from '../css/write.css';
import Switch from './Switch.jsx';
import axios from 'axios';

export default function Write(props){

    const [checked, setChecked] = useState( false );

    const regist = ()=>{
        const inputs = document.querySelectorAll('.write_wrap input');
        const text = document.querySelector('.write_wrap textarea');

        const data = {
            "title" : inputs[1].value,
            "text" : text.value,
            "stime" : inputs[2].value,
            "etime" : inputs[3].value,
            "pno" : props.selectedPno,
            "completed" : inputs[0].value=="on" ? checked : inputs[0].value,
        }

        axios.post('/todo/duty', data).then( (re)=>{
            if(re.data==1){
                inputs[0].value = false;
                inputs[1].value = null;
                inputs[2].value = null;
                inputs[3].value = null;
                text.value = null;
                props.onHide();
                props.getDayList();
            }else{
                console.log("실패용");
            }
        } );
    }

    return (
        <div className="write_wrap">
            <div className="write_btnBox">
                <img src="/img/ect/rightArrow2.png"
                    className="write_hideBtn"
                    onClick={ props.onHide }
                />
                <Switch checked={checked} setChecked={ ()=>{ setChecked(!checked); }} />
            </div>
            <div className="write_info">
                <div> <span> 제목 </span><input type="text" /> </div>
                <div> <span> 시작 </span><input type="datetime-local" /> </div>
                <div> <span> 마감 </span><input type="datetime-local" /> </div>
            </div>
            <div className="write_text">
                <textarea />
            </div>
            <div className="submitBox">
                <button className="submitBtn" onClick={regist}> 등록 </button>
            </div>
        </div>
    )
}