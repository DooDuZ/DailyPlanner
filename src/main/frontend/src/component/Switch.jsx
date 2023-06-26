import react, {useEffect} from 'react';
import Form from 'react-bootstrap/Form';

function Switch(props) {

  return (
    <Form>
      <Form.Check // prettier-ignore
        type="switch"
        id="custom-switch"
        label="완료"
        checked={props.checked}
        onClick = { (e)=>{
            // console.log(e.target.value);
            e.target.value = !props.checked;
            props.setChecked();
        }}
      />
    </Form>
  );
}

export default Switch;