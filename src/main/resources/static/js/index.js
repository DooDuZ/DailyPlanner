
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