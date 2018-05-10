const carousel = $(".carousel")
    .on("slid.bs.carousel", function (event) {
        APP.questionNumber = event.to;
        console.log(APP);
    })
    .carousel();

$(".contestAnswerBtn").click(function () {
    console.log("answer=" + this.value);
    fetch("/contest/" + APP.contestId + "/question/" + APP.questionNumber, {
        method: "POST",
        headers: {
            "content-type": "application/json"
        },
        body: JSON.stringify({
            value: this.value
        })
    }).then(res => carousel.carousel("next"));
});
