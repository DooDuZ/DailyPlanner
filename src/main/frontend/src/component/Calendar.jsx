import React, { useState, useEffect } from 'react';
import styles from '../css/Calendar.css';

function Calendar(props){
    const year = [ "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    const week = [ 'Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat' ];

    let today = new Date();

    const [ selectedMonth, setSelectedMonth ] = useState( today.getMonth() );
    const [ selectedYear, setSelectedYear ] = useState( today.getFullYear() );
    const [ monthData, setMonthData ] = useState([]);

    function setMonth( year, month ){
        let data = [];

        console.log(year);
        console.log(month);

        setSelectedYear(year);
        setSelectedMonth(month);

        const lastOfMonth = new Date(year, month+1, 0 ).getDate();
        const firstDay = new Date(year, month, 1).getDay();

        console.log("마지막날 " + lastOfMonth);

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
            <Calendar_Controller selectedYear={selectedYear} selectedMonth={selectedMonth} setMonth={setMonth} year={year} />
            <Calendar_Header week={week} />
            <Calendar_Body monthData={monthData}/>
        </div>
    )
}

function Calendar_Controller(props){
    return (
        <div className="controller_wrap">
            <div className="calendar_year">
                <h3> {props.selectedYear} </h3>
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
                <div className="controller_display">
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
    return (
        <div className="calendar_body">
            {
                props.monthData.map( (w, i)=>{
                    return(
                        <div className="calendar_row calendar_week" key={ `week${i}` }>
                            {
                                w.map( (d)=>{
                                    return(
                                        d<=0 ? <DayCell keyName={`space${d}`} /> : <DayCell day={d} keyName={`day${d}`}/>
                                    )
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
        <div className="dayCell" key={props.keyName}>
            {props.day}
        </div>
    )
}

export default Calendar;