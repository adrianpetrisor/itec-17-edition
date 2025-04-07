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