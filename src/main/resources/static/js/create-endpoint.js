$(document).ready(function () {
    $(".login-parent").css("opacity", "0");

    anime({
        targets: '.login-parent',
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