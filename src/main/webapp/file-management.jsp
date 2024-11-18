<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>文件管理</title>
    <!-- 引入Bulma CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bulma@1.0.2/css/bulma.min.css" rel="stylesheet">
    <style>
        html, body {
            height: 100%;
        }

        body {
            display: flex;
            flex-direction: column;
        }

        .section {
            flex-grow: 1;
        }

        /* Tab 栏样式 */
        .tabs {
            margin-bottom: 20px;
        }

        .tab-content {
            display: none;
        }

        .tab-content.active {
            display: block;
        }

        /* 审核流程样式 */
        .status-flow {
            display: flex;
            align-items: center;
            justify-content: space-between;
            position: relative;
        }

        .status-circle {
            width: 20px;
            height: 20px;
            border-radius: 50%;
            background-color: #e0e0e0;
            position: relative;
            z-index: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 12px;
            color: white;
        }

        .status-circle.active {
            background-color: #48c774;
            animation: pulse 1.5s infinite;
        }

        .status-circle.completed {
            background-color: #3273dc;
        }

        .status-circle.rejected {
            background-color: #ff3860;
        }

        .status-line {
            flex-grow: 1;
            height: 4px;
            background-color: #e0e0e0;
            position: relative;
            z-index: 0;
            margin: 0 5px;
        }

        .status-line.active {
            background-color: #48c774;
            animation: flow 1.5s infinite linear;
            background-size: 200% 100%;
            background-image: linear-gradient(to right, #48c774 50%, #e0e0e0 50%);
        }

        @keyframes pulse {
            0% {
                transform: scale(1);
            }
            50% {
                transform: scale(1.2);
            }
            100% {
                transform: scale(1);
            }
        }

        /* 退出按钮样式 */
        .logout-button {
            position: absolute;
            top: 10px;
            right: 10px;
        }

        /* 突出显示文件名 */
        .file-name {
            font-weight: bold;
            color: #3273dc;
        }
    </style>
    <script type="text/javascript">
        // 切换 Tab 栏
        function showTab(tabId) {
            var contents = document.getElementsByClassName("tab-content");
            for (var i = 0; i < contents.length; i++) {
                contents[i].classList.remove("active");
            }
            document.getElementById(tabId).classList.add("active");

            var tabs = document.querySelectorAll(".tabs li");
            for (var i = 0; i < tabs.length; i++) {
                tabs[i].classList.remove("is-active");
            }
            document.getElementById(tabId + "-tab").classList.add("is-active");
        }

        // 退出登录
        function logout() {
            if (confirm('确定要退出登录吗？')) {
                window.location.href = '/logout';
            }
        }

        // 文件选择后显示文件名
        function handleFileSelect(event) {
            var fileName = document.getElementById('fileName');
            if (event.target.files.length > 0) {
                var file = event.target.files[0];
                if (file.size > 20 * 1024 * 1024) { // 20MB
                    alert('文件大小不能超过20MB');
                    event.target.value = ''; // 清空选择的文件
                    fileName.textContent = '未选择文件';
                } else {
                    fileName.textContent = file.name;
                }
            } else {
                fileName.textContent = '未选择文件';
            }
        }

        // 提交表单并显示上传进度
        function handleFormSubmit(event) {
            event.preventDefault(); // 阻止表单默认提交

            var formData = new FormData(event.target);
            var xhr = new XMLHttpRequest();

            xhr.open('POST', event.target.action, true);

            // 确保每次上传开始时显示进度条
            var progressBar = document.getElementById('uploadProgress');
            progressBar.style.display = 'block';
            progressBar.value = 0; // 重置进度条

            xhr.upload.onprogress = function (event) {
                if (event.lengthComputable) {
                    var percentComplete = (event.loaded / event.total) * 100;
                    progressBar.value = percentComplete;
                    progressBar.classList.remove('is-danger');
                    progressBar.classList.add('is-primary');
                }
            };

            xhr.onload = function () {
                if (xhr.status === 200) {
                    alert('文件上传成功！');
                    progressBar.style.display = 'none';
                    // 获取当前分页参数
                    var currentPage = new URLSearchParams(window.location.search).get('page') || 1;
                    // 刷新页面并保留当前分页参数
                    window.location.href = '/files?page=' + currentPage;
                } else {
                    alert('文件上传失败！');
                    progressBar.classList.remove('is-primary');
                    progressBar.classList.add('is-danger');
                }
            };

            xhr.send(formData);
        }
    </script>
</head>
<body class="has-background-light">

<section class="section">
    <div class="container">
        <h2 class="title">文件管理</h2>

        <!-- 显示上传反馈信息 -->
        <c:if test="${not empty message}">
            <div class="notification is-success">
                    ${message}
            </div>
        </c:if>

        <!-- 退出登录按钮 -->
        <button class="button is-danger logout-button" onclick="logout()">退出登录</button>

        <!-- Tab 栏 -->
        <div class="tabs is-boxed">
            <ul>
                <li id="file-list-tab" class="is-active" onclick="showTab('file-list')"><a>已上传的文件</a></li>
                <li id="file-upload-tab" onclick="showTab('file-upload')"><a>上传新文件</a></li>
            </ul>
        </div>

        <!-- 文件列表内容 -->
        <div id="file-list" class="tab-content active">
            <h3 class="subtitle">已上传的文件</h3>

            <table class="table is-fullwidth is-striped">
                <thead>
                <tr>
                    <th>序号</th>
                    <c:if test="${currentUser.role eq 'SUPER_ADMINISTRATOR'}">
                        <th>上传用户</th>
                    </c:if>
                    <th>文件名</th>
                    <th>上传时间</th>
                    <c:if test="${currentUser.role ne 'SUPER_ADMINISTRATOR'}">
                        <th>审核人</th>
                    </c:if>
                    <th>审核时间</th>
                    <th>审核状态</th>
                    <th>文件大小</th>
                    <th>操作</th>
                    <c:if test="${currentUser.role eq 'SUPER_ADMINISTRATOR'}">
                        <th>审核操作</th>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="file" items="${uploadedFiles}" varStatus="status">
                    <tr>
                        <td>${(currentPage - 1) * 10 + status.index + 1}</td>
                        <c:if test="${currentUser.role eq 'SUPER_ADMINISTRATOR'}">
                            <td>${file.uploadPersonName}</td>
                        </c:if>
                        <td>${file.documentName}</td>
                        <td><fmt:formatDate value="${file.uploadTime}" pattern='yyyy-MM-dd HH:mm:ss'/></td>
                        <c:if test="${currentUser.role ne 'SUPER_ADMINISTRATOR'}">
                            <td>${file.reviewPersonName}</td>
                        </c:if>
                        <td><fmt:formatDate value="${file.reviewTime}" pattern='yyyy-MM-dd HH:mm:ss'/></td>
                        <td>
                            <div class="status-flow">
                                <div class="status-circle <c:choose>
                                    <c:when test='${file.status eq 0}'>status-unknown</c:when>
                                    <c:when test='${file.status eq 2}'>active</c:when>
                                    <c:when test='${file.status eq 1}'>completed</c:when>
                                    <c:when test='${file.status eq 3}'>rejected</c:when>
                                </c:choose>">开
                                </div>
                                <div class="status-line <c:if test='${file.status gt 0}'>active</c:if>"></div>
                                <div class="status-circle <c:choose>
                                    <c:when test='${file.status eq 2}'>active</c:when>
                                    <c:when test='${file.status eq 1}'>completed</c:when>
                                    <c:when test='${file.status eq 3}'>rejected</c:when>
                                </c:choose>">审
                                </div>
                                <div class="status-line <c:if test='${file.status eq 1 || file.status eq 3}'>active</c:if>"></div>
                                <div class="status-circle <c:if test='${file.status eq 1}'>completed</c:if>">通</div>
                                <div class="status-circle <c:if test='${file.status eq 3}'>rejected</c:if>">拒</div>
                            </div>
                        </td>
                        <td>${file.size} KB</td>
                        <td><a href="/download?fileId=${file.docId}" class="button is-small is-link">下载</a></td>
                        <c:choose>
                            <c:when test="${file.status eq 2 && currentUser.role eq 'SUPER_ADMINISTRATOR'}">
                                <td>
                                    <form action="/review" method="post"
                                          onsubmit="return confirm('确定要提交此审核操作吗？')">
                                        <input type="hidden" name="fileId" value="${file.docId}">
                                        <div class="select is-small">
                                            <select name="status">
                                                <option value="1">通过</option>
                                                <option value="3">不通过</option>
                                            </select>
                                        </div>
                                        <input type="submit" value="审核" class="button is-small is-primary">
                                    </form>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${currentUser.role eq 'SUPER_ADMINISTRATOR'}">
                                        <td>
                                            <button class="button is-small" disabled
                                                    style="background-color: #e0e0e0; color: #a0a0a0; cursor: not-allowed;">
                                                禁止操作
                                            </button>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td></td>
                                        <!-- 非管理员用户看不到任何内容 -->
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页控制 -->
            <nav class="pagination is-centered" role="navigation" aria-label="pagination">
                <c:if test="${currentPage > 1}">
                    <a class="pagination-previous" href="/files?page=${currentPage - 1}">上一页</a>
                </c:if>
                <c:if test="${currentPage < totalPages}">
                    <a class="pagination-next" href="/files?page=${currentPage + 1}">下一页</a>
                </c:if>
                <ul class="pagination-list">
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <li>
                            <a class="pagination-link <c:choose>
                                        <c:when test='${i == currentPage}'>is-current</c:when>
                                        <c:otherwise></c:otherwise>
                                      </c:choose>"
                               href="/files?page=${i}">
                                    ${i}
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </div>
        <!-- 文件上传内容 -->
        <div id="file-upload" class="tab-content">
            <h3 class="subtitle">上传新文件</h3>
            <form id="uploadForm" action="/upload" method="post" enctype="multipart/form-data"
                  onsubmit="handleFormSubmit(event)">
                <div class="field">
                    <div class="file is-boxed has-name">
                        <label class="file-label">
                            <input class="file-input" type="file" name="file" id="fileInput" required
                                   onchange="handleFileSelect(event)">
                            <span class="file-cta">
                                <span class="file-icon">
                                    <i class="fas fa-upload"></i>
                                </span>
                                <span class="file-label">
                                    选择文件
                                </span>
                            </span>
                            <span class="file-name" id="fileName">未选择文件</span>
                        </label>
                    </div>
                </div>
                <div class="field">
                    <progress id="uploadProgress" class="progress is-primary" value="0" max="100"
                              style="display:none;"></progress>
                </div>
                <div class="field">
                    <input type="submit" value="上传" class="button is-primary">
                </div>
            </form>
        </div>
    </div>
</section>

</body>
</html>
