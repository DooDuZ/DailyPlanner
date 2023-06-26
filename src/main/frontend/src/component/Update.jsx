import React, { useState, useEffect } from 'react';
import styles from '../css/update.css';
import Switch from './Switch.jsx';

export default function Update(props){

    const [checked, setChecked] = useState( false );

    useEffect(
        ()=>{
            setChecked(props.selectedTodo.completed)
        },
        [props.selectedTodo]
    );

    let todo = props.selectedTodo;

    return (
        <div className="update_wrap">
            <div className="update_btnBox">
                <img src="/img/ect/rightArrow2.png"
                    className="update_hideBtn"
                    onClick={ props.onHide }
                />
                <Switch checked={checked} setChecked={ ()=>{ setChecked(!checked); }} />
            </div>
            <div className="update_info">
                <div> <span> 제목 </span><input type="text" value={todo==null ? "" : todo.title}/></div>
                <div> <span> 시작 </span><input type="datetime-local" value={todo==null ? "" : todo.stime} /></div>
                <div> <span> 마감 </span><input type="datetime-local" value={todo==null ? "" : todo.etime}/></div>
            </div>
            <div className="update_text">
                <textarea value={todo==null ? "" : todo.text} />
            </div>
            <div className="submitBox">
                <button className="submitBtn"> 수정 </button>
            </div>
        </div>
    )
}