<%@ page import="at.ac.tuwien.big.we16.ue2.beans.Auction" %>
<jsp:useBean id="user" class="at.ac.tuwien.big.we16.ue2.beans.User" scope="session" />
<jsp:useBean id="sortiment" class="at.ac.tuwien.big.we16.ue2.beans.Sortiment" scope="session" />
<!doctype html>
<html lang="de">
<head>
    <meta charset="utf-8">
    <title>BIG Bid - Produkte</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../styles/style.css">
    <script>
        function readyLoading() {
            if(document.getElementById("username").firstChild.nodeValue == "vanessa.kos@gmx.at") {

            }
        }
        var socket = new WebSocket("ws://localhost:8080/socket");
        socket.onmessage = function(evt) {
            var parsedData = JSON.parse(evt.data);
            if(parsedData.typeMsg == "newGebot") {
                var auction = document.getElementById(parsedData.product_id.concat("gebot"));
                auction.firstChild.nodeValue = parsedData.price;
                var bieter = document.getElementById(parsedData.product_id.concat("bieter"));
                bieter.firstChild.nodeValue = parsedData.user;
            } else if(parsedData.typeMsg == "ueberboten") {
                var balance = document.getElementById("balance");
                balance.firstChild.nodeValue = parsedData.balance;
            } else if(parsedData.typeMsg == "endAuction") {
                var balance = document.getElementById("balance");
                balance.firstChild.nodeValue = parsedData.balance;
                var balance = document.getElementById("anzahl");
                balance.firstChild.nodeValue = parsedData.anzahl;
                var balance = document.getElementById("won");
                balance.firstChild.nodeValue = parsedData.won;
                var balance = document.getElementById("lost");
                balance.firstChild.nodeValue = parsedData.lost;
            }
        }

        socket.onclose = function(evt) {
            socket.close();
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
            if(typeof(Storage) !== "undefined") {
               /* document.getElementById("reViewHeadline").style.display = "initial";
                document.getElementById("reViewList").style.display = "initial";*/
                document.getElementById("first").innerHTML = localStorage.last1;
                document.getElementById("first").href = localStorage.href1;
                document.getElementById("second").innerHTML = localStorage.last2;
                document.getElementById("second").href = localStorage.href2;
                document.getElementById("third").innerHTML = localStorage.last3;
                document.getElementById("third").href = localStorage.href3;
            }
        }
        function clickCounter(node) {
            if(typeof(Storage) !== "undefined") {
                if(localStorage.last1 !== node.name) {
                    localStorage.last3 = localStorage.last2;
                    localStorage.href3 = localStorage.href2;
                    localStorage.last2 = localStorage.last1;
                    localStorage.href2 = localStorage.href1;
                    localStorage.last1 = node.name;
                    localStorage.href1 = "/../../controller/OverviewServlet?param=".concat(node.name);
                }
            }
        }
    </script>
</head>
<body data-decimal-separator="," data-grouping-separator="." onload="readyLoading();">

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
                <a href="" class="button" accesskey="l">Abmelden</a>
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
                <dd class="user-name" id="username"><%=user.getUsername()%></dd>
                <dt>Kontostand:</dt>
                <dd>
                    <span class="balance" id="balance"><%=user.getMoney()%> &euro;</span>
                </dd>
                <dt>Laufend:</dt>
                <dd>
                    <span class="running-auctions-count" id="anzahl"><%=user.getAuctions()%></span>
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
            <h3 class="reViewHeadline">Zuletzt angesehen</h3>
            <ul class="reViewList">
                <li class="recently-viewed-link"><a id="first" href=""></a></li>
                <li class="recently-viewed-link"><a id="second" href=""></a></li>
                <li class="recently-viewed-link"><a id="third" href=""></a></li>
            </ul>
        </div>
    </aside>
    <main aria-labelledby="productsheadline">
        <h2 class="main-headline" id="productsheadline">Produkte</h2>
        <div class="products">
            <% for (Auction p : sortiment.getAuction()) { %>
                <div class="product-outer" data-product-id="<%=p.getId()%>">
                    <a name="<%=p.getBezeichnung()%>" href="/../../controller/OverviewServlet?param=<%=p.getBezeichnung()%>" onclick="clickCounter(this);"
                       class="product <%=user.getUsername().equals(p.getHoechstbietender().getUsername())?" highlight":""%>" title="Mehr Informationen">
                        <img class="product-image" src="<%=p.getImg()%>" alt="">
                        <dl class="product-properties properties">
                            <dt>Bezeichnung</dt>
                            <dd class="product-name"><%=p.getBezeichnung()%></dd>
                            <dt>Preis</dt>
                            <dd class="product-price" id="<%=p.getId()%>gebot">
                                <%=p.getHoechstgebot()%> &euro;
                            </dd>
                            <dt>Verbleibende Zeit</dt>
                            <dd data-end-time="<%=p.getAblaufdatum()%>" data-end-text="abgelaufen"
                                class="product-time js-time-left"></dd>
                            <dt>Höchstbietende/r</dt>
                            <dd class="product-highest" id="<%=p.getId()%>bieter"><%=p.getHoechstbietender().getUsername()%></dd>
                        </dl>
                    </a>
                </div>
            <% } %>
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