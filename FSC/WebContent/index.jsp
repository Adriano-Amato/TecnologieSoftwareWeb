<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <script src="libraries/jquery.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
            integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>


    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="layout template responsive">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">


    <title>Futura Scuola Calcio</title>

    <link rel="shortcut icon" href="images/favico.png" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="style/index.css">

</head>
<body>

<jsp:include page="header.jsp"/>

<div class=container-fluid id=card>

    <div class="card-deck" id="notizieContainer">

        <div class="card" id="0">
            <img src="images/scuolacalcio.png" class="card-img-top" alt="...">
            <div class="card-body">
                <h5 class="card-title">Titolo</h5>
                <p class="card-text">Articolo</p><br>
                <a href="#" class="btn btn-primary">Vai all'articolo</a>
            </div>
        </div>

        <div class="card" id="1">
            <img src="images/esultanza.jpg" class="card-img-top" alt="...">
            <div class="card-body">
                <h5 class="card-title">Titolo</h5>
                <p class="card-text">Articolo</p><br>
                <a href="#" class="btn btn-primary">Vai all'articolo</a>
            </div>
        </div>

    </div>
</div>


<footer>
    <div class=container-fluid id=us>
        <div class=row>
            <div class=card-deck>
                <div class="column footer">
                    <p style=font-weight:bold>Contattaci:</p>
                    <a style="word-break:break-all;"
                       href="mailto:futurascuolacalcio@gmail.com">futurascuolacalcio@gmail.com</a><br>
                    <a href=https://www.google.it/maps/place/Fisciano+Universita/@40.7737282,14.7918352,17z/data=!3m1!4b1!4m5!3m4!1s0x133bc5a6195ca5b5:0xfa488453c72ec7d2!8m2!3d40.7737282!4d14.7940239>Univeristà
                        di Fisciano 84084 Fisciano SA</a>
                </div>

                <div class="column footer">
                    <p style=font-weight:bold>Il nostro team</p>
                    <div class=row>
                        <div class="column us">
                            <img src="images/Adriano.jpg" alt=avatar class="d-block w-100" alt="...">
                            <h5 style=text-align:center;background-color:transparent>Adriano Amato</h5>
                        </div>
                        <div class="column us">
                            <img src="images/index.jfif" alt=avatar class="d-block w-100" alt="...">
                            <h5 style=text-align:center;background-color:transparent>Antonio Cacciapuoti</h5>
                        </div>
                        <div class="column us">
                            <img src="images/Andrea.jpg" alt=avatar class="d-block w-100" alt="...">
                            <h5 style=text-align:center;background-color:transparent>Andrea Delle Serre</h5>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</footer>


<script>
    function makeReq() {
        $.ajax({
            type: "GET",
            url: "NotiziaController",
        }).done(function (data) {
            data = JSON.parse(data);
            console.log(data);
            for (let i = 0; i < divArray.length; i++) {
                let selectorString = "notizia" + i;
                let notizia = data[selectorString];
                let id = "#" + i;
                let articolo = notizia.articolo;
                articolo = articolo.substring(0, 100) + "...";
                $(id + " h5").text(notizia.titolo);
                $(id + " p").text(articolo);
                $(id + " a").attr("href", "NotiziaController?action=details&codiceNotizia=" + notizia.codice);
            }
        });
    }

    let divArray = $("#notizieContainer").children().toArray();
    makeReq();
</script>

</body>
</html>