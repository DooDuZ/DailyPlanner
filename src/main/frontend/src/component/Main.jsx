import React, { useState, useEffect } from 'react';
import Calendar from './Calendar.jsx';
import styles from '../css/Index.css';
import axios from 'axios';

export default function Main(props){
    return (
        <div>
            <Calendar />
        </div>
    )
}