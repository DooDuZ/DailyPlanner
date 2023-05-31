

// form -> JSON으로 수정 필요 !!!
function regist(){
    let form = document.querySelector('.todoForm');

    let data = {
        "title" : form[0].value,
        "text" : form[1].value,
        "stime" : form[2].value,
        "etime" : form[3].value,
        "pno" : 1
    }

    console.log(data);

    $.ajax({
        url : "/todo/duty",
        type : 'POST',
        data : JSON.stringify(data),
        contentType : "application/json",
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
        url : "/todo/list?pno=1",
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
                html += `<li> <span>${e.title}</span> <span onclick="completeTodo(${e.tno}, ${!e.completed})"> ${complete} </span> </li>`;
            });

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