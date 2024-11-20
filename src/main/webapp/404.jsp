<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>页面未找到</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #282c34;
            color: #ffffff;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .error-container {
            text-align: center;
        }

        h1 {
            font-size: 10rem;
            margin: 0;
            color: #61dafb;
            animation: float 3s ease-in-out infinite;
        }

        p {
            font-size: 1.5rem;
            margin: 20px 0;
        }

        a {
            color: #61dafb;
            text-decoration: none;
            font-weight: bold;
            border: 2px solid #61dafb;
            padding: 10px 20px;
            border-radius: 5px;
            transition: background-color 0.3s, color 0.3s;
        }

        a:hover {
            background-color: #61dafb;
            color: #282c34;
        }

        @keyframes float {
            0%, 100% {
                transform: translatey(0px);
            }
            50% {
                transform: translatey(-20px);
            }
        }
    </style>
</head>
<body>
<div class="error-container">
    <h1>404</h1>
    <p>抱歉，您访问的页面不存在......</p>
    <p><a href="/">返回首页</a></p>
</div>
</body>
</html>
