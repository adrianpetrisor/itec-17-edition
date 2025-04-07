$(document).ready(function () {
    $(".header").css("opacity", "0");
    $(".application-container").css("opacity", "0");

    anime({
        targets: '.header, .application-container',
        keyframes: [
            {opacity: 0},
            {opacity: 1}
        ],

        duration: 300,
        easing: 'linear'
    });
})

function closeNotification() {
    anime({
        targets: '.notification-container',
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