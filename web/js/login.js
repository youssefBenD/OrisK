var myApp = angular.module('myApp', []);
myApp.controller('loginCtrl', function($scope, $http) {
     $scope.login = function() {
         var $ = jQuery.noConflict();
 
            // Disable async
            $.ajaxSetup( { async: false } );
 
 
            // Performing login with username2 and passwordForUser2
            $.ajax( {
                cache: false,
                crossDomain: true,
                headers: {
                    "service_key": "3b91cab8-926f-49b6-ba00-920bcf934c2a"
                },
                dataType: "json",
                url: "http://localhost:8080/0risk/webresources/security/login/",
                type: "POST",
                data: {
                    "username": $scope.email,
                    "password": $scope.password
                },
                success: function( jsonObj, textStatus, xhr ) {
                    var result = jsonObj.auth_token.split("/");
                    console.log(result[2]);
                    sessionStorage.userId = result[0];
                    sessionStorage.auth_token = result[1];
                    sessionStorage.role = result[2];
                    switch (result[2]) {
                        case "agent":
                             window.location = "indexAgent.html"
                            break;
                        case "filiale":
                            window.location = "indexFiliale.html"
                            break;
                        case "siege_social":
                            window.location = "indexFiliale.html"
                            break;
                    }
 
                },
                error: function( xhr, textStatus, errorThrown ) {
                    alert("Email ou Password incorrect(s)");
                    console.log( "HTTP Status: " + xhr.status );
                    console.log( "Error textStatus: " + textStatus );
                    console.log( "Error thrown: " + errorThrown );
                }
            } );
 
        
     }
    
});

