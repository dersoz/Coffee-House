var drinks = [];
var addons = [];
var baseURL = "http://localhost:8080/kahveci";
// var baseURL = "https://floating-hamlet-85904.herokuapp.com";

// filling drinks
function fillDrinks() {
    drinks = JSON.parse(this.responseText)
    drinks.forEach(function (element) {
        drinks[element.id] = element;
        element.applicableAddons.forEach(function (addOn) {
            addons[addOn.id] = addOn;
        })
        $('#icecekSec').append($('<option>', {value: element.id, text: element.name}));
        $('#icecekSecRep').append($('<option>', {value: element.id, text: element.name}));
    })
    $('#eklentiSec').append($('<option>', {value: -1, text: 'Seciniz'}));
}

// filling available addOns
function addAddOns() {
    drinks.forEach(function (element) {
        //console.log($('#icecekSec'))
        if (element.id === $('#icecekSec').val()) {
            $('#eklentiSec').empty();
            element.applicableAddons.forEach(function (addon) {
                $('#eklentiSec').append($('<option>', {value: addon.id, text: addon.name}));
            })
        }
    })
}

$(document).ready(function () {
    // Fetch drink and addonList from sw
    var oReq = new XMLHttpRequest();
    oReq.addEventListener("load", fillDrinks);
    oReq.open("GET", baseURL + "/kahve");
    oReq.send();
});

var drinkList = [];
var totalCost = 0;
function addDrink() {
    drinkList.push({ drinkId: $("#icecekSec option:selected").val(), addonId: $("#eklentiSec option:selected").val() })
    $('#orders').html('')

    drinkList.forEach(function (element) {
        $('#orders').append('<p>' + drinks[element.drinkId].name + ' & ' + addons[element.addonId].name + ' => ' + (parseFloat(drinks[element.drinkId].price) + parseFloat(addons[element.addonId].price)) + ' TL </p>');
        totalCost += (parseFloat(drinks[element.drinkId].price) + parseFloat(addons[element.addonId].price))
    })
    $('#purchase').show();
    return false;
}
function fillRespPurchase(){
    $('#orders').html('')
    $('#orders').append('<hr>');
    $('#orders').append("Indirimsiz Tutar : ");
    $('#orders').append(JSON.parse(this.responseText).rawPrice);
    $('#orders').append('<hr>');
    $('#orders').append("Indirim Tutari : ");
    $('#orders').append(JSON.parse(this.responseText).savings);
    $('#orders').append('<hr>');
    $('#orders').append('<p>Tesekkurler</p>');
    $('#purchase').hide();
    drinkList = [];
    totalCost = 0;
}

function purchase() {
    // call backend purchase, which returns discount and total

    var oReq = new XMLHttpRequest();
    oReq.addEventListener("load", fillRespPurchase);
    oReq.open("PUT", baseURL + "/purchase");
    oReq.setRequestHeader("Content-Type", "application/json");
    var drinklistPost = {"items": []};
    drinkList.forEach(function (drink) {
        drinklistPost.items.push({"kahve": {"id": drink.drinkId}, "addons": [{"id": drink.addonId}]})
    })
    oReq.send(JSON.stringify(drinklistPost));

    return false;
}

function showReports() {
    $('#siparis').hide();
    $('#reports').show();
    $('#navSiparis').removeClass('active');
    $('#navReports').addClass('active');
}

function showSiparis() {
    $('#reports').hide();
    $('#siparis').show();
    $('#navReports').removeClass('active');
    $('#navSiparis').addClass('active');
}

function fillReport() {
    $('#stats').append("Satin alinan icecek sayisi : " + this.responseText);
}

function getReport() {
    $('#stats').html('')
    $('#stats').append('<hr>');
    var oReq = new XMLHttpRequest();
    oReq.addEventListener("load", fillReport);
    oReq.open("GET", baseURL + "/purchase/sales/" + $('#icecekSecRep').val());
    oReq.send();
    return false;
}
