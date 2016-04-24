<jsp:useBean id="user" class="at.ac.tuwien.big.we16.ue2.beans.User" scope="session" />
<jsp:useBean id="product" class="at.ac.tuwien.big.we16.ue2.beans.Auction" scope="session"/>
<%
    if(user==null||!user.isLoggedIn())
        response.sendRedirect("/views/login.jsp");
    else{
%>
<!doctype html>
<html lang="de">
<head>
    <meta charset="utf-8">
    <title>BIG Bid - Der Pate (Film)</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../styles/style.css">
        <script type="text/javascript">
                   function getAnswer() {
                       dataString = $('#ajax_form').serialize();
                       $.ajax({
                           type: "POST",
                           url: "/../../controller/BietenServlet",
                           data: dataString,
                           dataType: "json",
                           success: function (result) {
                               $.each(result, function(index, val) {
                                   if(index == "price") {
                                       var konto = document.getElementById("konto_stand");
                                       konto.firstChild.nodeValue = val;
                                   } else if(index == "status") {
                                       var error = document.getElementById("bid-error");
                                       alert("if");
                                       if(val == "error") {
                                           alert("error");
                                           error.style.display = 'block';
                                       } else {
                                           alert("kein error");
                                           error.style.display = 'none';
                                       }
                                   } else {
                                       var auktionen = document.getElementById("anzahl");
                                       auktionen.firstChild.nodeValue = val;
                                   }
                               });
                           }
                       });
                   }
            function bieterForm() {
                var e = document.getElementById("ende");
                var jetzt = new Date();
                var result = e.value.split(",");
                var ablauf = new Date();
                ablauf.setFullYear(result[0]);
                ablauf.setMonth(result[1]-1);
                ablauf.setDate(result[2]);
                ablauf.setHours(result[3]);
                ablauf.setMinutes(result[4]);
                ablauf.setSeconds(result[5]);
                ablauf.setMilliseconds(result[6]);
                var exp = document.getElementById("exp");
                var rest = document.getElementById("restzeit");
                var form = document.getElementById("ajax_form");
                if((ablauf - jetzt) > 0) {
                    exp.style.display ='none';
                    rest.style.display = 'block';
                    form.style.display = 'block';
                } else {
                    form.style.display = 'none';
                    exp.style.display = 'block';
                    rest.style.display = 'none';
                }
            }
            if(localStorage.last1 == null) {
                localStorage.last1 = "";
            }
            if(localStorage.last2 == null) {
                localStorage.last2 = "";
            }
            if(localStorage.last3 == null) {
                localStorage.last3 = "";
            }
            if(localStorage.href1 == null) {
                localStorage.href1 = "";
            }
            if(localStorage.href2 == null) {
                localStorage.href2 = "";
            }
            if(localStorage.href3 == null) {
                localStorage.href3 = "";
            }
            function print() {
                if(supportsLocalStorage()) {
                    document.getElementById("rH").style.display = "block";
                    document.getElementById("rU").style.display = "block";
                    document.getElementById("first").innerHTML = localStorage.last1;
                    document.getElementById("first").href = localStorage.href1;
                    document.getElementById("second").innerHTML = localStorage.last2;
                    document.getElementById("second").href = localStorage.href2;
                    document.getElementById("third").innerHTML = localStorage.last3;
                    document.getElementById("third").href = localStorage.href3;
                }
            }
            function clickCounter(node) {
                if(supportsLocalStorage()) {
                    if(localStorage.last1 !== node.name) {
                        localStorage.last3 = localStorage.last2;
                        localStorage.href3 = localStorage.href2;
                        localStorage.last2 = localStorage.last1;
                        localStorage.href2 = localStorage.href1;
                        localStorage.last1 = node.name;
                        localStorage.href1 = node.href;
                    }
                }
            }
        </script>
</head>
<body data-decimal-separator="," data-grouping-separator="." onload="bieterForm(); print();">
<a href="#productsheadline" class="accessibility">Zum Inhalt springen</a>

<header aria-labelledby="bannerheadline">
    <img class="title-image" src="../images/big-logo-small.png" alt="BIG Bid logo">

    <h1 class="header-title" id="bannerheadline">
        BIG Bid
    </h1>
    <nav aria-labelledby="navigationheadline">
        <h2 class="accessibility" id="navigationheadline">Navigation</h2>
        <ul class="navigation-list">
            <li>
                <a href="/../../controller/LoginServlet?login=logout" class="button" accesskey="l">Abmelden</a>
            </li>
        </ul>
    </nav>
</header>
<div class="main-container">
    <aside class="sidebar" aria-labelledby="userinfoheadline">
        <div class="user-info-container">
            <h2 class="accessibility" id="userinfoheadline">Benutzerdaten</h2>
            <dl class="user-data properties">
                <dt class="accessibility">Name:</dt>
                <dd class="user-name"><%=user.getUsername()%></dd>
                <dt>Kontostand:</dt>
                <dd>
                    <span id="konto_stand"  class="balance"><%=user.getMoney()%> &euro;</span>
                </dd>
                <dt>Laufend:</dt>
                <dd>
                    <span id="anzahl" class="running-auctions-count"><%=user.getAuctions()%></span>
                    <span class="auction-label" data-plural="Auktionen" data-singular="Auktion">Auktionen</span>
                </dd>
                <dt>Gewonnen:</dt>
                <dd>
                    <span class="won-auctions-count" id="won"><%=user.getWon()%></span>
                    <span class="auction-label" data-plural="Auktionen" data-singular="Auktion">Auktionen</span>
                </dd>
                <dt>Verloren:</dt>
                <dd>
                    <span class="lost-auctions-count" id="lost"><%=user.getLost()%></span>
                    <span class="auction-label" data-plural="Auktionen" data-singular="Auktion">Auktionen</span>
                </dd>
            </dl>
        </div>
        <div class="recently-viewed-container">
            <h3 class="recently-viewed-headline" style="display: none;" id="rH">Zuletzt angesehen</h3>
            <ul class="recently-viewed-list" style="display: none;" id="rU">
                <li class="recently-viewed-link"><a id="first" href=""></a></li>
                <li class="recently-viewed-link"><a id="second" href=""></a></li>
                <li class="recently-viewed-link"><a id="third" href=""></a></li>
            </ul>
        </div>
    </aside>
    <main aria-labelledby="productheadline" class="details-container">
        <div class="details-image-container">
            <img class="details-image" src=<%=product.getImg()%> alt="">
        </div>
        <div data-product-id="<%=product.getId()%>" class="details-data" id="<%=product.getId()%>">
            <h2 class="main-headline" id="productheadline"><%=product.getBezeichnung()%></h2>

            <div class="auction-expired-text" style="display:none" id="exp">
                <p>
                    Diese Auktion ist bereits abgelaufen.
                    Das Produkt wurde um
                    <span class="highest-bid" id="<%=product.getId()%>gebot2"><%=product.getHoechstgebot()%> &euro;</span> an
                    <span class="highest-bidder" id="<%=product.getId()%>bieter2"><%=product.getHoechstbietender().getUsername()%></span> verkauft.
                </p>
            </div>
            <p class="detail-time" id="restzeit">Restzeit: <span  class="detail-rest-time js-time-left" data-end-time="<%=product.getAblaufdatum()%>"
            ></span>
            </p>
            <form class="bid-form" method="post" id="ajax_form">
                <input type="hidden" id="ende" name="usrname" value="<%=product.getAblaufdatum()%>"/>
                <input type="hidden" value="<%=product.getId()%>" id="product_id"/>
                <label class="bid-form-field" id="highest-price">
                    <span class="highest-bid" id="<%=product.getId()%>gebot"><%=product.getHoechstgebot()%> &euro;</span>
                    <span class="highest-bidder" id="<%=product.getId()%>bieter"><%=product.getHoechstbietender().getUsername()%></span>
                </label>
                <label class="accessibility" for="price"></label>
                <input type="number" step="0.01" min="0" id="price" class="bid-form-field form-input"
                       name="price" required>
                <p id="bid-error" class="bid-error" style="display: none">Es gibt bereits ein h&ouml;heres Gebot oder der Kontostand ist zu
                    niedrig.</p>
                <input id="submit-price" type="button" class="bid-form-field button" name="submit-price" value="Bieten" onclick="getAnswer();">
            </form>
        </div>
    </main>
</div>
<footer>
    &copy; 2016 BIG Bid
</footer>
<script src="/scripts/jquery.js"></script>
<script src="/scripts/framework.js"></script>
</body>
</html>
<%}%>
