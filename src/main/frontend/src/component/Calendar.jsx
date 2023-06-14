import React, { useState, useEffect, useRef } from 'react';
import styles from '../css/Calendar.css';
import axios from "axios";
import Sidebar from './Sidebar.jsx';
import DayModal from './DayModal.jsx';
import 'bootstrap/dist/css/bootstrap.min.css';

let monthList = [];
let isSidebar = false;

function Calendar(props){
    const year = [ "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    const week = [ 'Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat' ];

    let today = new Date();

    const [ selectedMonth, setSelectedMonth ] = useState( today.getMonth() );
    const [ selectedYear, setSelectedYear ] = useState( today.getFullYear() );

    // used calendar_body
    const [ monthData, setMonthData ] = useState([]);

    // used sidebar
    const [ plannerList, setPlannerList ] = useState([]);

    // used DayModal
    const [ isVisible, setIsVisible ] = useState(false);

    const modalHandler = ( value ) =>{
        console.log("실행");
        console.log( value );
        setIsVisible( value );
    }

    function getPlannerList(){
        axios.get("/planner/list").then( (re)=>{
            let data = [...re.data];
            setPlannerList(data);
        })
    }

    function setSidebar(){
        if( !isSidebar ){
            openSidebar();
        }else{
            closeSidebar();
        }
    }

    const openSidebar = () => {
        console.log("open");
        const sidebar = document.querySelector('.sidebar');
        sidebar.style.left = 0;
        isSidebar = true;
        getPlannerList();
    }

    const closeSidebar = () => {
        console.log("close");
        const sidebar = document.querySelector('.sidebar');
        sidebar.style.left = "-400px";
        isSidebar = false;
    }

    async function getMonthList( year, month ){
        monthList = [];
        // pno 테스트데이터로 넣음
        await axios.get(`http://localhost:8080/todo/month-list?pno=${2}&year=${year}&month=${month+1}`).then( re => {
            monthList = [...re.data];
        })
    }

    async function setMonth( year, month ){
        let data = [];

        setSelectedYear(year);
        setSelectedMonth(month);

        await getMonthList( year, month );

        const lastOfMonth = new Date(year, month+1, 0 ).getDate();
        const firstDay = new Date(year, month, 1).getDay();

        let week = [];

        for(let i = firstDay; i>0; i--){
            week.push(i*-1);
        }

        let i;
        for( i = 1; i<=7-firstDay; i++){
            week.push(i);
        }
        data.push(week);
        week = [];

        const nextRow = i%7;
        let start = i;

        for( i = i; i <= lastOfMonth; i++){
            if( (i!=start && i%7 == nextRow)){
                data.push(week);
                week = [];
            }
            week.push(i);

            if( i==lastOfMonth ){
                data.push(week);
            }
        }

        setMonthData( [...data] );
    }

    useEffect(
        ()=>{ setMonth(selectedYear, selectedMonth)}, []
    );

    return (
        <div className="calendar_wrap">
            <button onClick={ () =>{ setSidebar() }} className="menuBtn"> <img src={process.env.PUBLIC_URL+"/img/menu.png"} /> </button>
            <Sidebar plannerList={plannerList} onMouseOver={ ()=>{console.log("open")} } onMouseOut={()=>{ console.log("close") }} />
            <Calendar_Controller selectedYear={selectedYear} selectedMonth={selectedMonth} setMonth={setMonth} year={year} />
            <Calendar_Header week={week} />
            <Calendar_Body monthData={monthData} today={today} selectedYear={selectedYear} selectedMonth={selectedMonth} monthList={monthList} openModal={ ()=>{ modalHandler(true) } } />
            <DayModal show={isVisible} onHide={ ()=>{setIsVisible(false)}} />
        </div>
    )
}

function Calendar_Controller(props){
    return (
        <div className="controller_wrap">
            <div className="calendar_year">
                <h3 className="display_year"> {props.selectedYear} </h3>
            </div>

            <div className="calender_controller">
                <button onClick={
                    ()=>{
                        let m = props.selectedMonth-1;
                        if(m >= 0){
                            props.setMonth( props.selectedYear, m );
                        }else {
                            props.setMonth( props.selectedYear-1, 11 );
                        }
                    }
                } className="controllerBtn">
                    <img src={process.env.PUBLIC_URL +"/img/leftArrow.png"} />
                </button>
                <div className="display_month">
                    <h3> {props.year[props.selectedMonth]}</h3>
                </div>
                <button onClick={
                    ()=>{
                        let m = props.selectedMonth+1;
                        if(m <= 11){
                            props.setMonth( props.selectedYear, m );
                        }else {
                            props.setMonth( props.selectedYear+1, 0 );
                        }
                    }
                } className="controllerBtn">
                    <img src={process.env.PUBLIC_URL +"/img/rightArrow.png"} />
                </button>
            </div>
        </div>
    )
}

function Calendar_Header(props){
    return (
        <div className="calendar_header calendar_row">
            {
                props.week.map( (d) =>{
                    return (
                        <div className="header_el" key={"header_"+d}> { d } </div>
                    )
                })
            }
        </div>
    )
}

function Calendar_Body(props){

    const todayYear = props.today.getFullYear();
    const todayMonth = props.today.getMonth();
    const todayDate = props.today.getDate();

    let days = new Map();

    for(let i = 1 ; i <= new Date(todayYear, todayMonth+1, 0).getDate() ;i++){
        days.set( i, [] );
    }

    if(props.monthList != null){
        monthList.map( ( e )=>{
            let day = new Date(e.stime).getDate();
            days.get(day).push(e);
        } )
    }

    return (
        <div className="calendar_body">
            {
                props.monthData.map( (w, i)=>{
                    return(
                        <div className="calendar_row calendar_week" key={ `week${i}` }>
                            {
                                w.map( (d)=>{
                                    if(d<=0){
                                        return(
                                            <DayCell keyName={`space${d}`} list={[]} openModal={ ()=>{alert("empty!")}} />
                                        )
                                    }else if( (props.selectedYear == todayYear && props.selectedMonth == todayMonth && d == todayDate) ){
                                        return (
                                            <DayCell day={d} id="todayCell" keyName={`day${d}`} list={days.get(d)} openModal={ props.openModal }/>
                                        )
                                    }else{
                                        return(
                                            <DayCell day={d} id="" keyName={`day${d}`} list={days.get(d)} openModal={ props.openModal }/>
                                        )
                                    }
                                } )
                            }
                        </div>
                    )
                } )
            }
        </div>
    )
}

function DayCell(props){
    return(
        <div className={"dayCell"+` ${props.id}`} key={props.keyName} onClick={props.openModal}>
            <p>{props.day}</p>
            <div className="list_preview">
                {props.list == null ? "" :
                    <ul>
                        {
                            props.list.map( (e)=>{
                                return(
                                    <li> <img src={process.env.PUBLIC_URL +"/img/listStyle.png"} /> {e.title} </li>
                                )
                            } )
                        }
                    </ul>
                }
            </div>
        </div>
    )
}

export default Calendar;