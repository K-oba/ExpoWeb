(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('ExposicionDetailController', ExposicionDetailController);

    ExposicionDetailController.$inject = ['Map','$timeout','$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Exposicion', 'Distrito', 'Categoria', 'Charla', 'Amenidades', 'Beacon', 'Timeline', 'Click', 'Pregunta', 'Usuario', 'SubCategoria'];

    function ExposicionDetailController(Map, $timeout, $scope, $rootScope, $stateParams, previousState, entity, Exposicion, Distrito, Categoria, Charla, Amenidades, Beacon, Timeline, Click, Pregunta, Usuario,SubCategoria) {
        var vm = this;

        vm.exposicion = entity;
        vm.previousState = previousState.name;
        vm.subCategories = {};
        loadAllByExpo();

        vm.byStands = vm.exposicion.stands[0] != null ? true : false;
        vm.byCategories = vm.exposicion.categoriaId != null ? true : false;
        vm.byAmenities = vm.exposicion.amenidades[0] != null ? true : false;

        console.log(vm.byAmenities);
        function loadAllByExpo(){
                         vm.subCategories = SubCategoria.queryByExpo({expoId:vm.exposicion.id});
        }
        console.log(vm.exposicion);

        function GetAddress() {
            var str = vm.exposicion.coordenadas
            var lat =  str.substring(str.lastIndexOf("t")+1,str.lastIndexOf(" l"));
            var lng = str.substring(str.lastIndexOf("g")+1);
            var latlng = new google.maps.LatLng(lat, lng);
            var geocoder = geocoder = new google.maps.Geocoder();
            geocoder.geocode({ 'latLng': latlng }, function (results, status) {
               if (status == google.maps.GeocoderStatus.OK) {
                     if (results[1]) {
                        Map.addMarkerByLatLng(latlng);
                        Map.infoPosition(results[1].formatted_address);
                     } else {
                        vm.expoUbication = 'No results found';
                     }
               }
            });
        }

        $timeout(function(){ vm.init(); }, 1000);

        vm.init = function(){
            Map.init();
         }
        $timeout(function(){ GetAddress(); }, 1000);

        var unsubscribe = $rootScope.$on('expoCrApp:exposicionUpdate', function(event, result) {
            vm.exposicion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
