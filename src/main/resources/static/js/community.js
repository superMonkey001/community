function post(){
    var questionId = $("#question_id")[0].value;
    var commentContent = $("#comment_content").val();
    $.ajax(
        {
            type:"post",
            url:"/comment",
            contentType:'application/json',
            data:JSON.stringify({
                "parentId":questionId,
                "content":commentContent,
                "type":1
            }),
            success:function (param) {
                console.log(param);
            },
            dataType:"json"
        }
    )
}