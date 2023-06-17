import react, {useState, useEffect} from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import styles from '../css/dayModal.css';
import leftArrow from '../img/leftArrow.png';
import rightArrow from '../img/rightArrow.png';

let lastDay = 0;
let selectedYear;
let selectedMonth;
function DayModal(props) {

    console.log(props.selectedDay + "모달 열려요");

    const [ selectedDay, setSelectedDay ] = useState(0);

    useEffect(
        ()=>{
            setSelectedDay(props.selectedDay);
            console.log(props.selectedDay + " 입력했어요");
            console.log("현재 값은 " + selectedDay + "에요" );
        }
        , []
    )
    console.log(selectedDay + "모달 열려요");

    selectedYear = props.selectedYear;
    selectedMonth = props.selectedMonth;

    lastDay = new Date( selectedYear, selectedMonth+1, 0).getDate();

    const nextDayHandler = ( d ) => {
        console.log(selectedDay);

        let change = d;
        if( d < lastDay ){
            change++;
        }else{
            change = 1;
        }
        console.log(change);
        setSelectedDay(change);
    }

    const prevDayHandler = ( d ) => {
        console.log(selectedDay);

        let change = d;

        if( d > 1 ){
            change--;
        }else{
            change = lastDay;
        }
        console.log(change);
        setSelectedDay(change);
    }

    return (
      <Modal
        {...props}
        aria-labelledby="contained-modal-title-vcenter"
        centered
      >
        <Modal.Header className="dayModal_header">
            <div className="dateBox">
                <img src={leftArrow} className="modalController" onClick={ ()=>{
                    prevDayHandler(selectedDay);
                }}/>
                <div className="dayBox">
                    <p className="modalDay">{ selectedDay<10 ? "0"+selectedDay : selectedDay}</p>
                </div>
                <div className="monthBox">
                    <p className="modalYear">{props.selectedYear}</p>
                    <p className="modalMonth">{props.year[props.selectedMonth]}</p>
                </div>
                <img src={rightArrow} className="modalController" onClick={ ()=>{
                    nextDayHandler(selectedDay);
                }}/>
            </div>
        </Modal.Header>
        <Modal.Body className="dayModal_body">

        </Modal.Body>
        <Modal.Footer>
          <Button onClick={props.onHide}>Close</Button>
        </Modal.Footer>
      </Modal>
    );
}

export default DayModal;