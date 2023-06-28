import React, { useState, useEffect } from 'react';
import styles from '../css/update.css';
import Switch from './Switch.jsx';
import axios from 'axios';

export default function Update(props){

    const [checked, setChecked] = useState( false );

    useEffect(
        ()=>{
            setChecked(props.selectedTodo.completed)
        },
        [props.selectedTodo]
    );

    let todo = props.selectedTodo;

    let start_time = (todo!= undefined) ? String(todo.stime).split("T")[1] : "";
    let end_time = (todo!= undefined) ? String(todo.etime).split("T")[1] : "";

    const update = ()=>{
        const inputs = document.querySelectorAll('.update_wrap input');
        const text = document.querySelector('.update_wrap textarea');

        let month = props.selectedMonth+1;
        let day = props.selectedDay;

        month = month < 10 ? "0"+month : month;
        day = day < 10 ? "0"+day : day;

        let today = props.selectedYear + "-" + month + "-" + day + "T";

        const data = {
            "tno" : props.selectedTodo.tno,
            "title" : inputs[1].value,
            "text" : text.value,
            "stime" : today+inputs[2].value,
            "etime" : today+inputs[3].value,
            "pno" : props.selectedPno,
            "completed" : inputs[0].value=="on" ? checked : inputs[0].value,
        }

        axios.put('/todo/duty', data).then( (re)=>{
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
        <div className="update_wrap">
            <div className="update_btnBox">
                <img src="/img/etc/rightArrow2.png"
                    className="update_hideBtn"
                    onClick={ props.onHide }
                />
                <Switch checked={checked} setChecked={ ()=>{ setChecked(!checked); }} />
            </div>
            <div className="update_info">
                <div> <span> 제목 </span><input type="text" defaultValue={todo==null ? "" : todo.title}/></div>
                <div> <span> 시작 </span><input type="time" defaultValue={todo==null ? "" : start_time} /></div>
                <div> <span> 마감 </span><input type="time" defaultValue={todo==null ? "" : end_time} /></div>
            </div>
            <div className="update_text">
                <textarea defaultValue={todo==null ? "" : todo.text} />
            </div>
            <div className="submitBox">
                <button className="submitBtn" onClick={update}> 수정 </button>
            </div>
        </div>
    )
}