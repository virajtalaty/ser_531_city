<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <script src="https://code.jquery.com/jquery-2.1.3.min.js"></script>
    <meta charset="UTF-8">
    <title>New In The City</title>
    <link href="${contextPath}/resources/css/city.css" rel="stylesheet">
</head>
<body>

<!-- Masthead -->
<header class="masthead bg-primary text-white text-center">
    <div class="container d-flex align-items-center flex-column">

        <!-- Masthead Heading -->
        <h1 class="masthead-heading text-uppercase mb-0">New In The City</h1>

        <!-- Icon Divider -->
        <div class="divider-custom divider-light">
            <div class="divider-custom-line"></div>
            <div class="divider-custom-icon">
                <i class="fas fa-star"></i>
            </div>
            <div class="divider-custom-line"></div>
        </div>

        <!-- Masthead Subheading -->
        <p class="masthead-subheading font-weight-light mb-0">Events - Restaurants - Real Estate</p>
    </div>
</header>


<section class="page-section portfolio" id="portfolio">
    <!-- City Div -->
    <div class="container">
        <h2 class="page-section-heading text-center text-uppercase text-secondary mb-0">Select City For Details</h2>
        <br>
        <br>
        <form method="post" action="${contextPath}/getZip">
            <div class="control-group">
                <select class="form-control" id="citySelected" name="citySelected">
                    <option value="Tempe">Tempe</option>
                    <option value="Phoenix">Phoenix</option>
                    <option value="Scottsdale">Scottsdale</option>
                    <option value="New York">New York</option>
                    <option value="Los Angeles">Los Angeles</option>
                    <option value="San Francisco">San Francisco</option>
                </select>
            </div>
            <br>
            <br>
            <div class="form-group" style="text-align: center">
                <button type="submit" class="btn btn-primary">Search</button>
            </div>
        </form>
    </div>
    <br><br>
    <!-- Zipcodes Div -->
    <c:if test="${zipCodes.size() > 0}">
    <div class="container">
        <!-- Portfolio Section Heading -->
        <h2 class="page-section-heading text-center text-uppercase text-secondary mb-0">Select zip code</h2>
        <br>
        <br>
        <form:form method="get" action="${contextPath}/getDetails" >
            <div class="control-group">
                <select class="form-control" id="zipSelected" name="zipSelected">
                    <c:forEach items="${zipCodes}"  var="zip">
                        <option value="${zip}">${zip}</option>
                    </c:forEach>
                </select>
            </div>
            <br>
            <br>
            <div style = "width:100%">
                <div style ="width:33%; float:left">
                    <h4>Real Estate Filter By Price</h4>
                    <div class="control-group">
                        <select class="form-control" id="realEstateFilter" name="realEstateFilter">
                            <option value="LtoH">Low to High</option>
                            <option value="HtoL">High to Low</option>
                        </select>
                    </div>
                </div>

                <div style ="width:33%; float:left">
                    <h4>Restaurant Filter By Ratings</h4>
                    <div class="control-group">
                        <select class="form-control" id="restaurantFilter" name="restaurantFilter">
                            <option value="LtoH">Low to High</option>
                            <option value="HtoL">High to Low</option>
                        </select>
                    </div>
                </div>

                <div style ="width:33%; float:left">
                    <h4>Event Filter By Price</h4>
                    <div class="control-group">
                        <select class="form-control" id="eventFilter" name="eventFilter">
                            <option value="LtoH">Low to High</option>
                            <option value="HtoL">High to Low</option>
                        </select>
                    </div>
                </div>
            </div>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>

            <div class="form-group" style="text-align: center;margin-top: 10px">
                <button type="submit" class="btn btn-primary">Search</button>
            </div>
            <input type="hidden" name="citySelected" value="${citySelected}">
        </form:form>
    </div>
    </c:if>


<c:if test="${not empty newCity}">
    <section class="page-section portfolio" id="details">
            <!-- Portfolio Section Heading -->
            <h2 class="page-section-heading text-center text-uppercase text-secondary mb-0">New City Details</h2>

            <div class="container">
                <div class="Result" >
                    <div class="ResultBanner">
                        <h4>Real Estate</h4>
                    </div>
                    <div class="ResultLeft">
                        <img src="${contextPath}/resources/img/event.png" alt="REAL STATE" class="resultImage">
                    </div>
                    <div class="ResultRight" >
                        <c:forEach var="real" items="${newCity.realEstate}">
                            <td>&nbsp;&nbsp;&nbsp;&nbsp;<b> Address:</b> ${real.street}</td><br/>
                            <td>&nbsp;&nbsp;&nbsp;&nbsp;<b> Price: </b> ${real.amount}</td><br/>
                            <td>
                                <a href=${real.name} target="_blank">
                                    <div>
                                        &nbsp;&nbsp;&nbsp;&nbsp; ${real.name}
                                    </div>
                                </a>
                            </td><br/><br/>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="Result" >
                    <div class="ResultBanner">
                        <h4>Restaurants and Groceries</h4>
                    </div>
                    <div class="ResultLeft">
                        <img src="${contextPath}/resources/img/SunBasket.jpg" alt="Restaurants" class="resultImage">
                    </div>
                    <div class="ResultRight" >
                        <c:forEach var="restaurant" items="${newCity.restaurants}">
                            <td>&nbsp;&nbsp;&nbsp;&nbsp; <b>Name: </b> ${restaurant.name}</td><br/>
                            <td>&nbsp;&nbsp;&nbsp;&nbsp; <b>Address: </b> ${restaurant.address}</td><br/>
                            <td>&nbsp;&nbsp;&nbsp;&nbsp; <b>Category: </b> ${restaurant.category}</td><br/>
                            <td>&nbsp;&nbsp;&nbsp;&nbsp; <b>Price: </b> ${restaurant.price}</td><br/>
                            <td>&nbsp;&nbsp;&nbsp;&nbsp; <b>Rating: </b> ${restaurant.rating}</td><br/><br/>
                        </c:forEach>
                    </div>
                </div>
            </div>


            <div class="container">

                <div class="Result" >
                    <div class="ResultBanner">
                        <h4>Events</h4>
                    </div>
                    <div class="ResultLeft">
                        <img src="${contextPath}/resources/img/181015-HCSA-Res-03-Events-banner.jpg" alt="EVENTS" class="resultImage">
                    </div>
                    <div class="ResultRight " >
                        <c:forEach var="event" items="${newCity.events}">
                            &nbsp;&nbsp;&nbsp;&nbsp;<b>Name: </b>${event.name}<br>
                            &nbsp;&nbsp;&nbsp;&nbsp;<b>Address: </b>${event.address}<br>
                            &nbsp;&nbsp;&nbsp;&nbsp;<b>Date: </b>${event.date}<br>
                            &nbsp;&nbsp;&nbsp;&nbsp;<b>Time: </b>${event.time}<br>
                            <c:if test="${event.maxPrice == '$0.0'}">
                                &nbsp;&nbsp;&nbsp;&nbsp;<b>Price: </b>Free<br>
                            </c:if>
                            <c:if test="${event.maxPrice != '$0.0'}">
                                &nbsp;&nbsp;&nbsp;&nbsp;<b>Price: </b>${event.minPrice} - ${event.maxPrice}<br>
                            </c:if>
                            <br>
                        </c:forEach>
                    </div>
                </div>
            </div>
    </section>
</c:if>
<br><br><br><br><br><br>

<c:if test="${not empty citySelected}">
    <script type="text/javascript">
        window.scrollTo(0,document.body.scrollHeight);
        $("#citySelected").val("${citySelected}");
    </script>
</c:if>

<c:if test="${not empty zipSelected}">
    <script type="text/javascript">
        $("#zipSelected").val("${zipSelected}");
    </script>
</c:if>

</body>
</html>
