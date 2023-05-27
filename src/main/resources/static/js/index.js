
function regist(){
    let form = document.querySelector('.todoForm');
    let formData = new FormData(form);

    formData.set("pno", 1);

    console.log(document.querySelector('.timeData'));
    console.log(document.querySelector('.timeData').value);

    $.ajax({
        url : "/todo/duty",
        type : 'POST',
        data : formData,
        contentType : false,
        processData : false,
        error : (err)=>{
            console.log(err);
        },
        success : (re)=>{
            console.log(re);
        }
    })
}

getList();
function getList(){
    const todoList = document.querySelector('.todoList');

    $.ajax({
        url : "/todo/list",
        type : 'GET',
        error : (err)=>{
            console.log("에러 발생!");
            console.log(err);
        },
        success : (re)=>{
            console.log(re);
            console.log(typeof(re));

            if(re==""){ return; }

            let html = "";

            re.forEach( e => {
                let complete = e.completed ? "완료" : "-";
                html += `<li> <span>${e.ttitle}</span> <span onclick="completeTodo(${e.tno}, ${!e.completed})"> ${complete} </span> </li>`;

            } );

            todoList.innerHTML = html;
        }
    })
}

function completeTodo( tno, isCompleted ){

    if(!confirm("상태를 변경 하시겠습니까?")){ return; }

    let data = { "tno" : tno, "completed" : isCompleted}

    console.log("==============data==============");
    console.log(data);
    console.log("================================");

    $.ajax({
        url : "/todo/completed",
        type : "PUT",
        data : JSON.stringify(data),
        contentType : "application/json",
        error : (err)=>{
            console.log(err);
        },
        success : (re)=>{
            console.log(re);
            getList();
        }
    })
}