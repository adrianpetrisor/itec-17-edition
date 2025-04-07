$(document).ready(function () {
    $(".title").css("opacity", "0");
    $(".back").css("opacity", "0");

    anime({
        targets: '.title, .back',
        keyframes: [
            {opacity: 0},
            {opacity: 1}
        ],

        duration: 300,
        easing: 'linear'
    });


})