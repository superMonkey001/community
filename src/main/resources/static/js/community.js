/*提交回复*/
function post() {
    var questionId = $("#question_id")[0].value;
    var commentContent = $("#comment_content").val();
    comment2target(questionId, 1, commentContent);
}

//抽取方法
function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容~~~");
        return;
    }
    $.ajax(
        {
            type: "post",
            url: "/comment",
            contentType: 'application/json',
            data: JSON.stringify({
                "parentId": targetId,
                "content": content,
                "type": type
            }),
            success: function (param) {
                if (param.code == 200) {
                    $("#comment_content").val("");
                    location.reload();
                } else {
                    if (param.code == 2003) {
                        var isAccept = confirm(param.message);
                        if (isAccept) {
                            window.open("https://github.com/login/oauth/authorize?client_id=cd40e3e26ced2f5e81d0&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                            // window.open("https://github.com/login/oauth/authorize");
                            window.localStorage.setItem("closeable", "true")
                        }
                    } else {
                        alert(param.message);
                    }
                }
                console.log(param);
            },
            dataType: "json"
        }
    )
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}

/*展开二级评论*/
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            comments.addClass("in");
            e.setAttribute("data-collapse", true);
            e.classList.add("active");
            $("#input-" + id).val("");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                e.setAttribute("data-collapse", true);
                e.classList.add("active");
                $("#input-" + id).val("");
            });
        }
    }
}