// create the module and name it scotchApp
var scotchApp = angular.module('scotchApp', ['ngRoute']);

// configure our routes
scotchApp.config(function($routeProvider) {
    $routeProvider

    // route for the home page
        .when('/', {
        templateUrl: 'pages/home.html',
        controller: 'mainController'
    })

    // route for the about page
    .when('/impaye', {
        templateUrl: 'pages/impaye.html',
        controller: 'impayeController'
    })

    .when('/abonnement', {
        templateUrl: 'pages/abonnement.html',
        controller: 'abonnementController'
    })

    .when('/estimationRisque', {
        templateUrl: 'pages/estimationRisque.html',
        controller: 'estimationRisqueController'
    })

    .when('/deconnexion', {
        templateUrl: 'index.html',
        controller: 'logoutController'
    })

    .when('/entreprises', {
        templateUrl: 'pages/entreprises.html',
        controller: 'entreprisesController'
    })

    .when('/rechercheCiblee', {
        templateUrl: 'pages/rechercheCiblee.html',
        controller: 'rechercheCibleeController'
    })

    .when('/signalerImpaye', {
        templateUrl: 'pages/signalerImpaye.html',
        controller: 'signalerImpayeController'
    })

    // route for the contact page
    .when('/contact', {
        templateUrl: 'pages/contact.html',
        controller: 'contactController'
    });
});

// create the controller and inject Angular's $scope
scotchApp.controller('mainController', function($scope, $http) {
    // create a message to display in our view
    var userId = sessionStorage.getItem("userId");
    var role = sessionStorage.getItem("role");
    $http.get("http://localhost:8080/0risk/webresources/filiale/findEntrprise/" + userId, {
            cache: false,
            crossDomain: true,
            headers: {
                "service_key": "3b91cab8-926f-49b6-ba00-920bcf934c2a",
                "auth_token": sessionStorage.auth_token
            },
            dataType: "json"
        })
        .success(function(response) {
            $scope.entreprise = response;
            sessionStorage.entrepriseId = response.entrepriseId;
            if (role == "siege_social") {
                var entreprise = jQuery.parseJSON(JSON.stringify(response));
                $scope.filiale = entreprise.siegeSocialCollection;
                $scope.role = role;
            }
            switch (role) {
                case "siege_social":
                    var entreprise = jQuery.parseJSON(JSON.stringify(response));
                    $scope.filiale = entreprise.siegeSocialCollection;
                    $scope.role = role;
                    break;
                case "filiale":
                    var entreprise = jQuery.parseJSON(JSON.stringify(response));
                    $scope.filiale = entreprise.siegeSocialCollection;
                    $scope.role = role;
                    break;

            }
        })
        .error(function(data, status, headers, config) {});
});

scotchApp.controller('signalerImpayeController', function($scope, $http) {


    $scope.typeImpaye = function() {
        if ($scope.typeClient == "Personne Morale") {
            $scope.labelId = "Matricule Fiscale";
            $scope.type = "Personne Morale";
        } else {
            $scope.labelId = "CIN / Passeport";
            $scope.type = "Personne Physique";
        }
        $("#typeFormulaire").hide();
        $("#suiteFormulaire").show();
    };

    $scope.demanderPassword = function() {
        $("#suiteFormulaire").hide();
        $("#password").show();
    };

    $scope.annuler = function() {
        $("#suiteFormulaire").hide();
        $("#password").hide();
        $("#typeFormulaire").show();
    };

    function formattedDate(date) {
        var d = new Date(date),
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

        if (month.length < 2)
            month = '0' + month;
        if (day.length < 2)
            day = '0' + day;

        return [year, month, day].join('-');
    }

    $scope.signalerImpaye = function() {
        $("#succes").hide();
        $("#echec").hide();
        $("#codeIncorrect").hide();
        var impaye = {
            montant: $scope.montant,
            datePaiement: "2001-01-01",
            clientId: $scope.id,
            dateEcheance: formattedDate($scope.dateEcheance),
            type: $scope.typeImpaye,
            filialeId: sessionStorage.getItem("userId"),
            typeClient: $scope.type,
            code: $scope.code,
            entrepriseId: sessionStorage.getItem("entrepriseId")
        }
        console.log(JSON.stringify(impaye));
        $http({
                url: "http://localhost:8080/0risk/webresources/impaye/ajout",
                method: "POST",
                dataType: 'json',
                headers: {
                    'Content-Type': 'application/json',
                    'service_key': "3b91cab8-926f-49b6-ba00-920bcf934c2a",
                    'auth_token': sessionStorage.auth_token,
                },
                data: JSON.stringify(impaye)
            })
            .success(function(response) {
                if (response === "Code Incorrect") {
                    $("#codeIncorrect").show();
                } else {
                    if (response === "Max Impayes depasse") {
                        $("#maxImpayesDepasse").show();
                    } else {
                        $scope.code = "";
                        $("#password").hide();
                        $("#typeFormulaire").show();
                        $("#succes").show();

                    }

                }

            })
            .error(function(data, status, headers, config, response) {
                console.log(response);
                $("#echec").show();
            });

    };

});

scotchApp.controller('impayeController', function($scope, $http) {
    var userId = sessionStorage.getItem("userId");
    var role = sessionStorage.getItem("role");
    var entrepriseId = sessionStorage.getItem("entrepriseId");
    $http.get("http://localhost:8080/0risk/webresources/entreprise/" + entrepriseId, {
            cache: false,
            crossDomain: true,
            headers: {
                "service_key": "3b91cab8-926f-49b6-ba00-920bcf934c2a",
                "auth_token": sessionStorage.auth_token
            },
            dataType: "json"
        })
        .success(function(response) {
            var filiales = jQuery.parseJSON(JSON.stringify(response.filialeCollection));
            var impayes = []
            for (var i = 0; i < filiales.length; i++) {
                for (var j = 0; j < filiales[i].impayeCollection.length; j++) {
                    impayes.push(filiales[i].impayeCollection[j]);
                }
            }
            var postImpayes = JSON.stringify(impayes);
            $http({
                url: "http://localhost:8080/0risk/webresources/impaye/ajoutInfo",
                method: "PUT",
                contentType: "application/json",
                headers: {
                    "Content-Type": 'application/json',
                    "service_key": "3b91cab8-926f-49b6-ba00-920bcf934c2a",
                    "auth_token": sessionStorage.auth_token,
                },
                data: postImpayes
            }).then(function(response) {
                var impayesTraites = jQuery.parseJSON(JSON.stringify(response.data));
                for (var i = 0; i < impayesTraites.length; i++) {
                    impayesTraites[i].dateAjout = formattedDate(impayesTraites[i].dateAjout);
                    impayesTraites[i].dateEcheance = formattedDate(impayesTraites[i].dateEcheance);
                }
                $scope.impayes = impayesTraites;

            }, function(response) {
                console.log("Echec connexion");
            });


        })
        .error(function(data, status, headers, config) {});

    $scope.historique = function(clientId, filialeId) {
        $("#impayes").hide("slow");
        $("#historique").show();
        $http.get("http://localhost:8080/0risk/webresources/client/" + clientId, {
                cache: false,
                crossDomain: true,
                headers: {
                    "service_key": "3b91cab8-926f-49b6-ba00-920bcf934c2a",
                    "auth_token": sessionStorage.auth_token
                },
                dataType: "json"
            })
            .success(function(response) {
                $scope.client = response;
            })
            .error(function(data, status, headers, config) {
                console.log("Echec connexion");
            });

        $http({
            url: "http://localhost:8080/0risk/webresources/historique/listeHistorique",
            method: "PUT",
            contentType: "application/json",
            headers: {
                "Content-Type": 'application/json',
                "service_key": "3b91cab8-926f-49b6-ba00-920bcf934c2a",
                "auth_token": sessionStorage.auth_token,
            },
            data: {
                'clientId': clientId,
                'filialeId': userId
            }
        }).then(function(response) {
            result = response.data;
            var historiquesNonPayes = [];
            var historiquesPayes = [];
            for (var i = 0; i < result.length; i++) {
                if (formattedDate(result[i][4]) === "01/01/2001") {
                    var historiqueNonPaye = {
                        "montant": result[i][2],
                        "dataAjout": formattedDate(result[i][3]),
                        "datePaiement": formattedDate(result[i][4]),
                        "clientId": result[i][5],
                        "dateEcheance": formattedDate(result[i][6])
                    };
                    historiquesNonPayes.push(historiqueNonPaye);

                } else {
                    var historiquePaye = {
                        "montant": result[i][2],
                        "dataAjout": formattedDate(result[i][3]),
                        "datePaiement": formattedDate(result[i][4]),
                        "clientId": result[i][5],
                        "dateEcheance": formattedDate(result[i][6])
                    };
                    historiquesPayes.push(historiquePaye);
                }

            }
            $scope.historiquesNonPayes = historiquesNonPayes;
            $scope.historiquesPayes = historiquesPayes;
        }, function(response) {
            console.log("Echec connexion");
        });

    }

    function formattedDate(date) {
        var d = new Date(date),
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

        if (month.length < 2)
            month = '0' + month;
        if (day.length < 2)
            day = '0' + day;

        return [day, month, year].join('/');
    }

    $scope.deleteImpaye = function(clientId) {
        $http({
            url: "http://localhost:8080/0risk/webresources/impaye/" + clientId,
            method: "DELETE",
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function(response) {
            alert("Impayé effacé");

        }, function(response) {
            alert("Impayé non effacé");
        });
    }

    $scope.close = function() {
        $("#impayes").show("slow");
        $("#historique").hide();
    }


    $scope.enregistrerModification = function() {
        $scope.impaye.montant = $scope.montant;
        $http({
            url: "http://localhost:8080/0risk/webresources/impaye",
            method: "PUT",
            headers: {
                'Content-Type': 'application/json'
            },
            data: $scope.impaye
        }).then(function(response) {
            console.log($scope.impaye);
            alert("Impayé modifié");

        }, function(response) {
            alert("Impayé non modifié");
        });
    }

    $scope.modifierImpaye = function(impaye, clientId) {
        $scope.clientId = clientId;
        $scope.impaye = impaye;
        console.log($scope.impaye);
        $scope.impaye['clientId'] = clientId;
        $scope.montant = impaye.montant;
    }
});

scotchApp.controller('contactController', function($scope) {
    $scope.message = 'Contact us! JK. This is just a demo.';
});

scotchApp.controller('logoutController', function($scope) {
    $.ajax({
        cache: false,
        crossDomain: true,
        headers: {
            "service_key": "3b91cab8-926f-49b6-ba00-920bcf934c2a",
            "auth_token": sessionStorage.auth_token
        },
        url: "http://localhost:8080/0risk/webresources/security/logout/",
        type: "POST",
        success: function(jsonObj, textStatus, xhr) {
            sessionStorage.clear();
            var href = window.location.href;
            hrefRes = href.split("0risk");
            document.location.href=hrefRes[0]+"/0risk/index.html"; 
            
        },
        error: function(xhr, textStatus, errorThrown) {
            sessionStorage.clear();
            document.location.href=hrefRes[0]+"/0risk/index.html"; 
            console.log("HTTP Status: " + xhr.status);
            console.log("Error textStatus: " + textStatus);
            console.log("Error thrown: " + errorThrown);
        }
    });
});

scotchApp.controller('estimationRisqueController', function($scope, $http) {
    $scope.estimationRisque = function(clientId) {
        $("#pasOk").hide();
        $("#ok").hide();
        $http({
            url: "http://localhost:8080/0risk/webresources/impaye/evaluerRisque",
            method: "PUT",
            contentType: "application/json",
            headers: {
                "Content-Type": 'application/json',
                "service_key": "3b91cab8-926f-49b6-ba00-920bcf934c2a",
                "auth_token": sessionStorage.auth_token,
            },
            data: {
                'clientId': $scope.clientId,
                'filialeId': sessionStorage.getItem("userId")
            }
        }).then(function(response) {
            if (response.data > 0) {
                $("#pasOk").show();
            } else {
                $("#ok").show();
            }

        }, function(response) {
            console.log("Echec connexion");
        });

    }


});

scotchApp.controller('abonnementController', function($scope, $http) {
    var userId = sessionStorage.getItem("userId");
    var role = sessionStorage.getItem("role");
    $http.get("http://localhost:8080/0risk/webresources/filiale/findEntrprise/" + userId, {
            cache: false,
            crossDomain: true,
            headers: {
                "service_key": "3b91cab8-926f-49b6-ba00-920bcf934c2a",
                "auth_token": sessionStorage.auth_token
            },
            dataType: "json"
        })
        .success(function(response) {
            var idEntreprise = response.entrepriseId;
            $scope.nomEntreprise = response.raisonSociale;
            var abonnement = jQuery.parseJSON(JSON.stringify(response.abonnementCollection));
            $http.get("http://localhost:8080/0risk/webresources/pack/" + abonnement[0].abonnementPK.packId, {
                    cache: false,
                    crossDomain: true,
                    headers: {
                        "service_key": "3b91cab8-926f-49b6-ba00-920bcf934c2a",
                        "auth_token": sessionStorage.auth_token
                    },
                    dataType: "json"
                })
                .success(function(response) {
                    abonnement[0].datePaiement = (formattedDate(abonnement[0].datePaiement));
                    abonnement[0].dateExpiration = (formattedDate(abonnement[0].dateExpiration));
                    $scope.pack = response;
                    $scope.abonnement = abonnement[0];
                    $http.get("http://localhost:8080/0risk/webresources/entreprise/nombreFiliale/" + idEntreprise, {
                            cache: false,
                            crossDomain: true,
                            headers: {
                                "service_key": "3b91cab8-926f-49b6-ba00-920bcf934c2a",
                                "auth_token": sessionStorage.auth_token
                            },
                            dataType: "json"
                        })
                        .success(function(response) {
                            $scope.nbrFiliale = response;
                        }).
                    error(function(data, status, headers, config) {
                        console.log("Erreur Connexion")
                    });

                    function formattedDate(date) {
                        var d = new Date(date),
                            month = '' + (d.getMonth() + 1),
                            day = '' + d.getDate(),
                            year = d.getFullYear();

                        if (month.length < 2)
                            month = '0' + month;
                        if (day.length < 2)
                            day = '0' + day;

                        return [day, month, year].join('/');
                    }
                }).
            error(function(data, status, headers, config) {
                console.log("Erreur Connexion")
            });

        }).
    error(function(data, status, headers, config) {
        console.log("Erreur Connexion")
    });
});

scotchApp.controller('entreprisesController', function($scope) {});

scotchApp.controller('rechercheCibleeController', function($scope, $http) {

    $scope.rechercher = function() {
        $http.get("http://localhost:8080/0risk/webresources/client/" + $scope.id, {
                headers: {
                    'Cache-Control': 'no-cache'
                }
            })
            .success(function(response) {
                var impaye = jQuery.parseJSON(JSON.stringify(response));
                $scope.id = "";
                if (impaye.impayeCollection == undefined || impaye.impayeCollection.length == 0) {
                    alert("Solvable");
                } else {
                    alert("Non Solvable");
                }
            }).
        error(function(data, status, headers, config) {
            alert("Connexion Failed");
        });
    }

});