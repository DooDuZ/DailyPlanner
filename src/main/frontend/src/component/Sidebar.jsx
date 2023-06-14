import React, { useState, useEffect, useRef } from 'react';
import styles from '../css/sidebar.css';
import axios from 'axios';

export default function Sidebar( props ){

    return (
        <div className="sidebar">
            <div className="sideTitle">
                Planner List
            </div>
            <div className="sideContent">
                <div className="personalPlanner">
                    {
                        props.plannerList.map( (e)=>{
                            return(
                                <h3> {e.pname} </h3>
                            )
                        })
                    }
                </div>
            </div>
        </div>
    )
}