<jsp:useBean id="user" class="at.ac.tuwien.big.we16.ue2.beans.User" scope="session" />
<jsp:useBean id="product" class="at.ac.tuwien.big.we16.ue2.beans.Auction" scope="session"/>
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
                               alert("result");
                               $.each(result, function(index, val) {
                                   if(index == "price") {
                                       var konto = document.getElementById("konto_stand");
                                       konto.firstChild.nodeValue = val;
                                   } else {
                                       var auktionen = document.getElementById("laufende_Auktionen");
                                       auktionen.firstChild.nodeValue = val;
                                   }
                               });
                           }
                       });
                   }
        </script>
</head>
<body data-decimal-separator="," data-grouping-separator=".">
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
        <div data-product-id="ce510a73-408f-489c-87f9-94817d845773" class="details-data">
            <h2 class="main-headline" id="productheadline"><%=product.getBezeichnung()%></h2>

            <div class="auction-expired-text" style="display:none">
                <p>
                    Diese Auktion ist bereits abgelaufen.
                    Das Produkt wurde um
                    <span class="highest-bid"><%=product.getHoechstgebot()%> &euro;</span> an
                    <span class="highest-bidder"><%=product.getHoechstbietender().getUsername()%></span> verkauft.
                </p>
            </div>
            <p class="detail-time">Restzeit: <span  class="detail-rest-time js-time-left" data-end-time=<%=product.getAblaufdatum()%>
            ></span>
            </p>
            <form class="bid-form" method="post" id="ajax_form">
                <label class="bid-form-field" id="highest-price">
                    <span class="highest-bid"><%=product.getHoechstgebot()%> &euro;</span>
                    <span class="highest-bidder"><%=product.getHoechstbietender().getUsername()%></span>
                </label>
                <label class="accessibility" for="price"></label>
                <input type="number" step="0.01" min="0" id="price" class="bid-form-field form-input"
                       name="price" required>
                <p id="bid-error" class="bid-error">Es gibt bereits ein höheres Gebot oder der Kontostand ist zu
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