<%@ page language="java" errorPage="/commons/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/commons/taglibs.jsp"%>
  <ul id="dashboard-menu">
    <li id="home-li">
        <a href="user">
            <i class="fa fa-home fa-fw"></i>
            <span>主页</span>
        </a>
    </li>            
    <li id="user-li">
        <a class="dropdown-toggle" href="#">
            <i class="fa fa-group fa-fw"></i>
            <span>个人中心</span>
            <i class="fa fa-chevron-down "></i>
        </a>
        <ul class="submenu">
            <li id="userinfo-li"><a href="user/userinfo">用户资料</a></li>
        </ul>
    </li>
    <li id="arti-li">
        <a class="dropdown-toggle" href="#">
            <i class="fa fa-group fa-fw"></i>
            <span>设备管理</span>
            <i class="fa fa-chevron-down "></i>
        </a>
        <ul class="submenu">
            <li id="artiinfo-li"><a href="user/arti">ARTI</a></li>
            <li id="nodeinfo-li"><a href="user/node">Node</a></li>
        </ul>
    </li>
     <li id="status-li">
        <a class="dropdown-toggle" href="#">
            <i class="fa fa-group fa-fw"></i>
            <span>设备监控</span>
            <i class="fa fa-chevron-down "></i>
        </a>
        <ul class="submenu">
            <li id="statusinfo-li"><a href="user/status">监控设备状态</a></li>
        </ul>
    </li>
    <li id="data-li">
        <a class="dropdown-toggle" href="#">
            <i class="fa fa-group fa-fw"></i>
            <span>数据监控</span>
            <i class="fa fa-chevron-down "></i>
        </a>
        <ul class="submenu">
            <li id="datainfo-li"><a href="user/dataRecord">取电开关状态</a></li>
            <li id="tongji-li"><a href="user/dataRecord/tongji">统计重复数据</a></li>
        </ul>
    </li>
</ul>


