app.controller("LoginCtrl", function ($scope, $http, $cookies, $location, $routeParams) {

    document.getElementById('homepage').style.visibility = 'hidden';

    $scope.login = function () {
        $http.post('authentication', {username: $scope.username, password: $scope.password})
            .success(function (response) {
                    if (response) {
                        username = $scope.username;
                        $location.path('/plot/'+$scope.username)
                    }
                }
            )
    };

    $scope.register = function () {
        // $http.put('authentication', {username: $scope.username, password: $scope.password})
        //     .success(function (response) {
        //             $scope.log_resp = response;
        //             window.location.href="dimapidor";
        //         }
        //     )
    };

    // $scope.setToken = function () {
    //     $http.post('authentication/getToken', {username: $scope.username})
    //         .success(function (response) {
    //                 var KUKconfig = {
    //                   secure:true
    //                 };
    //                 $cookies.put("mToken", response, KUKconfig)
    //             }
    //         );
    // };

    $http.get('authentication')
        .success(function (data) {
                $scope.log_resp = data;
            if (data) {
                $location.path('/plot/'+$cookies.get("username"));
            }
            }
        );

    $scope.entCheck = function (keyEvent) {
        if (keyEvent.which === 13)
            $scope.login();
    };

    $scope.nextField = function (keyEvent) {
        if (keyEvent.which === 13) {
            document.querySelector('#password').focus();
        }
    }

});
