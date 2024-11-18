<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>注册成功</title>
    <!-- 引入Bulma CSS 1.0.2 -->
    <link href="https://cdn.jsdelivr.net/npm/bulma@1.0.2/css/bulma.min.css" rel="stylesheet">
    <script type="text/javascript">
        var countdown = 5;  // 倒计时5秒
        function startCountdown() {
            var countdownElement = document.getElementById("countdown");
            var interval = setInterval(function () {
                countdown--;
                countdownElement.innerText = countdown;
                if (countdown <= 0) {
                    clearInterval(interval);
                    window.location.href = "/login";  // 倒计时结束后跳转到登录页面
                }
            }, 1000);
        }

        window.onload = startCountdown;
    </script>
    <style>
        /* 确保html和body占满整个视口高度 */
        html, body {
            height: 100%;
            margin: 0;
        }

        /* 使用Flexbox让内容上下左右居中 */
        .full-height {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
    </style>
</head>
<body class="has-background-light">
<div class="full-height">
    <div class="container has-text-centered">
        <h2 class="title is-2">注册成功！</h2>
        <p class="subtitle is-4">恭喜您，注册成功！您将在 <span id="countdown">5</span> 秒后自动跳转到登录页面。</p>
        <p>如果没有跳转，您可以 <a href="/login" class="button is-link">点击这里</a> 手动跳转。</p>
    </div>
</div>
</body>
</html>
