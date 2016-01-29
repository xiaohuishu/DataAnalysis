<%--suppress ALL --%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
    
    <head>
        <title>Statistics</title>
        <!-- Bootstrap -->
        <link href="<%=request.getContextPath()%>/styles/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
        <link href="<%=request.getContextPath()%>/styles/bootstrap/css/bootstrap-multiselect.css" rel="stylesheet" media="screen">
        <link href="<%=request.getContextPath()%>/styles/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
        <link href="<%=request.getContextPath()%>/styles/assets/styles.css" rel="stylesheet" media="screen">
        <!--[if lte IE 8]><script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/styles/vendors/flot/excanvas.min.js"></script><![endif]-->
        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
        <script src="<%=request.getContextPath()%>/styles/vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>

        <script src="<%=request.getContextPath()%>/styles/chart/echarts.min.js"></script>
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
                                <a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"> <i class="icon-user"></i> Vincent Gabriel <i class="caret"></i>

                                </a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a tabindex="-1" href="#">Profile</a>
                                    </li>
                                    <li class="divider"></li>
                                    <li>
                                        <a tabindex="-1" href="login.html">Logout</a>
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
                                <a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">Content <i class="caret"></i>

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
                                <a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">Users <i class="caret"></i>

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
                        <li>
                            <a href="/index"><i class="icon-chevron-right"></i> Dashboard</a>
                        </li>
                        <li>
                            <a href="calendar.html"><i class="icon-chevron-right"></i> Calendar</a>
                        </li>
                        <li class="active">
                            <a href="/analysis"><i class="icon-chevron-right"></i> Statistics (Charts)</a>
                        </li>
                        <li>
                            <a href="form.html"><i class="icon-chevron-right"></i> Forms</a>
                        </li>
                        <li>
                            <a href="tables.html"><i class="icon-chevron-right"></i> Tables</a>
                        </li>
                        <li>
                            <a href="buttons.html"><i class="icon-chevron-right"></i> Buttons & Icons</a>
                        </li>
                        <li>
                            <a href="editors.html"><i class="icon-chevron-right"></i> WYSIWYG Editors</a>
                        </li>
                        <li>
                            <a href="interface.html"><i class="icon-chevron-right"></i> UI & Interface</a>
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
                      <!-- morris stacked chart -->
                    <div class="row-fluid">
                        <!-- block -->
                        <div class="block">
                            <div class="navbar navbar-inner block-header">
                                <div id="systemState" class="muted pull-left">城市需求量</div>
                                <div class="pull-right"><span class="badge badge-info">${cityList.size()}</span>

                                </div>
                            </div>
                            <div class="block-content collapse in">

                                <div class="input-group btn-group">
                                    <span class="input-group-addon"><b class="glyphicon glyphicon-list-alt"></b></span>
                                    <select id="multiselect" multiple="multiple">
                                        <c:forEach items="${cityList}" var="city">
                                            <option>${city}</option>
                                        </c:forEach>
                                    </select>

                                    <button id="startDemandCity" class="btn btn-primary">分析</button>
                                </div>


                                <div id="catchart" style="width:100%;height:200px"></div>
                            </div>
                        </div>
                        <!-- /block -->
                    </div>

                    <div class="row-fluid">
                        <!-- block -->
                        <div class="block">
                            <div class="navbar navbar-inner block-header">
                                <div id="systemState" class="muted pull-left">城市薪水比例</div>
                                <div class="pull-right"><span class="badge badge-info">${cityList.size()}</span>

                                </div>
                            </div>
                            <div class="block-content collapse in">

                                <div class="input-group btn-group">
                                    <span class="input-group-addon"><b class="glyphicon glyphicon-list-alt"></b></span>
                                    <select id="selectSalaryByPsType" style="margin-bottom : 1px;">
                                        <c:forEach items="${psTypeList}" var="psType">
                                            <option>${psType}</option>
                                        </c:forEach>
                                    </select>

                                    <select id="selectSalaryByCity" multiple="multiple">
                                        <c:forEach items="${cityList}" var="city">
                                            <option>${city}</option>
                                        </c:forEach>
                                    </select>

                                    <button id="startDemandSalary" class="btn btn-primary">分析</button>
                                </div>
                                <br /><br /><br />
                                <div id="salaryChart" style="width:100%; height:500px;"></div>
                            </div>
                        </div>
                        <!-- /block -->
                    </div>

                </div>
            </div>
            <hr>
            <footer>
                <p>&copy; Vincent Gabriel 2013</p>
            </footer>
        </div>
        <!--/.fluid-container-->
        <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/vendors/morris/morris.css">


        <script src="<%=request.getContextPath()%>/styles/vendors/jquery-1.9.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/styles/vendors/jquery.knob.js"></script>
        <script src="<%=request.getContextPath()%>/styles/vendors/raphael-min.js"></script>
        <script src="<%=request.getContextPath()%>/styles/vendors/morris/morris.min.js"></script>

        <script src="<%=request.getContextPath()%>/styles/bootstrap/js/bootstrap.min.js"></script>
        <script src="<%=request.getContextPath()%>/styles/bootstrap/js/bootstrap-multiselect.js"></script>
        <script src="<%=request.getContextPath()%>/styles/vendors/flot/jquery.flot.js"></script>
        <script src="<%=request.getContextPath()%>/styles/vendors/flot/jquery.flot.categories.js"></script>
        <script src="<%=request.getContextPath()%>/styles/vendors/flot/jquery.flot.pie.js"></script>
        <script src="<%=request.getContextPath()%>/styles/vendors/flot/jquery.flot.time.js"></script>
        <script src="<%=request.getContextPath()%>/styles/vendors/flot/jquery.flot.stack.js"></script>
        <script src="<%=request.getContextPath()%>/styles/vendors/flot/jquery.flot.symbol.js"></script>
        <script src="<%=request.getContextPath()%>/styles/vendors/flot/jquery.flot.resize.js"></script>

        <script src="<%=request.getContextPath()%>/styles/assets/scripts.js"></script>
        <script>
        $(function() {

            $('#multiselect').multiselect({
                includeSelectAllOption: true,
                enableFiltering: true,
                maxHeight: 150
            });

            $('#selectSalaryByCity').multiselect({
                includeSelectAllOption: true,
                enableFiltering: true,
                maxHeight: 150
            });

            var dataAnalysisDemandByCity = {

                submitDataBySalary: function(psType, citys) {
                    $.ajax({
                        url : "/analysis/analysisSalaryByPosition",
                        type : "POST",
                        dataType : "text",
                        data: {psType : psType, citys : citys},
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success: function(data, dataStatus) {
                            returnJson = eval("(" + data + ")");
                            if(parseInt(returnJson.errcode) === 0) {
                                var salaryChart = echarts.init(document.getElementById('salaryChart'));
                                var salaryCity = [];
                                var low5 = [];
                                var low510 = [];
                                var low1016 = [];
                                var low1621 = [];
                                var low2126 = [];
                                var inc26 = [];
                                $.each(returnJson.data, function(i, item) {
                                    salaryCity.push(i);
                                    var salaryCounts = item.split(",");
                                    low5.push(parseInt(salaryCounts[0]));
                                    low510.push(parseInt(salaryCounts[1]));
                                    low1016.push(parseInt(salaryCounts[2]));
                                    low1621.push(parseInt(salaryCounts[3]));
                                    low2126.push(parseInt(salaryCounts[4]));
                                    inc26.push(parseInt(salaryCounts[5]));

                                });

                                var option = {
                                    tooltip : {
                                        trigger: 'axis',
                                        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                                            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                                        }
                                    },
                                    legend: {
                                        data:['低于5k','5k-10k','10k-16k','16k-21k','21k-26k','26k以上']
                                    },
                                    grid: {
                                        left: '3%',
                                        right: '4%',
                                        bottom: '3%',
                                        containLabel: true
                                    },
                                    xAxis : [
                                        {
                                            type : 'category',
                                            data : salaryCity
                                        }
                                    ],
                                    yAxis : [
                                        {
                                            type : 'value'
                                        }
                                    ],
                                    series : [
                                        {
                                            name:'低于5k',
                                            type:'bar',
                                            data:low5
                                        },
                                        {
                                            name:'5k-10k',
                                            type:'bar',
                                            data:low510
                                        },
                                        {
                                            name:'10k-16k',
                                            type:'bar',
                                            data:low1016
                                        },
                                        {
                                            name:'16k-21k',
                                            type:'bar',
                                            data:low1621
                                        },
                                        {
                                            name:'21-26k',
                                            type:'bar',
                                            data:low2126,
                                        },
                                        {
                                            name:'26以上',
                                            type:'bar',
                                            data:inc26
                                        }
                                    ]
                                };
                                salaryChart.setOption(option, true);
                            }
                        },
                        error: dataAnalysisDemandByCity.onError
                    });
                },

                submitData: function(citys) {
                    $.ajax({
                        url : "/analysis/analysisDemandByCity",
                        type : "POST",
                        dataType : "text",
                        data: {citys : citys},
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success: function(data, dataStatus) {
                            returnJson = eval("(" + data + ")");
                            if(parseInt(returnJson.errcode) === 0) {
                                //
                                var chartDataByPosition = []
                                var chartDataByCompany = []
                                $.each(returnJson.data, function(i, item) {
                                    var counts = item.split(",");
                                    var childEByPosition = [];
                                    var childEByCompany = [];
                                    childEByPosition.push(i);
                                    childEByPosition.push(parseInt(counts[0]));
                                    childEByCompany.push(i);
                                    childEByCompany.push(parseInt(counts[1]));

                                    chartDataByPosition.push(childEByPosition);
                                    chartDataByCompany.push(childEByCompany);
                                });

                                var dataSet = [
                                    {
                                        label : "职位",
                                        data: chartDataByPosition,
                                        color: "#DC5625",
                                        bars:{
                                            show: true,
                                            align: "center",
                                            barWidth: 0.6,
                                            lineWidth:1
                                        }
                                    }, {
                                        label : "公司",
                                        data: chartDataByCompany,
                                        yaxis: 2,
                                        color: "#007ACC",
                                        points: { symbol: "triangle", fillColor: "#007ACC", show: true },
                                        lines: {show:true}
                                    }
                                ]

                                var options = {
                                    xaxis: {
                                        mode: "categories",
                                        tickLength: 0,
                                        autoscaleMargin: .10,
                                        axisLabelFontFamily: 'Verdana, Arial',
                                        color: "black",
                                        axisLabelUseCanvas: true
                                    },
                                    yaxes: [{
                                        position: "left",
                                        color: "black",
                                        axisLabel: "职位",
                                        axisLabelUseCanvas: true
                                    }, {
                                        position: "right",
                                        clolor: "black",
                                        axisLabel: "公司",
                                        axisLabelUseCanvas: true
                                    }],
                                    legend: {
                                        labelBoxBorderColor: "#000000",
                                        position: "nw"
                                    },
                                    grid: {
                                        hoverable: true,
                                        borderWidth: 3,
                                        backgroundColor: { colors: ["#ffffff", "#EDF5FF"] }
                                    }
                                }
                                $.plot($("#catchart"), dataSet, options);
                                $("#catchart").UseTooltip();
                            }
                        },
                        error: dataAnalysisDemandByCity.onError
                    });
                },
                onError: function () {
                    console.log("dataAnalysisDemandByCity error");
                }
            };
            $('#startDemandCity').bind('click', function() {
                var citys = '';
                $("#multiselect option:selected").each(function () {
                    citys += $(this).text() + ",";
                });
                dataAnalysisDemandByCity.submitData(citys.substring(0, citys.lastIndexOf(",")));
            });


            $('#startDemandSalary').bind('click', function() {
                var citys = '';
                var psType = $("#selectSalaryByPsType option:selected").text();

                $("#selectSalaryByCity option:selected").each(function () {
                    citys += $(this).text() + ",";
                });
                dataAnalysisDemandByCity.submitDataBySalary(psType, citys.substring(0, citys.lastIndexOf(",")));
            });


            var previousPoint = null, previousLabel = null;

            $.fn.UseTooltip = function () {
                $(this).bind("plothover", function (event, pos, item) {
                    if (item) {
                        if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
                            previousPoint = item.dataIndex;
                            previousLabel = item.series.label;
                            $("#tooltip").remove();
                            //console.log(item.datapoint);
                            var x = item.datapoint[0];
                            var y = item.datapoint[1];

                            var color = item.series.color;
                            //console.log(item);

                            showTooltip(item.pageX, item.pageY, color,
                                    "<strong>" + item.series.label + "</strong><br><strong>" + y + "</strong>");
                        }
                    } else {
                        $("#tooltip").remove();
                        previousPoint = null;
                    }
                });
            };

            function showTooltip(x, y, color, contents) {
                $('<div id="tooltip">' + contents + '</div>').css({
                    position: 'absolute',
                    display: 'none',
                    top: y - 40,
                    left: x - 50,
                    border: '2px solid ' + color,
                    padding: '3px',
                    'font-size': '9px',
                    'border-radius': '5px',
                    'background-color': '#fff',
                    'font-family': 'Verdana, Arial, Helvetica, Tahoma, sans-serif',
                    opacity: 0.9
                }).appendTo("body").fadeIn(200);
            }
        });
        </script>
    </body>

</html>