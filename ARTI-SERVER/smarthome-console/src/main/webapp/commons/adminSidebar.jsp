<%@ page language="java" errorPage="/commons/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/commons/taglibs.jsp"%>
  <ul id="dashboard-menu">
            <li id="home-li">            
                <a href="admin">
                    <i class="fa fa-home fa-fw"></i>
                    <span>主页</span>
                </a>
            </li>            
            <li id="person-li">
                <a class="dropdown-toggle" href="#">
                    <i class="fa fa-group fa-fw"></i>
                    <span>个人中心</span>
                    <i class="fa fa-chevron-down fa-1g"></i>
                </a>
                <ul class="submenu">
                    <li id="userinfo-li"><a href="admin/userinfo">用户资料</a></li>
                </ul>
            </li>
            <li id="users-li">
                <a class="dropdown-toggle" href="#">
                    <i class="fa fa-group fa-fw"></i>
                    <span>用户管理</span>
                    <i class="fa fa-chevron-down fa-fw"></i>
                </a>
                <ul class="submenu">
                    <li id="userlist-li"><a href="admin/users">用户列表</a></li>
                </ul>
            </li>
            <li id="security-li">
                <a class="dropdown-toggle" href="#">
                    <i class="fa fa-group fa-fw"></i>
                    <span>权限管理</span>
                    <i class="fa fa-chevron-down fa-fw"></i>
                </a>
                <ul class="submenu">
                    <li id="roles-li"><a href="admin/access/roles">角色管理</a></li>
                    <li id="authority-li"><a href="admin/access/authorities">权限列表</a></li>
                    <li id="resource-li"><a href="admin/access/resources">资源列表</a></li>
                </ul>
            </li>
        </ul>
