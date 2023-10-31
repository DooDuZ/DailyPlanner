import react, {useState, useEffect} from 'react';
import styles from '../css/toggle.css';
import CheckBox from './CheckBox.jsx';

export default function Toggle(props){
    const [ open, setOpen ] = useState(false);

    // 시작 시간
    let time = getSimpleDate(props.todo.stime);

    // 종료 시간
    let endTime = getMonth( props.todo.etime ) + " " + getSimpleDate( props.todo.etime );

    console.log(props.todo);

    const rotateBtn = (e)=>{
        const btn = e.target;
        const body = e.target.parentNode.parentNode.parentNode;

        if(!open){
            btn.style.rotate = '90deg';
            body.childNodes[1].style.display = 'block';
        }else{
            btn.style.rotate = '0deg';
            body.childNodes[1].style.display = 'none';
        }

        setOpen(!open);
    }

    function getMonth( d ){
        return d.split("T")[0];
    }

    function getSimpleDate( d ){
        let simple = "";

        let date = new Date(d);
        simple = ( date.getHours() <10 ? "0"+date.getHours() : date.getHours() ) + ":" + ( date.getMinutes() <10 ? "0"+date.getMinutes() : date.getMinutes() );

        return simple;
    }

    return (
        <div className="toggle_wrap">
            <div className={ (props.todo.completed ? "completed" : "todo") + " toggle_top"}>
                <div className = "toggle_left">
                    {props.checkVisible ? <CheckBox tno={props.todo.tno} listHandler={props.listHandler} /> : null}
                    <img src="/img/etc/toggle2.png" className="toggle_btn" onClick={ (e)=>{ rotateBtn(e); } } />
                    <div className="toggle_time"> { time } </div>
                    <div className="toggle_title"> { props.todo.title } </div>
                </div>
                <div className = "toggle_right" onClick={ ()=>{ props.checked( props.todo.tno, !props.todo.completed );} }> {props.todo.completed ? "completed" : "before"} </div>
            </div>
            <div className="toggle_body">
                <button className="updateBtn" onClick={ ()=>{
                        props.loadData();
                        props.openUpdate();
                }} >
                    수정
                </button>
                <div className="toggle_body_text">
                    <div className="toggle_body_info">
                        <span> 발행 : {props.todo.openerName} </span>
                        <span> 완료 : {props.todo.closerName != "" ? props.todo.closerName : " - " } </span><br/>
                        <div> 기한 : {props.todo.etime != "" ? endTime : " - " } </div>
                    </div>
                    <br/>
                    <div className="toggle_body_contents">{props.todo.text}</div>
                </div>
            </div>
        </div>
    )
}