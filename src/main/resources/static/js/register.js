$(document).ready(function () {
    $(".register-title").css("opacity", "0");
    $(".register-parent").css("opacity", "0");
    $(".bottom-class").css("opacity", "0");

    $(".back-title").css("opacity", "0");
    $(".back").css("opacity", "0");


    anime({
        targets: '.register-title, .register-parent, .bottom-class, .card-parent, .back-title, .back',
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