(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('BrouchureDetailController', BrouchureDetailController);

    BrouchureDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Brouchure', 'Stand', 'SubCategoria'];

    function BrouchureDetailController($scope, $rootScope, $stateParams, previousState, entity, Brouchure, Stand, SubCategoria) {
        var vm = this;

        vm.brouchure = entity;
        vm.previousState = previousState.name;
        //var url = document.getElementById('blah');
        //url.src = "http://res.cloudinary.com/duxllywl7/image/upload/v1499220419/";
        function loadData(){
          if(vm.brouchure.id !== null){
            localStorage.setItem("url", vm.brouchure.urlimagen);
          }else {
            localStorage.setItem("url", "");
          }
        }
        loadData();
        var unsubscribe = $rootScope.$on('expoCrApp:brouchureUpdate', function(event, result) {
            vm.brouchure = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
