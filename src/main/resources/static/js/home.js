$(document).ready(function () {
    $(".navbar").css("opacity", "0");
    $(".footer").css("opacity", "0");
    $(".project-description").css("opacity", "0");
    $(".card-parent").css("opacity", "0");

    anime({
        targets: '.navbar, .footer, .project-description, .card-parent',
        keyframes: [
            {opacity: 0},
            {opacity: 1}
        ],

        duration: 300,
        easing: 'linear'
    });


})

function toggleNavMobile() {
    if($(".navbar-mobile-component").css("display") === "none") {
        $(".navbar-mobile-component").css("display", "flex");
        $(".navbar-mobile-component").css("opacity", "0");
        $("#nav-toggle-button").prop("disabled", true);
        $(".toggle-button").addClass('is-active');

        anime({
            targets: '.navbar-mobile-component',
            keyframes: [
                {opacity: 0},
                {opacity: 1}
            ],

            duration: 300,
            easing: 'linear',

            complete: function (anim) {
                $("#nav-toggle-button").prop("disabled", false);
            }
        });

    }else {
        $("#nav-toggle-button").prop("disabled", true);
        $(".toggle-button").removeClass('is-active');

        anime({
            targets: '.navbar-mobile-component',
            keyframes: [
                {opacity: 1},
                {opacity: 0}
            ],

            duration: 300,
            easing: 'linear',

            complete: function (anim) {
                $(".navbar-mobile-component").css("display", "none");
                $("#nav-toggle-button").prop("disabled", false);
            }
        });
    }
}