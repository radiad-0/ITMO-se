document.addEventListener('DOMContentLoaded', function() {

    // // выравнивание кнопки clear
    // document.getElementById('clear').style.width=$('#send').innerWidth()+'px'

    // дабавление класса selected элементу input[type=button] при нажатии
    doActionWithChildren('buttons', elem => {
        elem.onclick = function () {
            removeClassesIfExist('selected')
            elem.className = 'selected'
        }}, elem => elem.tagName === 'INPUT'
    )


    // обработка нажатия на изображение
    const imageContainer = document.querySelector('.img')
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

        sendData('controller', data, true)
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
            pointY: -y / radius * radiusPx + coordinatePlane.width / 2 + coordinatePlane.offsetTop - 2.5
        }

        sendData('controller', data)
    }
})

let xReady = false
let yReady = false
let rReady = false

Element.prototype.getFirstChild = function () {
    return this.childNodes.item(0)
}

function sendData(serverUrl, data={}, force = false){
    if ((xReady && yReady && rReady) || force) $.post(serverUrl, data, function() {
        console.log("Load was performed.")
        window.location.href = 'result.jsp'
    })
}

function getXCoordinate() {
    xReady = false
    const buttons = document.getElementsByClassName('selected')
    if (!buttons.length) {
        alert('Select "x" coordinate')
        return
    }
    xReady = true
    return buttons[0].value
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
    } else {
        let numberYCoordinate = parseFloat(yCoordinate)
        if (-5 > numberYCoordinate || numberYCoordinate > 3) {
            alert('"y" must be in range from -5 to 3')
            return
        }
    }
    yReady = true
    return yCoordinate
}

function getRadius() {
    rReady = false
    const checkboxes = []

    doActionWithChildren('checkboxes', elem => checkboxes.push(elem.getFirstChild()),
            elem => elem.tagName ==='LABEL' && elem.getFirstChild().checked)

    if (!checkboxes.length) {
        alert('Select radius "R"')
        return
    } else if (checkboxes.length > 1) {
        alert('Select only one radius "R"')
        return
    }
    rReady = true
    return checkboxes[0].value
}

function compileDataForMethodGet(data){
    const list = Object.entries(data)
    const result = list.reduce((result, item) => {
        return `${ result }&${ item[0] }=${ encodeURIComponent(item[1]) }`
    }, '?')
    console.log(result)
    return result
}

function removeClassesIfExist(className) {
    const elements = document.getElementsByClassName(className)
    for (let element of elements) {
        element.classList.remove(className)
    }
}

function doActionWithChildren(parentId, action, filter = () => true) {
    document.getElementById(parentId).childNodes.forEach(child => {
        if (filter(child))
            action(child)
    })
}