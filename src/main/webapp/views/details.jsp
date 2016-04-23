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
            var socket = new WebSocket("ws://localhost:8080/socket");
            socket.onmessage = function(evt) {
                var parsedData = JSON.parse(evt.data);
                if(parsedData.typeMsg == "newGebot") {
                    var auction = document.getElementById(parsedData.product_id.concat("gebot"));
                    auction.firstChild.nodeValue = parsedData.price;
                    var bieter = document.getElementById(parsedData.product_id.concat("bieter"));
                    bieter.firstChild.nodeValue = parsedData.user;
                } else if(parsedData.typeMsg == "ueberboten") {
                    var balance = document.getElementById("konto_stand");
                    balance.firstChild.nodeValue = parsedData.balance;
                }
            }
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
                                       if(val == "error") {
                                           error.style.display = 'block';
                                       } else {
                                           error.style.display = 'none';
                                       }
                                   } else {
                                       var auktionen = document.getElementById("laufende_Auktionen");
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
        </script>
</head>
<body data-decimal-separator="," data-grouping-separator="." onload="bieterForm()">
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
                    <span id="laufende_Auktionen" class="running-auctions-count"><%=user.getAuctions()%></span>
                    <span class="auction-label" data-plural="Auktionen" data-singular="Auktion">Auktionen</span>
                </dd>
                <dt>Gewonnen:</dt>
                <dd>
                    <span class="won-auctions-count"><%=user.getWon()%></span>
                    <span class="auction-label" data-plural="Auktionen" data-singular="Auktion">Auktionen</span>
                </dd>
                <dt>Verloren:</dt>
                <dd>
                    <span class="lost-auctions-count"><%=user.getLost()%></span>
                    <span class="auction-label" data-plural="Auktionen" data-singular="Auktion">Auktionen</span>
                </dd>
            </dl>
        </div>
        <div class="recently-viewed-container">
            <h3 class="recently-viewed-headline">Zuletzt angesehen</h3>
            <ul class="recently-viewed-list"></ul>
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
                    <span class="highest-bid"><%=product.getHoechstgebot()%> &euro;</span> an
                    <span class="highest-bidder"><%=product.getHoechstbietender().getUsername()%></span> verkauft.
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