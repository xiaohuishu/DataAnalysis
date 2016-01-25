package com.march.graduation.controller;

import com.google.common.collect.Lists;
import com.march.graduation.model.AnalysisResultCode;
import com.march.graduation.view.JsonAndView;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import qunar.api.pojo.Limit;
import qunar.tc.ordercenter.api.common.ConditionKeys;
import qunar.tc.ordercenter.api.common.OrderCenterStatusScene;
import qunar.tc.ordercenter.api.common.OrderUserStatus;
import qunar.tc.ordercenter.api.exception.ServiceException;
import qunar.tc.ordercenter.api.face.ModifyToken;
import qunar.tc.ordercenter.api.face.OrderQuery;
import qunar.tc.ordercenter.api.face.ResultList;
import qunar.tc.ordercenter.api.face.SourceSystem;
import qunar.tc.ordercenter.consumer.api.OrderActionService;
import qunar.tc.ordercenter.consumer.api.OrderQueryService;
import qunar.tc.ordercenter.consumer.pojo.ActionItem;
import qunar.tc.ordercenter.consumer.pojo.BusinessType;
import qunar.tc.ordercenter.consumer.pojo.Order;
import qunar.tc.ordercenter.consumer.pojo.OrderKey;
import qunar.tc.ordercenter.consumer.pojo.Result;

import java.util.Arrays;
import java.util.Map;

import static com.march.graduation.constant.OrderTypeEnum.DefineBusinessType;
import static com.march.graduation.constant.OrderTypeEnum.OrderCenterStatus;
import static com.march.graduation.constant.OrderTypeEnum.OrderVisibleStatus;
import static com.march.graduation.constant.OrderTypeEnum.SortKeyType;
import static com.march.graduation.utils.DateUtils.DateConvertType;
import static com.march.graduation.utils.DateUtils.convertStrToDateTime;

// ********************************************
// * 订单中心操作接口
// * @author: xiaohui.shu
// * @version: 日期: 16-1-21 时间: 上午10:49
// ********************************************
@Controller
@RequestMapping("/order")
public class OrderController implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private OrderQueryService orderQueryService;

    private OrderActionService orderActionService;

    /**
     * 订单删除
     * 删除分为两种：回收 and 永久删除，详见wiki
     * --订单号，业务Code不能为空，loginUserName当前登录用户名某些情况可以不为空
     * --参考wiki:
     *     --http://wiki.corp.qunar.com/pages/viewpage.action?pageId=63243355
     *
     * @param orderNo 订单号
     * @param systemCode 业务Code
     * @param operatorUserName 操作用户名
     * @param reason 删除原因
     * @param loginUserName 登录用户名
     * @param isPersistDel 是否永久删除
     */
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.GET)
    public JsonAndView deleteOrder(@RequestParam(value = "orderNo", defaultValue = "") String orderNo,
            @RequestParam(value = "systemCode", defaultValue = "0") int systemCode,
            @RequestParam(value = "operatorUserName", defaultValue = "") String operatorUserName,
            @RequestParam(value = "reason", defaultValue = "") String reason,
            @RequestParam(value = "loginUserName", defaultValue = "") String loginUserName,
            @RequestParam(value = "isPersistDel", defaultValue = "false") boolean isPersistDel) {

        if (StringUtils.isBlank(orderNo) || StringUtils.isBlank(loginUserName) || systemCode == 0) {
            return AnalysisResultCode.ILLEGAL_ERROR.getJsonAndView();
        }
        OrderKey orderKey = new OrderKey(systemCode, orderNo);

        ActionItem actionItem = new ActionItem(orderKey, loginUserName);
        ModifyToken modifyToken = new ModifyToken();
        if (StringUtils.isNotBlank(operatorUserName)) {
            modifyToken.setUser(operatorUserName);
        }
        if (StringUtils.isNotBlank(reason)) {
            modifyToken.setReason(reason);
        }
        modifyToken.setSystem(SourceSystem.USERORDER);
        Map<OrderKey, Result> orderKeyResultMap = null;

        try {
            orderKeyResultMap = orderActionService.doAction(modifyToken, isPersistDel ? 4 : 2,
                    Lists.newArrayList(actionItem));

        } catch (RuntimeException ex) {
            logger.error("order delete [order: {}] failure: {}", orderNo, ex);
        }

        if (MapUtils.isEmpty(orderKeyResultMap)) {
            return AnalysisResultCode.UNKNOWN_ERROR.getJsonAndView();
        }

        Result result = orderKeyResultMap.get(orderKey);
        if (result == null) {
            return AnalysisResultCode.UNKNOWN_ERROR.getJsonAndView();
        }
        JsonAndView jsonAndView = AnalysisResultCode.SUCCESS_INFO.getJsonAndView();
        jsonAndView.addData("result", result);

        return jsonAndView;
    }

    /**
     * 获取订单详情
     * --订单号不能为空
     * --参考wiki:
     *      --http://wiki.corp.qunar.com/pages/viewpage.action?pageId=63243463
     *
     * @param orderNo 订单号
     * @param sysCode 业务线标识Code
     * @param includeDynamic 是否加载动态数据 默认false
     */
    @RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
    public JsonAndView queryDetailOrder(@RequestParam(value = "orderNo", defaultValue = "") String orderNo,
            @RequestParam(value = "sysCode", defaultValue = "0") int sysCode,
            @RequestParam(value = "isDesc", defaultValue = "false") boolean includeDynamic) {

        if (StringUtils.isBlank(orderNo)) {
            return AnalysisResultCode.ILLEGAL_ERROR.getJsonAndView();
        }
        ResultList<Order> orderDetail;
        Limit limit = new Limit(1);
        try {
            if (sysCode == 0) {
                orderDetail = orderQueryService.query(null, orderNo, limit, includeDynamic);
            } else {
                orderDetail = orderQueryService.query(sysCode, orderNo, limit, includeDynamic);
            }
            if (orderDetail.isEmpty()) {
                return AnalysisResultCode.NOT_EXIST_ERROR.getJsonAndView().addData("status", orderDetail.getStatus());
            }
            JsonAndView jsonAndView = AnalysisResultCode.SUCCESS_INFO.getJsonAndView();
            jsonAndView.addData("status", orderDetail.getStatus());
            jsonAndView.addData("orderDetail", orderDetail.getItems().get(0));
            return jsonAndView;
        } catch (ServiceException e) {
            logger.error("query order detail [orderNo: {}] service failure: {}", orderNo, e);
        }

        return AnalysisResultCode.NOT_EXIST_ERROR.getJsonAndView();
    }

    /**
     * 获取订单列表
     * --用户名与手机号两者必须有一个不为空
     * --参考wiki:
     *      ---http://wiki.corp.qunar.com/pages/viewpage.action?pageId=63243463
     *
     * @param contactMobile 手机号
     * @param userName 用户名
     * @param businessType 业务类型 例：flight-机票，hotel-酒店 --参考： OrderTypeEnum.DefineBusinessType枚举类型
     * @param orderCenterStatus 订单用户状态(订单完成，支付) 例： 1-可用 2-已支付 3-已支付订单(剔除退款) 4-完成
     * @param orderVisibleStatus 订单用户可见状态 例：1-正常 2-回收站 3-删除(永久删除)
     * @param startTime 订单生成开始时间，精确到天
     * @param endTime 订单结束开始时间，精确到天
     * @param sortKeys 1,2,3(对应排序类型)
     * @param isDesc 排序方式-升序，降序
     * @param includeDynamic 是否请求加载动态数据 默认false
     * @param pageNo 页数
     * @param limit 一页查询记录数
     */
    @RequestMapping(value = "/queryList", method = RequestMethod.GET)
    public JsonAndView queryListOrder(@RequestParam(value = "contactMobile", defaultValue = "") String contactMobile,
            @RequestParam(value = "userName", defaultValue = "") String userName,
            @RequestParam(value = "businessType", defaultValue = "all") String businessType,
            @RequestParam(value = "orderCenterStatus", defaultValue = "1") int orderCenterStatus,
            @RequestParam(value = "orderVisibleStatus", defaultValue = "1") int orderVisibleStatus,
            @RequestParam(value = "startTime", defaultValue = "") String startTime,
            @RequestParam(value = "endTime", defaultValue = "") String endTime,
            @RequestParam(value = "sortKeys", defaultValue = "") String sortKeys,
            @RequestParam(value = "isDesc", defaultValue = "true") boolean isDesc,
            @RequestParam(value = "isDesc", defaultValue = "false") boolean includeDynamic,
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(value = "limit", defaultValue = "20") int limit) {

        if (StringUtils.isBlank(contactMobile) && StringUtils.isBlank(userName)) {
            return AnalysisResultCode.ILLEGAL_ERROR.getJsonAndView();
        }
        // 查询条件
        OrderQuery orderQuery = new OrderQuery();
        // 手机号
        if (StringUtils.isNotBlank(contactMobile)) {
            orderQuery.addCondition(ConditionKeys.contactMobile, contactMobile.trim());
        }
        // 用户名
        if (StringUtils.isNotBlank(userName)) {
            orderQuery.addCondition(ConditionKeys.userName, userName);
        }
        if (StringUtils.equals(businessType, "all")) {
            // 设置查询订单列表业务线
            orderQuery.addCondition(ConditionKeys.businessType,
                    Arrays.asList(BusinessType.flight.name(), BusinessType.hotel.name(), BusinessType.travel.name(),
                            BusinessType.train.name(), BusinessType.ticket.name(), BusinessType.bus,
                            BusinessType.local));
        } else {
            BusinessType sBusinessType = DefineBusinessType.getBusinessTypeByName(businessType).getBusinessType();
            if (sBusinessType == null) {
                return AnalysisResultCode.ILLEGAL_ERROR.getJsonAndView();
            }
            orderQuery.addCondition(ConditionKeys.businessType, sBusinessType);
        }
        // 设置查询可见订单状态
        OrderCenterStatusScene centerStatusScene = OrderCenterStatus.getOrderCenterStatus(orderCenterStatus)
                .getCenterStatusScene();
        orderQuery.addCondition(ConditionKeys.orderCenterStatusScene, centerStatusScene == null
                ? OrderCenterStatus.getOrderCenterStatus(1).getCenterStatusScene() : centerStatusScene);

        // 设置订单可见状态
        OrderUserStatus orderUserStatus = OrderVisibleStatus.getUserStatus(orderVisibleStatus).getOrderUserStatus();
        orderQuery.addCondition(ConditionKeys.orderUserStatus,
                orderUserStatus == null ? OrderVisibleStatus.getUserStatus(1)
                        .getOrderUserStatus().getCode() : orderUserStatus.getCode());

        // 设置下单开始，结束时间
        if (StringUtils.isNotBlank(startTime)) {
            DateTime startDateTime = convertStrToDateTime(DateConvertType.YYYY_MM_DD_DATETIME, startTime);
            if (startDateTime != null) {
                orderQuery.addCondition(ConditionKeys.beginTime, startDateTime.toDate());
            }
        }
        if (StringUtils.isNotBlank(endTime)) {
            DateTime endDateTime = convertStrToDateTime(DateConvertType.YYYY_MM_DD_DATETIME, endTime);
            if (endDateTime != null) {
                orderQuery.addCondition(ConditionKeys.endTime, endDateTime.toDate());
            }
        }
        // 设置排序字段
        if (StringUtils.isNotBlank(sortKeys)) {
            String[] sortKeyArray = StringUtils.split(sortKeys, ",");
            if (sortKeyArray != null && sortKeyArray.length > 0) {
                for (String sortKey : sortKeyArray) {
                    OrderQuery.SortKeys sortKeyEnum = SortKeyType.getSortKeyByIndex(NumberUtils.toInt(sortKey))
                            .getSortKey();
                    if (sortKeyEnum != null) {
                        orderQuery.addSort(sortKeyEnum, isDesc);
                    }
                }
            }
        }
        if (limit != 20 || pageNo != 0) {
            orderQuery.setLimit(new Limit(pageNo * limit, limit));
        }
        // 默认不请求动态数据。如果需要动态数据请与订单中心沟通。
        orderQuery.setIncludeDynamic(includeDynamic);
        try {
            ResultList<Order> orders = orderQueryService.query(orderQuery);
            if (logger.isDebugEnabled()) {
                logger.debug(String.valueOf(orders.getStatus()));
            }
            if (orders.isEmpty()) {
                return AnalysisResultCode.NOT_EXIST_ERROR.getJsonAndView().addData("status", orders.getStatus());
            }
            JsonAndView jsonAndView = AnalysisResultCode.SUCCESS_INFO.getJsonAndView();
            jsonAndView.addData("count", orders.getCount());
            jsonAndView.addData("status", orders.getStatus());
            jsonAndView.addData("orderList", orders.getItems());
            return jsonAndView;
        } catch (ServiceException e) {
            logger.error("query order list service failure: {}", e);
        }
        return AnalysisResultCode.NOT_EXIST_ERROR.getJsonAndView();
    }

    public void setOrderQueryService(OrderQueryService orderQueryService) {
        this.orderQueryService = orderQueryService;
    }

    public void setOrderActionService(OrderActionService orderActionService) {
        this.orderActionService = orderActionService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(orderQueryService, "orderQueryService must be not null");
        Assert.notNull(orderActionService, "orderActionService must be not null");
    }
}
