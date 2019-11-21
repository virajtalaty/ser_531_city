<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <script src="https://code.jquery.com/jquery-2.1.3.min.js"></script>
    <meta charset="UTF-8">
    <title>Hello ${name}!</title>
    <link href="${contextPath}/resources/css/city.min.css" rel="stylesheet">
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

<!-- Portfolio Section -->
<section class="page-section portfolio" id="portfolio">
    <div class="container">
        <!-- Portfolio Section Heading -->
        <h2 class="page-section-heading text-center text-uppercase text-secondary mb-0">Select City For Details</h2>
        <br>
        <br>
        <form method="post" action="${contextPath}/getZip">
            <div class="control-group">
                <select class="form-control" id="citySelected" name="citySelected">
                    <option value="Tempe">Tempe</option>
                    <option value="Phoenix">Phoenix</option>
                    <option value="Scottsdale">Scottsdale</option>
                </select>
            </div>
            <br>
            <br>
            <div class="form-group" style="text-align: center">
                <button type="submit" class="btn btn-primary">Search</button>
            </div>
        </form>
    </div>
</section>

<br>
<c:if test="${zipCodes.size() > 0}">
    <section class="page-section portfolio" id="zipcode">
        <div class="container">
            <!-- Portfolio Section Heading -->
            <h2 class="page-section-heading text-center text-uppercase text-secondary mb-0">Select zip code For Details</h2>
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
                <div class="form-group" style="text-align: center">
                    <button type="submit" class="btn btn-primary">Search</button>
                </div>
                <input type="hidden" name="citySelected" value="${citySelected}">
            </form:form>
        </div>
    </section>
</c:if>


<br>
<c:if test="${not empty newCity}">
    <section class="page-section portfolio" id="details">
        <div class="container">
            <!-- Portfolio Section Heading -->
            <h2 class="page-section-heading text-center text-uppercase text-secondary mb-0">Select zip code For Details</h2>
            <br>
            <br>
            <h3>Events</h3>
            <c:forEach var="event" items="${newCity.events}">
                    <td>${event}</td>
            </c:forEach>

            <h3>Restaurants and Groceries</h3>
            <c:forEach var="restaurant" items="${newCity.restaurants}">
                <td>${restaurant}</td>
            </c:forEach>

            <h3>Real Estate</h3>
            <c:forEach var="real" items="${newCity.realEstate}">
                <td>${real}</td>
            </c:forEach>
        </div>
    </section>
</c:if>

<c:if test="${not empty citySelected}">
    <script type="text/javascript">
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
