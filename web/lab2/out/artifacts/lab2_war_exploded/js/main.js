document.addEventListener('DOMContentLoaded', function() {
    // обработка нажатия на изображение
    const coordinatePlane = document.getElementById('area')
    const radiusPx = 116.67

    coordinatePlane.onclick = function (event) {
        const radius = getRadius()
        let data = {
            x: (event.offsetX - coordinatePlane.width / 2)  / radiusPx * radius,
            y: -(event.offsetY - coordinatePlane.height / 2)  / radiusPx * radius,
            r: radius,
            pointX: event.offsetX + coordinatePlane.offsetLeft - 2.5,
            pointY: event.offsetY + coordinatePlane.offsetTop - 2.5
        }

        sendData('controller', data, rReady)
    }

    // обработка нажатия на кнопку send в форме
    document.getElementById('send').onclick = function () {
        const x = getXCoordinate()
        const y = getYCoordinate()
        const radius = getRadius()
        let data = {
            x: x,
            y: y,
            r: radius,
            pointX: x / radius * radiusPx + coordinatePlane.width / 2 + coordinatePlane.offsetLeft - 2.5,
            pointY: -y / radius * radiusPx + coordinatePlane.height / 2 + coordinatePlane.offsetTop - 2.5
        }

        sendData('controller', data)

        console.log(data, coordinatePlane.width, coordinatePlane.height, coordinatePlane.offsetLeft, coordinatePlane.offsetTop)
    }
})

let xReady = false
let yReady = false
let rReady = false

function sendData(serverUrl, data={}, force = false){
    if (!validateX(data.x) || !validateY(data.y) || !validateR(data.r)) return
    if ((xReady && yReady && rReady) || force) $.post(serverUrl, data, function() {
        console.log("Load was performed.")
        window.location.href = 'result.jsp'
    })
}

function getXCoordinate() {
    xReady = false
    const x = $("input[name='x']:checked").val()
    if (x === undefined) {
        alert('Select "x" coordinate')
        return
    }
    xReady = true
    return x
}

function getYCoordinate() {
    yReady = false
    let yCoordinate = document.getElementById('y').value
    if (yCoordinate === '') {
        alert('Enter "y" coordinate')
        return
    } else if (isNaN(parseFloat(yCoordinate))) {
        alert('"y" must be number')
        return
    }
    yReady = true
    return yCoordinate
}

function getRadius() {
    rReady = true
    return parseInt($("#radius").children("option:selected").val())
}

function validateX(x) {
    x = parseFloat(x)
    if (-5 > x || x > 3) {
        alert('"x" must be in range from -5 to 3')
        return false
    }
    return true
}

function validateY(y) {
    y = parseFloat(y)
    if (-5 > y || y > 5) {
        alert('"y" must be in range from -5 to 5')
        return false
    }
    return true
}

function validateR(r) {
    r = parseFloat(r)
    if (1 > r || r > 5) {
        alert('"y" must be in range from 1 to 5')
        return false
    }
    return true
}