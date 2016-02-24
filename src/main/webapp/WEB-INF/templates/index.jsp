<%--suppress ALL --%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html class="no-js">

<head>
    <title>Admin Home Page</title>

    <style type="text/css">
        .loading{
            width:160px;
            height:56px;
            position: absolute;
            top:50%;
            left:50%;
            line-height:56px;
            color:#fff;
            padding-left:60px;
            font-size:15px;
            background: #000 url(images/loader.gif) no-repeat 10px 50%;
            opacity: 0.7;
            z-index:9999;
            -moz-border-radius:20px;
            -webkit-border-radius:20px;
            border-radius:20px;
            filter:progid:DXImageTransform.Microsoft.Alpha(opacity=70);
        }
    </style>
    <!-- Bootstrap -->
    <link href="<%=request.getContextPath()%>/styles/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="<%=request.getContextPath()%>/styles/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet"
          media="screen">
    <link href="<%=request.getContextPath()%>/styles/vendors/easypiechart/jquery.easy-pie-chart.css" rel="stylesheet"
          media="screen">
    <link href="<%=request.getContextPath()%>/styles/assets/styles.css" rel="stylesheet" media="screen">
    <!--/.fluid-container-->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/vendors/morris/morris.css">
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <script src="<%=request.getContextPath()%>/styles/vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>

</head>

<body>
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a class="brand" href="#">Admin Panel</a>

            <div class="nav-collapse collapse">
                <ul class="nav pull-right">
                    <li class="dropdown">
                        <a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"> <i
                                class="icon-user"></i> Vincent Gabriel <i class="caret"></i>

                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a tabindex="-1" href="#">Profile</a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a tabindex="-1" href="user/invalidate">Logout</a>
                            </li>
                        </ul>
                    </li>
                </ul>
                <ul class="nav">
                    <li class="active">
                        <a href="#">Dashboard</a>
                    </li>
                    <li class="dropdown">
                        <a href="#" data-toggle="dropdown" class="dropdown-toggle">Settings <b class="caret"></b>

                        </a>
                        <ul class="dropdown-menu" id="menu1">
                            <li>
                                <a href="#">Tools <i class="icon-arrow-right"></i>

                                </a>
                                <ul class="dropdown-menu sub-menu">
                                    <li>
                                        <a href="#">Reports</a>
                                    </li>
                                    <li>
                                        <a href="#">Logs</a>
                                    </li>
                                    <li>
                                        <a href="#">Errors</a>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <a href="#">SEO Settings</a>
                            </li>
                            <li>
                                <a href="#">Other Link</a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">Other Link</a>
                            </li>
                            <li>
                                <a href="#">Other Link</a>
                            </li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">Content <i
                                class="caret"></i>

                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a tabindex="-1" href="#">Blog</a>
                            </li>
                            <li>
                                <a tabindex="-1" href="#">News</a>
                            </li>
                            <li>
                                <a tabindex="-1" href="#">Custom Pages</a>
                            </li>
                            <li>
                                <a tabindex="-1" href="#">Calendar</a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a tabindex="-1" href="#">FAQ</a>
                            </li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">Users <i
                                class="caret"></i>

                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a tabindex="-1" href="#">User List</a>
                            </li>
                            <li>
                                <a tabindex="-1" href="#">Search</a>
                            </li>
                            <li>
                                <a tabindex="-1" href="#">Permissions</a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>
</div>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span3" id="sidebar">
            <ul class="nav nav-list bs-docs-sidenav nav-collapse collapse">
                <li class="active">
                    <a href="index.jsp"><i class="icon-chevron-right"></i> Dashboard</a>
                </li>
                <li>
                    <a href="analysis/calendar.jsp"><i class="icon-chevron-right"></i> Calendar</a>
                </li>
                <li>
                    <a href="/analysis"><i class="icon-chevron-right"></i> Statistics (Charts)</a>
                </li>
                <li>
                    <a href="analysis/form.jsp"><i class="icon-chevron-right"></i> Forms</a>
                </li>
                <li>
                    <a href="analysis/tables.jsp"><i class="icon-chevron-right"></i> Tables</a>
                </li>
                <li>
                    <a href="analysis/buttons.jsp"><i class="icon-chevron-right"></i> Buttons & Icons</a>
                </li>
                <li>
                    <a href="analysis/editors.jsp"><i class="icon-chevron-right"></i> WYSIWYG Editors</a>
                </li>
                <li>
                    <a href="analysis/interface.jsp"><i class="icon-chevron-right"></i> UI & Interface</a>
                </li>
                <li>
                    <a href="#"><span class="badge badge-success pull-right">731</span> Orders</a>
                </li>
                <li>
                    <a href="#"><span class="badge badge-success pull-right">812</span> Invoices</a>
                </li>
                <li>
                    <a href="#"><span class="badge badge-info pull-right">27</span> Clients</a>
                </li>
                <li>
                    <a href="#"><span class="badge badge-info pull-right">1,234</span> Users</a>
                </li>
                <li>
                    <a href="#"><span class="badge badge-info pull-right">2,221</span> Messages</a>
                </li>
                <li>
                    <a href="#"><span class="badge badge-info pull-right">11</span> Reports</a>
                </li>
                <li>
                    <a href="#"><span class="badge badge-important pull-right">83</span> Errors</a>
                </li>
                <li>
                    <a href="#"><span class="badge badge-warning pull-right">4,231</span> Logs</a>
                </li>
            </ul>
        </div>

        <!--/span-->
        <div class="span9" id="content">
            <div class="row-fluid">
                <div id="craw-Info">

                </div>
                <%--<div class="alert alert-success">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <h4>Success</h4>
                    The operation completed successfully</div>--%>
                <!-- block -->
                <div class="block">

                    <div class="navbar navbar-inner block-header">
                        <div class="muted pull-left">
                            选择抓取城市：
                            <select id="citySelect" class="chzn-select">
                                <c:forEach items="${cityList}" var="city">
                                    <option>${city}</option>
                                </c:forEach>
                            </select>
                            &nbsp;&nbsp;&nbsp;
                            选择抓取职位类型：
                            <select id="psTypeSelect" class="chzn-select">
                                <c:forEach items="${psTypeList}" var="psType">
                                    <option>${psType}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="pull-right">
                            <button id="startCraw" class="btn btn-primary">开始抓取</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row-fluid">
                <!-- block -->
                <div class="block">
                    <div class="navbar navbar-inner block-header">
                        <div id="systemState" class="muted pull-left">系统状态</div>
                        <div class="pull-right"><span class="badge badge-info">3</span>

                        </div>
                    </div>

                    <div class="block-content collapse in">
                        <button id="start" class="btn btn-primary">开始实时监控</button>
                        <button id="stop" class="btn btn-primary">停止实时监控</button>
                        <br/>

                        <div class="span3">
                            <div class="chart" data-percent="50"><span id="cpuRate">加载中...</span></div>
                            <div class="chart-bottom-heading"><span class="label label-info">CPU使用率</span>

                            </div>
                        </div>
                        <div class="span3" style="float: right">
                            <table class="table table-bordered table-striped">
                                <tbody>
                                <tr>
                                    <td>
                                        <span class="label">服务器总内存</span>
                                    </td>
                                    <td>
                                        <span id="totalMemory">加载中...</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="label">服务器剩余内存</span>
                                    </td>
                                    <td>
                                        <span id="freeMemory">加载中...</span>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>
            </div>

            <div class="row-fluid">
                <!-- block -->
                <div class="block">
                    <div class="navbar navbar-inner block-header">
                        <div id="crawDataDir" class="muted pull-left">已抓取数据目录</div>
                        <div id="crawDataDirs" class="pull-right"><span class="badge badge-info">20</span>

                        </div>
                    </div>

                    <div id="crawDataList" class="block-content collapse in">

                    </div>

                    <div id="crawDataShow" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidde
                         n="true">
                        <div class="modal-header">
                            <%-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>  --%>
                            <h3 id="myModalLabel">抓取数据</h3>
                        </div>
                        <div id="crawDataContent" class="modal-body">

                        </div>

                        <div id="crawDataFoot" class="modal-footer">

                        </div>

                    </div>

                </div>
            </div>

            <div class="row-fluid">
                <div id="craw-Info">

                </div>
                <%--<div class="alert alert-success">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <h4>Success</h4>
                    The operation completed successfully</div>--%>
                <!-- block -->
                <div class="block">

                    <div class="navbar navbar-inner block-header">
                        <div class="muted pull-left">
                            选择城市：
                            <select id="citySelectSearch" class="chzn-select">
                                <c:forEach items="${cityList}" var="city">
                                    <option>${city}</option>
                                </c:forEach>
                            </select>
                            &nbsp;&nbsp;&nbsp;
                            选择职位类型：
                            <select id="psTypeSelectSearch" class="chzn-select">
                                <c:forEach items="${psTypeList}" var="psType">
                                    <option>${psType}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="pull-right">
                            <button id="startCrawSearch" class="btn btn-primary">开始查询</button>
                        </div>
                    </div>

                    <div id="crawDataSearchShow" class="block-content collapse in">
                        <table class='table table-striped'>
                            <thead>
                                <tr>
                                    <th>职位</th>
                                    <th>公司</th>
                                    <th>情况</th>
                                    <th>投递数</th>
                                </tr>
                            </thead>
                            <tbody id="tbodySearch">

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <hr>
    <footer>
        <p>&copy; Vincent Gabriel 2013</p>
    </footer>
    <div id="message"></div>
</div>
<!--/.fluid-container-->
<script src="<%=request.getContextPath()%>/styles/vendors/jquery-1.9.1.min.js"></script>
<script src="<%=request.getContextPath()%>/styles/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/styles/vendors/easypiechart/jquery.easy-pie-chart.js"></script>

<script src="<%=request.getContextPath()%>/styles/vendors/jquery.knob.js"></script>
<script src="<%=request.getContextPath()%>/styles/vendors/raphael-min.js"></script>
<script src="<%=request.getContextPath()%>/styles/vendors/morris/morris.min.js"></script>

<script src="<%=request.getContextPath()%>/styles/vendors/flot/jquery.flot.js"></script>
<script src="<%=request.getContextPath()%>/styles/vendors/flot/jquery.flot.categories.js"></script>
<script src="<%=request.getContextPath()%>/styles/vendors/flot/jquery.flot.pie.js"></script>
<script src="<%=request.getContextPath()%>/styles/vendors/flot/jquery.flot.time.js"></script>
<script src="<%=request.getContextPath()%>/styles/vendors/flot/jquery.flot.stack.js"></script>
<script src="<%=request.getContextPath()%>/styles/vendors/flot/jquery.flot.resize.js"></script>
<script src="<%=request.getContextPath()%>/styles/assets/scripts.js"></script>
<%--suppress JSUnfilteredForInLoop --%>
<script>
    $(function () {

        var dataSearch = {

            submitData: function(psType, city) {
                $.ajax({
                    url : "/index/crawDataSearch",
                    type : "POST",
                    dataType : "text",
                    data: {psType : psType, city : city},
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    success: function(data, dataStatus) {
                        $('#tbodySearch').html("");
                        returnJson = eval("(" + data + ")");
                        if(parseInt(returnJson.errcode) === 0) {
                            var dataList = returnJson.data.result;
                            $.each(dataList, function (i, item) {
                                $('#tbodySearch').append("<tr><td>" + item.positionName + "</td><td>"
                                        + item.companyShortName + "</td><td>" + item.financeStage +
                                        "</td><td>" + item.deliverCount + "</td></tr>");
                            });
                        } else {
                            alert("查询无数据，数据未抓取或者加载");
                        }
                        $("#startCrawSearch").attr("class", "btn btn-primary");
                        $("#startCrawSearch").attr("disabled", false);
                        $("#startCrawSearch").text("开始查询");
                    },
                    error: dataSearch.onError
                });
            },
            onError: function () {
                console.log("dataSearch error");
            }
        };
        var crawDirList = {

            crawDirData : '',

            crawDirList : function () {
                $.ajax({
                    url : "/index/crawDataDirList",
                    type : "GET",
                    dataType : "text",
                    success: crawDirList.onSuccess,
                    error: crawDirList.onError
                });
            },

            reloadCrawl : function (psType) {

                $.ajax({
                    url : "/index/crawlDataAll",
                    type : "POST",
                    dataType : "text",
                    data : { psType : psType },
                    success : function (data, dataStatus) {
                        alert("后台正在抓取数据...");
                        $('#crawDataDbSubmit').remove();
                        $('#reloadCrawData').remove();
                    },
                    error : crawDirList.onError
                });

            },

            crawDataToDb : function (psType) {
                $.ajax({
                    url : "/index/crawDataToDb",
                    type : "GET",
                    data : { psType : psType },
                    dataType : "text",
                    success: function (data, dataStatus) {
                        alert("正在后台加载数据至数据库...");
                        crawDirList.crawDirList();
                        $('#crawDataDbSubmit').remove();
                    },
                    error: crawDirList.onError
                });
            },

            pullCrawDataList : function (psType, isLoad) {
                $.ajax({
                    url : "/index/crawData",
                    data: {psType : encodeURI(psType)},
                    type : "GET",
                    dataType : "text",
                    success: function(data, dataStatus) {
                        $('#crawDataShow #crawDataContent').html("");
                        returnJson = eval("(" + data + ")");
                        if(parseInt(returnJson.errcode) === 0) {
                            var dataList = returnJson.data.result;
                            $('#crawDataShow #crawDataContent').append("<table class='table table-striped'><thead><tr>" +
                                    "<th>投递人数</th><th>职位</th>" +
                                    "<th>公司名称</th></tr></thead><tbody>");
                            $.each(dataList, function (i, item) {
                                $('#crawDataShow #crawDataContent').append("<tr><td>" + item.deliverCount + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>" + item.positionName +
                                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>" + item.companyShortName + "</td></tr>");
                            });
                            $('#crawDataShow #crawDataContent').append("</tbody></table>");
                            if (!isLoad) {
                                $('#crawDataShow #crawDataFoot').html("<button class='btn' data-dismiss='modal' aria-hidden='true'>关闭" +
                                        "</button><button id='crawDataDbSubmit' class='btn btn-primary'>加载入库</button><button id='reloadCrawData' class='btn btn-primary'>重新抓取数据</button>");

                                $('#crawDataDbSubmit').click(submitEvent);
                                $('#reloadCrawData').click(reloadCrawl);

                                function submitEvent() {
                                    crawDirList.crawDataToDb(psType);
                                }
                            } else {
                                $('#crawDataShow #crawDataFoot').html("<button class='btn' data-dismiss='modal' aria-hidden='true'>关闭</button>");
                            }
                        } else {
                            $('#crawDataShow #crawDataContent').append("<p>目录不存在或者数据异常，请重新抓取数据</p>");
                            $('#crawDataShow #crawDataFoot').html("<button class='btn' data-dismiss='modal' aria-hidden='true'>关闭</button>" +
                                    "<button id='reloadCrawData' class='btn btn-primary'>重新抓取数据</button>");
                            $('#reloadCrawData').click(reloadCrawl);
                        }
                        function reloadCrawl() {
                            crawDirList.reloadCrawl(psType);
                        }
                    },
                    error: crawDirList.onError
                });
            },

            onSuccess: function (data, dataStatus) {
                try {
                    returnJson = eval("(" + data + ")");
                    if(parseInt(returnJson.errcode) === 0) {
                        $("#crawDataList").html("");
                        var dirList = returnJson.data.dirList;
                        $.each(dirList, function(i, item) {
                            var strList = item.split("_");
                            var isLoad;
                            if(strList.length == 1) {
                                item = crawDirList.replaceSpecial(item);
                                $("#crawDataList").append("<button id=" + item + " class='btn btn-primary'>" + item + "</button>&nbsp;&nbsp;");
                                $('#' + item).click(clientEvent);
                                isLoad = false;
                            } else {
                                item = crawDirList.replaceSpecial(strList[0]);
                                $("#crawDataList").append("<button id=" + item + " class='btn btn-inverse'>" + item + "目录数据已加载</button>&nbsp;&nbsp;");
                                $('#' + item).click(clientEvent);
                                isLoad = true;
                            }
                            function clientEvent() {
                                $('#crawDataShow').modal({
                                    backdrop:true,
                                    keyboard:false,
                                    show:true
                                });
                                $('#crawDataShow #crawDataContent').html("<p>正在加载数据...</p>");
                                crawDirList.pullCrawDataList(crawDirList.replaceSpecial(item), isLoad);
                            }
                        });
                    } else {
                        $("#crawDataList").html("<button class='btn btn-inverse'>数据目录不存在</button>&nbsp;&nbsp;");
                    }
                } catch (e) {
                    crawDirList.onError();
                }
            },
            onError: function () {
                console.log("crawDataList error;");
            },
            replaceSpecial: function(psType) {
                if(psType == 'c++') {
                    psType = 'cplus';
                }
                if(psType == 'c#') {
                    psType = 'cs';
                }
                return psType;
            },
        };
        crawDirList.crawDirList();

        var crawLagouData = {
            'cityPinyin' : '',
            'psTypePinyin' : '',

            crawData: function (city, psType) {
                $.ajax({
                    url: "/index/crawlData",
                    type: "POST",
                    data: {positionType : encodeURI(psType), cityName : encodeURI(city)},
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    dataType: "text",
                    success: crawLagouData.onSuccess,
                    error: crawLagouData.onError
                });
            },
            pollCrawState: function (psTypePinyin, cityPinyin) {
                $.ajax({
                    url: "/index/checkCrawData",
                    type: "POST",
                    data: {psTypePinyin : psTypePinyin, cityPinyin : cityPinyin},
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    dataType: "text",
                    success: crawLagouData.checkSuccess,
                    error: crawLagouData.onError
                });
            },
            checkSuccess: function (data, dataStatus) {
                returnJson = eval("(" + data + ")");
                $("#craw-Info").html("");
                if(parseInt(returnJson.errcode) === 0) {
                    $("#craw-Info").html("<div class='alert alert-success'><button type='button' class='close' data-dismiss='alert'>数据抓取成功</button><h4>完成</h4></div>");
                } else {
                    $("#craw-Info").html("<div class='alert alert-success'><button type='button' class='close' data-dismiss='alert'>后台正在处理数据...</button><h4>处理中...</h4></div>");
                    window.setTimeout(crawLagouData.pollCrawState(crawLagouData.psTypePinyin, crawLagouData.cityPinyin), 10000);
                }

            },
            onSuccess: function (data, dataStatus) {
                try {
                    returnJson = eval("(" + data + ")");
                    if(parseInt(returnJson.errcode) === 0) {
                        $("#craw-Info").html("");
                        crawLagouData.cityPinyin = returnJson.data.cityPinyin;
                        crawLagouData.psTypePinyin = returnJson.data.psTypePinyin;
                        $("#craw-Info").html("<div class='alert alert-success'><button type='button' class='close' data-dismiss='alert'>后台任务正在抓取数据，稍等...</button><h4>加载中...</h4></div>");
                        crawLagouData.pollCrawState(crawLagouData.psTypePinyin, crawLagouData.cityPinyin);
                    } else {
                        $("#craw-Info").html("<div class='alert alert-success'><button type='button' class='close' data-dismiss='alert'>任务已经抓取完成，请先清理数据!</button><h4>提示</h4></div>");
                    }
                } catch (e) {
                    updater.onError();
                }
            },
            onError: function () {
                console.log("crawData error;");
            }

        };

        $('#startCraw').bind("click", function () {
            var psType = $("#psTypeSelect option:selected").text();
            var city = $("#citySelect option:selected").text();
            crawLagouData.crawData(city, psType);
        });

        $('#startCrawSearch').bind("click", function () {
            var psType = $("#psTypeSelectSearch option:selected").text();
            var city = $("#citySelectSearch option:selected").text();
            $("#startCrawSearch").attr("class", "btn btn-info");
            $("#startCrawSearch").text("正在查询");
            $("#startCrawSearch").attr("disabled", true);
            dataSearch.submitData(psType, city);
        });

        // Morris Bar Chart
        // Easy pie charts
        $('.chart').easyPieChart({animate: 1000});

        var updater = {
            'stop': false,
            'rate': 50,
            nowTime: function () {
                var today = new Date();
                var year = today.getFullYear();
                var mouth = today.getMonth() + 1;
                var day = today.getDate();
                var str = "" + year + "年";
                str += mouth + "月";
                str += day + "日";

                var h = today.getHours();
                var m = today.getMinutes();
                m = checkTime(m);
                h = checkTime(h);
                $('#systemState').html("系统状态(" + str + " " + h + ":" + m + ")");
                interval = window.setTimeout(updater.nowTime, (60 - today.getSeconds()) * 1000);
                //个位数 补0
                function checkTime(i) {
                    if (i < 10) {
                        i = "0" + i
                    }
                    return i
                }
            },
            draw: function (rate) {
                $('.chart').each(function () {
                    $(this).data('easyPieChart').update(rate);
                    $(this).attr('data-percent', rate);
                    $('#cpuRate').text(rate + '%');
                });
            },
            poll: function () {
                $.ajax({
                    url: "/monitorSystemState",
                    type: "POST",
                    dataType: "text",
                    success: updater.onSuccess,
                    error: updater.onError
                });
            },
            onSuccess: function (data, dataStatus) {
                try {
                    if (data) {
                        var res = String($.trim(data));
                        var dataArray = res.split("\t");
                        updater.rate = parseInt(dataArray[0] * 100);
                        if(updater.rate == 0){
                            alert("当前系统未读取到cpu数据!")
                            updater.stop = true;
                        }
                        $("#totalMemory").text(' ' + dataArray[1]);
                        $("#freeMemory").text(' ' + dataArray[2]);
                        if(dataArray.length == 4) {
                            if (dataArray[3]) {
                                alert("批量插入数据成功");
                            } else {
                                alert("批量插入数据失败，请检查");
                            }
                        }
                    }
                }
                catch (e) {
                    updater.onError();
                    return;
                }
                updater.draw(updater.rate);
                if (!updater.stop) {
                    interval = window.setTimeout(updater.poll, 1);
                }
            },
            onError: function () {
                console.log("Poll error;");
            }
        };
        updater.poll();
        updater.nowTime();

        $('#stop').bind("click", function () {
            updater.stop = true;
            updater.draw(15);
        });

        $('#start').bind("click", function () {
            updater.stop = false;
            updater.poll();
        });
    });
</script>
</body>

</html>