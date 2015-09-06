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
    .when('/clients', {
        templateUrl: 'pages/clients.html',
        controller: 'clientsController'
    })

    .when('/abonnement', {
        templateUrl: 'pages/abonnement.html',
        controller: 'abonnementController'
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
            if (role == "siege_social") {
                var entreprise = jQuery.parseJSON(JSON.stringify(response));
                $scope.filiale = entreprise.siegeSocialCollection;
                $scope.role = role;
            }
        }).
    error(function(data, status, headers, config) {});
});

scotchApp.controller('signalerImpayeController', function($scope, $http) {
    $scope.submit = function() {
        var currentDate = new Date();
        var day = currentDate.getDate();
        var month = currentDate.getMonth() + 1;
        var year = currentDate.getFullYear();
        console.log($scope.id);
        var impaye = {
            montant: $scope.montant,
            dateAjout: currentDate,
            datePaiement: currentDate,
            clientId: $scope.id
        }
        $http({
            url: 'http://localhost:8080/0risk/webresources/client/ajouterImpaye',
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            data: impaye
        }).success(function(response) {
            alert("signalement effectue avec succes ")
        }).error(function(data, status, headers, config) {
            alert("Erreur: " + status);
        });
        $scope.id = "";
        $scope.montant = "";
    };

});

scotchApp.controller('clientsController', function($scope, $http) {
    var dateFormat = function() {
        var token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g,
            timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
            timezoneClip = /[^-+\dA-Z]/g,
            pad = function(val, len) {
                val = String(val);
                len = len || 2;
                while (val.length < len)
                    val = "0" + val;
                return val;
            };

        // Regexes and supporting functions are cached through closure
        return function(date, mask, utc) {
            var dF = dateFormat;

            // You can't provide utc if you skip other args (use the "UTC:" mask prefix)
            if (arguments.length == 1 && Object.prototype.toString.call(date) == "[object String]" && !/\d/.test(date)) {
                mask = date;
                date = undefined;
            }

            // Passing date through Date applies Date.parse, if necessary
            date = date ? new Date(date) : new Date;
            if (isNaN(date))
                throw SyntaxError("invalid date");

            mask = String(dF.masks[mask] || mask || dF.masks["default"]);

            // Allow setting the utc argument via the mask
            if (mask.slice(0, 4) == "UTC:") {
                mask = mask.slice(4);
                utc = true;
            }

            var _ = utc ? "getUTC" : "get",
                d = date[_ + "Date"](),
                D = date[_ + "Day"](),
                m = date[_ + "Month"](),
                y = date[_ + "FullYear"](),
                H = date[_ + "Hours"](),
                M = date[_ + "Minutes"](),
                s = date[_ + "Seconds"](),
                L = date[_ + "Milliseconds"](),
                o = utc ? 0 : date.getTimezoneOffset(),
                flags = {
                    d: d,
                    dd: pad(d),
                    ddd: dF.i18n.dayNames[D],
                    dddd: dF.i18n.dayNames[D + 7],
                    m: m + 1,
                    mm: pad(m + 1),
                    mmm: dF.i18n.monthNames[m],
                    mmmm: dF.i18n.monthNames[m + 12],
                    yy: String(y).slice(2),
                    yyyy: y,
                    h: H % 12 || 12,
                    hh: pad(H % 12 || 12),
                    H: H,
                    HH: pad(H),
                    M: M,
                    MM: pad(M),
                    s: s,
                    ss: pad(s),
                    l: pad(L, 3),
                    L: pad(L > 99 ? Math.round(L / 10) : L),
                    t: H < 12 ? "a" : "p",
                    tt: H < 12 ? "am" : "pm",
                    T: H < 12 ? "A" : "P",
                    TT: H < 12 ? "AM" : "PM",
                    Z: utc ? "UTC" : (String(date).match(timezone) || [""]).pop().replace(timezoneClip, ""),
                    o: (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
                    S: ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]
                };

            return mask.replace(token, function($0) {
                return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
            });
        };
    }();

    // Some common format strings
    dateFormat.masks = {
        "default": "ddd mmm dd yyyy HH:MM:ss",
        shortDate: "m/d/yy",
        mediumDate: "mmm d, yyyy",
        longDate: "mmmm d, yyyy",
        fullDate: "dddd, mmmm d, yyyy",
        shortTime: "h:MM TT",
        mediumTime: "h:MM:ss TT",
        longTime: "h:MM:ss TT Z",
        isoDate: "yyyy-mm-dd",
        isoTime: "HH:MM:ss",
        isoDateTime: "yyyy-mm-dd'T'HH:MM:ss",
        isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
    };

    // Internationalization strings
    dateFormat.i18n = {
        dayNames: [
            "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
            "Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"
        ],
        monthNames: [
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
            "Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"
        ]
    };

    // For convenience...
    Date.prototype.format = function(mask, utc) {
        return dateFormat(this, mask, utc);
    };

    $http({
        method: 'get',
        url: "http://localhost:8080/0risk/webresources/client/findAll",
        params: {
            date: $scope.currentDate
        },
        headers: {
            'Cache-Control': 'no-cache',
            'Pragma': 'no-cache'
        }
    }).success(function(response) {
        var clients = jQuery.parseJSON(JSON.stringify(response));
        for (var i = 0; i < clients.length; i++) {
            if (typeof clients[i].impayeCollection[0] != 'undefined') {
                var dateAjout = clients[i].impayeCollection[0].dateAjout;
                newDateAjout = new Date(dateAjout);
                clients[i].impayeCollection[0].dateAjout = newDateAjout;
                clients[i].impayeCollection[0].datePaiement = newDateAjout;
            };
        };
        $scope.clients = clients;
    }).error(function(data, status, headers, config) {
        alert("Connexion Failed");
    });



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
                            console.log("*****"+response);
                        }).
                    error(function(data, status, headers, config) {
                        console.log("Erreur Connexion")
                    });

                    function formattedDate(date) {
                        var d = new Date(date),
                            month = '' + (d.getMonth() + 1),
                            day = '' + d.getDate(),
                            year = d.getFullYear();

                        if (month.length < 2) month = '0' + month;
                        if (day.length < 2) day = '0' + day;

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