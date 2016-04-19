<!doctype html>
<html lang="de">
<head>
    <meta charset="utf-8">
    <title>BIG Bid - Anmelden</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../styles/style.css">
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
                <a href="" class="button" accesskey="l">Registrieren</a>
            </li>
        </ul>
    </nav>
</header>
<div class="main-container">
    <main aria-labelledby="formheadline">
        <form class="form" method="post">
            <h2 id="formheadline" class="registration-headline">Anmelden</h2>

            <div class="form-row">
                <label class="form-label" for="email-input">
                    Email
                </label>
                <input type="email" name="email" id="email-input" required class="form-input">
                <span id="email-error" class="error-text"></span>
            </div>

            <div class="form-row">
                <label class="form-label" for="password-input">
                    Passwort
                </label>
                <input type="password" name="password" id="password-input" required class="form-input" minlength="4"
                       maxlength="12">
                <span id="password-error" class="error-text"></span>
            </div>
            <div class="form-row form-row-center">
                <button class="button button-submit">
                    Anmelden
                </button>
            </div>
        </form>
    </main>
</div>
<footer>
    © 2016 BIG Bid
</footer>
<script src="/scripts/jquery.js"></script>
<script src="/scripts/framework.js"></script>
</body>
</html>