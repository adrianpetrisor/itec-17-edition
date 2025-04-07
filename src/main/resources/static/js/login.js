$(document).ready(function () {
    $(".login-title").css("opacity", "0");
    $(".login-parent").css("opacity", "0");
    $(".bottom-class").css("opacity", "0");

    $(".back-title").css("opacity", "0");
    $(".back").css("opacity", "0");


    anime({
        targets: '.login-title, .login-parent, .bottom-class, .card-parent, .back-title, .back',
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