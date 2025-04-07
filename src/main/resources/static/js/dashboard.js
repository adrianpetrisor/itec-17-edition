$(document).ready(function () {
    $(".header-components").css("opacity", "0");
    $(".applications-components").css("opacity", "0");
    $(".footer").css("opacity", "0");

    anime({
        targets: '.header-components, .applications-components, .footer',
        keyframes: [
            {opacity: 0},
            {opacity: 1}
        ],

        duration: 300,
        easing: 'linear'
    });


})