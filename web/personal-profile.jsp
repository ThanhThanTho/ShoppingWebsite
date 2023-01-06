<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="template/header.jsp" %>
<div id="content-left">
    <h3 style="font-weight: normal;">Welcome ${cust.getContactName()}</h3>
    <h3>Account Management</h3>
    <ul>
        <a href=""><li>Personal information</li></a>
    </ul>
    <h3>My order</h3>
    <ul>
        <a href="orders"><li>All orders</li></a>
        <a href="#"><li>Canceled order</li></a>
    </ul>
</div>

<form id="content-right" action="update" method="post">
    <div class="path">Personal information</b></div>
    <div class="content-main">
        <div id="profile-content">
            <div class="profile-content-col">
                <div>Company name: <br/><input name="companyName" value="${cust.getCompanyName()}" type="text"></div>
                <div>Contact name: <br/><input name="contactName" type="text" value="${cust.getContactName()}"></div>
                <div>
                    <input  type="submit" value="Edit info"
                            onclick="return confirm('Are you sure you want to update your infor? Note that if you left the Company Name field blank, it will be remain, all other will be updated')"/>
                </div>
            </div>
            <div class="profile-content-col">
                <div>Company title: <br/><input type="text" name="companyTitle" value="${cust.getContactTitle()}"></div>
                <div>Address: <br/><input type="text" name="address" value="${cust.getAddress()}"></div>
            </div>

            <div class="profile-content-col">
                <div>Email: <br/>${acc.getEmail()}</div>
            </div>
        </div>
    </div>
</form>
<%@include file="template/footer.jsp" %>
