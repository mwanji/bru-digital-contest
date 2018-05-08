const carousel = $(".carousel")
    .on("slid.bs.carousel", function (event) {
        APP.questionNumber = event.to;
        console.log(APP);
    })
    .carousel();

$(".contestAnswerBtn").click(function () {
    carousel.carousel("next");
    console.log("id=" + this.id);
    /*
    $.post("/contest/" + APP.id + "/question/NUMBER"), {
        type: "json",
        data: "digital"
    }
    */
});
