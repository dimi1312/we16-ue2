<jsp:useBean id="user" class="at.ac.tuwien.big.we16.ue2.beans.User" scope="session" />
<jsp:useBean id="sortiment" class="at.ac.tuwien.big.we16.ue2.beans.Sortiment" scope="session" />
<%
    if(user==null||!user.isLoggedIn())
        response.sendRedirect("/views/login.jsp");
    else
    {
%>
<%@ page import="at.ac.tuwien.big.we16.ue2.beans.Auction" %>
<!doctype html>
<html lang="de">
<head>
    <meta charset="utf-8">
    <title>BIG Bid - Produkte</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../styles/style.css">
    <script>
        function readyLoading() {
            var input = document.getElementById("refresh");
            input.value == 'yes' ? location.reload(true) : input.value = 'yes';
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
                if(localStorage.last1 !== node.title) {
                    localStorage.last3 = localStorage.last2;
                    localStorage.href3 = localStorage.href2;
                    localStorage.last2 = localStorage.last1;
                    localStorage.href2 = localStorage.href1;
                    localStorage.last1 = node.title;
                    localStorage.href1 = node.href;
                }
            }
        }
    </script>
</head>
<body data-decimal-separator="," data-grouping-separator="." onload="readyLoading(); print();">
<input type="hidden" id="refresh" value="no" />
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
                <dd class="user-name" id="username"><%=user.getUsername()%></dd>
                <dt>Kontostand:</dt>
                <dd>
                    <span class="balance" id="konto_stand"><%=user.getMoney()%> &euro;</span>
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
            <h3 class="recently-viewed-headline" style="display: none;" id="rH">Zuletzt angesehen</h3>
            <ul class="recently-viewed-list" style="display: none;" id="rU">
                <li class="recently-viewed-link"><a id="first" href="" title="Link to recently visited auction"></a></li>
                <li class="recently-viewed-link"><a id="second" href="" title="Link to recently visited auction"></a></li>
                <li class="recently-viewed-link"><a id="third" href="" title="Link to recently visited auction"></a></li>
            </ul>
        </div>
    </aside>
    <main aria-labelledby="productsheadline">
        <h2 class="main-headline" id="productsheadline">Produkte</h2>
        <div class="products">
            <% for (Auction p : sortiment.getAuction()) { %>
                <div class="product-outer" data-product-id="<%=p.getId()%>">
                    <a id="link<%=p.getId()%>" href="/../../controller/OverviewServlet?param=<%=p.getId()%>" onclick="clickCounter(this);"
                       class="product <%=user.equals(p.getHoechstbietender())?" highlight":""%>" title="<%=p.getBezeichnung()%>">
                        <img class="product-image" src="<%=p.getImg()%>" alt="<%=p.getBezeichnung()%>">
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
<%}%>