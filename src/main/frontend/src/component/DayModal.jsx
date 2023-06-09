import react, {useState, useEffect} from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import styles from '../css/dayModal.css';
import axios from 'axios';
import Toggle from './Toggle.jsx';
import Write from './Write.jsx';
import Update from './Update.jsx';

let lastDay = 0;
let selectedYear;
let selectedMonth;
function DayModal(props) {
    const year = [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ];
    const stella = [
        "/img/stars/염소자리_icon4.png",
        "/img/stars/물병자리_icon.png",
        "/img/stars/물고기자리_icon2.png",
        "/img/stars/양자리_icon.png",
        "/img/stars/황소자리_icon.png",
        "/img/stars/쌍둥이자리_icon.png",
        "/img/stars/게자리_icon.png",
        "/img/stars/사자자리_icon2.png",
        "/img/stars/처녀자리_icon2.png",
        "/img/stars/천칭자리_icon3.png",
        "/img/stars/전갈자리_icon.png",
        "/img/stars/궁수자리_icon2.png",
    ];

    const [ selectedDay, setSelectedDay ] = useState(props.selectedDay);
    const [ todoList, setTodoList ] = useState([]);
    const [ selectedTodo, setSelectedTodo ] = useState({});
    const [ checkVisible, setCheckVisible ] = useState(false);

    let tnoList = new Set();

    const checkboxHandler = ()=>{
        tnoList.clear();
        setCheckVisible(!checkVisible);
    }

    const listHandler = ( value, bool )=>{
        if(bool){
            tnoList.add(value);
        }else{
            tnoList.delete(value);
        }
    }

    async function getDayList(){
        const res = await axios.get(`/todo/list/day?pno=${props.selectedPno}&year=${selectedYear}&month=${selectedMonth+1}&day=${selectedDay}`);
        let list = [...res.data];

        list.sort( (e1,e2)=>{
            if(e1.completed == e2.completed){
                return new Date(e1.stime) - new Date(e2.stime);
            }
            return e1.completed - e2.completed;
        })

        setTodoList(list);
    }

    useEffect( ()=>{getDayList()}, [selectedDay] );

    function getStellaIndex( month, day ){
        let value = month*100 + day;
        if( value >= 1225 || value<=119 ){ return 0; }
        else if( value <= 218 ){ return 1; }
        else if( value <= 320 ){ return 2; }
        else if( value <= 419 ){ return 3; }
        else if( value <= 520 ){ return 4; }
        else if( value <= 621 ){ return 5; }
        else if( value <= 722 ){ return 6; }
        else if( value <= 822 ){ return 7; }
        else if( value <= 922 ){ return 8; }
        else if( value <= 1022 ){ return 9; }
        else if( value <= 1122 ){ return 10; }
        else if( value <= 1224 ){ return 11; }
    }

    async function checked( tno, isCompleted ){
        let data = {
            "tno" : tno,
            "completed" : isCompleted
        }
        const res = await axios.put('/todo/completed', data);
        if(res.data==1){
            getDayList();
        }
    }

    selectedYear = props.selectedYear;
    selectedMonth = props.selectedMonth;

    lastDay = new Date( selectedYear, selectedMonth+1, 0).getDate();

    const nextDayHandler = ( d ) => {
        let change = d;
        if( d < lastDay ){
            change++;
        }else{
            change = 1;
            props.nextMonthHandler();
        }

        setSelectedDay(change);
    }

    const prevDayHandler = ( d ) => {
        let change = d;

        if( d > 1 ){
            change--;
        }else{
            change = lastDay;
            props.prevMonthHandler();
        }

        setSelectedDay(change);
    }

    const openWrite = () => {
        const write = document.querySelector('.write_wrap');
        write.style.right = '0';
    }

    const closeWrite = () => {
        const write = document.querySelector('.write_wrap');
        write.style.right = '-480px';
    }

    const openUpdate = () => {
        const update = document.querySelector('.update_wrap');
        update.style.right = '0';
    }

    const closeUpdate = () => {
        const update = document.querySelector('.update_wrap');
        update.style.right = '-480px';
    }

    const deleteTodo = () => {
        if(!window.confirm( "선택 목록을 삭제하시겠습니까?" )){
            return;
        }else if(tnoList.size==0){
            alert("선택된 목록이 없습니다.");
            return;
        }
        console.log("돌이킬 수 없다 진자");
        axios.delete(`/todo/dutyList`, { data : {  "list" : Array.from(tnoList)  } }  )
            .then( (re) => {
                console.log(re);
                checkboxHandler();
                getDayList();
            });
    }

    return (
      <Modal
        {...props}
        aria-labelledby="contained-modal-title-vcenter"
        centered
        backdrop="static"
      >
        <div className = "stella_img">
            <img src= { stella[getStellaIndex( props.selectedMonth+1, selectedDay )]} />
        </div>
        <Modal.Header className="dayModal_header">
            <div className="dateBox">
                <img src="/img/etc/whiteArrow.png" className="modalController" onClick={ ()=>{
                    prevDayHandler(selectedDay);
                }}/>
                <div className="dayBox">
                    <p className="modalDay">{ selectedDay<10 ? "0"+selectedDay : selectedDay}</p>
                </div>
                <div className="monthBox">
                    <p className="modalYear">{props.selectedYear}</p>
                    <p className="modalMonth">{year[props.selectedMonth]}</p>
                </div>
                <img src="/img/etc/whiteArrow.png" className="modalController rightBtn" onClick={ ()=>{
                    nextDayHandler(selectedDay);
                }}/>
            </div>
        </Modal.Header>
        <Modal.Body className="dayModal_body">
            <div className="body_top">
                 <div className="body_top_title"> Todo List </div>
                 <div className="body_top_img">
                    <img src="/img/etc/plusbtn_pink.png"
                        onClick={openWrite}
                    />
                 </div>
            </div>
             <div className="body_contents">
                <button onClick={checkboxHandler} className="show_checkbox_btn" > select </button>
                { checkVisible ? <button onClick={checkboxHandler} className="deleteTodo_btn" onClick={deleteTodo} > delete </button> : null }
                {
                    todoList.map( ( el, i ) => {
                        return (
                            <Toggle
                                todo={el}
                                key={el.title + i}
                                checked={checked}
                                openUpdate = {openUpdate}
                                loadData = { ()=>{ setSelectedTodo(el) }}
                                listHandler = { listHandler }
                                checkVisible = {checkVisible}
                            />
                        )
                    })
                }
             </div>
             <Write
                onHide={closeWrite}
                selectedPno={props.selectedPno}
                getDayList={getDayList}
                selectedYear={selectedYear}
                selectedMonth={selectedMonth}
                selectedDay={selectedDay}
            />
             <Update
                onHide={closeUpdate}
                selectedTodo={selectedTodo}
                selectedPno={props.selectedPno}
                getDayList={getDayList}
                selectedYear={selectedYear}
                selectedMonth={selectedMonth}
                selectedDay={selectedDay}
            />
        </Modal.Body>
        <Modal.Footer className="dayModal_footer">
          <button onClick={props.onHide} className="modalBtn">Close</button>
        </Modal.Footer>
      </Modal>
    );
}

export default DayModal;