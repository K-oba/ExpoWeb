(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('StandDetailController', StandDetailController);

    StandDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Stand', 'Usuario', 'Brouchure', 'Click', 'Beacon', 'Exposicion'];

    function StandDetailController($scope, $rootScope, $stateParams, previousState, entity, Stand, Usuario, Brouchure, Click, Beacon, Exposicion) {
        var vm = this;

        vm.stand = entity;
        vm.brouchure ={};
             Brouchure.queryById({id:vm.stand.brouchureId}, function(response){
               vm.brouchure = response;
               loadData();
             });

        vm.previousState = previousState.name;
        function loadData(){
        if(vm.brouchure.id !== null){
                localStorage.setItem("url", vm.brouchure.urlimagen);
            }else {
                localStorage.setItem("url", "");
            }
         }

         function loadImage(){
            var name = localStorage.getItem("url");
            //console.log(name);
            var url = document.getElementById("blah");
            if(name !== ""){
              url.src = name;
            }else {
              url.src= "http://res.cloudinary.com/duxllywl7/image/upload/v1499319117/brochure_tzqddz.png";
            }
         }
         loadImage();

        var unsubscribe = $rootScope.$on('expoCrApp:standUpdate', function(event, result) {
            vm.stand = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
