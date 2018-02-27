app.controller("PlotCtrl", function ($scope, $http, $cookies, $routeParams, $location) {

    document.getElementById('homepage').style.visibility = 'visible';

    var Width = 400;
    var Height = 400;

    $scope.data = {
        xValue: '0',
        rValue: '1',
        yValue: 0,
        rScale: 50
    };

    $scope.getPoint = function () {
        var canvas = document.getElementById("graph");
        canvas.onclick = function (event) {
            $scope.data.xValue = event.clientX - canvas.getBoundingClientRect().left;
            $scope.data.yValue = event.clientY - canvas.getBoundingClientRect().top;
            $scope.data.xValue = (($scope.data.xValue - Width/2) /  $scope.data.rScale);
            $scope.data.yValue = (Height/2 - $scope.data.yValue) / $scope.data.rScale;


            $scope.addDot();
        }
    };

    $http.get('plot/' + $routeParams.username)
        .then(function (success) {
                $scope.dotCollection = success;
                $scope.drawPoints();
                $scope.getPoint();
            }
            ,function (error) {
            $location.path('/authentication');
            }
        );

    $scope.redraw = function () {
        var graph = document.getElementById('graph'),
            ctx = graph.getContext('2d'),
            r = $scope.data.rValue,
            rScale = $scope.data.rScale;
        graph.height = Height;
        graph.width = Width;
        ctx.fillStyle = "white";

        if (r < 0)
            r = 0;

        ctx.fillRect(0, 0, Width, Height);
        for (var i = rScale; i < Width / 2; i += rScale) {
            ctx.beginPath();
            ctx.moveTo(Width / 2 + i, 0);
            ctx.lineTo(Width / 2 + i, Height);
            ctx.stroke();
            ctx.closePath();
            ctx.beginPath();
            ctx.moveTo(0, Height / 2 + i);
            ctx.lineTo(Width, Height / 2 + i);
            ctx.stroke();
            ctx.closePath();
            ctx.beginPath();
            ctx.moveTo(0, Height / 2 - i);
            ctx.lineTo(Width, Height / 2 - i);
            ctx.stroke();
            ctx.closePath();
            ctx.beginPath();
            ctx.moveTo(Width / 2 - i, 0);
            ctx.lineTo(Width / 2 - i, Height);
            ctx.stroke();
            ctx.closePath();
        }
        ctx.fillStyle = "blue";
        ctx.beginPath();
        ctx.lineWidth = 3;
        ctx.moveTo(graph.width / 2, 0);
        ctx.lineTo(graph.width / 2, graph.height);
        ctx.moveTo(0, graph.height / 2);
        ctx.lineTo(graph.width, graph.height / 2);
        ctx.stroke();
        ctx.closePath();
        ctx.strokeStyle = "blue";
        //square
        ctx.strokeRect(graph.width / 2, graph.height / 2 - r * rScale, r * rScale, r * rScale);
        //triangle
        ctx.beginPath();
        ctx.moveTo(graph.width / 2, graph.height / 2);
        ctx.lineTo(graph.width / 2 - r * rScale, graph.height / 2);
        ctx.lineTo(graph.width / 2, graph.height / 2 + r * rScale);
        ctx.closePath();
        ctx.stroke();
        ctx.beginPath();
        //circle

        ctx.arc(graph.width / 2, graph.height / 2, r * rScale / 2, 0, Math.PI / 2, false);
        ctx.stroke();
        ctx.closePath();
    };

    $scope.drawPoints = function () {
        $scope.redraw();
        var res_table = document.getElementById("resTable");
        for (var i = 0; i < $scope.dotCollection.length; i++) {
            $scope.drawPoint($scope.dotCollection[i].xValue, $scope.dotCollection[i].yValue);
        }
    };

    $scope.pointResult = function (x, y, r) {
        r = parseInt(r);
        return (y >= 0 && x >= 0 && y <= r && x <= r) || (x <= 0 && y <= 0 && x >= -r && y >= -(x + r)) || (x >= 0 && x <= r / 2 && y <= 0 && y >= -r / 2 && (x * x + y * y) < r * r / 4);
    };

    $scope.drawPoint = function (x, y) {
        var canvas = document.getElementById("graph");
        var ctx = canvas.getContext("2d");
        var pi = Math.PI;
        ctx.beginPath();
        if ($scope.pointResult(x, y, parseInt($scope.data.rValue))) {
            ctx.fillStyle = "green";
            ctx.strokeStyle = "green";
        }
        else {
            ctx.fillStyle = "red";
            ctx.strokeStyle = "red";
        }
        x = x * $scope.data.rScale + Width / 2;
        y = Height / 2 - y * $scope.data.rScale;
        ctx.arc(x, y, 3, 0, 2 * pi);
        ctx.stroke();
        ctx.fill();
        ctx.closePath();

    };


    $scope.addDot = function () {
        var r = $scope.data.rValue;
        if (r < 0)
            r = 0;

        $http.post('plot/' + $routeParams.username, {
            xValue: $scope.data.xValue,
            yValue: $scope.data.yValue,
            rValue: r
        })
            .success(function (data) {
                    $scope.dotCollection = data;
                    $scope.drawPoints();
                }
            )
    };

    $scope.tokenKUK = $cookies.get("mToken");

    $scope.usr = $routeParams.username;
});


// module.factory('Dot', function ($resource) {
//     return $resource(':username/plot', {username: '@username'});
// })
//     .controller('PlotController', function($scope, Dot))