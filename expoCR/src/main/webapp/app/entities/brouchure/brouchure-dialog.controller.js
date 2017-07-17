(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('BrouchureDialogController', BrouchureDialogController);

    BrouchureDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Brouchure', 'Stand', 'SubCategoria'];

    function BrouchureDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Brouchure, Stand, SubCategoria) {
        var vm = this;

        vm.brouchure = entity;
        vm.clear = clear;
        vm.save = save;
        vm.setUrl = setUrl;
        vm.stands = Stand.query();
        vm.subcategorias = SubCategoria.query();
        vm.url ="";

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            //console.log(vm.brouchure);
            vm.isSaving = true;
            var url = document.getElementById('blah');
            //console.log(url.src);
            //var url = document.getElementById('url').files[0];
            //url.value.replace("C:\\fakepath\\", "");
            //console.log(url.value.replace("C:\\fakepath\\", ""));
              //console.log(vm.brouchure);
            vm.brouchure.urlimagen = url.src;
            //console.log(vm.brouchure);
            if (vm.brouchure.id !== null) {
              url.src = vm.brouchure.id;
                Brouchure.update(vm.brouchure, onSaveSuccess, onSaveError);
            } else {
                Brouchure.save(vm.brouchure, onSaveSuccess, onSaveError);
            }
        }
        function setUrl (url){
          //console.log(url);
        }
        function onSaveSuccess (result) {
            $scope.$emit('expoCrApp:brouchureUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function loadData(){
          if(vm.brouchure.id !== null){
            //var url = document.getElementById("blah");
            //console.log(vm.brouchure);
            localStorage.setItem("url", vm.brouchure.urlimagen);
          }else {
            localStorage.setItem("url", "");
          }
        }
        loadData();
    }
})();
