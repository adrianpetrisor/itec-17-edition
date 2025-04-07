$(document).ready(function () {
    $(".create-application-container").css("opacity", "0");
    $(".create-application-title").css("opacity", "0");
    $(".error-container").css("opacity", "0");

    anime({
        targets: '.create-application-container, .create-application-title, .error-container',
        keyframes: [
            {opacity: 0},
            {opacity: 1}
        ],

        duration: 300,
        easing: 'linear'
    });
})

function closeError() {
    anime({
        targets: '.error-container',
        keyframes: [
            {opacity: 1},
            {opacity: 0}
        ],

        duration: 300,
        easing: 'linear',
        complete: function (anim) {
            $(".error-container").remove();
        }
    });
}