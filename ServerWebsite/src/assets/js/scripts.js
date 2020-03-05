
function alertUser () {
    alert("Message has been sent");
}

function ping(port, imgName, textName){
    // https://cors-anywhere.herokuapp.com/ eh uma API que permite passar pelo CORS 
    //(Source= "https://ourcodeworld.com/articles/read/73/how-to-bypass-access-control-allow-origin-error-with-xmlhttprequest-jquery-ajax-")
    $.ajax({
        url: 'https://cors-anywhere.herokuapp.com/' + 'http://uberpower.ddns.net' + port,
        success: function(result){
            $(imgName).attr("src", "assets/imgs/available.png")
            $(textName).html("Available");
        },   
        error: function(result){
            $(imgName).attr("src", "assets/imgs/notavailable.png");
            $(textName).html("Not Available");
        }
    });
}

function click(buttonID) {
    $('#homeButton').removeClass('active');
    $('#contactButton').removeClass('active');
    $('#aboutButton').removeClass('active');
       
    $(buttonID).addClass('active');
}

