package com.march.graduation.constant;

import com.google.common.collect.Maps;
import qunar.tc.ordercenter.api.common.OrderCenterStatusScene;
import qunar.tc.ordercenter.api.common.OrderUserStatus;
import qunar.tc.ordercenter.api.face.OrderQuery;
import qunar.tc.ordercenter.consumer.pojo.BusinessType;

import java.util.Map;

//********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-21 时间: 下午6:52
//********************************************
public class OrderTypeEnum {

    //订单查询排序字段类型
    public enum SortKeyType {

        UNBOUND(1, OrderQuery.SortKeys.unbound),
        FLIGHT_SORT(2, OrderQuery.SortKeys.flightSort),
        ORDER_TIME(3, OrderQuery.SortKeys.orderTime);

        SortKeyType(int sortIndex, OrderQuery.SortKeys sortKey) {
            this.sortIndex = sortIndex;
            this.sortKey = sortKey;
        }

        private int sortIndex;
        private OrderQuery.SortKeys sortKey;

        public int getSortIndex() {
            return sortIndex;
        }

        public OrderQuery.SortKeys getSortKey() {
            return this.sortKey;
        }

        public static SortKeyType getSortKeyByIndex(int sortIndex) {
            return SORT_KEY_TYPE_MAP.get(sortIndex);
        }

        private static final Map<Integer, SortKeyType> SORT_KEY_TYPE_MAP = Maps.newHashMap();

        static {
            for(SortKeyType sortKeyType : SortKeyType.values()) {
                SORT_KEY_TYPE_MAP.put(sortKeyType.getSortIndex(), sortKeyType);
            }
        }
    }

    //订单查询排序字段类型
    public enum OrderCenterStatus {

        AVAILABLE(1, OrderCenterStatusScene.AVAILABLE),
        ALREADY_PAY(2, OrderCenterStatusScene.ALREADY_PAY),
        ALREADY_PAY_NO_REFUND(3, OrderCenterStatusScene.ALREADY_PAY_NO_REFUND),
        COMPLETE(4, OrderCenterStatusScene.COMPLETE);

        OrderCenterStatus(int index, OrderCenterStatusScene centerStatusScene) {
            this.index = index;
            this.centerStatusScene = centerStatusScene;
        }

        private int index;
        private OrderCenterStatusScene centerStatusScene;

        public int getIndex() {
            return index;
        }

        public OrderCenterStatusScene getCenterStatusScene() {
            return this.centerStatusScene;
        }

        public static OrderCenterStatus getOrderCenterStatus(int index) {
            return CENTER_STATUS_MAP.get(index);
        }

        private static final Map<Integer, OrderCenterStatus> CENTER_STATUS_MAP = Maps.newHashMap();

        static {
            for(OrderCenterStatus centerStatus : OrderCenterStatus.values()) {
                CENTER_STATUS_MAP.put(centerStatus.getIndex(), centerStatus);
            }
        }

    }

    public enum OrderVisibleStatus {

        ORDER_TIME(1, OrderUserStatus.NORMAL),
        RECYCLED(2, OrderUserStatus.RECYCLED),
        DELETED(3, OrderUserStatus.DELETED);

        OrderVisibleStatus(int index, OrderUserStatus orderUserStatus) {
            this.index = index;
            this.orderUserStatus = orderUserStatus;
        }

        private int index;
        private OrderUserStatus orderUserStatus;

        public int getIndex() {
            return index;
        }

        public OrderUserStatus getOrderUserStatus() {
            return orderUserStatus;
        }

        public static OrderVisibleStatus getUserStatus(int index) {
            return STATUS_MAP.get(index);
        }

        private static final Map<Integer, OrderVisibleStatus> STATUS_MAP = Maps.newHashMap();

        static {
            for(OrderVisibleStatus visibleStatus : OrderVisibleStatus.values()) {
                STATUS_MAP.put(visibleStatus.getIndex(), visibleStatus);
            }
        }
    }


    //订单业务类型
    public enum DefineBusinessType {

        hotel_w("hotel_w", BusinessType.hotel_w),
        hotel_group_w("hotel_group_w", BusinessType.hotel_group_w),
        hotel_hour("hotel_hour", BusinessType.hotel_hour),
        pay("pay", BusinessType.pay),
        hotel("hotel", BusinessType.hotel),
        flight("flight", BusinessType.flight),
        group("group", BusinessType.group),
        travel("travel", BusinessType.travel),
        train("train", BusinessType.train),
        ticket("ticket", BusinessType.ticket),
        car("car", BusinessType.car),
        apartment("apartment", BusinessType.apartment),
        tese("tese", BusinessType.tese),
        local("local", BusinessType.local),
        bus("bus", BusinessType.bus),
        insure("insure", BusinessType.insure),
        new_apartment("new_apartment", BusinessType.new_apartment),
        icar("icar", BusinessType.icar),
        movie("movie", BusinessType.movie),
        orderinsure("orderinsure", BusinessType.orderinsure),
        qunar_card("qunar_card", BusinessType.qunar_card),
        food("food", BusinessType.food),
        qmall("qmall", BusinessType.qmall),
        waimai("waimai", BusinessType.waimai),
        huiyi("huiyi", BusinessType.huiyi),
        directbus("directbus", BusinessType.directbus);

        DefineBusinessType(String business, BusinessType businessType) {
            this.business = business;
            this.businessType = businessType;
        }

        private String business;
        private BusinessType businessType;

        public BusinessType getBusinessType() {
            return businessType;
        }

        public static DefineBusinessType getBusinessTypeByName(String business) {
            return BUSINESS_TYPE_MAP.get(business);
        }

        public String getBusiness() {
            return business;
        }

        private static final Map<String, DefineBusinessType> BUSINESS_TYPE_MAP = Maps.newHashMap();

        static {
            for(DefineBusinessType defineBusinessType : DefineBusinessType.values()) {
                BUSINESS_TYPE_MAP.put(defineBusinessType.getBusiness(), defineBusinessType);
            }
        }
    }

}
