document.addEventListener('DOMContentLoaded', function() {

    // загрузка таблицы
    sendData('server/load.php')
    // выравнивание кнопки clear
    document.getElementById('clear').style.width=$('#send').innerWidth()+'px'

    // дабавление класса selected элементу input[type=button] при нажатии
    doActionWithChildren('buttons', elem => {
        elem.onclick = function () {
            removeClassesIfExist('selected')
            elem.className = 'selected'
        }}, elem => elem.compareTagName('INPUT')
    )

    // обработка нажатия на кнопку send в форме
    document.getElementById('send').onclick = function () {
        let data = {
            x: getXCoordinate(),
            y: getYCoordinate(),
            r: getRadius()
        }

        sendData('server/main.php', data)
    }

    // обработка нажатия на кнопку clear в форме
    document.getElementById('clear').onclick = function () {
        sendData('server/clear.php', {})
    }
})

let dataReady = false

Element.prototype.getFirstChild = function () {
    return this.childNodes.item(0)
}

Element.prototype.compareTagName = function (tagName) {
    return this.tagName === tagName
}

function sendData(serverUrl, data={}){
    if (dataReady) $.get(serverUrl + compileDataForMethodGet(data), null, function(receivedData) {
        $("#results").html(receivedData)
        console.log("Load was performed.")
    })
}

function getXCoordinate() {
    dataReady = false
    const buttons = document.getElementsByClassName('selected')
    if (!buttons.length) {
        alert('Select "x" coordinate')
        return
    }
    dataReady = true
    return buttons[0].value
}

function getYCoordinate() {
    if (!dataReady) return
    dataReady = false
    let yCoordinate = document.getElementById('y').value
    if (yCoordinate === '') {
        alert('Enter "y" coordinate')
        return
    } else if (parseFloat(yCoordinate) === NaN) {
        alert('"y" must be number')
        return
    } else {
        let numberYCoordinate = parseFloat(yCoordinate)
        if (-5 > numberYCoordinate || numberYCoordinate > 3) {
            alert('"y" must be in range from -5 to 3')
            return
        }
    }
    dataReady = true
    return yCoordinate
}

function getRadius() {
    if (!dataReady) return
    dataReady = false
    const checkboxes = []

    doActionWithChildren('checkboxes', elem => checkboxes.push(elem.getFirstChild()),
            elem => elem.compareTagName('LABEL') && elem.getFirstChild().checked)

    if (!checkboxes.length) {
        alert('Select radius "R"')
        return
    } else if (checkboxes.length > 1) {
        alert('Select only one radius "R"')
        return
    }
    dataReady = true
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