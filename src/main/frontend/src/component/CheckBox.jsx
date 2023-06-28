import react, {useEffect, useState} from 'react';
import styles from '../css/checkBox.css'

export default function CheckBox( props ){

    const [checked, setChecked] = useState(false);

    return (
        <div className="check_wrap">
            <label className={checked ? "check_btn_label_true" : "check_btn_label_false"} onClick={ ()=>{ props.listHandler(props.tno, !checked)} }>
                <input type="checkbox" className="check_btn" checked={checked} onClick={ () => {setChecked(!checked);} } />
            </label>
        </div>
    )
}