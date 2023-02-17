document.addEventListener('DOMContentLoaded', function() {

    //разделить весь код на функции
    let xCoordinate
    const y = document.getElementById('y')

    //сделать список и цикло по списку
    //или как нибуть красиво оформить
    //js как можно получить элементы когда много id-шников
    //погуглить
    //что то про мердж реквест или чтото такое сказал мартин райла
    setXCoordinatesWhenClick("x-5", -5)
    setXCoordinatesWhenClick('x-4', -4)
    setXCoordinatesWhenClick('x-3', -3)
    setXCoordinatesWhenClick('x-2', -2)
    setXCoordinatesWhenClick('x-1', -1)
    setXCoordinatesWhenClick('x0', 0)
    setXCoordinatesWhenClick('x1', 1)
    setXCoordinatesWhenClick('x2', 2)
    setXCoordinatesWhenClick('x3', 3)




    document.getElementById('send').onclick = function () {
        //сделать список и цикло по списку
        //или как нибуть красиво оформить
        const r1 = document.getElementById('r1')
        const r2 = document.getElementById('r2')
        const r3 = document.getElementById('r3')
        const r4 = document.getElementById('r4')
        const r5 = document.getElementById('r5')

        let yCoordinate = y.value
        let radius

        if (!xCoordinate) {
            alert('Select "x" coordinate')
            return
        }

        //перенести валидацию
        if (yCoordinate === '') {
            alert('Enter "y" coordinate')
            return
        } else if (!Number.isInteger(Number(yCoordinate))) {
            alert('"y" must be integer')
            return
        } else {
            yCoordinate = parseInt(yCoordinate)
            if (-5 > yCoordinate || yCoordinate > 3) {
                alert('"y" must be in range from -5 to 3')
                return
            }
        }

        let countOfRadius = 0
        if (r1.checked) {
            radius = r1.value
            countOfRadius++
        }
        if (r2.checked) {
            radius = r2.value
            countOfRadius++
        }
        if (r3.checked) {
            radius = r3.value
            countOfRadius++
        }
        if (r4.checked) {
            radius = r4.value
            countOfRadius++
        }
        if (r5.checked) {
            radius = r5.value
            countOfRadius++
        }

        if (countOfRadius === 0) {
            alert('Select radius "R"')
            return
        } else if (countOfRadius > 1) {
            alert('Select only one radius "R"')
            return
        }

        let sendData = 'x=' + encodeURIComponent(xCoordinate) + '&y=' + encodeURIComponent(yCoordinate) + '&r=' +
            encodeURIComponent(radius)

        // const request = new XMLHttpRequest()
        // request.open('GET', "main.php?" + sendData)
        // request.send()
        // console.log(request.response.length)
        // document.getElementById('results').append(request.response)


        //перенести
        $.get("main.php?" + sendData, function(data) {
            $("#results").html(data)
            console.log("Load was performed.")
        })

    }

    function setXCoordinatesWhenClick(elementId, newXCoordinate) {
        document.getElementById(elementId).onclick = function() {
            const elements = document.getElementsByClassName('select')
            if (elements.length !== 0) elements.item(0).classList.remove('select')
            xCoordinate = newXCoordinate;
            this.className = 'select'
        }
    }

})