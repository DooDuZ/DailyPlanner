import React, { useState, useEffect } from 'react';
import styles from '../css/write.css';
import Switch from './Switch.jsx';

/*
    import Editor from '../toast/editor.jsx'
    import '@toast-ui/editor/dist/toastui-editor.css';
*/

export default function Write(props){

    return (
        <div className="write_wrap">
            <div className="write_btnBox">
                <img src="/img/ect/rightArrow2.png"
                    className="write_hideBtn"
                    onClick={ props.onHide }
                />
                <Switch />
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
                <button className="submitBtn"> 등록 </button>
            </div>
        </div>
    )
}