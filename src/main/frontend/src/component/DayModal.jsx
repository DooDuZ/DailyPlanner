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

    const year = [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ];
    const constellation = [];

    const [ selectedDay, setSelectedDay ] = useState(props.selectedDay);

    selectedYear = props.selectedYear;
    selectedMonth = props.selectedMonth;

    lastDay = new Date( selectedYear, selectedMonth+1, 0).getDate();

    const nextDayHandler = ( d ) => {
        let change = d;
        if( d < lastDay ){
            change++;
        }else{
            change = 1;
        }

        setSelectedDay(change);
    }

    const prevDayHandler = ( d ) => {
        let change = d;

        if( d > 1 ){
            change--;
        }else{
            change = lastDay;
        }

        setSelectedDay(change);
    }

    return (
      <Modal
        {...props}
        aria-labelledby="contained-modal-title-vcenter"
        centered
      >
        <div className = "stella_img">
            <img src= {"/img/stars/게자리_icon.png"} />
        </div>
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
                    <p className="modalMonth">{year[props.selectedMonth]}</p>
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