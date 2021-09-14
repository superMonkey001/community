function post(){
    var questionId = $("#question_id")[0].value;
    var commentContent = $("#comment_content").val();
    if (!commentContent)
    {
        alert("不能回复空内容~~~");
        return;
    }
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
                if(param.code==200)
                {
                    location.reload();
                }
                else
                {
                    if(param.code==2003)
                    {
                        var isAccept = confirm(param.message);
                        if(isAccept)
                        {
                                window.open("https://github.com/login/oauth/authorize?client_id=cd40e3e26ced2f5e81d0&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                                window.localStorage.setItem("closeable","true")
                        }
                    }else
                    {
                        alert(param.message);
                    }
                }
                console.log(param);
            },
            dataType:"json"
        }
    )
}