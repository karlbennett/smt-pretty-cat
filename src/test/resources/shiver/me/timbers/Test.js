function test(one) {

    var two = 2;

    return function (three) {

        return one + two + three;
    }
}

var text = test("one")(3.0);

console.log(text);

