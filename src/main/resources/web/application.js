const questionCount = $("#contestQuestionCount");
const carousel = $(".carousel")
    .on("slid.bs.carousel", function (event) {
        APP.questionNumber = event.to;
        questionCount.html((APP.questionNumber + 1) + "/10");
    })
    .carousel();

$("#analogBtn,#digitalBtn").click(function () {
    fetch("/contest/" + APP.contestId + "/question/" + APP.questionNumber, {
        method: "POST",
        headers: {
            "content-type": "application/json"
        },
        body: JSON.stringify({
            value: this.value
        })
    })
    .then(res => res.json())
    .then(res => {
        switch (res.status) {
            case "hasNext":
                carousel.carousel("next");
                break;
            case "ended":
                window.location.href = window.location.origin + "/contest/" + APP.contestId;
                break;
        }
    });
});
