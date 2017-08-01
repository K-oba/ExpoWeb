(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('ExposicionDialogController', ExposicionDialogController);

    ExposicionDialogController.$inject = ['SubCategoria','$state','$filter','$timeout', '$scope', '$stateParams', '$q', 'entity', 'Exposicion', 'Distrito', 'Categoria', 'Charla', 'Amenidades', 'Beacon', 'Timeline', 'Click', 'Pregunta', 'Usuario', 'Map'];

    function ExposicionDialogController (SubCategoria,$state,$filter,$timeout, $scope, $stateParams, $q, entity, Exposicion, Distrito, Categoria, Charla, Amenidades, Beacon, Timeline, Click, Pregunta, Usuario, Map) {

        var vm = this;

        vm.typeExpo = "";

        vm.listSubCategory = [];
        vm.checked = false;


        vm.subCategoriaToSave = [];

        vm.exposicion = entity;
        vm.exposicion.fechaInicio = new Date();
        vm.exposicion.fechaFin = new Date();
        vm.currentDate = new Date();
        vm.save = save;
        vm.sendInvite = sendInvite;
        vm.distritos = Distrito.query();
        vm.categorias = Categoria.query();
        vm.charlas = Charla.query();
        vm.amenidades = Amenidades.query();
        vm.beacons = Beacon.query();
        vm.timelines = Timeline.query();

        vm.clicks = Click.query({filter: 'exposicion-is-null'});
        $q.all([vm.exposicion.$promise, vm.clicks.$promise]).then(function() {
            if (!vm.exposicion.clickId) {
                return $q.reject();
            }
            return Click.get({id : vm.exposicion.clickId}).$promise;
        }).then(function(click) {
            vm.clicks.push(click);
        });
        vm.preguntas = Pregunta.query();
        vm.usuarios = Usuario.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });


        vm.addSubCategoria = function(name){
            if(!vm.subCategoriaToSave.includes(name))vm.subCategoriaToSave.push(name);
            vm.checked = true;
            console.log(vm.exposicion.amenidades);
            console.log(vm.amenities);
        }

        vm.saveSubCategory = function(){
           for (var i in vm.subCategoriaToSave) {
                vm.subCategoria = {};
                vm.subCategoria.nombre = vm.subCategoriaToSave[i];
                vm.subCategoria.categoriaId = vm.exposicion.categoriaId;
                SubCategoria.save(vm.subCategoria);
           }
        }

         vm.place = {};

            vm.search = function() {
                vm.apiError = false;
                Map.search(vm.searchPlace)
                .then(
                    function(res) { // success
                        Map.addMarker(res);
                        vm.exposicion.coordenadas = "Lat " + res.geometry.location.lat() + " lng " + res.geometry.location.lng();
                    },
                    function(status) { // error
                        vm.apiError = true;
                        vm.apiStatus = status;
                    }
                );
            }

            $timeout(function(){ vm.init(); }, 1000);

            vm.init = function(){
                Map.init();
            }

        function save () {
            vm.isSaving = true;
            vm.exposicion.usuarioId = 5 // FUCKING USER EN SESION PERO NO HAY SESION LOL
            vm.exposicion.fechaInicio = $filter('date')(vm.exposicion.fechaInicio, "dd-MM-yyyy");
            vm.exposicion.fechaFin = $filter('date')(vm.exposicion.fechaFin, "dd-MM-yyyy");
            if (vm.exposicion.id !== null) {
                Exposicion.update(vm.exposicion, onSaveSuccess, onSaveError);
            } else {
                vm.saveSubCategory();
                Exposicion.save(vm.exposicion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
//            $scope.$emit('expoCrApp:exposicionUpdate', result);
            $state.go('exposicion');

            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function sendInvite () {
            vm.isSaving = true;
            if (vm.exposicion.id !== null) {
                Exposicion.update(vm.exposicion, onSaveSuccess, onSaveError);
            } else {
                Exposicion.save(vm.exposicion, onSaveSuccess, onSaveError);
            }
        }

        function onSendInviteSuccess (result) {
            $scope.$emit('expoCrApp:exposicionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSendInviteErrorError () {
            vm.isSaving = false;
        }


    }
})();
