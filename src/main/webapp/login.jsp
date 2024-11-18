<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>登录页面</title>
    <!-- 引入Bulma CSS 1.0.2 -->
    <link href="https://cdn.jsdelivr.net/npm/bulma@1.0.2/css/bulma.min.css" rel="stylesheet">
    <!-- 引入CryptoJS库 -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.1.1/crypto-js.min.js"></script>
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
    <div class="container">
        <div class="columns is-centered">
            <div class="column is-half-tablet is-one-third-desktop is-one-quarter-widescreen">
                <h2 class="title is-2 has-text-centered">用户登录</h2>

                <!-- 显示错误信息 -->
                <c:if test="${not empty errorMessage}">
                    <div class="notification is-danger">
                            ${errorMessage}
                    </div>
                </c:if>

                <form id="loginForm" action="/login" method="post">
                    <div class="field">
                        <label class="label" for="username">用户名</label>
                        <div class="control">
                            <input class="input" type="text" id="username" name="username" required>
                        </div>
                    </div>
                    <div class="field">
                        <label class="label" for="password">密码</label>
                        <div class="control">
                            <input class="input" type="password" id="password" name="password" required>
                        </div>
                    </div>
                    <div class="field">
                        <button type="submit" class="button is-primary is-fullwidth">登录</button>
                    </div>
                </form>

                <!-- 跳转到注册页面 -->
                <p class="has-text-centered">还没有账号？<a href="/register">立即注册</a></p>
            </div>
        </div>
    </div>
</div>

<script>
    document.getElementById('loginForm').addEventListener('submit', function (event) {
        event.preventDefault(); // 阻止默认表单提交

        var passwordField = document.getElementById('password');
        var password = passwordField.value;

        // 使用 CryptoJS 对密码进行加密
        var encryptedPassword = CryptoJS.SHA256(password).toString();

        // 将加密后的密码设置为表单字段的值
        passwordField.value = encryptedPassword;

        // 提交表单
        this.submit();
    });
</script>
</body>
</html>
